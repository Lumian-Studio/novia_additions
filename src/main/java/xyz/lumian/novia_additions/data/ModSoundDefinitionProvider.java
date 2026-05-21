package xyz.lumian.novia_additions.data;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import xyz.lumian.novia_additions.Define;
import xyz.lumian.novia_additions.ModSoundEvents;



//**********************************************************************************************************************
public class ModSoundDefinitionProvider
    extends SoundDefinitionsProvider
{
    //******************************************************************************************************************
    protected ModSoundDefinitionProvider(final PackOutput output, final ExistingFileHelper helper)
    {
        super(output, Define.MOD_ID, helper);
    }
    
    //==================================================================================================================
    @Override
    public void registerSounds()
    {
        this.add(ModSoundEvents.REVOLUTION.value(), SoundDefinition.definition()
            .with(SoundDefinition.Sound
                .sound(Define.mod("records/revolution"), SoundDefinition.SoundType.SOUND)
                .stream()));
        this.add(ModSoundEvents.EVOLUTION.value(), SoundDefinition.definition()
            .with(SoundDefinition.Sound
                .sound(Define.mod("records/evolution"), SoundDefinition.SoundType.SOUND)
                .stream()));
        this.add(ModSoundEvents.ITEM_TRUTHSEEKER_ACTIVATE.value(), SoundDefinition.definition()
            .with(
                SoundDefinition.Sound
                    .sound(Define.mod("item/truthseeker/activate"), SoundDefinition.SoundType.SOUND)
                    .pitch(1.16f)
                    .volume(0.4f),
                SoundDefinition.Sound
                    .sound(Define.mod("item/truthseeker/activate"), SoundDefinition.SoundType.SOUND)
                    .pitch(1.33f)
                    .volume(0.4f),
                SoundDefinition.Sound
                    .sound(Define.mod("item/truthseeker/activate"), SoundDefinition.SoundType.SOUND)
                    .pitch(1.24f)
                    .volume(0.4f))
            .subtitle("subtitles.item.novia_additions.activate"));
        this.add(ModSoundEvents.ITEM_TRUTHSEEKER_DEACTIVATE.value(), SoundDefinition.definition()
            .with(
                SoundDefinition.Sound
                    .sound(Define.mod("item/truthseeker/deactivate"), SoundDefinition.SoundType.SOUND)
                    .volume(0.5f),
                SoundDefinition.Sound
                    .sound(Define.mod("item/truthseeker/deactivate"), SoundDefinition.SoundType.SOUND)
                    .pitch(0.89f)
                    .volume(0.5f),
                SoundDefinition.Sound
                    .sound(Define.mod("item/truthseeker/deactivate"), SoundDefinition.SoundType.SOUND)
                    .pitch(0.8f)
                    .volume(0.5f))
            .subtitle("subtitles.item.novia_additions.deactivate"));
    }
}
