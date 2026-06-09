package xyz.lumian.novia_additions.entity;

import lombok.Getter;
import net.minecraft.Util;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;



//**********************************************************************************************************************
public class LumaProjectile
    extends Projectile
{
    //******************************************************************************************************************
    public static LumaProjectile create(final Level level)
    {
        return new LumaProjectile(ModEntityTypes.LUMA_PROJECTILE.get(), level);
    }
    
    public static LumaProjectile create(final Level level, final Player player)
    {
        return Util.make(new LumaProjectile(ModEntityTypes.LUMA_PROJECTILE.get(), level), (luma ->
        {
            luma.setOwner(player);
            luma.setPos(player.getEyePosition());
        }));
    }
    
    //******************************************************************************************************************
    private int tickCount = 0;
    
    @Getter private float oldRotX = 0f;
    @Getter private float oldRotY = 0f;
    
    //******************************************************************************************************************
    protected LumaProjectile(final EntityType<? extends Projectile> entityType, final Level level)
    {
        super(entityType, level);
    }
    
    //==================================================================================================================
    @Override protected void defineSynchedData(final SynchedEntityData.Builder builder) {}
    
    //==================================================================================================================
    @SuppressWarnings("resource")
    public void tick()
    {
        super.tick();
        
        if (this.level().isClientSide)
        {
            if (this.tickCount % 5 == 0)
            {
                this.level().addParticle(ParticleTypes.GLOW, this.getX(), this.getY(), this.getZ(), 1f, 0f, 1f);
            }
            
            this.oldRotX = this.getXRot();
            this.oldRotY = this.getYRot();
            this.setXRot((this.getXRot() + (float) (Math.PI / 4)) % 180);
            this.setYRot((this.getYRot() + (float) (Math.PI / 8)) % 180);
            
            ++this.tickCount;
        }
    }
    
    //==================================================================================================================
    @Override
    protected void onHitBlock(final BlockHitResult result)
    {
        super.onHitBlock(result);
    }
}
