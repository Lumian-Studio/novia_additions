package xyz.lumian.novia_additions.world.generator;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import xyz.lumian.novia_additions.Define;
import xyz.lumian.novia_additions.block.ModBlockTags;
import xyz.lumian.novia_additions.block.ModBlocks;
import xyz.lumian.novia_additions.world.ModBiomeTags;

import java.util.List;



//**********************************************************************************************************************
public class ModFeatures
{
    //******************************************************************************************************************
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_NOVIUM_SMALL_CONFIGURED
        = configured("ore_novium_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_NOVIUM_LARGE_CONFIGURED
        = configured("ore_novium_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_DEMURIUM_COVERED_CONFIGURED
        = configured("ore_demurium_covered");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_DEMURIUM_EXPOSED_CONFIGURED
        = configured("ore_demurium_exposed");
    
    public static final ResourceKey<PlacedFeature> ORE_NOVIUM_SMALL_PLACED     = placed("ore_novium_small");
    public static final ResourceKey<PlacedFeature> ORE_NOVIUM_LARGE_PLACED     = placed("ore_novium_large");
    public static final ResourceKey<PlacedFeature> ORE_DEMURIUM_COVERED_PLACED = placed("ore_demurium_covered");
    public static final ResourceKey<PlacedFeature> ORE_DEMURIUM_EXPOSED_PLACED = placed("ore_demurium_exposed");
    public static final ResourceKey<PlacedFeature> OAK_TREE                    = placed("oak_tree");
    
    public static final ResourceKey<BiomeModifier> NOVIUM_ORE_MODIFIER   = modifier("novium_ore");
    public static final ResourceKey<BiomeModifier> DEMURIUM_ORE_MODIFIER = modifier("demurium_ore");
    
    //==================================================================================================================
    public static final TagKey<PlacedFeature> OVERWORLD_ORE_GENERATION = TagKey.create(
        Registries.PLACED_FEATURE,
        Define.mod("ores/overworld"));
    public static final TagKey<PlacedFeature> END_ORE_GENERATION = TagKey.create(
        Registries.PLACED_FEATURE,
        Define.mod("ores/the_end"));
    
    //******************************************************************************************************************
    public static void bootstrapConfigured(final BootstrapContext<ConfiguredFeature<?, ?>> context)
    {
        // NOVIUM
        {
            final RuleTest deep_slate_test = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
            context.register(ModFeatures.ORE_NOVIUM_SMALL_CONFIGURED, new ConfiguredFeature<>(
                Feature.SCATTERED_ORE,
                new OreConfiguration(
                    deep_slate_test,
                    ModBlocks.DEEPSLATE_NOVIUM_ORE.get().defaultBlockState(),
                    2, 0.7f)));
            context.register(ModFeatures.ORE_NOVIUM_LARGE_CONFIGURED, new ConfiguredFeature<>(
                Feature.SCATTERED_ORE,
                new OreConfiguration(
                    deep_slate_test,
                    ModBlocks.DEEPSLATE_NOVIUM_ORE.get().defaultBlockState(),
                    3, 1f)));
        }
        
        // DEMURIUM
        {
            final RuleTest end_stone_test = new TagMatchTest(ModBlockTags.END_STONE_ORE_REPLACEABLES);
            context.register(ModFeatures.ORE_DEMURIUM_EXPOSED_CONFIGURED, new ConfiguredFeature<>(
                Feature.SCATTERED_ORE,
                new OreConfiguration(
                    end_stone_test,
                    ModBlocks.END_STONE_DEMURIUM_ORE.get().defaultBlockState(),
                    1, 0.3f)));
            context.register(ModFeatures.ORE_DEMURIUM_COVERED_CONFIGURED, new ConfiguredFeature<>(
                Feature.SCATTERED_ORE,
                new OreConfiguration(
                    end_stone_test,
                    ModBlocks.END_STONE_DEMURIUM_ORE.get().defaultBlockState(),
                    2, 1f)));
        }
    }
    
    public static void bootstrapPlaced(final BootstrapContext<PlacedFeature> context)
    {
        final HolderGetter<ConfiguredFeature<?, ?>> lookup = context.lookup(Registries.CONFIGURED_FEATURE);
        final List<PlacementModifier> mods   = List.of(
            BiomeFilter.biome(),
            InSquarePlacement.spread()
        );
        
        // NOVIUM
        {
            context.register(ModFeatures.ORE_NOVIUM_SMALL_PLACED, new PlacedFeature(
                lookup.getOrThrow(ModFeatures.ORE_NOVIUM_SMALL_CONFIGURED),
                ImmutableList.<PlacementModifier>builder().addAll(mods)
                             .add(HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(4), VerticalAnchor.top()))
                             .build()));
            context.register(ModFeatures.ORE_NOVIUM_LARGE_PLACED, new PlacedFeature(
                lookup.getOrThrow(ModFeatures.ORE_NOVIUM_LARGE_CONFIGURED),
                ImmutableList.<PlacementModifier>builder().addAll(mods)
                    .add(HeightRangePlacement.triangle(
                        VerticalAnchor.aboveBottom(4),
                        VerticalAnchor.aboveBottom(20)))
                    .build()));
        }
        
        // DEMURIUM
        {
            context.register(ModFeatures.ORE_DEMURIUM_COVERED_PLACED, new PlacedFeature(
                lookup.getOrThrow(ModFeatures.ORE_DEMURIUM_COVERED_CONFIGURED),
                ImmutableList.<PlacementModifier>builder().addAll(mods)
                    .add(HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()))
                    .build()));
            context.register(ModFeatures.ORE_DEMURIUM_EXPOSED_PLACED, new PlacedFeature(
                lookup.getOrThrow(ModFeatures.ORE_DEMURIUM_EXPOSED_CONFIGURED),
                ImmutableList.<PlacementModifier>builder().addAll(mods)
                    .add(HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()))
                    .build()));
        }
        
        context.register(ModFeatures.OAK_TREE, new PlacedFeature(lookup.getOrThrow(TreeFeatures.OAK), List.of()));
    }
    
    public static void bootstrapModifiers(final BootstrapContext<BiomeModifier> context)
    {
        final HolderGetter<Biome>         biomes   = context.lookup(Registries.BIOME);
        final HolderGetter<PlacedFeature> features = context.lookup(Registries.PLACED_FEATURE);
        
        context.register(ModFeatures.NOVIUM_ORE_MODIFIER, new BiomeModifiers.AddFeaturesBiomeModifier(
            biomes.getOrThrow(ModBiomeTags.HAS_NOVIUM_ORE),
            features.getOrThrow(ModFeatures.OVERWORLD_ORE_GENERATION),
            GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ModFeatures.DEMURIUM_ORE_MODIFIER, new BiomeModifiers.AddFeaturesBiomeModifier(
            biomes.getOrThrow(ModBiomeTags.HAS_DEMURIUM_ORE),
            features.getOrThrow(ModFeatures.END_ORE_GENERATION),
            GenerationStep.Decoration.UNDERGROUND_ORES));
    }
    
    //==================================================================================================================
    private static ResourceKey<ConfiguredFeature<?, ?>> configured(final String name)
    {
        return Define.key(Registries.CONFIGURED_FEATURE, name);
    }
    
    private static ResourceKey<PlacedFeature> placed(final String name)
    {
        return Define.key(Registries.PLACED_FEATURE, name);
    }
    
    private static ResourceKey<BiomeModifier> modifier(final String name)
    {
        return Define.key(NeoForgeRegistries.Keys.BIOME_MODIFIERS, name);
    }
}
