package xyz.lumian.novia_additions.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatterns;
import xyz.lumian.novia_additions.Define;



//**********************************************************************************************************************
public class ModBannerPatterns
{
    //******************************************************************************************************************
    public static final ResourceKey<BannerPattern> NOVIA = create("novia");
    
    //==================================================================================================================
    public static final TagKey<BannerPattern> PATTERN_ITEM_NOVIA = createTag("pattern_item/novia");
    
    //******************************************************************************************************************
    public static void bootstrap(final BootstrapContext<BannerPattern> context)
    {
        BannerPatterns.register(context, ModBannerPatterns.NOVIA);
    }
    
    //==================================================================================================================
    private static ResourceKey<BannerPattern> create(final String name)
    {
        return Define.key(Registries.BANNER_PATTERN, name);
    }
    
    private static TagKey<BannerPattern> createTag(final String name)
    {
        return TagKey.create(Registries.BANNER_PATTERN, Define.mod(name));
    }
}
