package xyz.lumian.novia_additions.entity;


import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.registries.DeferredBlock;
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
    
    
    //******************************************************************************************************************
    private static <T extends Entity> Supplier<EntityType<?>> register(final String name, final EntityType<T> type)
    {
        return ModEntityTypes.TYPES.register(name, (() -> type));
    }
}
