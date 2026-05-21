package xyz.lumian.novia_additions.world.generator.processor;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import xyz.lumian.novia_additions.NoviaAdditions;
import xyz.lumian.novia_additions.world.generator.ModStructureProcessors;

import java.util.List;
import java.util.Optional;



//**********************************************************************************************************************
public class ItemFrameLootProcessor
    extends StructureProcessor
{
    //******************************************************************************************************************
    public static final MapCodec<ItemFrameLootProcessor> CODEC = RecordCodecBuilder.mapCodec(i -> i
        .group(
            ResourceKey.codec(Registries.LOOT_TABLE)
                .fieldOf("chestLootTable")
                .forGetter(proc -> proc.lootTable),
            ItemStack.OPTIONAL_CODEC
                .optionalFieldOf("fallbackItem", ItemStack.EMPTY)
                .forGetter(proc -> proc.fallbackItem),
            PosRuleTest.CODEC
                .fieldOf("posTest")
                .forGetter(proc -> proc.posTest))
        .apply(i, ItemFrameLootProcessor::new));
    
    //******************************************************************************************************************
    private final ResourceKey<LootTable> lootTable;
    private final ItemStack              fallbackItem;
    private final PosRuleTest            posTest;
    
    //******************************************************************************************************************
    public ItemFrameLootProcessor(final ResourceKey<LootTable> chestLootTable,
                                  final ItemStack              fallbackItem,
                                  final PosRuleTest            posTest)
    {
        this.posTest      = posTest;
        this.lootTable    = chestLootTable;
        this.fallbackItem = fallbackItem.copyWithCount(1);
    }
    
    public ItemFrameLootProcessor(final ResourceKey<LootTable> chestLootTable, final ItemStack fallbackItem)
    {
       this(chestLootTable, fallbackItem, PosAlwaysTrueTest.INSTANCE);
    }
    
    public ItemFrameLootProcessor(final ResourceKey<LootTable> chestLootTable, final PosRuleTest posTest)
    {
       this(chestLootTable, ItemStack.EMPTY, posTest);
    }
    
    public ItemFrameLootProcessor(final ResourceKey<LootTable> chestLootTable)
    {
       this(chestLootTable, ItemStack.EMPTY, PosAlwaysTrueTest.INSTANCE);
    }
    
    //==================================================================================================================
    @Override protected StructureProcessorType<?> getType() { return ModStructureProcessors.ITEM_FRAME_LOOT.value(); }
    
    //==================================================================================================================
    @Override
    public StructureTemplate.StructureEntityInfo processEntity(
        final LevelReader                           level,
        final BlockPos                              pos,
        final StructureTemplate.StructureEntityInfo info,
        final StructureTemplate.StructureEntityInfo relInfo,
        final StructurePlaceSettings                settings,
        final StructureTemplate                     template
    )
    {
        if (!(level instanceof ServerLevelAccessor level_accessor))
        {
            return relInfo;
        }
        
        final ServerLevel  slevel = level_accessor.getLevel();
        final RandomSource random = settings.getRandom(relInfo.blockPos);
        
        if (
            !(EntityType.create(info.nbt, slevel).orElse(null) instanceof ItemFrame iframe)
            || !iframe.getItem().isEmpty()
            || !this.posTest.test(info.blockPos, relInfo.blockPos, pos, random)
        )
        {
            return relInfo;
        }
        
        ItemStack result = ItemStack.EMPTY;
        
        final LootTable table = slevel.getServer().reloadableRegistries().getLootTable(this.lootTable);
        
        try
        {
            if (table.getParamSet() != LootContextParamSets.CHEST)
            {
                throw new IllegalStateException("'" + this.lootTable.location() + "' is not a chest loot table");
            }
            
            final LootParams params = new LootParams.Builder(slevel)
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(info.blockPos))
                .create(LootContextParamSets.CHEST);
            final List<ItemStack> stacks = table.getRandomItems(params, random);
            
            if (!stacks.isEmpty())
            {
                final ItemStack result_stack = stacks.get(random.nextInt(0, stacks.size()));
            
                if (!result_stack.isEmpty())
                {
                    result = result_stack.copyWithCount(1);
                }
            }
        }
        catch (final IllegalStateException ex)
        {
            NoviaAdditions.LOGGER.error("could not load item frame loot table", ex);
        }
        
        if (result.isEmpty() && !this.fallbackItem.isEmpty())
        {
            result = this.fallbackItem;
        }
        
        if (!result.isEmpty())
        {
            iframe.setItem(result, false);
            
            // not necessary, but we want to mute this vexing console error
            final BlockPos block_pos = relInfo.blockPos;
            iframe.setPos(block_pos.getX(), block_pos.getY(), block_pos.getZ());
            
            final CompoundTag data = new CompoundTag();
            Optional.ofNullable(iframe.getEncodeId()).ifPresent(id -> data.putString("id", id));
            iframe.saveWithoutId(data);
            
            return new StructureTemplate.StructureEntityInfo(relInfo.pos, relInfo.blockPos, data);
        }
        
        return relInfo;
    }
}
