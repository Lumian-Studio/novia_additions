package xyz.lumian.novia_additions.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import xyz.lumian.novia_additions.Define;
import xyz.lumian.novia_additions.entity.LumaProjectile;



//**********************************************************************************************************************
public class LumaProjectileRenderer
    extends EntityRenderer<LumaProjectile>
{
    //******************************************************************************************************************
    public static final ResourceLocation LUMA_LOCATION = Define.mod("textures/entity/projectiles/luma.png");
    
    //******************************************************************************************************************
    protected LumaProjectileRenderer(final EntityRendererProvider.Context context) { super(context); }
    
    //==================================================================================================================
    @Override
    public ResourceLocation getTextureLocation(final LumaProjectile lumaProjectile)
    {
        return LumaProjectileRenderer.LUMA_LOCATION;
    }
    
    @Override protected int getBlockLightLevel(final LumaProjectile entity, final BlockPos pos) { return 15; }
    
    //==================================================================================================================
    @Override
    public void render(final LumaProjectile projectile, final float entityYaw, final float partialTick,
                       final PoseStack poseStack, final MultiBufferSource bufferSource, final int packedLight)
    {
        final VertexConsumer vertices = bufferSource.getBuffer(RenderType.TRANSLUCENT);
        
        final float x_rot = Mth.lerp(partialTick, projectile.getOldRotX(), projectile.getXRot());
        final float y_rot = Mth.lerp(partialTick, projectile.getOldRotY(), projectile.getYRot());
        
        poseStack.pushPose();
        {
            final PoseStack.Pose pose = poseStack.last();
            poseStack.scale(0.12F, 0.12F, 0.12F);
            
            poseStack.rotateAround(Axis.XP.rotation(x_rot),      0f, 0f, 0f);
            poseStack.rotateAround(Axis.YP.rotation(y_rot),      0f, 0f, 0f);
            poseStack.rotateAround(Axis.ZP.rotation(x_rot - y_rot), 0f, 0f, 0f);
            LumaProjectileRenderer.cube(pose, vertices, 0x77ffffbb, packedLight);
        }
        poseStack.popPose();
        
        poseStack.pushPose();
        {
            final PoseStack.Pose pose = poseStack.last();
            poseStack.scale(0.25F, 0.25F, 0.25F);
            poseStack.rotateAround(Axis.ZP.rotation(x_rot),      0f, 0f, 0f);
            poseStack.rotateAround(Axis.YP.rotation(y_rot),      0f, 0f, 0f);
            poseStack.rotateAround(Axis.XP.rotation(y_rot - x_rot), 0f, 0f, 0f);
            
            LumaProjectileRenderer.cube(pose, vertices, 0x77ffffff, packedLight);
        }
        poseStack.popPose();
        
        super.render(projectile, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
    
    //------------------------------------------------------------------------------------------------------------------
    private static void cube(final PoseStack.Pose pose, final VertexConsumer vertices, final int colour,
                             final int packedLight)
    {
        LumaProjectileRenderer.vertex(pose, vertices, colour,  1f,  1f,  1f, packedLight,  0f,  0f,  1f);
        LumaProjectileRenderer.vertex(pose, vertices, colour, -1f,  1f,  1f, packedLight,  0f,  0f,  1f);
        LumaProjectileRenderer.vertex(pose, vertices, colour, -1f, -1f,  1f, packedLight,  0f,  0f,  1f);
        LumaProjectileRenderer.vertex(pose, vertices, colour,  1f, -1f,  1f, packedLight,  0f,  0f,  1f);
        LumaProjectileRenderer.vertex(pose, vertices, colour,  1f,  1f, -1f, packedLight,  0f,  0f, -1f);
        LumaProjectileRenderer.vertex(pose, vertices, colour,  1f, -1f, -1f, packedLight,  0f,  0f, -1f);
        LumaProjectileRenderer.vertex(pose, vertices, colour, -1f, -1f, -1f, packedLight,  0f,  0f, -1f);
        LumaProjectileRenderer.vertex(pose, vertices, colour, -1f,  1f, -1f, packedLight,  0f,  0f, -1f);
        LumaProjectileRenderer.vertex(pose, vertices, colour,  1f,  1f,  1f, packedLight,  1f,  0f,  0f);
        LumaProjectileRenderer.vertex(pose, vertices, colour,  1f, -1f,  1f, packedLight,  1f,  0f,  0f);
        LumaProjectileRenderer.vertex(pose, vertices, colour,  1f, -1f, -1f, packedLight,  1f,  0f,  0f);
        LumaProjectileRenderer.vertex(pose, vertices, colour,  1f,  1f, -1f, packedLight,  1f,  0f,  0f);
        LumaProjectileRenderer.vertex(pose, vertices, colour, -1f,  1f,  1f, packedLight, -1f,  0f,  0f);
        LumaProjectileRenderer.vertex(pose, vertices, colour, -1f,  1f, -1f, packedLight, -1f,  0f,  0f);
        LumaProjectileRenderer.vertex(pose, vertices, colour, -1f, -1f, -1f, packedLight, -1f,  0f,  0f);
        LumaProjectileRenderer.vertex(pose, vertices, colour, -1f, -1f,  1f, packedLight, -1f,  0f,  0f);
        LumaProjectileRenderer.vertex(pose, vertices, colour,  1f,  1f,  1f, packedLight,  0f,  1f,  0f);
        LumaProjectileRenderer.vertex(pose, vertices, colour,  1f,  1f, -1f, packedLight,  0f,  1f,  0f);
        LumaProjectileRenderer.vertex(pose, vertices, colour, -1f,  1f, -1f, packedLight,  0f,  1f,  0f);
        LumaProjectileRenderer.vertex(pose, vertices, colour, -1f,  1f,  1f, packedLight,  0f,  1f,  0f);
        LumaProjectileRenderer.vertex(pose, vertices, colour,  1f, -1f,  1f, packedLight,  0f, -1f,  0f);
        LumaProjectileRenderer.vertex(pose, vertices, colour, -1f, -1f,  1f, packedLight,  0f, -1f,  0f);
        LumaProjectileRenderer.vertex(pose, vertices, colour, -1f, -1f, -1f, packedLight,  0f, -1f,  0f);
        LumaProjectileRenderer.vertex(pose, vertices, colour,  1f, -1f, -1f, packedLight,  0f, -1f,  0f);
    }
    
    private static void vertex(final PoseStack.Pose pose, final VertexConsumer consumer, final int colour,
                               final float x, final float y, final float z, final int light, final float nx,
                               final float ny, final float nz)
    {
        consumer
            .addVertex(pose, x, y, z)
            .setColor(colour)
            .setUv(0, 0)
            .setLight(light)
            .setNormal(nx, ny, nz);
    }
}
