package xyz.lumian.novia_additions.item.medallions;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SimpleExplosionDamageCalculator;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;



//**********************************************************************************************************************
public class AngelMedallionItem
    extends MedallionItem
{
    //******************************************************************************************************************
    public AngelMedallionItem(final Properties props) { super(props); }
    
    //==================================================================================================================
    @Override public @Nullable ParticleOptions getUseParticle() { return ParticleTypes.GLOW; }
    @Override public int getUseCooldown() { return 50; }
    
    //==================================================================================================================
    @Override public boolean shouldCooldownIncreasePerStage() { return true; }
    @Override public boolean shouldDamagePerStage() { return false; }
    
    //==================================================================================================================
    @Override
    public boolean releaseCharge(final Level level, final Player player, final ItemStack stack, final int stage)
    {
        if (!level.isClientSide)
        {
            final ExplosionDamageCalculator edc = new SimpleExplosionDamageCalculator(
                true, false,
                Optional.of((float) stage),
                BuiltInRegistries.BLOCK.getTag(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity()));
            level.explode(null, null, edc, player.getX(), player.getY(), player.getZ(), 1.2F, false,
                          Level.ExplosionInteraction.TRIGGER, ParticleTypes.GUST_EMITTER_SMALL,
                          ParticleTypes.GUST_EMITTER_LARGE, SoundEvents.WIND_CHARGE_BURST);
        }
        
        return true;
    }
}
