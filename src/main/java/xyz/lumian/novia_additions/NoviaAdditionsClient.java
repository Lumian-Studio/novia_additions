package xyz.lumian.novia_additions;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import xyz.lumian.novia_additions.client.renderer.TruthseekerRenderer;



//**********************************************************************************************************************
@Mod(value = Define.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Define.MOD_ID, value = Dist.CLIENT)
public class NoviaAdditionsClient
{
    //******************************************************************************************************************
    public NoviaAdditionsClient(final ModContainer container)
    {
        NeoForge.EVENT_BUS.register(TruthseekerRenderer.INSTANCE);
    }
    
    //==================================================================================================================
    @SubscribeEvent private static void onClientSetup(final FMLClientSetupEvent e) {}
}
