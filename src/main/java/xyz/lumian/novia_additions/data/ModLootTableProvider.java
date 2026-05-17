package xyz.lumian.novia_additions.data;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import xyz.lumian.novia_additions.data.loot.ModBlockLootProvider;
import xyz.lumian.novia_additions.data.loot.ModChestLootProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;



//**********************************************************************************************************************
public class ModLootTableProvider
    extends LootTableProvider
{
    //******************************************************************************************************************
    private static final List<SubProviderEntry> SUB_PROVIDERS = ImmutableList.of(
        new SubProviderEntry(ModBlockLootProvider::new, LootContextParamSets.BLOCK),
        new SubProviderEntry(ModChestLootProvider::new, LootContextParamSets.CHEST)
    );
    
    //******************************************************************************************************************
    public ModLootTableProvider(final PackOutput output, final CompletableFuture<HolderLookup.Provider> registries)
    {
        super(output, Set.of(), ModLootTableProvider.SUB_PROVIDERS, registries);
    }
}
