package xyz.lumian.novia_additions.world.generator;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import xyz.lumian.novia_additions.Define;
import xyz.lumian.novia_additions.block.ModBlockTags;
import xyz.lumian.novia_additions.block.ModBlocks;
import xyz.lumian.novia_additions.registry.ModRegistries;

import java.util.List;
import java.util.function.Function;



//**********************************************************************************************************************
public final class ModWeightedHolderLists
{
    //******************************************************************************************************************
    public static final ResourceKey<WeightedHolderList<?>> DEMITHRIL_SHRINE_ORE_DEPOSIT
        = create("shrine_ore_deposit");
    public static final ResourceKey<WeightedHolderList<?>> CATACOMBS_STORAGE_BLOCK_TREASURE
        = create("catacombs_storage_block_treasure");
    
    public static final ResourceKey<WeightedHolderList<?>> DEMITHRIL_SHRINE_LIBRARY_BOOKS
        = create("shrine_library_books");
    
    //******************************************************************************************************************
    public static void bootstrap(final BootstrapContext<WeightedHolderList<?>> context)
    {
        final HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);
        final HolderGetter<Item>  items  = context.lookup(Registries.ITEM);
        
        // Block lists
        {
            final Function<Block, Holder<Block>> wrapper = BuiltInRegistries.BLOCK::wrapAsHolder;
            context.register(ModWeightedHolderLists.DEMITHRIL_SHRINE_ORE_DEPOSIT, new WeightedHolderList<>(
                Registries.BLOCK,
                List.of(blocks.getOrThrow(Tags.Blocks.ORES)),
                List.of(
                    WeightedHolder.create(blocks.getOrThrow(Tags.Blocks.ORES_IN_GROUND_NETHERRACK),  0),
                    WeightedHolder.create(blocks.getOrThrow(ModBlockTags.ORES_IN_GROUND_END_STONE),  0),
                    WeightedHolder.create(ModBlocks.DEEPSLATE_NOVIUM_ORE,                            2),
                    WeightedHolder.create(Blocks.ANCIENT_DEBRIS, wrapper,                            4),
                    WeightedHolder.create(blocks.getOrThrow(BlockTags.DIAMOND_ORES),                17),
                    WeightedHolder.create(blocks.getOrThrow(BlockTags.LAPIS_ORES),                  18),
                    WeightedHolder.create(blocks.getOrThrow(BlockTags.EMERALD_ORES),                29),
                    WeightedHolder.create(blocks.getOrThrow(BlockTags.GOLD_ORES),                   31),
                    WeightedHolder.create(blocks.getOrThrow(BlockTags.COAL_ORES),                   87)
                ), 31));
            context.register(ModWeightedHolderLists.CATACOMBS_STORAGE_BLOCK_TREASURE, new WeightedHolderList<>(
                Registries.BLOCK,
                List.of(blocks.getOrThrow(Tags.Blocks.STORAGE_BLOCKS)),
                List.of(
                    WeightedHolder.create(Blocks.IRON_BLOCK,   wrapper, 50),
                    WeightedHolder.create(Blocks.COPPER_BLOCK, wrapper, 50),
                    WeightedHolder.create(List.of(
                        Blocks.GOLD_BLOCK,
                        Blocks.EMERALD_BLOCK
                    ), wrapper, 22),
                    WeightedHolder.create(List.of(
                        Blocks.DIAMOND_BLOCK,
                        Blocks.LAPIS_BLOCK
                    ), wrapper, 7),
                    WeightedHolder.create(Blocks.NETHERITE_BLOCK, wrapper, 2),
                    WeightedHolder.create(ModBlocks.NOVIUM_BLOCK,          1),
                    WeightedHolder.create(HolderSet.direct(
                        ModBlocks.DEMURIUM_BLOCK,
                        ModBlocks.DEMITHRIUM_BLOCK
                    ), 0)
                ),
                1));
        }
        
        // Item lists
        {
            final Function<Item, Holder<Item>> wrapper = BuiltInRegistries.ITEM::wrapAsHolder;
            context.register(ModWeightedHolderLists.DEMITHRIL_SHRINE_LIBRARY_BOOKS, new WeightedHolderList<>(
                Registries.ITEM,
                List.of(items.getOrThrow(ItemTags.BOOKSHELF_BOOKS)),
                List.of(
                    WeightedHolder.create(Items.BOOK,           wrapper,  10),
                    WeightedHolder.create(Items.WRITABLE_BOOK,  wrapper,   7),
                    WeightedHolder.create(Items.ENCHANTED_BOOK, wrapper,   3)
                )
            ));
        }
    }
    
    //==================================================================================================================
    private static ResourceKey<WeightedHolderList<?>> create(final String name)
    {
        return Define.key(ModRegistries.WEIGHTED_HOLDER_LIST, name);
    }
}
