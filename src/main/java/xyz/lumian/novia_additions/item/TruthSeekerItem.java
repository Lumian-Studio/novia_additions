package xyz.lumian.novia_additions.item;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.jspecify.annotations.Nullable;
import xyz.lumian.novia_additions.ModSoundEvents;
import xyz.lumian.novia_additions.block.TruthSeekerBlockBase;
import xyz.lumian.novia_additions.registry.ModAttachments;



//**********************************************************************************************************************
@EventBusSubscriber
public class TruthSeekerItem
    extends Item
{
    //******************************************************************************************************************
    @SubscribeEvent
    private static void whileUsing(final PlayerTickEvent.Post e)
    {
        final Player player = e.getEntity();
        
        if (!player.isLocalPlayer())
        {
            return;
        }
        
        final boolean is_seeking = player.getData(ModAttachments.SEEKING);
        
        if (is_seeking && !player.isHolding(ModItems.TRUTHSEEKER.get()))
        {
            player.setData(ModAttachments.SEEKING, false);
            player.playSound(ModSoundEvents.ITEM_TRUTHSEEKER_DEACTIVATE.value(), 1.0F, 1.0F);
        }
    }
    
    @SubscribeEvent
    private static void interactWithEntity(final PlayerInteractEvent.EntityInteract e)
    {
        final Player player = e.getEntity();
        
        if (
            !player.isCreative()
            || !player.isCrouching()
            || !player.hasPermissions(1)
            || !player.getMainHandItem().is(ModItems.TRUTHSEEKER)
        )
        {
            return;
        }
        
        final Entity  entity = e.getTarget();
        final Boolean mode   = entity.getExistingDataOrNull(ModAttachments.TRUTHSEEKER_INVISIBLE_DEFAULT);
        final Boolean next   = TruthSeekerItem.nextState(mode);
        
        if (next != null)
        {
            entity.setData(ModAttachments.TRUTHSEEKER_INVISIBLE_DEFAULT, next);
        }
        else
        {
            entity.removeData(ModAttachments.TRUTHSEEKER_INVISIBLE_DEFAULT);
        }
        
        if (player instanceof ServerPlayer splayer)
        {
            final String state = (next == null ? "None" : (next ? "Default Invisible" : "Default Visible"));
            splayer.displayClientMessage(Component.literal("Set entity truthseeker state to: " + state), true);
        }
        else
        {
            player.playSound(SoundEvents.CANDLE_PLACE, 1.0F, 1.0F);
        }
        
        e.setCancellationResult(InteractionResult.sidedSuccess(player.isLocalPlayer()));
        e.setCanceled(true);
    }
    
    private static @Nullable Boolean nextState(final @Nullable Boolean current)
    {
        if (current == null) return false;
        if (!current)        return true;
        return null;
    }
    
    //******************************************************************************************************************
    public TruthSeekerItem(final Properties properties) { super(properties.stacksTo(1)); }
    
    //==================================================================================================================
    @Override
    public InteractionResultHolder<ItemStack> use(final Level level, final Player player, final InteractionHand hand)
    {
        final boolean seeking = player.getData(ModAttachments.SEEKING);
        player.setData(ModAttachments.SEEKING, !seeking);
        
        final SoundEvent sound = (!seeking
            ? ModSoundEvents.ITEM_TRUTHSEEKER_ACTIVATE.value()
            : ModSoundEvents.ITEM_TRUTHSEEKER_DEACTIVATE.value());
        player.playSound(sound, 1.0F, 1.0F);
        
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
    }
    
    @Override
    public InteractionResult useOn(final UseOnContext context)
    {
        final Player player = context.getPlayer();
        
        if (player == null || !player.isCreative() || !player.isCrouching())
        {
            return super.useOn(context);
        }
        
        final BlockState                           state = context.getLevel().getBlockState(context.getClickedPos());
        final TruthSeekerBlockBase.TruthSeekerMode mode  = state
            .getOptionalValue(TruthSeekerBlockBase.TRUTH_SEEKER_MODE)
            .orElse(null);
        
        if (mode == null)
        {
            return super.useOn(context);
        }
        
        final TruthSeekerBlockBase.TruthSeekerMode next = TruthSeekerBlockBase.TruthSeekerMode
            .values()[((mode.ordinal() + 1) % TruthSeekerBlockBase.TruthSeekerMode.values().length)];
        context.getLevel().setBlock(
            context.getClickedPos(),
            state.setValue(TruthSeekerBlockBase.TRUTH_SEEKER_MODE, next),
            TruthSeekerBlockBase.UPDATE_IMMEDIATE);
        
        if (player instanceof ServerPlayer splayer)
        {
            splayer.displayClientMessage(Component.literal("Set state to: " + next.name()), true);
        }
        
        return InteractionResult.sidedSuccess(player.isLocalPlayer());
    }
}
