package xyz.lumian.novia_additions.item;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Unbreakable;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.lumian.novia_additions.Define;
import xyz.lumian.novia_additions.block.ModBlocks;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;



//**********************************************************************************************************************
@EventBusSubscriber
public class ModItems
{
    //******************************************************************************************************************
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Define.MOD_ID);
    
    //==================================================================================================================
    public static final Component NOVIUM_UPGRADE_APPLIES_TO = Component
        .translatable(Util.makeDescriptionId("item", Define.mod("smithing_template.novium_upgrade.applies_to")))
        .withStyle(ChatFormatting.BLUE);
    public static final Component NOVIUM_UPGRADE_INGREDIENTS = Component
        .translatable(Util.makeDescriptionId("item", Define.mod("smithing_template.novium_upgrade.ingredients")))
        .withStyle(ChatFormatting.BLUE);
    public static final Component NOVIUM_UPGRADE = Component
        .translatable(Util.makeDescriptionId("upgrade", Define.mod("novium_upgrade")))
        .withStyle(ChatFormatting.GRAY);
    public static final Component NOVIUM_UPGRADE_BASE_SLOT_DESCRIPTION = Component
        .translatable(Util.makeDescriptionId(
            "item",
            Define.mod("smithing_template.novium_upgrade.base_slot_description")));
    public static final Component NOVIUM_UPGRADE_ADDITIONS_SLOT_DESCRIPTION = Component
        .translatable(Util.makeDescriptionId(
            "item",
            Define.mod("smithing_template.novium_upgrade.additions_slot_description")));
    public static final Component DEMITHRIUM_UPGRADE_APPLIES_TO = Component
        .translatable(Util.makeDescriptionId("item", Define.mod("smithing_template.demithrium_upgrade.applies_to")))
        .withStyle(ChatFormatting.BLUE);
    public static final Component DEMITHRIUM_UPGRADE_INGREDIENTS = Component
        .translatable(Util.makeDescriptionId("item", Define.mod("smithing_template.demithrium_upgrade.ingredients")))
        .withStyle(ChatFormatting.BLUE);
    public static final Component DEMITHRIUM_UPGRADE = Component
        .translatable(Util.makeDescriptionId("upgrade", Define.mod("demithrium_upgrade")))
        .withStyle(ChatFormatting.GRAY);
    public static final Component DEMITHRIUM_UPGRADE_BASE_SLOT_DESCRIPTION = Component
        .translatable(Util.makeDescriptionId(
            "item",
            Define.mod("smithing_template.demithrium_upgrade.base_slot_description")));
    public static final Component DEMITHRIUM_UPGRADE_ADDITIONS_SLOT_DESCRIPTION = Component
        .translatable(Util.makeDescriptionId(
            "item",
            Define.mod("smithing_template.demithrium_upgrade.additions_slot_description")));
    
    //==================================================================================================================
    // ITEMS
    public static final DeferredItem<Item> MUSIC_DISC_REVOLUTION = register("music_disc_revolution",
        (props -> new Item(props
            .stacksTo(1)
            .rarity(Rarity.EPIC)
            .jukeboxPlayable(ModJukeboxSongs.REVOLUTION))));
    
    public static final DeferredItem<Item> MUSIC_DISC_EVOLUTION = register("music_disc_evolution",
        (props -> new Item(props
            .stacksTo(1)
            .rarity(Rarity.EPIC)
            .jukeboxPlayable(ModJukeboxSongs.EVOLUTION))));
    
    public static final DeferredItem<Item> NOVIA_BANNER_PATTERN = register("novia_banner_pattern", (props ->
        new BannerPatternItem(ModBannerPatterns.PATTERN_ITEM_NOVIA, props
            .rarity(Rarity.RARE)
            .stacksTo(1))));
    
    //..................................................................................................................
    public static final DeferredItem<Item> TRUTHSEEKER = register("truthseeker", (props ->
        new TruthSeekerItem(props.stacksTo(1).rarity(Rarity.EPIC))));
    
    //..................................................................................................................
    // NOVIUM & DEMITHRIUM STUFF
    public static final DeferredItem<Item> NOVIUM_PELLET = register("novium_pellet", (props ->
        new Item(props.fireResistant())));
    public static final DeferredItem<Item> DEMURIUM_CRYSTAL  = register("demurium_crystal", Item::new);
    
    public static final DeferredItem<Item> NOVIUM_INGOT = register("novium_ingot", (props ->
        new Item(props.fireResistant())));
    public static final DeferredItem<Item> DEMITHRIUM_INGOT = register("demithrium_ingot", (props ->
        new Item(props.fireResistant())));
    
    public static final DeferredItem<Item> NOVIUM_NUGGET = register("novium_nugget", (props -> new Item(props
        .fireResistant())));
    public static final DeferredItem<Item> DEMITHRIUM_NUGGET = register("demithrium_nugget", (props -> new Item(props
        .fireResistant())));
    
    public static final DeferredItem<Item> NOVIUM_UPGRADE_SMITHING_TEMPLATE = register(
        "novium_upgrade_smithing_template",
        (props -> new SmithingTemplateItem(
            ModItems.NOVIUM_UPGRADE_APPLIES_TO,
            ModItems.NOVIUM_UPGRADE_INGREDIENTS,
            ModItems.NOVIUM_UPGRADE,
            ModItems.NOVIUM_UPGRADE_BASE_SLOT_DESCRIPTION,
            ModItems.NOVIUM_UPGRADE_ADDITIONS_SLOT_DESCRIPTION,
            List.of(
                ResourceLocation.withDefaultNamespace("item/empty_armor_slot_helmet"),
                ResourceLocation.withDefaultNamespace("item/empty_armor_slot_chestplate"),
                ResourceLocation.withDefaultNamespace("item/empty_armor_slot_leggings"),
                ResourceLocation.withDefaultNamespace("item/empty_armor_slot_boots"),
                ResourceLocation.withDefaultNamespace("item/empty_slot_hoe"),
                ResourceLocation.withDefaultNamespace("item/empty_slot_axe"),
                ResourceLocation.withDefaultNamespace("item/empty_slot_sword"),
                ResourceLocation.withDefaultNamespace("item/empty_slot_shovel"),
                ResourceLocation.withDefaultNamespace("item/empty_slot_pickaxe")
            ),
            Collections.singletonList(ResourceLocation.withDefaultNamespace("item/empty_slot_ingot")))
        ));
    public static final DeferredItem<Item> DEMITHRIUM_UPGRADE_SMITHING_TEMPLATE = register(
        "demithrium_upgrade_smithing_template",
        (props -> new SmithingTemplateItem(
            ModItems.DEMITHRIUM_UPGRADE_APPLIES_TO,
            ModItems.DEMITHRIUM_UPGRADE_INGREDIENTS,
            ModItems.DEMITHRIUM_UPGRADE,
            ModItems.DEMITHRIUM_UPGRADE_BASE_SLOT_DESCRIPTION,
            ModItems.DEMITHRIUM_UPGRADE_ADDITIONS_SLOT_DESCRIPTION,
            List.of(
                ResourceLocation.withDefaultNamespace("item/empty_armor_slot_helmet"),
                ResourceLocation.withDefaultNamespace("item/empty_armor_slot_chestplate"),
                ResourceLocation.withDefaultNamespace("item/empty_armor_slot_leggings"),
                ResourceLocation.withDefaultNamespace("item/empty_armor_slot_boots"),
                ResourceLocation.withDefaultNamespace("item/empty_slot_hoe"),
                ResourceLocation.withDefaultNamespace("item/empty_slot_axe"),
                ResourceLocation.withDefaultNamespace("item/empty_slot_sword"),
                ResourceLocation.withDefaultNamespace("item/empty_slot_shovel"),
                ResourceLocation.withDefaultNamespace("item/empty_slot_pickaxe")
            ),
            Collections.singletonList(ResourceLocation.withDefaultNamespace("item/empty_slot_ingot")))
        ));
    
    public static final DeferredItem<Item> NOVIUM_PICKAXE = register("novium_pickaxe", (props ->
        new PickaxeItem(ModTiers.NOVIUM, props
            .rarity(Rarity.EPIC)
            .fireResistant()
            .attributes(PickaxeItem.createAttributes(ModTiers.NOVIUM, 6.0f, -0.6f)))));
    public static final DeferredItem<Item> DEMITHRIUM_PICKAXE = register("demithrium_pickaxe", (props ->
        new PickaxeItem(ModTiers.DEMITHRIUM, props
            .component(DataComponents.UNBREAKABLE, new Unbreakable(true))
            .rarity(Rarity.EPIC)
            .fireResistant()
            .attributes(PickaxeItem.createAttributes(ModTiers.DEMITHRIUM, 2.0f, 2.0f)))));
    
    public static final DeferredItem<Item> NOVIUM_AXE = register("novium_axe", (props ->
        new AxeItem(ModTiers.NOVIUM, props
            .rarity(Rarity.EPIC)
            .fireResistant()
            .attributes(AxeItem.createAttributes(ModTiers.NOVIUM, 15.0f, -2.8f)))));
    public static final DeferredItem<Item> DEMITHRIUM_AXE = register("demithrium_axe", (props ->
        new AxeItem(ModTiers.DEMITHRIUM, props
            .component(DataComponents.UNBREAKABLE, new Unbreakable(true))
            .rarity(Rarity.EPIC)
            .fireResistant()
            .attributes(AxeItem.createAttributes(ModTiers.DEMITHRIUM, 4.0f, 2.4f)))));
    
    public static final DeferredItem<Item> NOVIUM_SHOVEL = register("novium_shovel", (props ->
        new ShovelItem(ModTiers.NOVIUM, props
            .rarity(Rarity.EPIC)
            .fireResistant()
            .attributes(ShovelItem.createAttributes(ModTiers.NOVIUM, 6.0f, -0.6f)))));
    public static final DeferredItem<Item> DEMITHRIUM_SHOVEL = register("demithrium_shovel", (props ->
        new ShovelItem(ModTiers.DEMITHRIUM, props
            .component(DataComponents.UNBREAKABLE, new Unbreakable(true))
            .rarity(Rarity.EPIC)
            .fireResistant()
            .attributes(ShovelItem.createAttributes(ModTiers.DEMITHRIUM, 1.0f, 1.9f)))));
    
    public static final DeferredItem<Item> NOVIUM_HOE = register("novium_hoe", (props ->
        new HoeItem(ModTiers.NOVIUM, props
            .rarity(Rarity.EPIC)
            .fireResistant()
            .attributes(HoeItem.createAttributes(ModTiers.NOVIUM, -1f, 0f)))));
    public static final DeferredItem<Item> DEMITHRIUM_HOE = register("demithrium_hoe", (props ->
        new HoeItem(ModTiers.DEMITHRIUM, props
            .component(DataComponents.UNBREAKABLE, new Unbreakable(true))
            .rarity(Rarity.EPIC)
            .fireResistant()
            .attributes(HoeItem.createAttributes(ModTiers.DEMITHRIUM, 0f, 0f)))));
    
    public static final DeferredItem<Item> NOVIUM_SWORD = register("novium_sword", (props ->
        new SwordItem(ModTiers.NOVIUM, props
            .rarity(Rarity.EPIC)
            .fireResistant()
            .attributes(SwordItem.createAttributes(ModTiers.NOVIUM, 15, 0.4f)))));
    public static final DeferredItem<Item> DEMITHRIUM_SWORD = register("demithrium_sword", (props ->
        new SwordItem(ModTiers.DEMITHRIUM, props
            .component(DataComponents.UNBREAKABLE, new Unbreakable(true))
            .rarity(Rarity.EPIC)
            .fireResistant()
            .attributes(SwordItem.createAttributes(ModTiers.DEMITHRIUM, 3, 2.9f)))));
    
    public static final DeferredItem<Item> NOVIUM_HELMET = register("novium_helmet", (props ->
        new ArmorItem(ModArmorMaterials.NOVIUM, ArmorItem.Type.HELMET, props
            .fireResistant()
            .durability(ArmorItem.Type.HELMET.getDurability(70)))));
    public static final DeferredItem<Item> NOVIUM_CHESTPLATE= register("novium_chestplate", (props ->
        new ArmorItem(ModArmorMaterials.NOVIUM, ArmorItem.Type.CHESTPLATE, props
            .fireResistant()
            .durability(ArmorItem.Type.CHESTPLATE.getDurability(70)))));
    public static final DeferredItem<Item> NOVIUM_LEGGINS = register("novium_leggings", (props ->
        new ArmorItem(ModArmorMaterials.NOVIUM, ArmorItem.Type.LEGGINGS, props
            .fireResistant()
            .durability(ArmorItem.Type.LEGGINGS.getDurability(70)))));
    public static final DeferredItem<Item> NOVIUM_BOOTS = register("novium_boots", (props ->
        new ArmorItem(ModArmorMaterials.NOVIUM, ArmorItem.Type.BOOTS, props
            .fireResistant()
            .durability(ArmorItem.Type.BOOTS.getDurability(70)))));
    
    public static final DeferredItem<Item> DEMITHRIUM_HELMET = register("demithrium_helmet", (props ->
        new ArmorItem(ModArmorMaterials.DEMITHRIUM, ArmorItem.Type.HELMET, props
            .fireResistant()
            .component(DataComponents.UNBREAKABLE, new Unbreakable(true))
            .durability(ArmorItem.Type.HELMET.getDurability(100)))));
    public static final DeferredItem<Item> DEMITHRIUM_CHESTPLATE = register("demithrium_chestplate", (props ->
        new ArmorItem(ModArmorMaterials.DEMITHRIUM, ArmorItem.Type.CHESTPLATE, props
            .fireResistant()
            .component(DataComponents.UNBREAKABLE, new Unbreakable(true))
            .durability(ArmorItem.Type.CHESTPLATE.getDurability(100)))));
    public static final DeferredItem<Item> DEMITHRIUM_LEGGINS = register("demithrium_leggings", (props ->
        new ArmorItem(ModArmorMaterials.DEMITHRIUM, ArmorItem.Type.LEGGINGS, props
            .fireResistant()
            .component(DataComponents.UNBREAKABLE, new Unbreakable(true))
            .durability(ArmorItem.Type.LEGGINGS.getDurability(100)))));
    public static final DeferredItem<Item> DEMITHRIUM_BOOTS = register("demithrium_boots", (props ->
        new ArmorItem(ModArmorMaterials.DEMITHRIUM, ArmorItem.Type.BOOTS, props
            .fireResistant()
            .component(DataComponents.UNBREAKABLE, new Unbreakable(true))
            .durability(ArmorItem.Type.BOOTS.getDurability(100)))));
    
    //******************************************************************************************************************
    public static <T extends Item> DeferredItem<T> register(final String                       name,
                                                            final Function<Item.Properties, T> func)
    {
        return ModItems.ITEMS.registerItem(name, func);
    }
    
    //==================================================================================================================
    @SubscribeEvent
    public static void buildContents(final BuildCreativeModeTabContentsEvent e)
    {
        if (e.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES)
        {
            e.acceptAll(List.of(
                ModItems.NOVIUM_AXE.toStack(),
                ModItems.NOVIUM_PICKAXE.toStack(),
                ModItems.NOVIUM_SHOVEL.toStack(),
                ModItems.NOVIUM_HOE.toStack(),
                ModItems.DEMITHRIUM_AXE.toStack(),
                ModItems.DEMITHRIUM_PICKAXE.toStack(),
                ModItems.DEMITHRIUM_SHOVEL.toStack(),
                ModItems.DEMITHRIUM_HOE.toStack(),
                ModItems.TRUTHSEEKER.toStack(),
                ModItems.MUSIC_DISC_EVOLUTION.toStack(),
                ModItems.MUSIC_DISC_REVOLUTION.toStack(),
                ModItems.NOVIA_BANNER_PATTERN.toStack()
            ));
        }
        else if (e.getTabKey() == CreativeModeTabs.COMBAT)
        {
            e.acceptAll(List.of(
                ModItems.NOVIUM_SWORD.toStack(),
                ModItems.DEMITHRIUM_SWORD.toStack(),
                ModItems.NOVIUM_HELMET.toStack(),
                ModItems.NOVIUM_CHESTPLATE.toStack(),
                ModItems.NOVIUM_LEGGINS.toStack(),
                ModItems.NOVIUM_BOOTS.toStack(),
                ModItems.DEMITHRIUM_HELMET.toStack(),
                ModItems.DEMITHRIUM_CHESTPLATE.toStack(),
                ModItems.DEMITHRIUM_LEGGINS.toStack(),
                ModItems.DEMITHRIUM_BOOTS.toStack()
            ));
        }
        else if (e.getTabKey() == CreativeModeTabs.INGREDIENTS)
        {
            e.acceptAll(List.of(
                ModItems.NOVIUM_PELLET.toStack(),
                ModItems.DEMURIUM_CRYSTAL.toStack(),
                ModItems.NOVIUM_NUGGET.toStack(),
                ModItems.DEMITHRIUM_NUGGET.toStack(),
                ModItems.NOVIUM_INGOT.toStack(),
                ModItems.DEMITHRIUM_INGOT.toStack(),
                ModItems.NOVIUM_UPGRADE_SMITHING_TEMPLATE.toStack(),
                ModItems.DEMITHRIUM_UPGRADE_SMITHING_TEMPLATE.toStack()
            ));
        }
        else if (e.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
        {
            e.acceptAll(List.of(
                ModBlocks.DEMITHRIL_STATUE.toStack(),
                ModBlocks.DEEPSLATE_NOVIUM_ORE.toStack(),
                ModBlocks.END_STONE_DEMURIUM_ORE.toStack(),
                ModBlocks.NOVIUM_PELLET_BLOCK.toStack(),
                ModBlocks.NOVIUM_BLOCK.toStack(),
                ModBlocks.DEMURIUM_BLOCK.toStack(),
                ModBlocks.DEMITHRIUM_BLOCK.toStack(),
                ModBlocks.GHOST_BLOCK.toStack()
            ));
        }
    }
}