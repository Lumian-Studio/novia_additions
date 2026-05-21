package xyz.lumian.novia_additions.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderHighlightEvent;
import xyz.lumian.novia_additions.Define;
import xyz.lumian.novia_additions.block.TruthSeekerBlockBase;
import xyz.lumian.novia_additions.registry.ModAttachments;

import java.util.Objects;



//**********************************************************************************************************************
public enum TruthseekerRenderer
{
    //******************************************************************************************************************
    INSTANCE;
    
    //******************************************************************************************************************
    public static final ResourceLocation POST_EFFECT_SHADER = Define.mod("shaders/post/truthseeker.json");
    
    //******************************************************************************************************************
    private final Minecraft client;
    
    private boolean screenEffectApplied = false;
    
    //******************************************************************************************************************
    TruthseekerRenderer()
    {
        this.client = Minecraft.getInstance();
    }
    
    //==================================================================================================================
    private boolean isCheckedIntoRenderer()
    {
        final PostChain chain = this.client.gameRenderer.currentEffect();
        return (chain != null && chain.getName().equals(TruthseekerRenderer.POST_EFFECT_SHADER.toString()));
    }
    
    //==================================================================================================================
    @SuppressWarnings("resource")
    @SubscribeEvent
    private void renderHighlight(final RenderHighlightEvent.Block e)
    {
        final Player player = Objects.requireNonNull(this.client.player);
        
        if (player.isCreative())
        {
            return;
        }
        
        final Level      level = player.level();
        final BlockState state = level.getBlockState(e.getTarget().getBlockPos());
        
        if (TruthSeekerBlockBase.isBlockInvisible(player, state))
        {
            e.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void clientTick(final ClientTickEvent.Pre e)
    {
        final Player player = this.client.player;
        
        if (player != null)
        {
            final boolean seeking = player.getData(ModAttachments.SEEKING);
            
            if (seeking)
            {
                if (player.isSpectator())
                {
                    if (this.screenEffectApplied)
                    {
                        if (this.isCheckedIntoRenderer())
                        {
                            this.client.gameRenderer.shutdownEffect();
                        }
                        
                        this.screenEffectApplied = false;
                    }
                    
                    return;
                }
                
                if (!this.screenEffectApplied)
                {
                    this.client.gameRenderer.loadEffect(TruthseekerRenderer.POST_EFFECT_SHADER);
                    this.screenEffectApplied = true;
                }
            }
            else
            {
                if (this.screenEffectApplied)
                {
                    if (this.isCheckedIntoRenderer())
                    {
                        this.client.gameRenderer.shutdownEffect();
                    }
                    
                    this.screenEffectApplied = false;
                }
            }
        }
    }
}
