package xyz.lumian.novia_additions.item;

import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import xyz.lumian.novia_additions.Define;



//**********************************************************************************************************************
public class ModItemTags
{
    //******************************************************************************************************************
    public static final TagKey<Item> ARMOR_SETS       = mod("armor_sets");
    public static final TagKey<Item> NOVIUM_ARMOR     = mod("novium_armor");
    public static final TagKey<Item> DEMITHRIUM_ARMOR = mod("demithrium_armor");
    public static final TagKey<Item> HEAD_ARMOR       = mod("head_armor");
    public static final TagKey<Item> CHEST_ARMOR      = mod("chest_armor");
    public static final TagKey<Item> LEG_ARMOR        = mod("leg_armor");
    public static final TagKey<Item> FOOT_ARMOR       = mod("foot_armor");
    
    public static final TagKey<Item> MUSIC_DISCS = mod("music_discs");
    
    //==================================================================================================================
    public static final TagKey<Item> CAN_WALK_ON_POWDERED_SNOW    = c("equipment/feet/can_walk_on_powdered_snow");
    public static final TagKey<Item> RESISTANT_TO_FIRE            = c("equipment/chest/resistant_to_fire");
    public static final TagKey<Item> RESISTANT_TO_WITHER_EFFECT   = c("equipment/chest/resistant_to_wither_effect");
    public static final TagKey<Item> SOOTHES_PIGLINS              = c("equipment/soothes_piglins");
    public static final TagKey<Item> GRANTS_MAX_AIR_SUPPLY        = c("equipment/head/grants_max_air_supply");
    public static final TagKey<Item> RESISTANT_TO_DARKNESS_EFFECT = c("equipment/head/resistant_to_darkness_effect");
    
    //******************************************************************************************************************
    private static TagKey<Item> mod(final String name) { return ItemTags.create(Define.mod(name)); }
    
    private static TagKey<Item> c(final String name) { return ItemTags.create(Define.common(name)); }
}
