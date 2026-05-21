package xyz.lumian.novia_additions.block;

import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.lumian.novia_additions.Define;
import xyz.lumian.novia_additions.item.ModItems;

import java.util.function.Function;



//**********************************************************************************************************************
public class ModBlocks
{
    //******************************************************************************************************************
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Define.MOD_ID);
    
    //==================================================================================================================
    // BLOCKS
    public static final DeferredBlock<Block> DEMITHRIL_STATUE = register("demithril_statue", StatueBlock::new);
    
    public static final DeferredBlock<Block> DEEPSLATE_NOVIUM_ORE = register("deepslate_novium_ore",
        (props -> new DropExperienceBlock(ConstantInt.of(0), props
            .sound(SoundType.DEEPSLATE)
            .lightLevel(state -> 4)
            .requiresCorrectToolForDrops()
            .strength(50f, 5000f))),
        (new Item.Properties()).fireResistant());
    public static final DeferredBlock<Block> END_STONE_DEMURIUM_ORE = register("end_stone_demurium_ore",
        (props -> new Block(props
            .lightLevel(state -> 5)
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)
            .strength(3F, 3F))),
        (new Item.Properties()).fireResistant());
    
    public static final DeferredBlock<Block> NOVIUM_PELLET_BLOCK = register("novium_pellet_block",
        (props -> new PelletBlock(props
            .sound(SoundType.ANCIENT_DEBRIS)
            .requiresCorrectToolForDrops()
            .strength(60f, 5000f))),
        (new Item.Properties()).fireResistant());
    
    public static final DeferredBlock<Block> NOVIUM_BLOCK = register("novium_block",
        (props -> new Block(props
            .sound(SoundType.NETHERITE_BLOCK)
            .requiresCorrectToolForDrops()
            .strength(60f, 5000f))),
        (new Item.Properties()).fireResistant());
    public static final DeferredBlock<Block> DEMURIUM_BLOCK = register("demurium_block", (props -> new Block(props
        .sound(SoundType.GLASS)
        .requiresCorrectToolForDrops()
        .strength(1f, 5000f))));
    public static final DeferredBlock<Block> DEMITHRIUM_BLOCK = register("demithrium_block",
        (props -> new Block(props
            .sound(SoundType.NETHERITE_BLOCK)
            .requiresCorrectToolForDrops()
            .strength(70f, 5000f))),
        (new Item.Properties()).fireResistant());
    
    public static final DeferredBlock<Block> GHOST_BLOCK = register("ghost_block", GhostBlock::new);
    
    //******************************************************************************************************************
    public static <T extends Block> DeferredBlock<T> register(
        final String                                 name,
        final Function<BlockBehaviour.Properties, T> func
    )
    {
        final DeferredBlock<T> block = ModBlocks.BLOCKS.registerBlock(name, func);
        ModItems.ITEMS.registerSimpleBlockItem(name, block);
        return block;
    }
    
    public static <T extends Block> DeferredBlock<T> register(
        final String                                 name,
        final Function<BlockBehaviour.Properties, T> func,
        final Item.Properties                        itemProps
        )
    {
        final DeferredBlock<T> block = ModBlocks.BLOCKS.registerBlock(name, func);
        ModItems.ITEMS.registerSimpleBlockItem(name, block, itemProps);
        return block;
    }
}
