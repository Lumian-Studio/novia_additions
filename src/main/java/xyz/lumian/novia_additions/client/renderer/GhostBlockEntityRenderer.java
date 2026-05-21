package xyz.lumian.novia_additions.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import xyz.lumian.novia_additions.block.TruthSeekerBlockBase;
import xyz.lumian.novia_additions.block.entity.GhostBlockEntity;



//**********************************************************************************************************************
public class GhostBlockEntityRenderer
    implements BlockEntityRenderer<GhostBlockEntity>
{
    //******************************************************************************************************************
    private final BlockRenderDispatcher       blockRenderDispatcher;
    private final BlockEntityRenderDispatcher blockEntityRenderDispatcher;
    
    //******************************************************************************************************************
    public GhostBlockEntityRenderer(final BlockEntityRendererProvider.Context context)
    {
        this.blockRenderDispatcher       = context.getBlockRenderDispatcher();
        this.blockEntityRenderDispatcher = context.getBlockEntityRenderDispatcher();
    }
    
    //==================================================================================================================
    @Override
    public void render(final GhostBlockEntity ghostBlockEntity, final float v, final PoseStack poseStack,
                       final MultiBufferSource multiBufferSource, final int i, final int i1)
    {
        final BlockState copy_state = ghostBlockEntity.getCopyState();
        
        {
            final Player player = Minecraft.getInstance().player;
            
            if (player != null && TruthSeekerBlockBase.isBlockInvisible(player, ghostBlockEntity.getBlockState()))
            {
                return;
            }
        }
        
        if (!copy_state.isAir())
        {
            final BlockEntity mock_be = ghostBlockEntity.getMockBlockEntity();
            
            if (mock_be != null)
            {
                this.blockEntityRenderDispatcher.render(mock_be, v, poseStack, multiBufferSource);
                return;
            }
            
            //noinspection DataFlowIssue
            this.blockRenderDispatcher.renderSingleBlock(copy_state, poseStack, multiBufferSource, i, i1,
                                                         ModelData.EMPTY, null);
        }
    }
}
