package xyz.lumian.novia_additions.world.generator;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import xyz.lumian.novia_additions.Define;
import xyz.lumian.novia_additions.world.ModBiomeTags;
import xyz.lumian.novia_additions.world.generator.structure.pool.DemithrilShrinePools;
import xyz.lumian.novia_additions.world.generator.structure.pool.VasquilanCatacombPools;

import java.util.List;
import java.util.Optional;



//**********************************************************************************************************************
public class ModStructures
{
    //******************************************************************************************************************
    public static final ResourceKey<Structure> SHRINE    = create("demithril_shrine");
    public static final ResourceKey<Structure> CATACOMBS = create("vasquilan_catacombs");
    
    //******************************************************************************************************************
    public static void bootstrap(final BootstrapContext<Structure> context)
    {
        final HolderGetter<StructureTemplatePool> pools  = context.lookup(Registries.TEMPLATE_POOL);
        final HolderGetter<Biome>                 biomes = context.lookup(Registries.BIOME);
        context.register(ModStructures.SHRINE, new JigsawStructure(
            (new Structure.StructureSettings.Builder(biomes.getOrThrow(ModBiomeTags.HAS_SHRINE)))
                .generationStep(GenerationStep.Decoration.SURFACE_STRUCTURES)
                .terrainAdapation(TerrainAdjustment.BEARD_THIN)
                .build(),
            pools.getOrThrow(DemithrilShrinePools.START),
            Optional.empty(),
            4,
            ConstantHeight.of(VerticalAnchor.absolute(0)),
            false,
            Optional.of(Heightmap.Types.WORLD_SURFACE),
            40,
            List.of(),
            DimensionPadding.ZERO,
            JigsawStructure.DEFAULT_LIQUID_SETTINGS));
        context.register(ModStructures.CATACOMBS, new JigsawStructure(
            (new Structure.StructureSettings.Builder(biomes.getOrThrow(ModBiomeTags.HAS_CATACOMBS)))
                .generationStep(GenerationStep.Decoration.UNDERGROUND_STRUCTURES)
                .terrainAdapation(TerrainAdjustment.BURY)
                .build(),
            pools.getOrThrow(VasquilanCatacombPools.START),
            Optional.empty(),
            16,
            ConstantHeight.of(VerticalAnchor.absolute(-34)),
            false,
            Optional.of(Heightmap.Types.WORLD_SURFACE_WG),
            110,
            List.of(),
            DimensionPadding.ZERO,
            LiquidSettings.IGNORE_WATERLOGGING));
    }
    
    //==================================================================================================================
    private static ResourceKey<Structure> create(final String name) { return Define.key(Registries.STRUCTURE, name); }
}
