package xyz.lumian.novia_additions.block;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import xyz.lumian.novia_additions.registry.ModAttachments;



//**********************************************************************************************************************
public abstract class TruthSeekerBlockBase
    extends Block
{
    //******************************************************************************************************************
    public enum TruthSeekerMode
        implements StringRepresentable
    {
        DEFAULT_VISIBLE,
        DEFAULT_INVISIBLE,
        UNAFFECTED,
        ;
        
        //**************************************************************************************************************
        @Override
        public String getSerializedName()
        {
            return switch (this)
            {
                case DEFAULT_VISIBLE   -> "visible";
                case DEFAULT_INVISIBLE -> "invisible";
                case UNAFFECTED        -> "unaffected";
            };
        }
    }
    
    //******************************************************************************************************************
    public static final EnumProperty<TruthSeekerMode> TRUTH_SEEKER_MODE = EnumProperty
        .create("truth_seeker_mode", TruthSeekerMode.class);
    
    //******************************************************************************************************************
    public static boolean isBlockInvisible(final Player player, final BlockState state)
    {
        final TruthSeekerMode mode = state.getOptionalValue(TruthSeekerBlockBase.TRUTH_SEEKER_MODE).orElse(null);
        
        if (player.isSpectator())
        {
            return (mode == TruthSeekerMode.DEFAULT_INVISIBLE);
        }
        
        if (mode == null || mode == TruthSeekerMode.UNAFFECTED)
        {
            return false;
        }
        
        final boolean seeking = player.getData(ModAttachments.SEEKING);
        return ((mode == TruthSeekerMode.DEFAULT_INVISIBLE) != seeking);
    }
    
    public static boolean isEntityInvisible(final Player player, final Entity entity)
    {
        final Boolean mode = entity.getExistingDataOrNull(ModAttachments.TRUTHSEEKER_INVISIBLE_DEFAULT);
        
        if (player.isSpectator())
        {
            return (mode == Boolean.TRUE);
        }
        
        if (mode == null)
        {
            return false;
        }
        
        final boolean seeking = player.getData(ModAttachments.SEEKING);
        return (mode != seeking);
    }
    
    //******************************************************************************************************************
    public TruthSeekerBlockBase(final Properties properties) { super(properties); }
    
    //==================================================================================================================
    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(TruthSeekerBlockBase.TRUTH_SEEKER_MODE);
    }
}
