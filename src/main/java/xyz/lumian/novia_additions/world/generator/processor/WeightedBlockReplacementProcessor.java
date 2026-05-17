package xyz.lumian.novia_additions.world.generator.processor;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import org.jspecify.annotations.Nullable;
import xyz.lumian.novia_additions.registry.ModRegistries;
import xyz.lumian.novia_additions.world.generator.ModStructureProcessors;
import xyz.lumian.novia_additions.world.generator.WeightedHolderList;



//**********************************************************************************************************************
public class WeightedBlockReplacementProcessor
    extends StructureProcessor
{
    //******************************************************************************************************************
    public static final MapCodec<WeightedBlockReplacementProcessor> CODEC = RecordCodecBuilder.mapCodec(i -> i
        .group(
            RuleTest.CODEC
                .fieldOf("targetTest")
                .forGetter(proc -> proc.ruleTest),
            PosRuleTest.CODEC
                .optionalFieldOf("posTest", PosAlwaysTrueTest.INSTANCE)
                .forGetter(proc -> proc.posTest),
            RegistryFileCodec.create(ModRegistries.WEIGHTED_HOLDER_LIST, WeightedHolderList.CODEC)
                .fieldOf("weights")
                .forGetter(proc -> proc.weightedBlockList.getHolder()))
        .apply(i, WeightedBlockReplacementProcessor::new));
    
    //******************************************************************************************************************
    private final PosRuleTest                       posTest;
    private final RuleTest                          ruleTest;
    private final WeightedHolderList.Wrapper<Block> weightedBlockList;
    
    //******************************************************************************************************************
    public WeightedBlockReplacementProcessor(final RuleTest                      targetTest,
                                             final PosRuleTest                   posTest,
                                             final Holder<WeightedHolderList<?>> weightedBlockList)
    {
        this(targetTest, posTest, WeightedHolderList.wrap(Registries.BLOCK, weightedBlockList));
    }
    
    public WeightedBlockReplacementProcessor(final RuleTest                      targetTest,
                                             final Holder<WeightedHolderList<?>> weightedBlockList)
    {
        this(targetTest, PosAlwaysTrueTest.INSTANCE, weightedBlockList);
    }
    
    //------------------------------------------------------------------------------------------------------------------
    private WeightedBlockReplacementProcessor(final RuleTest                          targetTest,
                                              final PosRuleTest                       posTest,
                                              final WeightedHolderList.Wrapper<Block> weightedBlockList)
    {
        this.posTest           = posTest;
        this.ruleTest          = targetTest;
        this.weightedBlockList = weightedBlockList;
    }
    
    //==================================================================================================================
    @Override
    protected StructureProcessorType<?> getType()
    {
        return ModStructureProcessors.WEIGHTED_BLOCK_REPLACEMENT.get();
    }
    
    //==================================================================================================================
    @Override
    public StructureTemplate.@Nullable StructureBlockInfo process(
        final           LevelReader                          level,
        final           BlockPos                             offset,
        final           BlockPos                             pos,
        final           StructureTemplate.StructureBlockInfo info,
        final           StructureTemplate.StructureBlockInfo relInfo,
        final           StructurePlaceSettings               settings,
        final @Nullable StructureTemplate                    template
    )
    {
        final RandomSource random = settings.getRandom(relInfo.pos());
        
        if (this.ruleTest.test(relInfo.state(), random) && this.posTest.test(info.pos(), relInfo.pos(), pos, random))
        {
            return this.weightedBlockList.value().getRandomHolder(random)
                .map(holder -> new StructureTemplate.StructureBlockInfo(
                    relInfo.pos(),
                    holder.value().defaultBlockState(),
                    relInfo.nbt()))
                .orElse(relInfo);
        }
        
        return relInfo;
    }
}
