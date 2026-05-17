package xyz.lumian.novia_additions.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;



//**********************************************************************************************************************
public class StatueBlock
    extends HorizontalDirectionalBlock
{
    //******************************************************************************************************************
    public static final MapCodec<StatueBlock> CODEC = HorizontalDirectionalBlock.simpleCodec(StatueBlock::new);
    
    //******************************************************************************************************************
    public StatueBlock(final Properties properties)
    {
        super(properties
            .sound(SoundType.STONE)
            .strength(1.5F, 6.0F)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .noOcclusion());
        this.registerDefaultState(this.stateDefinition.any()
            .setValue(StatueBlock.FACING, Direction.NORTH));
    }
    
    //==================================================================================================================
    @Override
    public @Nullable BlockState getStateForPlacement(final BlockPlaceContext context)
    {
        return this.defaultBlockState().setValue(StatueBlock.FACING, context.getHorizontalDirection());
    }
    
    @Override protected MapCodec<StatueBlock> codec() { return StatueBlock.CODEC; }
    
    //==================================================================================================================
    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(StatueBlock.FACING);
    }
}
