package xyz.lumian.novia_additions.data.loot;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import xyz.lumian.novia_additions.block.GhostBlock;
import xyz.lumian.novia_additions.block.ModBlocks;
import xyz.lumian.novia_additions.item.ModItems;

import java.util.Set;



//**********************************************************************************************************************
public class ModBlockLootProvider
    extends BlockLootSubProvider
{
    //******************************************************************************************************************
    public ModBlockLootProvider(final HolderLookup.Provider lookup)
    {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), lookup);
    }
    
    //==================================================================================================================
    @Override
    protected void generate()
    {
        this.dropSelf(ModBlocks.DEMITHRIL_STATUE.get());
        this.dropSelf(ModBlocks.DEMURIUM_BLOCK.get());
        this.dropSelf(ModBlocks.NOVIUM_BLOCK.get());
        this.dropSelf(ModBlocks.DEMITHRIUM_BLOCK.get());
        this.dropSelf(ModBlocks.NOVIUM_PELLET_BLOCK.get());
        
        this.add(
            ModBlocks.END_STONE_DEMURIUM_ORE.get(),
            (block -> this.createOreDrop(block, ModItems.DEMURIUM_CRYSTAL.get())));
        this.add(
            ModBlocks.DEEPSLATE_NOVIUM_ORE.get(),
            (block -> this.createOreDrop(block, ModItems.NOVIUM_PELLET.get())));
        
        this.add(ModBlocks.GHOST_BLOCK.get(), LootTable.lootTable());
    }
    
    @Override
    protected Iterable<Block> getKnownBlocks()
    {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
