package xyz.lumian.novia_additions.world;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import xyz.lumian.novia_additions.Define;



//**********************************************************************************************************************
public class ModLootTables
{
    //******************************************************************************************************************
    public static final ResourceKey<LootTable> SPAWNER_SHRINE_COMMON   = spawner("demithril_shrine/common");
    public static final ResourceKey<LootTable> SPAWNER_SHRINE_UNCOMMON = spawner("demithril_shrine/uncommon");
    public static final ResourceKey<LootTable> SPAWNER_SHRINE_RARE     = spawner("demithril_shrine/rare");
    public static final ResourceKey<LootTable> SPAWNER_SHRINE_OMINOUS  = spawner("demithril_shrine/ominous");
    
    public static final ResourceKey<LootTable> CHEST_SHRINE_GRAVE = chest("demithril_shrine/grave");
    public static final ResourceKey<LootTable> CHEST_CATACOMBS    = chest("vasquilan_catacombs");
    
    public static final ResourceKey<LootTable> POT_CATACOMBS_COMMON   = pot("vasquilan_catacombs/common");
    public static final ResourceKey<LootTable> POT_CATACOMBS_UNCOMMON = pot("vasquilan_catacombs/uncommon");
    public static final ResourceKey<LootTable> POT_CATACOMBS_RARE     = pot("vasquilan_catacombs/rare");
    
    public static final ResourceKey<LootTable> FRAME_SHRINE = frame("demithril_shrine/statue");
    
    //******************************************************************************************************************
    private static ResourceKey<LootTable> chest(final String name) { return ModLootTables.create("chests/" + name); }
    private static ResourceKey<LootTable> pot(final String name) { return ModLootTables.create("pots/" + name); }
    private static ResourceKey<LootTable> frame(final String name) { return ModLootTables.create("frames/" + name); }
    private static ResourceKey<LootTable> spawner(final String name) { return ModLootTables.create("spawners/" + name); }
    private static ResourceKey<LootTable> create(final String name) { return Define.key(Registries.LOOT_TABLE, name); }
}
