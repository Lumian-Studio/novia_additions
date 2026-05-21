package xyz.lumian.novia_additions.registry;

import com.mojang.serialization.Codec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import xyz.lumian.novia_additions.Define;

import java.util.function.Function;
import java.util.function.Supplier;



//**********************************************************************************************************************
public final class ModAttachments
{
    //******************************************************************************************************************
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister
        .create(NeoForgeRegistries.ATTACHMENT_TYPES, Define.MOD_ID);
    
    //==================================================================================================================
    public static final Supplier<AttachmentType<Boolean>> SEEKING = register("seeking",
        Function.identity(), (() -> false));
    
    public static final Supplier<AttachmentType<Boolean>> TRUTHSEEKER_INVISIBLE_DEFAULT = register(
        "truthseeker_invisible_default",
        (builder -> builder.serialize(Codec.BOOL).sync(ByteBufCodecs.BOOL)),
        (() -> false));
    
    //******************************************************************************************************************
    private static <T> Supplier<AttachmentType<T>> register(
        final String                                                         name,
        final Function<AttachmentType.Builder<T>, AttachmentType.Builder<T>> builder,
        final Supplier<T>                                                    defaultValueSupplier)
    {
        return ModAttachments.ATTACHMENTS.register(
            name,
            (() -> builder.apply(AttachmentType.builder(defaultValueSupplier)).build()));
    }
}
