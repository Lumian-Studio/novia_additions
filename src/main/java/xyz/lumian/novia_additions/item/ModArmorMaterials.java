package xyz.lumian.novia_additions.item;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.lumian.novia_additions.Define;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;



//**********************************************************************************************************************
public class ModArmorMaterials
{
    //******************************************************************************************************************
    public static final DeferredRegister<ArmorMaterial> MATERIALS = DeferredRegister
        .create(BuiltInRegistries.ARMOR_MATERIAL, Define.MOD_ID);
    
    //==================================================================================================================
    public static final Holder<ArmorMaterial> NOVIUM = register("novium",
        50,
        SoundEvents.ARMOR_EQUIP_NETHERITE,
        (() -> Ingredient.of(ModItems.NOVIUM_INGOT.get())),
        5f,
        0.15f,
        4, 9, 7, 4);
    public static final Holder<ArmorMaterial> DEMITHRIUM = register("demithrium",
        100,
        SoundEvents.ARMOR_EQUIP_NETHERITE,
        (() -> Ingredient.of(ModItems.DEMITHRIUM_INGOT.get())),
        9f,
        0.25f,
        6, 11, 9, 6);
    
    //******************************************************************************************************************
    public static Holder<ArmorMaterial> register(
        final String               name,
        final int                  enchantmentValue,
        final Holder<SoundEvent>   equipSound,
        final Supplier<Ingredient> repairItem,
        final float                toughness,
        final float                knockbackResistance,
        final int helmet, final int chestplate, final int leggings, final int boots
    )
    {
        final List<ArmorMaterial.Layer> layers   = Collections.singletonList(new ArmorMaterial.Layer(Define.mod(name)));
        final ArmorMaterial             material = new ArmorMaterial(
            Map.of(
                ArmorItem.Type.HELMET,     helmet,
                ArmorItem.Type.CHESTPLATE, chestplate,
                ArmorItem.Type.LEGGINGS,   leggings,
                ArmorItem.Type.BOOTS,      boots
            ),
            enchantmentValue, equipSound, repairItem, layers, toughness, knockbackResistance);
        return ModArmorMaterials.MATERIALS.register(name, (() -> material));
    }
}
