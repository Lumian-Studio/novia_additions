package xyz.lumian.novia_additions;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;



//**********************************************************************************************************************
@Mod(value = Define.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Define.MOD_ID, value = Dist.CLIENT)
public class NoviaAdditionsClient
{
    //******************************************************************************************************************
    public NoviaAdditionsClient(final ModContainer container) {}

    //==================================================================================================================
    @SubscribeEvent static void onClientSetup(final FMLClientSetupEvent e) {}
}
