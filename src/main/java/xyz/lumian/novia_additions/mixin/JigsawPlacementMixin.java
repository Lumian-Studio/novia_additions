package xyz.lumian.novia_additions.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.lumian.novia_additions.world.generator.structure.pool.ModStructurePoolElementTypes;
import xyz.lumian.novia_additions.world.generator.structure.pool.UniquePoolElement;

import java.util.Collection;



//**********************************************************************************************************************
@Mixin(targets = "net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement$Placer")
public class JigsawPlacementMixin
{
    //******************************************************************************************************************
    @Unique
    private final Object2IntMap<ResourceLocation> noviaAdditions$uniquePoolElements = new Object2IntOpenHashMap<>();
    
    //******************************************************************************************************************
    @ModifyArg(
        method = "tryPlacingChildren",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/List;addAll(Ljava/util/Collection;)Z",
            ordinal = 0
        ),
        index = 0
    )
    private Collection<StructurePoolElement> filterUniqueElements(final Collection<StructurePoolElement> c)
    {
        return c.stream().filter(element ->
        {
            if (element.getType() == ModStructurePoolElementTypes.UNIQUE)
            {
                final ResourceLocation location = ((UniquePoolElement) element).getId();
                final int              uses     = this.noviaAdditions$uniquePoolElements.getOrDefault(location, 1);
                return (uses > 0);
            }
            
            return true;
        }).toList();
    }
    
    @Inject(
        method = "tryPlacingChildren",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
            shift = At.Shift.AFTER
        )
    )
    private void addUniqueElements(final PoolElementStructurePiece piece, final MutableObject<VoxelShape> free,
                                   final int depth, final boolean useExpansionHack, final LevelHeightAccessor level,
                                   final RandomState random, final PoolAliasLookup poolAliasLookup,
                                   final LiquidSettings liquidSettings, final CallbackInfo ci,
                                   @Local(ordinal = 1) final PoolElementStructurePiece poolelementstructurepiece)
    {
        final StructurePoolElement element = poolelementstructurepiece.getElement();
        
        if (element.getType() == ModStructurePoolElementTypes.UNIQUE)
        {
            final UniquePoolElement pool_element = (UniquePoolElement) element;
            final ResourceLocation  id           = pool_element.getId();
            
            final int placements_left = this.noviaAdditions$uniquePoolElements
                .computeIfAbsent(id, (id1 -> pool_element.getMaxOccurrences()));
            this.noviaAdditions$uniquePoolElements.put(id, (placements_left - 1));
        }
    }
}
