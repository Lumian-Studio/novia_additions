package xyz.lumian.novia_additions.mixin;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.lumian.novia_additions.item.ModItemTags;



//**********************************************************************************************************************
@Mixin(Item.class)
public abstract class ItemMixin
{
    //******************************************************************************************************************
    @Inject(
        method = "inventoryTick",
        at = @At("TAIL")
    )
    public void onInventoryTick(final ItemStack stack, final Level level, final Entity entity, final int slotId,
                                final boolean isSelected, final CallbackInfo ci)
    {
        // We are doing some Allthemodium integration here, but this can be used for any kind of armor
        final int e_id = (slotId - Inventory.INVENTORY_SIZE);
        
        // check we are dealing with armor slots here, so we don't needlessly incur branching overhead every single tick
        // for every slot
        if (e_id < 0 || e_id > 3)
        {
            return;
        }
        
        if (!stack.isEmpty() && entity instanceof LivingEntity le)
        {
            switch (EquipmentSlot.values()[e_id + 2])
            {
                case EquipmentSlot.HEAD:
                {
                    if (le.hasEffect(MobEffects.DARKNESS) && stack.is(ModItemTags.RESISTANT_TO_DARKNESS_EFFECT))
                    {
                        le.removeEffect(MobEffects.DARKNESS);
                    }
                    
                    if (le.isInWater() && stack.is(ModItemTags.GRANTS_MAX_AIR_SUPPLY))
                    {
                        le.setAirSupply(300);
                    }
                } break;
                
                case EquipmentSlot.CHEST:
                {
                    if (le.isOnFire() && stack.is(ModItemTags.RESISTANT_TO_FIRE))
                    {
                        le.clearFire();
                    }
                    
                    if (le.hasEffect(MobEffects.WITHER) && stack.is(ModItemTags.RESISTANT_TO_WITHER_EFFECT))
                    {
                        le.removeEffect(MobEffects.WITHER);
                    }
                } break;
            }
        }
    }
}
