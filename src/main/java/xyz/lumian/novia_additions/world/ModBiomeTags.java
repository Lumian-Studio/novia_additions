package xyz.lumian.novia_additions.world;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import xyz.lumian.novia_additions.Define;



//**********************************************************************************************************************
public class ModBiomeTags
{
    //******************************************************************************************************************
    public static final TagKey<Biome> HAS_SHRINE       = mod("has_structure/demithril_shrine");
    public static final TagKey<Biome> HAS_CATACOMBS    = mod("has_structure/vasquilan_catacombs");
    public static final TagKey<Biome> HAS_NOVIUM_ORE   = mod("has_ore/novium");
    public static final TagKey<Biome> HAS_DEMURIUM_ORE = mod("has_ore/demurium");
    
    //******************************************************************************************************************
    public static TagKey<Biome> mod(final String name) { return TagKey.create(Registries.BIOME, Define.mod(name)); }
}
