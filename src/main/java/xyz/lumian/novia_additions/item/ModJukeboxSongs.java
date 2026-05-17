package xyz.lumian.novia_additions.item;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import xyz.lumian.novia_additions.Define;
import xyz.lumian.novia_additions.ModSoundEvents;
import xyz.lumian.novia_additions.data.ModDataGenerators;



//**********************************************************************************************************************
public class ModJukeboxSongs
{
    //******************************************************************************************************************
    public static final ResourceKey<JukeboxSong> REVOLUTION = create("revolution");
    public static final ResourceKey<JukeboxSong> EVOLUTION  = create("evolution");
    
    //******************************************************************************************************************
    public static void bootstrap(final BootstrapContext<JukeboxSong> context)
    {
        ModJukeboxSongs.register(context, ModJukeboxSongs.REVOLUTION, ModSoundEvents.REVOLUTION, 221, 1);
        ModJukeboxSongs.register(context, ModJukeboxSongs.EVOLUTION,  ModSoundEvents.EVOLUTION,  289, 2);
    }
    
    //==================================================================================================================
    private static void register(final BootstrapContext<JukeboxSong> context,
                                 final ResourceKey<JukeboxSong>      key,
                                 final Holder<SoundEvent>            soundEvent,
                                 final int                           lengthInSeconds,
                                 final int                           comparatorOutput)
    {
        context.register(key, new JukeboxSong(
            soundEvent,
            Component.translatable(Util.makeDescriptionId("jukebox_song", key.location())),
            lengthInSeconds,
            comparatorOutput));
    }
    
    //==================================================================================================================
    private static ResourceKey<JukeboxSong> create(final String name)
    {
        return Define.key(Registries.JUKEBOX_SONG, name);
    }
}
