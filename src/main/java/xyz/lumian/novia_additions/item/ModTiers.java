package xyz.lumian.novia_additions.item;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import xyz.lumian.novia_additions.block.ModBlockTags;

import java.util.function.Supplier;



//**********************************************************************************************************************
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum ModTiers
    implements Tier
{
    NOVIUM    (16383,           13f, 16f, ModBlockTags.INCORRECT_FOR_NOVIUM_TOOL,     50, (() -> Ingredient.of(ModItems.NOVIUM_INGOT))),
    DEMURIUM  (32,              12f,  0F, BlockTags.INCORRECT_FOR_GOLD_TOOL,          22, (() -> Ingredient.of(ModItems.DEMURIUM_CRYSTAL))),
    DEMITHRIUM(Short.MAX_VALUE, 18f, 32f, ModBlockTags.INCORRECT_FOR_DEMITHRIUM_TOOL, 98, (() -> Ingredient.of(ModItems.DEMITHRIUM_INGOT))),
    ;
    
    //******************************************************************************************************************
    @Getter private final int           uses;
    @Getter private final float         speed;
    @Getter private final float         attackDamageBonus;
    @Getter private final TagKey<Block> incorrectBlocksForDrops;
    @Getter private final int           enchantmentValue;
    
    private final Supplier<Ingredient> repairIngredient;
    
    //******************************************************************************************************************
    @Override public Ingredient getRepairIngredient() { return this.repairIngredient.get(); }
}
