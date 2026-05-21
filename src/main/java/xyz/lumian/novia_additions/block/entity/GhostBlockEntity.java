package xyz.lumian.novia_additions.block.entity;

import com.mojang.serialization.DynamicOps;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import xyz.lumian.novia_additions.block.GhostBlock;
import xyz.lumian.novia_additions.block.ModBlockEntities;



//**********************************************************************************************************************
public class GhostBlockEntity
    extends BlockEntity
{
    //******************************************************************************************************************
    public static GhostBlockEntity newGhostBlock(final BlockPos pos, final BlockState state)
    {
        return new GhostBlockEntity(ModBlockEntities.GHOST_BLOCK.get(), pos, state);
    }
    
    //******************************************************************************************************************
    @Getter
    private BlockState copyState = Blocks.AIR.defaultBlockState();
    
    private @Nullable BlockEntity mockBlockEntity = null;
    
    //******************************************************************************************************************
    public GhostBlockEntity(final BlockEntityType<?> type, final BlockPos pos, final BlockState state)
    {
        super(type, pos, state);
    }
    
    //==================================================================================================================
    @Override
    public CompoundTag getUpdateTag(final HolderLookup.Provider registries)
    {
        final CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag, registries);
        return tag;
    }
    
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    
    public @Nullable BlockEntity getMockBlockEntity() { return this.mockBlockEntity; }
    
    //==================================================================================================================
    public boolean isEmpty() { return this.copyState.isAir(); }
    
    //==================================================================================================================
    public void setCopyState(final @Nullable BlockState copyState)
    {
        final BlockState new_state = (copyState != null ? copyState : Blocks.AIR.defaultBlockState());
        
        if (this.copyState != new_state)
        {
            final BlockState old = this.copyState;
            
            this.copyState       = new_state;
            this.mockBlockEntity = null;
            
            this.sendUpdates(old);
        }
    }
    
    //------------------------------------------------------------------------------------------------------------------
    private void sendUpdates(final BlockState previousState)
    {
        this.setChanged();
        assert (level != null);
        
        if (previousState != this.copyState && previousState.isAir() || this.copyState.isAir())
        {
            this.level.setBlock(
                this.getBlockPos(),
                this.getBlockState().setValue(GhostBlock.HAS_MATERIAL, !this.isEmpty()),
                Block.UPDATE_ALL);
        }
        else
        {
            this.level.sendBlockUpdated(
                this.getBlockPos(), this.getBlockState(), this.getBlockState(),
                Block.UPDATE_ALL);
        }
    }
    
    //==================================================================================================================
    @Override
    protected void loadAdditional(final CompoundTag tag, final HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        
        if (tag.contains("CopyState"))
        {
            final DynamicOps<Tag> ops = registries.createSerializationContext(NbtOps.INSTANCE);
            BlockState.CODEC.parse(ops, tag.get("CopyState")).ifSuccess(copy_state -> this.copyState = copy_state);
            
            if (this.level != null && this.level.isClientSide && this.mockBlockEntity == null)
            {
                final BlockState state = this.copyState;
                
                if (state.hasBlockEntity() && (state.getBlock() instanceof EntityBlock eb))
                {
                    this.mockBlockEntity = eb.newBlockEntity(this.getBlockPos(), state);
                    
                    if (this.mockBlockEntity != null)
                    {
                        this.mockBlockEntity.setLevel(this.level);
                    }
                }
            }
            else
            {
                this.mockBlockEntity = null;
            }
        }
    }
    
    @Override
    protected void saveAdditional(final CompoundTag tag, final HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        
        if (!this.copyState.isAir())
        {
            final DynamicOps<Tag> ops = registries.createSerializationContext(NbtOps.INSTANCE);
            BlockState.CODEC.encodeStart(ops, this.copyState).ifSuccess(copy_state -> tag.put("CopyState", copy_state));
        }
    }
}
