package xyz.lumian.novia_additions.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.PaintingRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.lumian.novia_additions.block.TruthSeekerBlockBase;



//**********************************************************************************************************************
@Mixin(PaintingRenderer.class)
@OnlyIn(Dist.CLIENT)
public class PaintingRendererMixin
{
    //******************************************************************************************************************
    @Inject(
        method      = "renderPainting",
        at          = @At("HEAD"),
        cancellable = true
    )
    private static void renderPainting(final PoseStack pose, final VertexConsumer consumer, final Painting painting,
                                       final int width, final int height, final TextureAtlasSprite paintingSprite,
                                       final TextureAtlasSprite backSprite, final CallbackInfo ci)
    {
        final Player player = Minecraft.getInstance().player;
        
        if (player != null && TruthSeekerBlockBase.isEntityInvisible(player, painting))
        {
            ci.cancel();
        }
    }
}
