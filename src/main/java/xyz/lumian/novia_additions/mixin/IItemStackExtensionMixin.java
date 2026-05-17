package xyz.lumian.novia_additions.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.extensions.IItemStackExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.lumian.novia_additions.item.ModItemTags;



//**********************************************************************************************************************
@Mixin(IItemStackExtension.class)
public interface IItemStackExtensionMixin
{
    //******************************************************************************************************************
    @Shadow private ItemStack self() { throw new AssertionError(); }
    
    //******************************************************************************************************************
    @Inject(
        method      = "canWalkOnPowderedSnow",
        at          = @At("HEAD"),
        cancellable = true
    )
    default void canWalkOnPowderedSnow(final LivingEntity wearer, final CallbackInfoReturnable<Boolean> cir)
    {
        if (this.self().is(ModItemTags.CAN_WALK_ON_POWDERED_SNOW))
        {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
    
    @Inject(
        method      = "makesPiglinsNeutral",
        at          = @At("HEAD"),
        cancellable = true
    )
    default void makesPiglinsNeutral(final LivingEntity wearer, final CallbackInfoReturnable<Boolean> cir)
    {
        if (this.self().is(ModItemTags.SOOTHES_PIGLINS))
        {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
