package xyz.lumian.novia_additions;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;



//**********************************************************************************************************************
public class ModSoundEvents
{
    //******************************************************************************************************************
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister
        .create(BuiltInRegistries.SOUND_EVENT, Define.MOD_ID);
    
    //==================================================================================================================
    public static final DeferredHolder<SoundEvent, SoundEvent> REVOLUTION
        = ModSoundEvents.register("music_disc.revolution");
    public static final DeferredHolder<SoundEvent, SoundEvent> EVOLUTION
        = ModSoundEvents.register("music_disc.evolution");
    
    //******************************************************************************************************************
    private static DeferredHolder<SoundEvent, SoundEvent> register(final String name)
    {
        return ModSoundEvents.SOUNDS.register(name, (() -> SoundEvent.createVariableRangeEvent(Define.mod(name))));
    }
}
