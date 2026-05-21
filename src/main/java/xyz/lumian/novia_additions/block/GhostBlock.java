package xyz.lumian.novia_additions.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;
import xyz.lumian.novia_additions.block.entity.GhostBlockEntity;



//**********************************************************************************************************************
public class GhostBlock
    extends TruthSeekerBlockBase
    implements EntityBlock
{
    //******************************************************************************************************************
    public static final BooleanProperty HAS_MATERIAL = BooleanProperty.create("has_material");
    
    public static final MapCodec<GhostBlock> CODEC = Block.simpleCodec(GhostBlock::new);
    
    //******************************************************************************************************************
    public GhostBlock(final Properties properties)
    {
        super(properties
            .noCollission()
            .forceSolidOn()
            .pushReaction(PushReaction.IGNORE)
            .instabreak()
            .sound(SoundType.FROGLIGHT));
        this.registerDefaultState(this.stateDefinition.any()
            .setValue(GhostBlock.HAS_MATERIAL, false)
            .setValue(TruthSeekerBlockBase.TRUTH_SEEKER_MODE, TruthSeekerMode.DEFAULT_VISIBLE));
    }
    
    //==================================================================================================================
    @Override protected RenderShape getRenderShape(final BlockState state) { return RenderShape.ENTITYBLOCK_ANIMATED; }
    
    @Override protected MapCodec<GhostBlock> codec() { return GhostBlock.CODEC; }
    
    //==================================================================================================================
    @Override
    public @Nullable BlockEntity newBlockEntity(final BlockPos blockPos, final BlockState blockState)
    {
        return GhostBlockEntity.newGhostBlock(blockPos, blockState);
    }
    
    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(GhostBlock.HAS_MATERIAL);
    }
    
    //==================================================================================================================
    @Override
    protected ItemInteractionResult useItemOn(final ItemStack stack, final BlockState state, final Level level,
                                              final BlockPos pos, final Player player, final InteractionHand hand,
                                              final BlockHitResult hitResult)
    {
        if (
            !(level.getBlockEntity(pos) instanceof GhostBlockEntity be)
            || !player.isCreative()
            || hand != InteractionHand.MAIN_HAND
        )
        {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }
        
        if (be.isEmpty())
        {
            if (
                player.isCrouching()
                || !(stack.getItem() instanceof BlockItem item)
                || item.getBlock() == this
            )
            {
                return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
            }
            
            final BlockState new_state = item.getBlock()
                .getStateForPlacement(new BlockPlaceContext(player, hand, stack, hitResult));
            
            if (new_state != null && !new_state.isAir())
            {
                if (!level.isClientSide)
                {
                    final SoundType type = new_state.getSoundType(level, pos, player);
                    level.playSound(null, pos, type.getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                
                be.setCopyState(new_state);
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        else if (player.isCrouching() && stack.isEmpty())
        {
            if (!level.isClientSide)
            {
                final BlockState copy_state = be.getCopyState();
                final SoundType  type       = copy_state.getSoundType(level, pos, player);
                level.playSound(null, pos, type.getBreakSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            
            be.setCopyState(null);
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }
}
