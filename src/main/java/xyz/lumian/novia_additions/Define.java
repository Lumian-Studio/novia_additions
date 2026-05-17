package xyz.lumian.novia_additions;


import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;



//**********************************************************************************************************************
public class Define
{
    //******************************************************************************************************************
    public static final String MOD_ID = "novia_additions";
    
    //******************************************************************************************************************
    public static ResourceLocation mod(final String name)
    {
        return ResourceLocation.fromNamespaceAndPath(Define.MOD_ID, name);
    }
    
    public static ResourceLocation common(final String name)
    {
        return ResourceLocation.fromNamespaceAndPath("c", name);
    }
    
    public static <T, R extends Registry<T>> ResourceKey<T> key(final ResourceKey<R> registry, final String name)
    {
        return ResourceKey.create(registry, Define.mod(name));
    }
}
