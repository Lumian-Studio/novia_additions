package xyz.lumian.novia_additions.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.lumian.novia_additions.Define;



//**********************************************************************************************************************
@Mixin(StructureTemplate.class)
public abstract class StructureTemplateMixin
{
    //******************************************************************************************************************
    /// Hot fixing issue MC-102223 with the fix from the comments (by user BluSpring)
    @Inject(
        method = "lambda$addEntitiesToWorld$5",
        at = @At(
            value  = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;moveTo(DDDFF)V",
            shift  = At.Shift.AFTER
        )
    )
    private static void fixPaintings(final StructurePlaceSettings settings, final Vec3 vec3,
                                     final ServerLevelAccessor levelAccessor, final Entity entity,
                                     final CallbackInfo ci)
    {
        if (!(entity instanceof Painting painting)) {
            return;
        }

        var variant = painting.getVariant().value();
        
        // added to bail out early if it's not a painting from the mod, so as to not affect other mod's code
        if (!variant.assetId().getNamespace().equals(Define.MOD_ID))
        {
            return;
        }
        
        var pos = new BlockPos.MutableBlockPos();
        pos.set(painting.getPos());
        
        var width = variant.width();
        var height = variant.height();
        var direction = painting.getDirection();

        // paintings with an even height seem to always be moved upwards...
        if ((height & 1) == 0) {
            pos.move(0, -1, 0);
        }

        // paintings with an even width seem to be moved in the clockwise direction of their facing direction,
        // if they're west or south.
        if ((width & 1) == 0 && (direction == Direction.WEST || direction == Direction.SOUTH)) {
            var moveTo = direction.getClockWise().getNormal();
            pos.move(moveTo);
        }

        painting.setPos(pos.getCenter());
    }
}
