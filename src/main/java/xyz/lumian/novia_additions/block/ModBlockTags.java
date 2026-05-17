package xyz.lumian.novia_additions.block;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import xyz.lumian.novia_additions.Define;



//**********************************************************************************************************************
public class ModBlockTags
{
    //******************************************************************************************************************
    public static final TagKey<Block> INCORRECT_FOR_NOVIUM_TOOL     = mod("incorrect_for_novium_tool");
    public static final TagKey<Block> INCORRECT_FOR_DEMITHRIUM_TOOL = mod("incorrect_for_demithrium_tool");
    
    public static final TagKey<Block> NEEDS_STARTER_TOOL    = mod("needs_starter_tool");
    public static final TagKey<Block> NEEDS_NOVIUM_TOOL     = mod("needs_novium_tool");
    public static final TagKey<Block> NEEDS_DEMITHRIUM_TOOL = mod("needs_demithrium_tool");
    
    //==================================================================================================================
    public static final TagKey<Block> ORES_IN_GROUND_END_STONE = c("ores_in_ground/end_stone");
    
    public static final TagKey<Block> END_STONE_ORE_REPLACEABLES = c("end_stone_ore_replaceables");
    
    //******************************************************************************************************************
    private static TagKey<Block> mod(final String name) { return BlockTags.create(Define.mod(name)); }
    
    private static TagKey<Block> c(final String name) { return BlockTags.create(Define.common(name)); }
}
