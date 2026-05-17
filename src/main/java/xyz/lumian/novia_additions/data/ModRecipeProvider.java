package xyz.lumian.novia_additions.data;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import xyz.lumian.novia_additions.block.ModBlocks;
import xyz.lumian.novia_additions.item.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static xyz.lumian.novia_additions.item.ModItems.NOVIUM_PICKAXE;



//**********************************************************************************************************************
public class ModRecipeProvider
    extends RecipeProvider
{
    //******************************************************************************************************************
    public static final ImmutableList<ItemLike> NOVIUM_SMELTABLES = ImmutableList.of(
        ModItems.NOVIUM_PELLET, ModBlocks.DEEPSLATE_NOVIUM_ORE
    );
    
    public static final ImmutableList<ItemLike> NOVIUM_NUGGET_SMELTABLES = ImmutableList.of(
        NOVIUM_PICKAXE, ModItems.NOVIUM_AXE, ModItems.NOVIUM_HOE, ModItems.NOVIUM_SHOVEL, ModItems.NOVIUM_SWORD
    );
    
    public static final ImmutableList<ItemLike> DEMITHRIUM_NUGGET_SMELTABLES = ImmutableList.of(
        ModItems.DEMITHRIUM_PICKAXE, ModItems.DEMITHRIUM_AXE, ModItems.DEMITHRIUM_HOE, ModItems.DEMITHRIUM_SHOVEL,
        ModItems.DEMITHRIUM_SWORD
    );
    
    //******************************************************************************************************************
    public static void noviumSmithing(final RecipeOutput   output,
                                      final ItemLike       ingredient,
                                      final RecipeCategory category,
                                      final ItemLike       result)
    {
        SmithingTransformRecipeBuilder
            .smithing(
                Ingredient.of(ModItems.NOVIUM_UPGRADE_SMITHING_TEMPLATE),
                Ingredient.of(ingredient),
                Ingredient.of(ModItems.NOVIUM_INGOT),
                category,
                result.asItem())
            .unlocks("has_novium_ingot", RecipeProvider.has(ModItems.NOVIUM_INGOT))
            .save(output, RecipeProvider.getItemName(result) + "_smithing");
    }
    
    public static void demithriumSmithing(final RecipeOutput   output,
                                          final ItemLike       ingredient,
                                          final RecipeCategory category,
                                          final ItemLike       result)
    {
        SmithingTransformRecipeBuilder
            .smithing(
                Ingredient.of(ModItems.DEMITHRIUM_UPGRADE_SMITHING_TEMPLATE),
                Ingredient.of(ingredient),
                Ingredient.of(ModItems.DEMITHRIUM_INGOT),
                category,
                result.asItem())
            .unlocks("has_demithrium_ingot", RecipeProvider.has(ModItems.DEMITHRIUM_INGOT))
            .save(output, RecipeProvider.getItemName(result) + "_smithing");
    }
    
    public static void nuggetRescue(final RecipeOutput   output,
                                    final List<ItemLike> ingredients,
                                    final RecipeCategory category,
                                    final ItemLike       result)
    {
        {
            final SimpleCookingRecipeBuilder builder = SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(ingredients.stream().map(ItemStack::new)),
                category,
                result,
                0.5F, 200);
            ingredients.forEach(ingredient -> builder.unlockedBy(
                RecipeProvider.getHasName(ingredient),
                RecipeProvider.has(ingredient)));
            
            builder.save(output, RecipeProvider.getSmeltingRecipeName(result));
        }
        
        {
            final SimpleCookingRecipeBuilder builder = SimpleCookingRecipeBuilder.blasting(
                Ingredient.of(ingredients.stream().map(ItemStack::new)),
                category,
                result,
                0.5F, 100);
            ingredients.forEach(ingredient -> builder.unlockedBy(
                RecipeProvider.getHasName(ingredient),
                RecipeProvider.has(ingredient)));
            
            builder.save(output, RecipeProvider.getBlastingRecipeName(result));
        }
    }
    
    //******************************************************************************************************************
    public ModRecipeProvider(final PackOutput output, final CompletableFuture<HolderLookup.Provider> lookup)
    {
        super(output, lookup);
    }
    
    //==================================================================================================================
    @Override
    protected void buildRecipes(final RecipeOutput output, final HolderLookup.Provider lookup)
    {
        // Storage blocks
        RecipeProvider.nineBlockStorageRecipesRecipesWithCustomUnpacking(
            output,
            RecipeCategory.MISC,            ModItems.NOVIUM_PELLET,
            RecipeCategory.BUILDING_BLOCKS, ModBlocks.NOVIUM_PELLET_BLOCK,
            "novium_pellet_from_novium_pellet_block",
            "novium_ingot");
        RecipeProvider.nineBlockStorageRecipesRecipesWithCustomUnpacking(
            output,
            RecipeCategory.MISC,            ModItems.NOVIUM_INGOT,
            RecipeCategory.BUILDING_BLOCKS, ModBlocks.NOVIUM_BLOCK,
            "novium_block_from_novium_ingot",
            "novium_ingot");
        RecipeProvider.nineBlockStorageRecipesRecipesWithCustomUnpacking(
            output,
            RecipeCategory.MISC,            ModItems.DEMITHRIUM_INGOT,
            RecipeCategory.BUILDING_BLOCKS, ModBlocks.DEMITHRIUM_BLOCK,
            "demithrium_block_from_demithrium_ingot",
            "demithrium_ingot");
        RecipeProvider.twoByTwoPacker(output, RecipeCategory.BUILDING_BLOCKS, ModBlocks.DEMURIUM_BLOCK,
                                      ModItems.DEMURIUM_CRYSTAL);
        
        // Nuggets
        RecipeProvider.nineBlockStorageRecipesWithCustomPacking(
            output,
            RecipeCategory.MISC, ModItems.NOVIUM_NUGGET,
            RecipeCategory.MISC, ModItems.NOVIUM_INGOT,
            "novium_ingot_from_nuggets",
            "novium_ingot");
        RecipeProvider.nineBlockStorageRecipesWithCustomPacking(
            output,
            RecipeCategory.MISC, ModItems.DEMITHRIUM_NUGGET,
            RecipeCategory.MISC, ModItems.DEMITHRIUM_INGOT,
            "demithrium_ingot_from_nuggets",
            "demithrium_ingot");
        ModRecipeProvider.nuggetRescue(output, ModRecipeProvider.NOVIUM_NUGGET_SMELTABLES, RecipeCategory.MISC,
                                       ModItems.NOVIUM_NUGGET);
        ModRecipeProvider.nuggetRescue(output, ModRecipeProvider.DEMITHRIUM_NUGGET_SMELTABLES, RecipeCategory.MISC,
                                       ModItems.DEMITHRIUM_NUGGET);
        
        // Ingots
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.NOVIUM_INGOT)
            .requires(Items.NETHERITE_SCRAP,  4)
            .requires(ModItems.NOVIUM_PELLET, 2)
            .group("novium_ingot")
            .unlockedBy(RecipeProvider.getHasName(Items.NETHERITE_SCRAP), RecipeProvider.has(Items.NETHERITE_SCRAP))
            .save(output, RecipeProvider.getSimpleRecipeName(ModItems.NOVIUM_INGOT));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DEMITHRIUM_INGOT)
            .requires(ModItems.NOVIUM_INGOT,     4)
            .requires(ModItems.DEMURIUM_CRYSTAL, 2)
            .group("demithrium_ingot")
            .unlockedBy(
                RecipeProvider.getHasName(ModItems.DEMURIUM_CRYSTAL),
                RecipeProvider.has(ModItems.DEMURIUM_CRYSTAL))
            .save(output, RecipeProvider.getSimpleRecipeName(ModItems.DEMITHRIUM_INGOT));
        
        // Templates
        RecipeProvider.copySmithingTemplate(output, ModItems.NOVIUM_UPGRADE_SMITHING_TEMPLATE, Items.NETHERITE_INGOT);
        RecipeProvider.copySmithingTemplate(output, ModItems.DEMITHRIUM_UPGRADE_SMITHING_TEMPLATE,
                                            ModItems.NOVIUM_INGOT);
        
        // Smithing
        ModRecipeProvider.noviumSmithing(output, Items.NETHERITE_PICKAXE, RecipeCategory.TOOLS,
                                         ModItems.NOVIUM_PICKAXE);
        ModRecipeProvider.noviumSmithing(output, Items.NETHERITE_AXE, RecipeCategory.TOOLS, ModItems.NOVIUM_AXE);
        ModRecipeProvider.noviumSmithing(output, Items.NETHERITE_SHOVEL, RecipeCategory.TOOLS, ModItems.NOVIUM_SHOVEL);
        ModRecipeProvider.noviumSmithing(output, Items.NETHERITE_HOE, RecipeCategory.TOOLS, ModItems.NOVIUM_HOE);
        ModRecipeProvider.noviumSmithing(output, Items.NETHERITE_SWORD, RecipeCategory.COMBAT, ModItems.NOVIUM_SWORD);
        ModRecipeProvider.noviumSmithing(output, Items.NETHERITE_HELMET, RecipeCategory.COMBAT, ModItems.NOVIUM_HELMET);
        ModRecipeProvider.noviumSmithing(output, Items.NETHERITE_CHESTPLATE, RecipeCategory.COMBAT,
                                         ModItems.NOVIUM_CHESTPLATE);
        ModRecipeProvider.noviumSmithing(output, Items.NETHERITE_LEGGINGS, RecipeCategory.COMBAT,
                                         ModItems.NOVIUM_LEGGINS);
        ModRecipeProvider.noviumSmithing(output, Items.NETHERITE_BOOTS, RecipeCategory.COMBAT, ModItems.NOVIUM_BOOTS);
        
        ModRecipeProvider.demithriumSmithing(output, ModItems.NOVIUM_PICKAXE, RecipeCategory.TOOLS,
                                             ModItems.DEMITHRIUM_PICKAXE);
        ModRecipeProvider.demithriumSmithing(output, ModItems.NOVIUM_AXE, RecipeCategory.TOOLS,
                                             ModItems.DEMITHRIUM_AXE);
        ModRecipeProvider.demithriumSmithing(output, ModItems.NOVIUM_SHOVEL, RecipeCategory.TOOLS,
                                             ModItems.DEMITHRIUM_SHOVEL);
        ModRecipeProvider.demithriumSmithing(output, ModItems.NOVIUM_HOE, RecipeCategory.TOOLS,
                                             ModItems.DEMITHRIUM_HOE);
        ModRecipeProvider.demithriumSmithing(output, ModItems.NOVIUM_SWORD, RecipeCategory.COMBAT,
                                             ModItems.DEMITHRIUM_SWORD);
        ModRecipeProvider.demithriumSmithing(output, ModItems.NOVIUM_HELMET, RecipeCategory.COMBAT,
                                             ModItems.DEMITHRIUM_HELMET);
        ModRecipeProvider.demithriumSmithing(output, ModItems.NOVIUM_CHESTPLATE, RecipeCategory.COMBAT,
                                             ModItems.DEMITHRIUM_CHESTPLATE);
        ModRecipeProvider.demithriumSmithing(output, ModItems.NOVIUM_LEGGINS, RecipeCategory.COMBAT,
                                             ModItems.DEMITHRIUM_LEGGINS);
        ModRecipeProvider.demithriumSmithing(output, ModItems.NOVIUM_BOOTS, RecipeCategory.COMBAT,
                                             ModItems.DEMITHRIUM_BOOTS);
    }
}
