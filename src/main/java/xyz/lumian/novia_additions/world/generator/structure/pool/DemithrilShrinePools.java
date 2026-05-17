package xyz.lumian.novia_additions.world.generator.structure.pool;

import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import xyz.lumian.novia_additions.Define;
import xyz.lumian.novia_additions.registry.ModRegistries;
import xyz.lumian.novia_additions.world.ModLootTables;
import xyz.lumian.novia_additions.world.generator.ModPools;
import xyz.lumian.novia_additions.world.generator.ModWeightedHolderLists;
import xyz.lumian.novia_additions.world.generator.WeightedHolderList;
import xyz.lumian.novia_additions.world.generator.processor.BookshelfLootProcessor;
import xyz.lumian.novia_additions.world.generator.processor.ItemFrameLootProcessor;
import xyz.lumian.novia_additions.world.generator.processor.WeightedBlockReplacementProcessor;

import java.util.ArrayList;
import java.util.List;



//**********************************************************************************************************************
public class DemithrilShrinePools
{
    //******************************************************************************************************************
    public static final ResourceLocation START_ID = Define.mod("demithril_shrine");
    
    public static final ResourceKey<StructureTemplatePool> START = ModPools
        .create(DemithrilShrinePools.START_ID.withSuffix("/centre").getPath());
    
    //==================================================================================================================
    public static final ModPools.ProcessorBuilder SHRINE_PROCESSORS = ModPools.processorBuilder()
        .withAllRules(
            ModPools.simpleRule(
                new RandomBlockMatchTest(Blocks.QUARTZ_BRICKS, 0.2f),
                Blocks.QUARTZ_BLOCK.defaultBlockState()),
            ModPools.simpleRule(
                new RandomBlockMatchTest(Blocks.QUARTZ_BRICKS, 0.2f),
                Blocks.DIORITE.defaultBlockState()),
            ModPools.simpleRule(
                new RandomBlockMatchTest(Blocks.GRAVEL, 0.2f),
                Blocks.SUSPICIOUS_GRAVEL.defaultBlockState())
        )
        .with(new BlockRotProcessor(
            HolderSet.direct(BuiltInRegistries.BLOCK.wrapAsHolder(Blocks.QUARTZ_BRICKS)),
            0.9f))
        .with(new BlockIgnoreProcessor(List.of(Blocks.BEDROCK)));
    
    public static final ModPools.ProcessorBuilder GRAVE_PROCESSORS = ModPools.processorBuilder()
        .withAllRules(
            ModPools.simpleRule(
                new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.05f),
                Blocks.COBWEB.defaultBlockState()),
            ModPools.simpleRule(
                new RandomBlockMatchTest(Blocks.GREEN_CARPET, 0.5f),
                Blocks.AIR.defaultBlockState()),
            ModPools.simpleRule(
                new RandomBlockMatchTest(Blocks.GREEN_CARPET, 0.7f),
                Blocks.MOSS_CARPET.defaultBlockState()),
            ModPools.simpleRule(
                new RandomBlockMatchTest(Blocks.GRAVEL, 0.1f),
                Blocks.SUSPICIOUS_GRAVEL.defaultBlockState())
        )
        .with(new BlockRotProcessor(HolderSet.direct(
            BuiltInRegistries.BLOCK.wrapAsHolder(Blocks.STONE_BRICKS)),
            0.9f))
        .with(new BlockAgeProcessor(0.07f));
    
    //******************************************************************************************************************
    public static void bootstrap(final BootstrapContext<StructureTemplatePool> context)
    {
        DemithrilShrinePools.registerSurfacePools(context);
        DemithrilShrinePools.registerGravePools  (context);
    }
    
    //==================================================================================================================
    private static void registerSurfacePools(final BootstrapContext<StructureTemplatePool> context)
    {
        final LiquidSettings                waterlog = LiquidSettings.APPLY_WATERLOGGING;
        final Holder<StructureTemplatePool> empty    = context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY);

        final Holder<StructureProcessorList> shrine_processors = DemithrilShrinePools.SHRINE_PROCESSORS.build();
        
        context.register(DemithrilShrinePools.START, new StructureTemplatePool(
            empty,
            List.of(
                ModPools.simpleElement(DemithrilShrinePools.createId("centre"),
                    DemithrilShrinePools.SHRINE_PROCESSORS
                        .with(new ItemFrameLootProcessor(
                            ModLootTables.FRAME_SHRINE,
                            new ItemStack(Items.EMERALD)))
                        .build(),
                    waterlog, 1)
            ),
            StructureTemplatePool.Projection.RIGID));
        DemithrilShrinePools.subPool(context, "attachments/back", new StructureTemplatePool(
            empty,
            List.of(
                ModPools.simpleElement(DemithrilShrinePools.createId("back/well"), shrine_processors, waterlog, 1)
            ),
            StructureTemplatePool.Projection.RIGID));
        
        final var side_attachments = List.of(
            ModPools.simpleElement(DemithrilShrinePools.createId("side/park"), shrine_processors, waterlog, 1)
        );
        DemithrilShrinePools.subPool(context, "attachments/right", new StructureTemplatePool(
            empty,
            Util.make(new ArrayList<>(), (list -> {
                list.addAll(side_attachments);
                list.add(ModPools.simpleElement(DemithrilShrinePools.createId("right/vex_garden"),
                    shrine_processors,
                    waterlog, 1));
            })),
            StructureTemplatePool.Projection.RIGID));

        DemithrilShrinePools.subPool(context, "attachments/left", new StructureTemplatePool(
            empty,
            Util.make(new ArrayList<>(), (list -> {
                list.addAll(side_attachments);
                list.add(ModPools.simpleElement(DemithrilShrinePools.createId("left/burial_side"),
                    DemithrilShrinePools.SHRINE_PROCESSORS
                        .withAllRules(
                            ModPools.simpleRule(
                                new RandomBlockMatchTest(Blocks.QUARTZ_SLAB, 0.2f),
                                Blocks.AIR.defaultBlockState()),
                            ModPools.simpleRule(
                                new RandomBlockMatchTest(Blocks.DIORITE_WALL, 0.2f),
                                Blocks.BIRCH_FENCE.defaultBlockState())
                        )
                        .build(),
                    waterlog, 1));
            })),
            StructureTemplatePool.Projection.RIGID
        ));
        DemithrilShrinePools.subPool(context, "entrances", new StructureTemplatePool(
            empty,
            List.of(
                ModPools.simpleElement(DemithrilShrinePools.createId("entrance/pillars"),
                    shrine_processors,
                    waterlog, 1),
                ModPools.simpleElement(DemithrilShrinePools.createId("entrance/goblets"),
                    shrine_processors,
                    waterlog, 1)
            ),
            StructureTemplatePool.Projection.RIGID
        ));
        
        final HolderGetter<PlacedFeature> features = context.lookup(Registries.PLACED_FEATURE);
        DemithrilShrinePools.subPool(context, "features/trees", new StructureTemplatePool(
            empty,
            List.of(
                Pair.of(StructurePoolElement.feature(features.getOrThrow(TreePlacements.OAK_CHECKED)),    4),
                Pair.of(StructurePoolElement.feature(features.getOrThrow(TreePlacements.OAK_BEES_002)),   1),
                Pair.of(StructurePoolElement.feature(features.getOrThrow(TreePlacements.OAK_BEES_0002)),  1),
                Pair.of(StructurePoolElement.feature(features.getOrThrow(TreePlacements.SPRUCE_CHECKED)), 3),
                Pair.of(StructurePoolElement.feature(features.getOrThrow(TreePlacements.BIRCH_CHECKED)),  4),
                Pair.of(StructurePoolElement.feature(features.getOrThrow(TreePlacements.BIRCH_BEES_002)), 1)
            ),
            StructureTemplatePool.Projection.RIGID
        ));
    }
    
    private static void registerGravePools(final BootstrapContext<StructureTemplatePool> context)
    {
        final LiquidSettings                waterlog = LiquidSettings.IGNORE_WATERLOGGING;
        final Holder<StructureTemplatePool> empty    = context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY);
        
        final Holder<StructureProcessorList> grave_processors = DemithrilShrinePools.GRAVE_PROCESSORS.build();
        
        DemithrilShrinePools.subPool(context, "grave/start", new StructureTemplatePool(
            empty,
            List.of(ModPools.simpleElement(DemithrilShrinePools.createId("grave/entrance"),
                grave_processors,
                waterlog, 1)),
            StructureTemplatePool.Projection.RIGID));
        DemithrilShrinePools.subPool(context, "grave/rooms", new StructureTemplatePool(
            empty,
            List.of(
                ModPools.simpleElement(DemithrilShrinePools.createId("grave/dead_end"),
                    grave_processors,
                    waterlog, 17),
                ModPools.simpleElement(DemithrilShrinePools.createId("grave/medium"),
                    grave_processors,
                    waterlog, 20),
                ModPools.simpleElement(DemithrilShrinePools.createId("grave/trial"),
                    grave_processors,
                    waterlog, 15),
                ModPools.simpleElement(DemithrilShrinePools.createId("grave/small"),
                    grave_processors,
                    waterlog, 20)
            ),
            StructureTemplatePool.Projection.RIGID
        ));
        
        final HolderGetter<WeightedHolderList<?>> weights = context.lookup(ModRegistries.WEIGHTED_HOLDER_LIST);
        DemithrilShrinePools.subPool(context, "grave/auxiliary", new StructureTemplatePool(
            empty,
            List.of(
                ModPools.simpleElement(DemithrilShrinePools.createId("grave/dead_end"),
                    grave_processors,
                    waterlog, 2),
                ModPools.simpleElement(DemithrilShrinePools.createId("grave/library"),
                    DemithrilShrinePools.GRAVE_PROCESSORS
                        .withRule(ModPools.simpleRule(
                            new RandomBlockMatchTest(Blocks.CHISELED_BOOKSHELF, 0.9f),
                            Blocks.BOOKSHELF.defaultBlockState()))
                        .with(new ItemFrameLootProcessor(
                            ModLootTables.FRAME_SHRINE,
                            new ItemStack(Items.EMERALD)))
                        .with(new BookshelfLootProcessor(
                            weights.getOrThrow(ModWeightedHolderLists.DEMITHRIL_SHRINE_LIBRARY_BOOKS),
                            0.5f,
                            new BookshelfLootProcessor.EnchantmentProvider(
                                context.lookup(Registries.ENCHANTMENT).getOrThrow(EnchantmentTags.ON_RANDOM_LOOT),
                                UniformGenerator.between(10f, 30f)),
                            true))
                        .build(),
                    waterlog, 2),
                ModPools.simpleElement(DemithrilShrinePools.createId("grave/ore_deposit"),
                    ModPools.directProc(new WeightedBlockReplacementProcessor(
                        new BlockMatchTest(Blocks.EMERALD_BLOCK),
                        weights.getOrThrow(ModWeightedHolderLists.DEMITHRIL_SHRINE_ORE_DEPOSIT))),
                    waterlog, 1),
                ModPools.simpleElement(DemithrilShrinePools.createId("grave/campfire_cave"),
                    grave_processors,
                    waterlog, 3)
            ),
            StructureTemplatePool.Projection.RIGID));
    }
    
    //==================================================================================================================
    private static void subPool(final BootstrapContext<StructureTemplatePool> context,
                                final String                                  name,
                                final StructureTemplatePool                   pool)
    {
        ModPools.register(context, ("demithril_shrine/" + name), pool);
    }
    
    private static ResourceLocation createId(final String name)
    {
        return DemithrilShrinePools.START_ID.withSuffix("/" + name);
    }
}
