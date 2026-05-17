package xyz.lumian.novia_additions.data.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import xyz.lumian.novia_additions.item.ModItemTags;
import xyz.lumian.novia_additions.item.ModItems;
import xyz.lumian.novia_additions.world.ModLootTables;

import java.util.function.BiConsumer;



//**********************************************************************************************************************
public record ModChestLootProvider(HolderLookup.Provider registries)
    implements LootTableSubProvider
{
    //******************************************************************************************************************
    private static void spawnerLootTables(final BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output)
    {
        output.accept(ModLootTables.SPAWNER_SHRINE_COMMON, LootTable.lootTable()
                                                                    .withPool(LootPool.lootPool()
                .setRolls(UniformGenerator.between(1f, 3f))
                .add(LootItem.lootTableItem(Items.EMERALD)
                    .setWeight(35)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(5f, 13f))))
                .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(1))
                .add(LootItem.lootTableItem(Items.ENDER_PEARL).setWeight(25))
                .add(TagEntry.expandTag(ItemTags.CREEPER_DROP_MUSIC_DISCS).setWeight(15))
                .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(40))));
        
        output.accept(ModLootTables.SPAWNER_SHRINE_UNCOMMON, LootTable.lootTable()
                                                                      .withPool(LootPool.lootPool()
                .setRolls(UniformGenerator.between(1f, 2f))
                .add(LootItem.lootTableItem(ModItems.NOVIA_BANNER_PATTERN).setWeight(15))
                .add(LootItem.lootTableItem(Items.DIAMOND)
                    .setWeight(20)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2f, 4f))))
                .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP).setWeight(3))
                .add(LootItem.lootTableItem(Items.DIAMOND_PICKAXE).setWeight(11))
                .add(LootItem.lootTableItem(Items.DIAMOND_SHOVEL).setWeight(11))
                .add(LootItem.lootTableItem(Items.DIAMOND_HOE).setWeight(11))
                .add(LootItem.lootTableItem(Items.DIAMOND_AXE).setWeight(11))
                .add(LootItem.lootTableItem(Items.DIAMOND_SWORD).setWeight(11))
                .add(LootItem.lootTableItem(Items.DIAMOND_HELMET).setWeight(11))
                .add(LootItem.lootTableItem(Items.DIAMOND_BOOTS).setWeight(11))
                .add(LootItem.lootTableItem(Items.DIAMOND_CHESTPLATE).setWeight(11))
                .add(LootItem.lootTableItem(Items.DIAMOND_LEGGINGS).setWeight(11))));
        
        output.accept(ModLootTables.SPAWNER_SHRINE_RARE, LootTable.lootTable()
                                                                  .withPool(LootPool.lootPool()
                .setBonusRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(Items.NETHERITE_INGOT)
                    .setWeight(20)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 2f))))
                .add(TagEntry.expandTag(ModItemTags.MUSIC_DISCS).setWeight(30))
                .add(LootItem.lootTableItem(ModItems.NOVIUM_PELLET).setWeight(20))
                .add(LootItem.lootTableItem(ModItems.DEMURIUM_CRYSTAL)
                    .setWeight(20)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 3f))))
                .add(LootItem.lootTableItem(ModItems.DEMITHRIUM_INGOT).setWeight(1))));
        
        output.accept(ModLootTables.SPAWNER_SHRINE_OMINOUS, LootTable.lootTable()
                                                                     .withPool(LootPool.lootPool()
                .setBonusRolls(ConstantValue.exactly(0.5f))
                .add(NestedLootTable.lootTableReference(ModLootTables.SPAWNER_SHRINE_RARE).setWeight(2))
                .add(LootItem.lootTableItem(ModItems.NOVIUM_UPGRADE_SMITHING_TEMPLATE).setWeight(1))));
        
        output.accept(ModLootTables.CHEST_SHRINE_GRAVE, LootTable.lootTable()
                                                                 .withPool(LootPool.lootPool()
                .setRolls(UniformGenerator.between(4f, 8f))
                .add(LootItem.lootTableItem(ModItems.NOVIA_BANNER_PATTERN) .setWeight(3))
                .add(LootItem.lootTableItem(ModItems.MUSIC_DISC_EVOLUTION) .setWeight(1))
                .add(LootItem.lootTableItem(ModItems.MUSIC_DISC_REVOLUTION).setWeight(1))
                .add(LootItem.lootTableItem(Items.DIAMOND)
                    .setWeight(15)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2f, 4f))))
                .add(LootItem.lootTableItem(Items.EMERALD)
                    .setWeight(20)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2f, 16f))))
                .add(LootItem.lootTableItem(Items.IRON_INGOT)
                    .setWeight(30)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(6f, 16f))))
                .add(LootItem.lootTableItem(Items.CHARCOAL)
                    .setWeight(30)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(21f, 20f))))
                .add(LootItem.lootTableItem(Items.GOLDEN_PICKAXE)   .setWeight(15))
                .add(LootItem.lootTableItem(Items.GOLDEN_SHOVEL)    .setWeight(15))
                .add(LootItem.lootTableItem(Items.GOLDEN_HOE)       .setWeight(15))
                .add(LootItem.lootTableItem(Items.GOLDEN_AXE)       .setWeight(15))
                .add(LootItem.lootTableItem(Items.GOLDEN_SWORD)     .setWeight(15))
                .add(LootItem.lootTableItem(Items.GOLDEN_HELMET)    .setWeight(15))
                .add(LootItem.lootTableItem(Items.GOLDEN_BOOTS)     .setWeight(15))
                .add(LootItem.lootTableItem(Items.GOLDEN_CHESTPLATE).setWeight(15))
                .add(LootItem.lootTableItem(Items.GOLDEN_LEGGINGS)  .setWeight(15)))
                                                                 .withPool(LootPool.lootPool()
                .add(EmptyLootItem.emptyItem().setWeight(30))
                .add(LootItem.lootTableItem(Items.GOLDEN_APPLE)
                    .setWeight(15)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 3f))))
                .add(LootItem.lootTableItem(Items.GOLDEN_CARROT)
                    .setWeight(18)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3f, 8f))))
                .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(3))));
        
        output.accept(ModLootTables.FRAME_SHRINE, LootTable.lootTable()
                                                           .withPool(LootPool.lootPool()
                .add(LootItem.lootTableItem(Items.EMERALD)               .setWeight(25))
                .add(LootItem.lootTableItem(Items.DIAMOND)               .setWeight(15))
                .add(LootItem.lootTableItem(Items.GOLD_INGOT)            .setWeight(20))
                .add(LootItem.lootTableItem(Items.IRON_INGOT)            .setWeight(40))
                .add(LootItem.lootTableItem(Items.AMETHYST_SHARD)        .setWeight(30))
                .add(LootItem.lootTableItem(Items.ECHO_SHARD)            .setWeight( 1))
                .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight( 1))
                .add(LootItem.lootTableItem(Items.COPPER_INGOT)          .setWeight(40))
                .add(LootItem.lootTableItem(Items.IRON_NUGGET)           .setWeight(40))
                .add(LootItem.lootTableItem(Items.GOLD_NUGGET)           .setWeight(30))));
    }
    
    @Override
    public void generate(final BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output)
    {
        ModChestLootProvider.spawnerLootTables(output);
    }
}
