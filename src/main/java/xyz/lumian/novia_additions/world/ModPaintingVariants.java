package xyz.lumian.novia_additions.world;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.PaintingVariant;
import xyz.lumian.novia_additions.Define;



//**********************************************************************************************************************
public class ModPaintingVariants
{
    //******************************************************************************************************************
    public static final ResourceKey<PaintingVariant> SKULL = create("skull");
    
    //******************************************************************************************************************
    public static void bootstrap(final BootstrapContext<PaintingVariant> context)
    {
        ModPaintingVariants.register(context, ModPaintingVariants.SKULL, 3, 4);
    }
    
    //==================================================================================================================
    public static ResourceKey<PaintingVariant> create(final String name)
    {
        return Define.key(Registries.PAINTING_VARIANT, name);
    }
    
    private static void register(final BootstrapContext<PaintingVariant> context,
                                 final ResourceKey<PaintingVariant> key, final int width, final int height)
    {
        context.register(key, new PaintingVariant(width, height, key.location()));
    }
}
