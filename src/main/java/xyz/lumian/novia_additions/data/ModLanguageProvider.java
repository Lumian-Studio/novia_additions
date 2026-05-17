package xyz.lumian.novia_additions.data;

import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.apache.commons.lang3.text.WordUtils;
import xyz.lumian.novia_additions.*;
import xyz.lumian.novia_additions.block.ModBlockTags;
import xyz.lumian.novia_additions.block.ModBlocks;
import xyz.lumian.novia_additions.item.ModBannerPatterns;
import xyz.lumian.novia_additions.item.ModItemTags;
import xyz.lumian.novia_additions.item.ModItems;
import xyz.lumian.novia_additions.item.ModJukeboxSongs;
import xyz.lumian.novia_additions.world.ModBiomeTags;

import java.util.Arrays;



//**********************************************************************************************************************
public class ModLanguageProvider
    extends LanguageProvider
{
    //******************************************************************************************************************
    public static String fromComponent(final Component component)
    {
        if (!(component.getContents() instanceof TranslatableContents translatable))
        {
            throw new IllegalArgumentException("Component contents must be a translatable");
        }
        
        return translatable.getKey();
    }
    
    //******************************************************************************************************************
    public ModLanguageProvider(final PackOutput output) { super(output, Define.MOD_ID, "en_us"); }
    
    //==================================================================================================================
    public void addJukeboxSong(final ResourceKey<JukeboxSong> song, final String translation)
    {
        this.add("jukebox_song." + song.location().toLanguageKey(), translation);
    }
    
    public void addBannerPattern(final Item item, final ResourceKey<BannerPattern> pattern, final String name)
    {
        this.add((item.getDescriptionId() + ".desc"), name);
        
        //noinspection deprecation
        Arrays.stream(DyeColor.values()).forEach(dye -> this.add(
            "block.%s.%s".formatted(pattern.location().withPrefix("banner.").toLanguageKey(), dye.getName()),
            (name + ' ' + WordUtils.capitalizeFully(dye.getName().replaceAll("_", " ")))));
    }
    
    //==================================================================================================================
    @Override
    protected void addTranslations()
    {
        // ITEMS
        this.addJukeboxSong(ModJukeboxSongs.EVOLUTION, "Demithril - Evolution");
        this.addJukeboxSong(ModJukeboxSongs.REVOLUTION, "Demithril - Revolution");
        
        this.add(ModItems.MUSIC_DISC_EVOLUTION .get(), "Music Disc");
        this.add(ModItems.MUSIC_DISC_REVOLUTION.get(), "Music Disc");
        this.add(ModItems.NOVIA_BANNER_PATTERN .get(), "Banner Pattern");
        
        this.addBannerPattern(ModItems.NOVIA_BANNER_PATTERN.get(), ModBannerPatterns.NOVIA, "Novia");
        
        this.add(ModItems.NOVIUM_PICKAXE    .get(), "Novium Pickaxe");
        this.add(ModItems.DEMITHRIUM_PICKAXE.get(), "Demithrium Pickaxe");
        this.add(ModItems.NOVIUM_AXE        .get(), "Novium Axe");
        this.add(ModItems.DEMITHRIUM_AXE    .get(), "Demithrium Axe");
        this.add(ModItems.NOVIUM_SHOVEL     .get(), "Novium Shovel");
        this.add(ModItems.DEMITHRIUM_SHOVEL .get(), "Demithrium Shovel");
        this.add(ModItems.NOVIUM_HOE        .get(), "Novium Hoe");
        this.add(ModItems.DEMITHRIUM_HOE    .get(), "Demithrium Hoe");
        this.add(ModItems.NOVIUM_SWORD      .get(), "Novium Sword");
        this.add(ModItems.DEMITHRIUM_SWORD  .get(), "Demithrium Sword");
        
        this.add(ModItems.NOVIUM_PELLET   .get(), "Novium Pellet");
        this.add(ModItems.DEMURIUM_CRYSTAL.get(), "Demurium Crystal");
        
        this.add(ModItems.NOVIUM_INGOT    .get(), "Novium Ingot");
        this.add(ModItems.DEMITHRIUM_INGOT.get(), "Demithrium Ingot");
        
        this.add(ModItems.NOVIUM_NUGGET    .get(), "Novium Nugget");
        this.add(ModItems.DEMITHRIUM_NUGGET.get(), "Demithrium Nugget");
        
        this.add(ModItems.NOVIUM_HELMET        .get(), "Novium Helmet");
        this.add(ModItems.NOVIUM_CHESTPLATE    .get(), "Novium Chestplate");
        this.add(ModItems.NOVIUM_LEGGINS       .get(), "Novium Leggins");
        this.add(ModItems.NOVIUM_BOOTS         .get(), "Novium Boots");
        this.add(ModItems.DEMITHRIUM_HELMET    .get(), "Demithrium Helmet");
        this.add(ModItems.DEMITHRIUM_CHESTPLATE.get(), "Demithrium Chestplate");
        this.add(ModItems.DEMITHRIUM_LEGGINS   .get(), "Demithrium Leggins");
        this.add(ModItems.DEMITHRIUM_BOOTS     .get(), "Demithrium Boots");
        
        // BLOCKS
        this.add(ModBlocks.DEMITHRIL_STATUE.get(), "Demithril Statue");
        
        this.add(ModBlocks.DEEPSLATE_NOVIUM_ORE  .get(), "Deepslate Novium Ore");
        this.add(ModBlocks.END_STONE_DEMURIUM_ORE.get(), "End Stone Demurium Ore");
        
        this.add(ModBlocks.NOVIUM_PELLET_BLOCK.get(), "Novium Pellet Block");
        this.add(ModBlocks.NOVIUM_BLOCK       .get(), "Novium Block");
        this.add(ModBlocks.DEMURIUM_BLOCK     .get(), "Demurium Block");
        this.add(ModBlocks.DEMITHRIUM_BLOCK   .get(), "Demithrium Block");
        
        // Smithing Templates
        this.add(ModItems.NOVIUM_UPGRADE_SMITHING_TEMPLATE    .get(), "Smithing Template");
        this.add(ModItems.DEMITHRIUM_UPGRADE_SMITHING_TEMPLATE.get(), "Smithing Template");
        
        this.add(ModLanguageProvider.fromComponent(ModItems.NOVIUM_UPGRADE_APPLIES_TO), "Netherite Equipment");
        this.add(ModLanguageProvider.fromComponent(ModItems.NOVIUM_UPGRADE), "Novium Upgrade");
        this.add(
            ModLanguageProvider.fromComponent(ModItems.NOVIUM_UPGRADE_BASE_SLOT_DESCRIPTION),
            "Add Netherite equipment");
        this.add(
            ModLanguageProvider.fromComponent(ModItems.NOVIUM_UPGRADE_ADDITIONS_SLOT_DESCRIPTION),
            "Add Novium Ingot");
        this.add(ModLanguageProvider.fromComponent(ModItems.NOVIUM_UPGRADE_INGREDIENTS), "Novium Ingot");
        
        this.add(ModLanguageProvider.fromComponent(ModItems.DEMITHRIUM_UPGRADE_APPLIES_TO), "Novium Equipment");
        this.add(ModLanguageProvider.fromComponent(ModItems.DEMITHRIUM_UPGRADE), "Demithrium Upgrade");
        this.add(
            ModLanguageProvider.fromComponent(ModItems.DEMITHRIUM_UPGRADE_BASE_SLOT_DESCRIPTION),
            "Add Novium equipment");
        this.add(
            ModLanguageProvider.fromComponent(ModItems.DEMITHRIUM_UPGRADE_ADDITIONS_SLOT_DESCRIPTION),
            "Add Demithrium Ingot");
        this.add(ModLanguageProvider.fromComponent(ModItems.DEMITHRIUM_UPGRADE_INGREDIENTS), "Demithrium Ingot");
        
        this.add(ModBlockTags.INCORRECT_FOR_NOVIUM_TOOL,     "Incorrect for Novium Tool");
        this.add(ModBlockTags.INCORRECT_FOR_DEMITHRIUM_TOOL, "Incorrect for Demithrium Tool");
        this.add(ModBlockTags.NEEDS_STARTER_TOOL,            "Needs Starter Tool");
        this.add(ModBlockTags.NEEDS_NOVIUM_TOOL,             "Needs Novium Tool");
        this.add(ModBlockTags.NEEDS_DEMITHRIUM_TOOL,         "Needs Demithrium Tool");
        
        this.add(ModItemTags.ARMOR_SETS,       "Novia Armor Sets");
        this.add(ModItemTags.NOVIUM_ARMOR,     "Novium Armor Set");
        this.add(ModItemTags.DEMITHRIUM_ARMOR, "Demithrium Armor Set");
        this.add(ModItemTags.HEAD_ARMOR,       "Novia Head Armor");
        this.add(ModItemTags.CHEST_ARMOR,      "Novia Chest Armor");
        this.add(ModItemTags.LEG_ARMOR,        "Novia Leg Armor");
        this.add(ModItemTags.FOOT_ARMOR,       "Novia Foot Armor");
        this.add(ModItemTags.MUSIC_DISCS,      "Novia Music Discs");
        
        this.add(ModBiomeTags.HAS_NOVIUM_ORE,   "Has Novium Ore");
        this.add(ModBiomeTags.HAS_DEMURIUM_ORE, "Has Demurium Ore");
        this.add(ModBiomeTags.HAS_SHRINE,       "Has Demithril Shrine");
        this.add(ModBiomeTags.HAS_CATACOMBS,    "Has Vasquilan Catacombs");
        
        // Structure signs
        this.add("sign.novia.demithril_shrine.catacombs.grave.small.1.0", "Here li . a");
        this.add("sign.novia.demithril_shrine.catacombs.grave.small.1.1", "l.st so#l fro  the");
        this.add("sign.novia.demithril_shrine.catacombs.grave.small.1.2", "Gra.de .#eat W r");
        this.add("sign.novia.demithril_shrine.catacombs.grave.small.1.3", "");
        
        this.add("sign.novia.demithril_shrine.catacombs.grave.small.2.0", "");
        this.add("sign.novia.demithril_shrine.catacombs.grave.small.2.1", "R**t in");
        this.add("sign.novia.demithril_shrine.catacombs.grave.small.2.2", "Pie.e");
        this.add("sign.novia.demithril_shrine.catacombs.grave.small.2.3", "");
    }
}
