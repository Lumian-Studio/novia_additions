package xyz.lumian.novia_additions.world.generator.structure;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import xyz.lumian.novia_additions.Define;
import xyz.lumian.novia_additions.world.generator.ModStructures;

import java.util.List;



//**********************************************************************************************************************
public class ModStructureSets
{
    //******************************************************************************************************************
    public static final ResourceKey<StructureSet> SHRINES   = create("shrines");
    public static final ResourceKey<StructureSet> CATACOMBS = create("catacombs");
    
    //******************************************************************************************************************
    public static void bootstrap(final BootstrapContext<StructureSet> context)
    {
        final HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);
        context.register(ModStructureSets.SHRINES, new StructureSet(
            List.of(new StructureSet.StructureSelectionEntry(structures.getOrThrow(ModStructures.SHRINE), 1)),
            new RandomSpreadStructurePlacement(57, 23, RandomSpreadType.LINEAR, 729453476)));
        /*context.register(ModStructureSets.CATACOMBS, new StructureSet(
            List.of(new StructureSet.StructureSelectionEntry(structures.getOrThrow(ModStructures.CATACOMBS), 1)),
            new RandomSpreadStructurePlacement(30, 20, RandomSpreadType.LINEAR, 309340239)));*/
    }
    
    //==================================================================================================================
    private static ResourceKey<StructureSet> create(final String name)
    {
        return Define.key(Registries.STRUCTURE_SET, name);
    }
}
