package xyz.lumian.novia_additions.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.lumian.novia_additions.block.TruthSeekerBlockBase;



//**********************************************************************************************************************
@Mixin(LivingEntityRenderer.class)
@OnlyIn(Dist.CLIENT)
public class LivingEntityRendererMixin<T extends LivingEntity>
{
    //******************************************************************************************************************
    @Inject(
        method      = "getRenderType",
        at          = @At("HEAD"),
        cancellable = true
    )
    protected void getRenderType(final T livingEntity, final boolean bodyVisible, final boolean translucent,
                                 final boolean glowing, final CallbackInfoReturnable<@Nullable RenderType> cir)
    {
        final Player player = Minecraft.getInstance().player;
        
        if (player != null && TruthSeekerBlockBase.isEntityInvisible(player, livingEntity))
        {
            cir.setReturnValue(null);
        }
    }
}
