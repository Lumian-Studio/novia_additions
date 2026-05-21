package xyz.lumian.novia_additions.world.generator.structure.pool;


import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.lumian.novia_additions.Define;



//**********************************************************************************************************************
public final class ModStructurePoolElementTypes
{
    //******************************************************************************************************************
    public static final DeferredRegister<StructurePoolElementType<?>> TYPES = DeferredRegister
        .create(Registries.STRUCTURE_POOL_ELEMENT, Define.MOD_ID);
    
    //==================================================================================================================
    public static final Holder<StructurePoolElementType<?>> UNIQUE
        = register("unique_pool_element", UniquePoolElement.CODEC);
    
    //******************************************************************************************************************
    private static <T extends StructurePoolElement> Holder<StructurePoolElementType<?>> register(
        final String      name,
        final MapCodec<T> codec
    )
    {
        return ModStructurePoolElementTypes.TYPES.register(name, (() -> (StructurePoolElementType<T>)(() -> codec)));
    }
}
