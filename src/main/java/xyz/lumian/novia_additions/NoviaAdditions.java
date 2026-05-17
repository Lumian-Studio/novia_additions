package xyz.lumian.novia_additions;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import xyz.lumian.novia_additions.block.ModBlocks;
import xyz.lumian.novia_additions.item.ModArmorMaterials;
import xyz.lumian.novia_additions.item.ModItems;
import xyz.lumian.novia_additions.world.generator.ModStructureProcessors;



//**********************************************************************************************************************
@Mod(Define.MOD_ID)
public class NoviaAdditions
{
    //******************************************************************************************************************
    public static final Logger LOGGER = LogUtils.getLogger();
    
    //******************************************************************************************************************
    public NoviaAdditions(final IEventBus modEventBus, final ModContainer modContainer)
    {
        NoviaAdditions.LOGGER.info("Initialising registries");
        
        ModItems              .ITEMS     .register(modEventBus);
        ModBlocks             .BLOCKS    .register(modEventBus);
        ModSoundEvents        .SOUNDS    .register(modEventBus);
        ModArmorMaterials     .MATERIALS .register(modEventBus);
        ModStructureProcessors.PROCESSORS.register(modEventBus);
        
        NeoForge.EVENT_BUS.register(this);
    }
    
    //==================================================================================================================
    @SubscribeEvent public void onServerStarting(final ServerStartingEvent event) {}
}
