package xyz.lumian.novia_additions.item.medallions;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import xyz.lumian.novia_additions.entity.LumaProjectile;



//**********************************************************************************************************************
public class CelestialMedallionItem
    extends MedallionItem
{
    //******************************************************************************************************************
    public CelestialMedallionItem(final Properties props) { super(props); }
    
    //==================================================================================================================
    @Override public @Nullable ParticleOptions getUseParticle() { return ParticleTypes.GLOW_SQUID_INK; }
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
            final Projectile proj = LumaProjectile.create(level, player);
            proj.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1f, 0f);
            level.addFreshEntity(proj);
        }
        
        return true;
    }
}
