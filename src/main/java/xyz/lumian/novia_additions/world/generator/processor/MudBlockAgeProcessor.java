package xyz.lumian.novia_additions.world.generator.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.AllArgsConstructor;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import xyz.lumian.novia_additions.world.generator.ModStructureProcessors;

import javax.annotation.Nullable;



//**********************************************************************************************************************
@AllArgsConstructor
public class MudBlockAgeProcessor
    extends StructureProcessor
{
    //******************************************************************************************************************
    public static final MapCodec<MudBlockAgeProcessor> CODEC = RecordCodecBuilder.mapCodec(i -> i
        .group(
            Codec.FLOAT
                .fieldOf("decay")
                .forGetter(proc -> proc.decay),
            Codec.FLOAT
                .optionalFieldOf("fullBlockProbability", MudBlockAgeProcessor.DEFAULT_FULL_BLOCK_PROBABILITY)
                .forGetter(proc -> proc.fullBlockProbability),
            Codec.FLOAT
                .optionalFieldOf("stairBlockProbability", MudBlockAgeProcessor.DEFAULT_STAIR_BLOCK_PROBABILITY)
                .forGetter(proc -> proc.stairBlockProbability))
        .apply(i, MudBlockAgeProcessor::new));
    
    public static final float DEFAULT_FULL_BLOCK_PROBABILITY  = 0.5F;
    public static final float DEFAULT_STAIR_BLOCK_PROBABILITY = 0.5F;
    
    //******************************************************************************************************************
    private static BlockState getRandomBlock(final RandomSource random, final BlockState[] states)
    {
        return states[random.nextInt(states.length)];
    }
    
    private static BlockState getRandomFacingStairs(final RandomSource random, final Block block)
    {
        return block.defaultBlockState()
            .setValue(StairBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random))
            .setValue(StairBlock.HALF,   Util.getRandom(Half.values(), random));
    }
    
    //******************************************************************************************************************
    private final float decay;
    private final float fullBlockProbability;
    private final float stairBlockProbability;
    
    //******************************************************************************************************************
    @Override protected StructureProcessorType<?> getType() { return ModStructureProcessors.MUD_BLOCK_AGE.get(); }
    
    //==================================================================================================================
    @Override
    @Nullable
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
        final RandomSource random = settings.getRandom(relInfo.pos());
        final BlockState   state  = relInfo.state();
        
        BlockState result = null;
        
        if (state.is(Blocks.MUD_BRICK_STAIRS))
        {
            result = this.maybeReplaceStairs(random, relInfo.state());
        }
        else if (state.is(Blocks.MUD_BRICK_SLAB))
        {
            result = this.maybeReplaceSlab(random);
        }
        else if (state.is(Blocks.PACKED_MUD))
        {
            result = this.maybeReplacePackedMud(random);
        }
        else if (state.is(Blocks.MUD_BRICKS) || state.is(Blocks.MUD))
        {
            result = this.maybeReplaceBricks(random);
        }

        return (result != null
            ? new StructureTemplate.StructureBlockInfo(relInfo.pos(), result, relInfo.nbt())
            : relInfo);
    }
    
    //------------------------------------------------------------------------------------------------------------------
    private @Nullable BlockState maybeReplacePackedMud(final RandomSource random)
    {
        if (random.nextFloat() >= this.fullBlockProbability)
        {
            return null;
        }
        
        final BlockState[] normal = new BlockState[] {
            Blocks.MUD.defaultBlockState(),
            Blocks.MUD_BRICK_SLAB.defaultBlockState()
                .setValue(BlockStateProperties.WATERLOGGED, random.nextBoolean())
        };
        final BlockState[] decayed = new BlockState[] {
            Blocks.DEEPSLATE_BRICKS.defaultBlockState(),
            MudBlockAgeProcessor.getRandomFacingStairs(random, Blocks.DEEPSLATE_BRICK_STAIRS),
            Blocks.DEEPSLATE_BRICK_SLAB.defaultBlockState()
                .setValue(BlockStateProperties.WATERLOGGED, random.nextBoolean())
        };
        
        return this.getRandomBlock(random, normal, decayed);
    }
    
    private @Nullable BlockState maybeReplaceBricks(final RandomSource random)
    {
        if (random.nextFloat() >= this.fullBlockProbability)
        {
            return null;
        }
        
        final BlockState[] normal = new BlockState[] {
            Blocks.PACKED_MUD.defaultBlockState(),
            MudBlockAgeProcessor.getRandomFacingStairs(random, Blocks.MUD_BRICK_STAIRS)
        };
        final BlockState[] decayed = new BlockState[] {
            Blocks.DEEPSLATE_BRICKS.defaultBlockState(),
            MudBlockAgeProcessor.getRandomFacingStairs(random, Blocks.DEEPSLATE_BRICK_STAIRS)
        };
        
        return this.getRandomBlock(random, normal, decayed);
    }

    private @Nullable BlockState maybeReplaceStairs(final RandomSource random, final BlockState state)
    {
        if (random.nextFloat() >= this.stairBlockProbability)
        {
            return null;
        }
        
        final BlockState[] normal  = new BlockState[] { Blocks.MUD_BRICK_SLAB.defaultBlockState() };
        final BlockState[] decayed = new BlockState[] {
            Blocks.DEEPSLATE_BRICK_STAIRS.defaultBlockState()
                .setValue(StairBlock.FACING, state.getValue(StairBlock.FACING))
                .setValue(StairBlock.HALF,   state.getValue(StairBlock.HALF)),
            Blocks.DEEPSLATE_BRICK_SLAB.defaultBlockState()
        };
        
        return this.getRandomBlock(random, normal, decayed);
    }

    private @Nullable BlockState maybeReplaceSlab(final RandomSource random)
    {
        return (random.nextFloat() < this.decay ? Blocks.DEEPSLATE_BRICK_SLAB.defaultBlockState() : null);
    }
    
    private BlockState getRandomBlock(final RandomSource random, final BlockState[] normal, final BlockState[] decayed)
    {
        return (random.nextFloat() < this.decay
            ? MudBlockAgeProcessor.getRandomBlock(random, normal)
            : MudBlockAgeProcessor.getRandomBlock(random, decayed));
    }
}
