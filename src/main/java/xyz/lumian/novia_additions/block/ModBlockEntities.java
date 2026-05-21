package xyz.lumian.novia_additions.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.lumian.novia_additions.Define;
import xyz.lumian.novia_additions.block.entity.GhostBlockEntity;

import java.util.function.Supplier;



//**********************************************************************************************************************
public class ModBlockEntities
{
    //******************************************************************************************************************
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
        .create(Registries.BLOCK_ENTITY_TYPE, Define.MOD_ID);
    
    //==================================================================================================================
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GhostBlockEntity>> GHOST_BLOCK = register(
        "ghost_block",
        (() -> BlockEntityType.Builder.of(GhostBlockEntity::newGhostBlock, ModBlocks.GHOST_BLOCK.get())));
    
    //******************************************************************************************************************
    private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(
        final String                               name,
        final Supplier<BlockEntityType.Builder<T>> builder
    )
    {
        //noinspection DataFlowIssue
        return ModBlockEntities.BLOCK_ENTITIES.register(name, (() -> builder.get().build(null)));
    }
}
