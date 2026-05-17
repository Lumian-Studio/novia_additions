package xyz.lumian.novia_additions.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;



//**********************************************************************************************************************
public class PelletBlock
    extends DirectionalBlock
{
    //******************************************************************************************************************
    public static final MapCodec<PelletBlock> CODEC = DirectionalBlock.simpleCodec(PelletBlock::new);
    
    //******************************************************************************************************************
    public PelletBlock(final Properties props)
    {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(PelletBlock.FACING, Direction.UP));
    }
    
    //==================================================================================================================
    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context)
    {
        return this.defaultBlockState()
            .setValue(DirectionalBlock.FACING, context.getClickedFace().getOpposite());
    }
    
    @Override protected MapCodec<? extends DirectionalBlock> codec() { return PelletBlock.CODEC; }
    
    //==================================================================================================================
    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(DirectionalBlock.FACING);
    }
    
    @Override
    protected BlockState rotate(final BlockState state, final Rotation rot)
    {
        return state.setValue(DirectionalBlock.FACING, rot.rotate(state.getValue(DirectionalBlock.FACING)));
    }
}
