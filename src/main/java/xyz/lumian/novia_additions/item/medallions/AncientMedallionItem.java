package xyz.lumian.novia_additions.item.medallions;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;



//**********************************************************************************************************************
public class AncientMedallionItem
    extends MedallionItem
{
    //******************************************************************************************************************
    public AncientMedallionItem(final Properties props) { super(props); }
    
    //==================================================================================================================
    @Override public @Nullable ParticleOptions getUseParticle() { return ParticleTypes.SCULK_CHARGE_POP; }
    
    //==================================================================================================================
    @Override
    public boolean releaseCharge(final Level level, final Player player, final ItemStack stack, final int stage)
    {
        if (level instanceof ServerLevel slevel)
        {
            final HitResult hit = ProjectileUtil.getHitResultOnViewVector(
                player,
                (entity1 -> entity1 instanceof LivingEntity),
                15);
            
            final Vec3         pos  = player.getEyePosition();
            final Vec3         dest;
            final LivingEntity entity;
            
            if (hit instanceof EntityHitResult ehr)
            {
                entity = (LivingEntity) ehr.getEntity();
                dest   = entity.getEyePosition().subtract(pos);
            }
            else
            {
                entity = null;
                dest   = hit.getLocation().subtract(pos);
            }
            
            final float amount = ((float) stage / this.getMaxStages());
            final Vec3  dir    = dest.normalize();
            final int   i      = (Mth.floor(dest.length()));
            
            for (int j = 1; j < i; ++j)
            {
                final Vec3 vec33 = pos.add(dir.scale(j));
                slevel.sendParticles(ParticleTypes.SONIC_BOOM, vec33.x, vec33.y, vec33.z, 1, 0f, 0f, 0f, 0f);
            }
            
            if (entity != null)
            {
                final DamageSource source = slevel.damageSources().sonicBoom(player);
                
                if (entity.hurt(source, (10f * amount)))
                {
                    final double knockback_resistance = entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
                    final double d1                   = (0.5d * (1d - knockback_resistance) * amount);
                    final double d0                   = (2.5d * (1d - knockback_resistance) * amount);
                    entity.push((dir.x() * d0), (dir.y() * d1), (dir.z() * d0));
                }
            }
        }
        
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WARDEN_SONIC_BOOM,
                        SoundSource.PLAYERS, 0.7f, 1f);
        return true;
    }
}
