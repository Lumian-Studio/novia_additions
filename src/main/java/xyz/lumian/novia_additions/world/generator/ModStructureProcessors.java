package xyz.lumian.novia_additions.world.generator;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.lumian.novia_additions.Define;
import xyz.lumian.novia_additions.world.generator.processor.*;



//**********************************************************************************************************************
public class ModStructureProcessors
{
    //******************************************************************************************************************
    public static final DeferredRegister<StructureProcessorType<?>> PROCESSORS = DeferredRegister
        .create(Registries.STRUCTURE_PROCESSOR, Define.MOD_ID);
    
    //==================================================================================================================
    public static final Holder<StructureProcessorType<?>> ITEM_FRAME_LOOT
        = register("item_frame_loot", ItemFrameLootProcessor.CODEC);
    public static final Holder<StructureProcessorType<?>> MUD_BLOCK_AGE
        = register("mud_block_age", MudBlockAgeProcessor.CODEC);
    public static final Holder<StructureProcessorType<?>> WEIGHTED_BLOCK_REPLACEMENT
        = register("weighted_block_replacement", WeightedBlockReplacementProcessor.CODEC);
    public static final Holder<StructureProcessorType<?>> BOOKSHELF_LOOT
        = register("bookshelf_loot", BookshelfLootProcessor.CODEC);
    
    //******************************************************************************************************************
    private static <P extends StructureProcessor> Holder<StructureProcessorType<?>>
    register(
        final String      name,
        final MapCodec<P> codec
    )
    {
        return ModStructureProcessors.PROCESSORS.register(name, (() -> (StructureProcessorType<P>)(() -> codec)));
    }
}
