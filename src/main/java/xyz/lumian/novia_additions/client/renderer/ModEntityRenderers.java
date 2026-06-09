package xyz.lumian.novia_additions.client.renderer;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import xyz.lumian.novia_additions.block.ModBlockEntities;
import xyz.lumian.novia_additions.entity.ModEntityTypes;



//**********************************************************************************************************************
@EventBusSubscriber
public final class ModEntityRenderers
{
    //******************************************************************************************************************
    @SubscribeEvent
    public static void registerEntityRenderers(final EntityRenderersEvent.RegisterRenderers e)
    {
        e.registerBlockEntityRenderer(ModBlockEntities.GHOST_BLOCK.get(), GhostBlockEntityRenderer::new);
        e.registerEntityRenderer(ModEntityTypes.LUMA_PROJECTILE.get(), LumaProjectileRenderer::new);
    }
}
