package xyz.lumian.novia_additions.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BannerPatternTagsProvider;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import xyz.lumian.novia_additions.Define;
import xyz.lumian.novia_additions.block.ModBlockTags;
import xyz.lumian.novia_additions.item.ModBannerPatterns;
import xyz.lumian.novia_additions.block.ModBlocks;
import xyz.lumian.novia_additions.item.ModItemTags;
import xyz.lumian.novia_additions.item.ModItems;
import xyz.lumian.novia_additions.world.ModBiomeTags;
import xyz.lumian.novia_additions.world.generator.ModFeatures;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;



//**********************************************************************************************************************
public class ModTagProvider
{
    //******************************************************************************************************************
    public static class Blocks
        extends BlockTagsProvider
    {
        //**************************************************************************************************************
        public Blocks(final PackOutput                               output,
                      final CompletableFuture<HolderLookup.Provider> lookup,
                      final @Nullable ExistingFileHelper             existingFileHelper)
        {
            super(output, lookup, Define.MOD_ID, existingFileHelper);
        }
        
        //==============================================================================================================
        @Override
        protected void addTags(final HolderLookup.Provider provider)
        {
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                ModBlocks.DEMITHRIL_STATUE.get(),
                ModBlocks.DEEPSLATE_NOVIUM_ORE.get(),
                ModBlocks.END_STONE_DEMURIUM_ORE.get(),
                ModBlocks.NOVIUM_BLOCK.get(),
                ModBlocks.DEMURIUM_BLOCK.get(),
                ModBlocks.DEMITHRIUM_BLOCK.get(),
                ModBlocks.NOVIUM_PELLET_BLOCK.get()
            );
            
            {
                //noinspection unchecked
                Stream
                    .of(
                        BlockTags.INCORRECT_FOR_WOODEN_TOOL,
                        BlockTags.INCORRECT_FOR_GOLD_TOOL,
                        BlockTags.INCORRECT_FOR_IRON_TOOL,
                        BlockTags.INCORRECT_FOR_STONE_TOOL,
                        BlockTags.INCORRECT_FOR_DIAMOND_TOOL
                    )
                    .forEach(tag -> this.tag(tag).addTags(
                        ModBlockTags.NEEDS_STARTER_TOOL,
                        ModBlockTags.NEEDS_NOVIUM_TOOL,
                        ModBlockTags.NEEDS_DEMITHRIUM_TOOL
                    ));
                
                //noinspection unchecked
                this.tag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).addTags(
                    ModBlockTags.NEEDS_NOVIUM_TOOL,
                    ModBlockTags.NEEDS_DEMITHRIUM_TOOL
                );
                
                this.tag(ModBlockTags.INCORRECT_FOR_NOVIUM_TOOL).addTag(ModBlockTags.NEEDS_DEMITHRIUM_TOOL);
                
                this.tag(ModBlockTags.INCORRECT_FOR_DEMITHRIUM_TOOL);
                
                // Tier blocks
                {
                    this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).addTag(ModBlockTags.NEEDS_STARTER_TOOL);
                    this.tag(ModBlockTags.NEEDS_STARTER_TOOL).add(
                        ModBlocks.DEEPSLATE_NOVIUM_ORE.get(),
                        ModBlocks.NOVIUM_PELLET_BLOCK.get(),
                        ModBlocks.NOVIUM_BLOCK.get()
                    );
                    
                    this.tag(ModBlockTags.NEEDS_NOVIUM_TOOL).add(
                        ModBlocks.END_STONE_DEMURIUM_ORE.get(),
                        ModBlocks.DEMURIUM_BLOCK.get(),
                        ModBlocks.DEMITHRIUM_BLOCK.get()
                    );
                    
                    this.tag(ModBlockTags.NEEDS_DEMITHRIUM_TOOL);
                }
                
                this.tag(Tags.Blocks.RELOCATION_NOT_SUPPORTED).add(
                    ModBlocks.DEEPSLATE_NOVIUM_ORE.get(),
                    ModBlocks.END_STONE_DEMURIUM_ORE.get()
                );
                
                this.tag(Tags.Blocks.STORAGE_BLOCKS).add(
                    ModBlocks.DEMURIUM_BLOCK.get(),
                    ModBlocks.DEMITHRIUM_BLOCK.get(),
                    ModBlocks.NOVIUM_BLOCK.get()
                );
                
                this.tag(Tags.Blocks.ORES).add(
                    ModBlocks.END_STONE_DEMURIUM_ORE.get(),
                    ModBlocks.DEEPSLATE_NOVIUM_ORE.get()
                );
                
                this.tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE).add(ModBlocks.DEEPSLATE_NOVIUM_ORE.get());
                this.tag(ModBlockTags.ORES_IN_GROUND_END_STONE).add(ModBlocks.END_STONE_DEMURIUM_ORE.get());
                
                // Misc
                this.tag(BlockTags.BEACON_BASE_BLOCKS).add(
                    ModBlocks.NOVIUM_BLOCK.get(),
                    ModBlocks.DEMITHRIUM_BLOCK.get()
                );
                
                this.tag(ModBlockTags.END_STONE_ORE_REPLACEABLES).add(net.minecraft.world.level.block.Blocks.END_STONE);
            }
        }
    }
    
    public static class Items
        extends IntrinsicHolderTagsProvider<Item>
    {
        //**************************************************************************************************************
        public Items(final PackOutput                               output,
                     final CompletableFuture<HolderLookup.Provider> lookup,
                     final @Nullable ExistingFileHelper             existingFileHelper)
        {
            super(output, Registries.ITEM, lookup,
                  ((item) -> BuiltInRegistries.ITEM.getResourceKey(item).orElseThrow()), Define.MOD_ID,
                  existingFileHelper);
        }
        
        //==============================================================================================================
        @Override
        protected void addTags(final HolderLookup.Provider provider)
        {
            this.tag(Tags.Items.MUSIC_DISCS).add(
                ModItems.MUSIC_DISC_REVOLUTION.get(),
                ModItems.MUSIC_DISC_EVOLUTION.get()
            );
            
            // SWORDS
            this.tag(ItemTags.SWORDS).add(
                ModItems.NOVIUM_SWORD.get(),
                ModItems.DEMITHRIUM_SWORD.get()
            );
            
            // PICKAXES
            this.tag(ItemTags.PICKAXES).add(
                ModItems.NOVIUM_PICKAXE.get(),
                ModItems.DEMITHRIUM_PICKAXE.get()
            );
            this.tag(Tags.Items.MINING_TOOL_TOOLS).add(
                ModItems.NOVIUM_PICKAXE.get(),
                ModItems.DEMITHRIUM_PICKAXE.get()
            );
            
            // AXES
            this.tag(ItemTags.AXES).add(
                ModItems.NOVIUM_AXE.get(),
                ModItems.DEMITHRIUM_AXE.get()
            );
            
            // SHOVELS
            this.tag(ItemTags.SHOVELS).add(
                ModItems.NOVIUM_SHOVEL.get(),
                ModItems.DEMITHRIUM_SHOVEL.get()
            );
            
            // HOES
            this.tag(ItemTags.HOES).add(
                ModItems.NOVIUM_HOE    .get(),
                ModItems.DEMITHRIUM_HOE.get()
            );
            
            // MISC
            this.tag(Tags.Items.MELEE_WEAPON_TOOLS).add(
                ModItems.NOVIUM_SWORD.get(),
                ModItems.DEMITHRIUM_SWORD.get(),
                ModItems.NOVIUM_AXE.get(),
                ModItems.DEMITHRIUM_AXE.get()
            );
            
            this.tag(ItemTags.CLUSTER_MAX_HARVESTABLES).add(
                ModItems.NOVIUM_PICKAXE.get(),
                ModItems.DEMITHRIUM_PICKAXE.get()
            );
            
            this.tag(Tags.Items.RAW_MATERIALS).add(
                ModItems.NOVIUM_PELLET.get(),
                ModItems.DEMURIUM_CRYSTAL.get()
            );
            
            this.tag(Tags.Items.INGOTS).add(
                ModItems.NOVIUM_INGOT.get(),
                ModItems.DEMITHRIUM_INGOT.get()
            );
            
            this.tag(Tags.Items.NUGGETS).add(
                ModItems.NOVIUM_NUGGET.get(),
                ModItems.DEMITHRIUM_NUGGET.get()
            );
            
            // ARMOR
            // convenience tags
            this.tag(ModItemTags.HEAD_ARMOR).add(
                ModItems.NOVIUM_HELMET.get(),
                ModItems.DEMITHRIUM_HELMET.get()
            );
            this.tag(ModItemTags.CHEST_ARMOR).add(
                ModItems.NOVIUM_CHESTPLATE.get(),
                ModItems.DEMITHRIUM_CHESTPLATE.get()
            );
            this.tag(ModItemTags.LEG_ARMOR).add(
                ModItems.NOVIUM_LEGGINS.get(),
                ModItems.DEMITHRIUM_LEGGINS.get()
            );
            this.tag(ModItemTags.FOOT_ARMOR).add(
                ModItems.NOVIUM_BOOTS.get(),
                ModItems.DEMITHRIUM_BOOTS.get()
            );
            this.tag(ModItemTags.NOVIUM_ARMOR).add(
                ModItems.NOVIUM_HELMET.get(),
                ModItems.NOVIUM_CHESTPLATE.get(),
                ModItems.NOVIUM_LEGGINS.get(),
                ModItems.NOVIUM_BOOTS.get()
            );
            this.tag(ModItemTags.DEMITHRIUM_ARMOR).add(
                ModItems.DEMITHRIUM_HELMET.get(),
                ModItems.DEMITHRIUM_CHESTPLATE.get(),
                ModItems.DEMITHRIUM_LEGGINS.get(),
                ModItems.DEMITHRIUM_BOOTS.get()
            );
            
            //noinspection unchecked
            this.tag(ModItemTags.ARMOR_SETS).addTags(
                ModItemTags.NOVIUM_ARMOR,
                ModItemTags.DEMITHRIUM_ARMOR
            );
            
            this.tag(ItemTags.HEAD_ARMOR) .addTag(ModItemTags.HEAD_ARMOR);
            this.tag(ItemTags.CHEST_ARMOR).addTag(ModItemTags.CHEST_ARMOR);
            this.tag(ItemTags.LEG_ARMOR)  .addTag(ModItemTags.LEG_ARMOR);
            this.tag(ItemTags.FOOT_ARMOR) .addTag(ModItemTags.FOOT_ARMOR);
            
            this.tag(ModItemTags.CAN_WALK_ON_POWDERED_SNOW)   .addTag(ModItemTags.FOOT_ARMOR);
            this.tag(ModItemTags.RESISTANT_TO_FIRE)           .add   (ModItems.DEMITHRIUM_CHESTPLATE.get());
            this.tag(ModItemTags.RESISTANT_TO_WITHER_EFFECT)  .addTag(ModItemTags.CHEST_ARMOR);
            this.tag(ModItemTags.SOOTHES_PIGLINS)             .addTag(ModItemTags.ARMOR_SETS);
            this.tag(ModItemTags.RESISTANT_TO_DARKNESS_EFFECT).add   (ModItems.DEMITHRIUM_HELMET.get());
            
            // Misc
            this.tag(ItemTags.BEACON_PAYMENT_ITEMS).add(
                ModItems.NOVIUM_INGOT.get(),
                ModItems.DEMURIUM_CRYSTAL.get(),
                ModItems.DEMITHRIUM_INGOT.get()
            );
            this.tag(ModItemTags.MUSIC_DISCS).add(
                ModItems.MUSIC_DISC_REVOLUTION.get(),
                ModItems.MUSIC_DISC_EVOLUTION.get()
            );
        }
    }
    
    public static class BannerPatterns
        extends BannerPatternTagsProvider
    {
        //**************************************************************************************************************
        protected BannerPatterns(final PackOutput                               output,
                                 final CompletableFuture<HolderLookup.Provider> lookup,
                                 final ExistingFileHelper                       helper)
        {
            super(output, lookup, Define.MOD_ID, helper);
        }
        
        //==============================================================================================================
        @Override
        protected void addTags(final HolderLookup.Provider provider)
        {
            this.tag(ModBannerPatterns.PATTERN_ITEM_NOVIA)
                .add(ModBannerPatterns.NOVIA);
        }
    }
    
    public static class PlacedFeatures
        extends TagsProvider<PlacedFeature>
    {
        //**************************************************************************************************************
        protected PlacedFeatures(final           PackOutput                                      output,
                                 final           CompletableFuture<HolderLookup.Provider>        lookup,
                                 final @Nullable ExistingFileHelper                              helper)
        {
            super(output, Registries.PLACED_FEATURE, lookup, Define.MOD_ID, helper);
        }
        
        //==============================================================================================================
        @Override
        protected void addTags(final HolderLookup.Provider provider)
        {
            this.tag(ModFeatures.OVERWORLD_ORE_GENERATION).add(
                ModFeatures.ORE_NOVIUM_SMALL_PLACED,
                ModFeatures.ORE_NOVIUM_LARGE_PLACED
            );
            this.tag(ModFeatures.END_ORE_GENERATION).add(
                ModFeatures.ORE_DEMURIUM_COVERED_PLACED,
                ModFeatures.ORE_DEMURIUM_EXPOSED_PLACED
            );
        }
    }
    
    public static class Biomes
        extends BiomeTagsProvider
    {
        //**************************************************************************************************************
        public Biomes(final PackOutput                               output,
                      final CompletableFuture<HolderLookup.Provider> lookup,
                      final @Nullable ExistingFileHelper             helper)
        {
            super(output, lookup, Define.MOD_ID, helper);
        }
        
        //==============================================================================================================
        @Override
        protected void addTags(final HolderLookup.Provider lookup)
        {
            this.tag(ModBiomeTags.HAS_SHRINE)
                .addTag(BiomeTags.IS_FOREST)
                .add(
                    net.minecraft.world.level.biome.Biomes.PLAINS,
                    net.minecraft.world.level.biome.Biomes.DESERT,
                    net.minecraft.world.level.biome.Biomes.SUNFLOWER_PLAINS,
                    net.minecraft.world.level.biome.Biomes.MEADOW
                );
            this.tag(ModBiomeTags.HAS_CATACOMBS).add(
                net.minecraft.world.level.biome.Biomes.SWAMP,
                net.minecraft.world.level.biome.Biomes.MANGROVE_SWAMP
            );
            this.tag(ModBiomeTags.HAS_NOVIUM_ORE).addTag(BiomeTags.IS_OVERWORLD);
            this.tag(ModBiomeTags.HAS_DEMURIUM_ORE).addTag(BiomeTags.IS_END);
        }
    }
}
