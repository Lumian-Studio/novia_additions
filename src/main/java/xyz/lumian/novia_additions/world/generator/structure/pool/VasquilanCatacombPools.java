package xyz.lumian.novia_additions.world.generator.structure.pool;

import com.google.common.collect.Streams;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import xyz.lumian.novia_additions.Define;
import xyz.lumian.novia_additions.registry.ModRegistries;
import xyz.lumian.novia_additions.world.ModLootTables;
import xyz.lumian.novia_additions.world.generator.ModPools;
import xyz.lumian.novia_additions.world.generator.ModWeightedHolderLists;
import xyz.lumian.novia_additions.world.generator.WeightedHolderList;
import xyz.lumian.novia_additions.world.generator.processor.ItemFrameLootProcessor;
import xyz.lumian.novia_additions.world.generator.processor.MudBlockAgeProcessor;
import xyz.lumian.novia_additions.world.generator.processor.WeightedBlockReplacementProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;



//**********************************************************************************************************************
public class VasquilanCatacombPools
{
    //******************************************************************************************************************
    public static final ResourceLocation START_ID = Define.mod("vasquilan_catacombs");
    
    public static final ResourceKey<StructureTemplatePool> START = ModPools
        .create(VasquilanCatacombPools.START_ID.withSuffix("/start").getPath());
    
    public static final ModPools.ProcessorBuilder CATACOMBS_PROCESSORS = ModPools.processorBuilder()
        .withRule(ModPools.simpleRule(
            new RandomBlockMatchTest(Blocks.MUD_BRICKS, 0.01f),
            Blocks.COBWEB.defaultBlockState()))
        .with(new BlockRotProcessor(
            HolderSet.direct(BuiltInRegistries.BLOCK.wrapAsHolder(Blocks.MUD_BRICKS)),
            0.99f))
        .with(new MudBlockAgeProcessor(0.4f, 0.1f, 0.1f));
    
    //******************************************************************************************************************
    public static void bootstrap(final BootstrapContext<StructureTemplatePool> context)
    {
        final HolderGetter<StructureTemplatePool> pools = context.lookup(Registries.TEMPLATE_POOL);
        final Holder<StructureTemplatePool>       empty = pools.getOrThrow(Pools.EMPTY);
        
        final LiquidSettings waterlog = LiquidSettings.IGNORE_WATERLOGGING;
        
        context.register(VasquilanCatacombPools.START, new StructureTemplatePool(
            empty,
            List.of(
                ModPools.simpleElement(VasquilanCatacombPools.createId("entrance"),
                    DemithrilShrinePools.GRAVE_PROCESSORS.build(),
                    waterlog, 1)
            ),
            StructureTemplatePool.Projection.RIGID
        ));
        
        VasquilanCatacombPools.subPool(context, "entrance", new StructureTemplatePool(
            empty,
            List.of(
                ModPools.simpleElement(VasquilanCatacombPools.createId("entrance_staircase"),
                    DemithrilShrinePools.GRAVE_PROCESSORS
                        .withAll(VasquilanCatacombPools.CATACOMBS_PROCESSORS)
                        .build(),
                    waterlog, 1)
            ),
            StructureTemplatePool.Projection.RIGID
        ));
        
        final Holder<StructureProcessorList> catacombs_processors = VasquilanCatacombPools.CATACOMBS_PROCESSORS.build();
        
        final StructureProcessor frame_loot_proc = new ItemFrameLootProcessor(
            ModLootTables.FRAME_SHRINE,
            new ItemStack(Items.EMERALD));
        
        final ProcessorRule sus_rule = ModPools.simpleRule(
             new RandomBlockMatchTest(Blocks.GRAVEL, 0.2f),
             Blocks.SUSPICIOUS_GRAVEL.defaultBlockState());
        
        final var dead_end = ModPools.simpleElement(VasquilanCatacombPools.createId("dead_end"),
            catacombs_processors,
            waterlog, 15);
        VasquilanCatacombPools.subPool(context, "dead_ends", new StructureTemplatePool(
            empty,
            List.of(dead_end),
            StructureTemplatePool.Projection.RIGID
        ));
        
        final Holder<StructureTemplatePool> ending_pool = pools.getOrThrow(ResourceKey.create(
            Registries.TEMPLATE_POOL,
            VasquilanCatacombPools.createId("dead_ends")));
        VasquilanCatacombPools.subPool(context, "paths", new StructureTemplatePool(
            ending_pool,
            Streams.concat(
                IntStream.rangeClosed(1, 5).mapToObj(i -> ModPools.simpleElement(
                    VasquilanCatacombPools.createId("path/mud_" + i),
                    catacombs_processors,
                    waterlog, 25)),
                Stream.of(dead_end)
            ).toList(),
            StructureTemplatePool.Projection.RIGID
        ));
        
        final HolderGetter<WeightedHolderList<?>> weights = context.lookup(ModRegistries.WEIGHTED_HOLDER_LIST);
        final var small_rooms = List.of(
            ModPools.simpleElement(VasquilanCatacombPools.createId("room/brush_haven"),
                VasquilanCatacombPools.CATACOMBS_PROCESSORS
                    .withRule(sus_rule)
                    .build(),
                waterlog, 8),
            ModPools.simpleElement(VasquilanCatacombPools.createId("room/loot_haven"),
                VasquilanCatacombPools.CATACOMBS_PROCESSORS
                    .with(frame_loot_proc)
                    .build(),
                waterlog, 7),
            ModPools.simpleElement(VasquilanCatacombPools.createId("room/chest_haven"),
                catacombs_processors,
                waterlog, 6),
            ModPools.simpleElement(VasquilanCatacombPools.createId("room/deathrow/main"),
                catacombs_processors,
                waterlog, 6),
            ModPools.simpleElement(VasquilanCatacombPools.createId("room/liars_haven"),
                VasquilanCatacombPools.CATACOMBS_PROCESSORS
                    .with(new WeightedBlockReplacementProcessor(
                        new BlockMatchTest(Blocks.EMERALD_BLOCK),
                        weights.getOrThrow(ModWeightedHolderLists.CATACOMBS_STORAGE_BLOCK_TREASURE)))
                    .build(),
                waterlog, 5)
        );
        
        VasquilanCatacombPools.subPool(context, "rooms", new StructureTemplatePool(
            ending_pool,
            Util.make(new ArrayList<>(), (list ->
            {
                list.addAll(small_rooms);
                list.add(ModPools.simpleElement(VasquilanCatacombPools.createId("room/cross_section"),
                    catacombs_processors,
                    waterlog, 33));
                list.add(ModPools.simpleElement(VasquilanCatacombPools.createId("room/arena"),
                    VasquilanCatacombPools.CATACOMBS_PROCESSORS
                        .with(frame_loot_proc)
                        .build(),
                    waterlog, 33));
                list.add(ModPools.simpleElement(VasquilanCatacombPools.createId("room/hall"),
                    catacombs_processors,
                    waterlog, 30));
                list.add(ModPools.simpleElement(VasquilanCatacombPools.createId("room/pillar"),
                    catacombs_processors,
                    waterlog, 21));
                list.add(ModPools.simpleElement(VasquilanCatacombPools.createId("path/mud_1"),
                    catacombs_processors,
                    waterlog, 25));
                list.add(ModPools.simpleElement(VasquilanCatacombPools.createId("path/mud_2"),
                    catacombs_processors,
                    waterlog, 25));
                list.add(ModPools.simpleElement(VasquilanCatacombPools.createId("arena/labyrinth"),
                    VasquilanCatacombPools.CATACOMBS_PROCESSORS
                        .with(frame_loot_proc)
                        .build(),
                    waterlog, 30));
                list.add(dead_end);
            })),
            StructureTemplatePool.Projection.RIGID
        ));
        VasquilanCatacombPools.subPool(context, "small_rooms", new StructureTemplatePool(
            ending_pool,
            small_rooms,
            StructureTemplatePool.Projection.RIGID
        ));
        VasquilanCatacombPools.subPool(context, "arena_parts", new StructureTemplatePool(
            empty,
            List.of(
                ModPools.simpleElement(VasquilanCatacombPools.createId("arena/labyrinth"),
                    VasquilanCatacombPools.CATACOMBS_PROCESSORS
                        .with(frame_loot_proc)
                        .build(),
                    waterlog, 32),
                ModPools.simpleElement(VasquilanCatacombPools.createId("arena/snake"),
                    VasquilanCatacombPools.CATACOMBS_PROCESSORS
                        .with(frame_loot_proc)
                        .build(),
                    waterlog, 36),
                ModPools.simpleElement(VasquilanCatacombPools.createId("arena/trial"),
                    catacombs_processors,
                    waterlog, 28),
                ModPools.simpleElement(VasquilanCatacombPools.createId("arena/wall"),
                    catacombs_processors,
                    waterlog, 28)
            ),
            StructureTemplatePool.Projection.RIGID
        ));
        VasquilanCatacombPools.subPool(context, "arena", new StructureTemplatePool(
            empty,
            List.of(
                ModPools.simpleElement(VasquilanCatacombPools.createId("room/arena"),
                    VasquilanCatacombPools.CATACOMBS_PROCESSORS
                        .with(frame_loot_proc)
                        .build(),
                    waterlog, 5),
                ModPools.simpleElement(VasquilanCatacombPools.createId("arena/wall"),
                    catacombs_processors,
                    waterlog, 1)
            ),
            StructureTemplatePool.Projection.RIGID
        ));
        VasquilanCatacombPools.subPool(context, "compartments", new StructureTemplatePool(
            empty,
            List.of(
                ModPools.simpleElement(VasquilanCatacombPools.createId("room/deathrow/dead_end"),
                    ModPools.directProc(),
                    waterlog, 10),
                ModPools.simpleElement(VasquilanCatacombPools.createId("room/deathrow/exit"),
                    ModPools.directProc(),
                    waterlog, 1)
            ),
            StructureTemplatePool.Projection.RIGID
        ));
        VasquilanCatacombPools.subPool(context, "death_paths", new StructureTemplatePool(
            ending_pool,
            Util.make(new ArrayList<>(), (list ->
            {
                list.addAll(small_rooms);
                list.add(ModPools.uniqueElement(VasquilanCatacombPools.createId("room/deathrow/entrance"),
                    catacombs_processors,
                    waterlog, 1, 7));
            })),
            StructureTemplatePool.Projection.RIGID
        ));
    }
    
    //==================================================================================================================
    private static void subPool(final BootstrapContext<StructureTemplatePool> context,
                                final String                                  name,
                                final StructureTemplatePool                   pool)
    {
        ModPools.register(context, ("vasquilan_catacombs/" + name), pool);
    }
    
    private static ResourceLocation createId(final String name)
    {
        return VasquilanCatacombPools.START_ID.withSuffix("/" + name);
    }
}
