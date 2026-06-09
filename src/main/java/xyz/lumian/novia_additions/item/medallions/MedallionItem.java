package xyz.lumian.novia_additions.item.medallions;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import xyz.lumian.novia_additions.ModSoundEvents;



//**********************************************************************************************************************
public abstract class MedallionItem
    extends Item
{
    //******************************************************************************************************************
    public MedallionItem(final Properties props)
    {
        super(props
            .stacksTo(1)
            .rarity(Rarity.RARE)
            .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));
    }
    
    //==================================================================================================================
    public int getUseCooldown()   { return 100; }
    public int getMaxStages()     { return 3;  }
    public int getTicksPerStage() { return 20; }
    public int getUseDamage()     { return 1;  }
    
    //==================================================================================================================
    public boolean shouldDamagePerStage() { return true; }
    public boolean shouldCooldownIncreasePerStage() { return false; }
    
    //==================================================================================================================
    @Override public int getUseDuration(final ItemStack stack, final LivingEntity entity) { return 72000; }
    @Override public UseAnim getUseAnimation(final ItemStack stack) { return UseAnim.BOW; }
    public @Nullable ParticleOptions getUseParticle() { return null; }
    
    //==================================================================================================================
    public abstract boolean releaseCharge(Level level, Player player, ItemStack stack, int stage);
    
    //==================================================================================================================
    @Override
    public InteractionResultHolder<ItemStack> use(final Level level, final Player player, final InteractionHand hand)
    {
        if (this.getUseCooldown() > 0 && player.getCooldowns().isOnCooldown(this))
        {
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
        
        player.startUsingItem(hand);
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
    
    @Override
    public void releaseUsing(final ItemStack stack, final Level level, final LivingEntity entity, final int timeLeft)
    {
        if (!(entity instanceof Player player))
        {
            return;
        }
        
        final int charged_ticks = (this.getUseDuration(stack, entity) - timeLeft);
        
        if (charged_ticks < this.getTicksPerStage())
        {
            return;
        }
        
        final int stage = Math.min(this.getMaxStages(), (charged_ticks / this.getTicksPerStage()));
        
        if (!player.isCreative() && this.getUseCooldown() > 0)
        {
            final int cooldown = (this.getUseCooldown() * (this.shouldCooldownIncreasePerStage() ? stage : 1));
            player.getCooldowns().addCooldown(this, cooldown);
        }
        
        if (this.releaseCharge(level, player, stack, stage))
        {
            player.awardStat(Stats.ITEM_USED.get(this));
            stack.hurtAndBreak(
                (this.getUseDamage() * (this.shouldDamagePerStage() ? stage : 1)),
                player,
                LivingEntity.getSlotForHand(player.getUsedItemHand()));
        }
    }
    
    @Override
    public void onUseTick(final Level level, final LivingEntity entity, final ItemStack stack, final int timeLeft)
    {
        if (!(entity instanceof Player player))
        {
            return;
        }
        
        final int charged_ticks = (this.getUseDuration(stack, entity) - timeLeft);
        final int last_tick     = (this.getMaxStages() * this.getTicksPerStage());
        
        if (charged_ticks > last_tick)
        {
            return;
        }
        
        if ((charged_ticks % this.getTicksPerStage()) == 0 && charged_ticks > 0)
        {
            if (player.isLocalPlayer())
            {
                final int stage = (charged_ticks / this.getTicksPerStage());
                player.playSound(ModSoundEvents.ITEM_MEDALLATION_STAGE_INDICATOR.value(), 0.4f,
                                 (1.5f + 0.075f * stage));
            }
            
            if (this.getUseParticle() != null && level instanceof ServerLevel slevel)
            {
                final Vec3 eyes = player.getEyePosition().add(player.getLookAngle());
                slevel.sendParticles(this.getUseParticle(), eyes.x, eyes.y, eyes.z, 7, 0, 0, 0, 0.05f);
            }
        }
    }
}
