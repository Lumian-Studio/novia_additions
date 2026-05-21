package xyz.lumian.novia_additions.world.generator.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
import org.jspecify.annotations.Nullable;
import xyz.lumian.novia_additions.impl.ChiseledBookShelfBlockEntityExtension;
import xyz.lumian.novia_additions.registry.ModRegistries;
import xyz.lumian.novia_additions.world.generator.ModStructureProcessors;
import xyz.lumian.novia_additions.world.generator.WeightedHolderList;

import java.util.Optional;



//**********************************************************************************************************************
public class BookshelfLootProcessor
    extends StructureProcessor
{
    //******************************************************************************************************************
    public record EnchantmentProvider(HolderSet<Enchantment> possibleEnchantments, NumberProvider level)
    {
        //**************************************************************************************************************
        public static final Codec<EnchantmentProvider> CODEC = RecordCodecBuilder.create(i -> i
            .group(
                RegistryCodecs.homogeneousList(Registries.ENCHANTMENT)
                    .fieldOf("possibleEnchantments")
                    .forGetter(EnchantmentProvider::possibleEnchantments),
                NumberProviders.CODEC
                    .fieldOf("level")
                    .forGetter(EnchantmentProvider::level))
            .apply(i, EnchantmentProvider::new));
        
        public static EnchantmentProvider EMPTY = new EnchantmentProvider(HolderSet.empty(), ConstantValue.exactly(0f));
        
        //**************************************************************************************************************
        public ItemStack enchant(final ServerLevel level, final RandomSource random, final ItemStack stack)
        {
            if (this.possibleEnchantments.size() == 0)
            {
                return stack;
            }
            
            final LootContext context = Enchantment.itemContext(level, 0, ItemStack.EMPTY);
            return EnchantmentHelper.enchantItem(random, stack, this.level.getInt(context), level.registryAccess(),
                                                 Optional.of(this.possibleEnchantments));
        }
    }
    
    //******************************************************************************************************************
    public static final MapCodec<BookshelfLootProcessor> CODEC = RecordCodecBuilder.mapCodec(i -> i
        .group(
            RegistryFileCodec.create(ModRegistries.WEIGHTED_HOLDER_LIST, WeightedHolderList.CODEC)
                .fieldOf("bookWeights")
                .forGetter(proc -> proc.weights.getHolder()),
            Codec.FLOAT
                .fieldOf("chance")
                .forGetter(proc -> proc.replacementChance),
            EnchantmentProvider.CODEC
                .optionalFieldOf("enchantmentProvider", EnchantmentProvider.EMPTY)
                .forGetter(proc -> proc.enchantmentProvider),
            Codec.BOOL
                .optionalFieldOf("canReplaceBooks", BookshelfLootProcessor.DEFAULT_CAN_REPLACE_BOOKS)
                .forGetter(proc -> proc.canReplaceBooks),
            PosRuleTest.CODEC
                .optionalFieldOf("posTest", PosAlwaysTrueTest.INSTANCE)
                .forGetter(proc -> proc.posTest))
        .apply(i, BookshelfLootProcessor::new));
    
    public static final boolean DEFAULT_CAN_REPLACE_BOOKS = false;
    
    //******************************************************************************************************************
    private final PosRuleTest                      posTest;
    private final WeightedHolderList.Wrapper<Item> weights;
    private final float                            replacementChance;
    private final boolean                          canReplaceBooks;
    private final EnchantmentProvider              enchantmentProvider;
    
    private @Nullable HolderSet<Item> allowedItems = null;
    
    //******************************************************************************************************************
    public BookshelfLootProcessor(final Holder<WeightedHolderList<?>> bookWeights,
                                  final float                         replacementChance,
                                  final EnchantmentProvider           enchantmentProvider,
                                  final boolean                       canReplaceBooks,
                                  final PosRuleTest                   posTest)
    {
        this.posTest             = posTest;
        this.weights             = WeightedHolderList.wrap(Registries.ITEM, bookWeights);
        this.replacementChance   = replacementChance;
        this.canReplaceBooks     = canReplaceBooks;
        this.enchantmentProvider = enchantmentProvider;
    }
    
    public BookshelfLootProcessor(final Holder<WeightedHolderList<?>> bookWeights,
                                  final float                         replacementChance,
                                  final EnchantmentProvider           enchantmentProvider,
                                  final boolean                       canReplaceBooks)
    {
       this(bookWeights, replacementChance, enchantmentProvider, canReplaceBooks, PosAlwaysTrueTest.INSTANCE);
    }
    
    public BookshelfLootProcessor(final Holder<WeightedHolderList<?>> bookWeights,
                                  final float                         replacementChance,
                                  final EnchantmentProvider           enchantmentProvider)
    {
       this(bookWeights, replacementChance, enchantmentProvider, BookshelfLootProcessor.DEFAULT_CAN_REPLACE_BOOKS);
    }
    
    public BookshelfLootProcessor(final Holder<WeightedHolderList<?>> bookWeights, final float replacementChance)
    {
       this(bookWeights, replacementChance, EnchantmentProvider.EMPTY,
            BookshelfLootProcessor.DEFAULT_CAN_REPLACE_BOOKS);
    }
    
    //==================================================================================================================
    @Override protected StructureProcessorType<?> getType() { return ModStructureProcessors.BOOKSHELF_LOOT.value(); }
    
    //==================================================================================================================
    @Override
    public StructureTemplate.StructureBlockInfo process(
        final           LevelReader                          level,
        final           BlockPos                             offset,
        final           BlockPos                             pos,
        final           StructureTemplate.StructureBlockInfo info,
        final           StructureTemplate.StructureBlockInfo relInfo,
        final           StructurePlaceSettings               settings,
        final @Nullable StructureTemplate                    template
    )
    {
        if (!(level instanceof ServerLevelAccessor level_accessor) || this.replacementChance <= 0f)
        {
            return relInfo;
        }
        
        final ServerLevel  slevel = level_accessor.getLevel();
        final RandomSource random = settings.getRandom(relInfo.pos());
        
        if (
            !relInfo.state().is(Blocks.CHISELED_BOOKSHELF)
            || !this.posTest.test(info.pos(), relInfo.pos(), pos, random)
            || relInfo.nbt() == null
        )
        {
            return relInfo;
        }
        
        final BlockEntity be = BlockEntity
            .loadStatic(relInfo.pos(), relInfo.state(), relInfo.nbt(), level.registryAccess());
        
        if (!(be instanceof ChiseledBookShelfBlockEntity cbsbe))
        {
            return relInfo;
        }
        
        BlockState shelf_state = relInfo.state();
        
        for (int i = 0; i < cbsbe.getContainerSize(); ++i)
        {
            final ItemStack prev = cbsbe.getItem(i);
            
            if (
                (!prev.isEmpty() && !this.canReplaceBooks)
                || (random.nextFloat() > this.replacementChance)
            )
            {
                continue;
            }
            
            if (this.allowedItems == null)
            {
                this.allowedItems = level.holderLookup(Registries.ITEM).getOrThrow(ItemTags.BOOKSHELF_BOOKS);
            }
            
            final Holder<Item> book = this.weights.value().getRandomHolderOf(random, this.allowedItems).orElse(null);
            
            if (book == null)
            {
                return relInfo;
            }
            
            ItemStack result = new ItemStack(book);
            
            if (result.is(Items.ENCHANTED_BOOK))
            {
                result = this.enchantmentProvider.enchant(slevel, random, new ItemStack(Items.BOOK));
            }
            
            ((ChiseledBookShelfBlockEntityExtension) cbsbe).noviaAdditions$setItemNoUpdate(i, result);
            shelf_state = shelf_state.setValue(ChiseledBookShelfBlock.SLOT_OCCUPIED_PROPERTIES.get(i), true);
        }
        
        final CompoundTag nbt = cbsbe.saveWithId(slevel.registryAccess());
        return new StructureTemplate.StructureBlockInfo(relInfo.pos(), shelf_state, nbt);
    }
}
