package xyz.lumian.novia_additions.client.renderer;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import xyz.lumian.novia_additions.block.ModBlockEntities;



//**********************************************************************************************************************
@EventBusSubscriber
public final class ModBlockEntityRenderers
{
    //******************************************************************************************************************
    @SubscribeEvent
    public static void registerEntityRenderers(final EntityRenderersEvent.RegisterRenderers e)
    {
        e.registerBlockEntityRenderer(ModBlockEntities.GHOST_BLOCK.get(), GhostBlockEntityRenderer::new);
    }
}
