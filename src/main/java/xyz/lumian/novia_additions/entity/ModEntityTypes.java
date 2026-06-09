package xyz.lumian.novia_additions.entity;


import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.lumian.novia_additions.Define;

import java.util.function.Supplier;



//**********************************************************************************************************************
public final class ModEntityTypes
{
    //******************************************************************************************************************
    public static final DeferredRegister<EntityType<?>> TYPES = DeferredRegister
        .create(Registries.ENTITY_TYPE, Define.MOD_ID);
    
    //==================================================================================================================
    public static final Supplier<EntityType<LumaProjectile>> LUMA_PROJECTILE = register("luma", EntityType.Builder
        .of(LumaProjectile::new, MobCategory.MISC)
            .sized(0.5F, 0.5F)
            .eyeHeight(0.13F)
            .clientTrackingRange(4)
            .updateInterval(20));
    
    //******************************************************************************************************************
    private static <T extends Entity> Supplier<EntityType<T>> register(final String                name,
                                                                       final EntityType.Builder<T> builder)
    {
        return ModEntityTypes.TYPES.register(name, (() -> builder.build(name)));
    }
}
