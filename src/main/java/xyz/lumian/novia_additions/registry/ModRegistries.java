package xyz.lumian.novia_additions.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import xyz.lumian.novia_additions.Define;
import xyz.lumian.novia_additions.world.generator.WeightedHolderList;



//**********************************************************************************************************************
@EventBusSubscriber
public final class ModRegistries
{
    //******************************************************************************************************************
    public static final ResourceKey<Registry<WeightedHolderList<?>>> WEIGHTED_HOLDER_LIST
        = createKey("weighted_holder_list");
    
    //******************************************************************************************************************
    @SubscribeEvent
    public static void registerDynamicRegistries(final DataPackRegistryEvent.NewRegistry e)
    {
        e.dataPackRegistry(ModRegistries.WEIGHTED_HOLDER_LIST, WeightedHolderList.CODEC);
    }
    
    //==================================================================================================================
    public static <T> ResourceKey<Registry<T>> createKey(final String name)
    {
        return ResourceKey.createRegistryKey(Define.mod(name));
    }
}
