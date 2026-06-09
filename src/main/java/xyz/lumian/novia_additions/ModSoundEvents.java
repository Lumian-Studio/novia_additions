package xyz.lumian.novia_additions;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;



//**********************************************************************************************************************
public class ModSoundEvents
{
    //******************************************************************************************************************
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister
        .create(BuiltInRegistries.SOUND_EVENT, Define.MOD_ID);
    
    //==================================================================================================================
    public static final Holder<SoundEvent> REVOLUTION = register("music_disc.revolution");
    public static final Holder<SoundEvent> EVOLUTION  = register("music_disc.evolution");
    
    //==================================================================================================================
    public static final Holder<SoundEvent> ITEM_TRUTHSEEKER_ACTIVATE   = register("item.truthseeker.activate");
    public static final Holder<SoundEvent> ITEM_TRUTHSEEKER_DEACTIVATE = register("item.truthseeker.deactivate");
    
    public static final Holder<SoundEvent> ITEM_MEDALLATION_STAGE_INDICATOR
        = register("item.medallation.stage_indicator");
    public static final Holder<SoundEvent> ITEM_MEDALLATION_STAGE_TICK
        = register("item.medallation.stage_tick");
    
    //******************************************************************************************************************
    private static Holder<SoundEvent> register(final String name)
    {
        return ModSoundEvents.SOUNDS.register(name, (() -> SoundEvent.createVariableRangeEvent(Define.mod(name))));
    }
}
