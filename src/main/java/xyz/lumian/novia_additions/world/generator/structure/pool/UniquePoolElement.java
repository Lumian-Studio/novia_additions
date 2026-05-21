package xyz.lumian.novia_additions.world.generator.structure.pool;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.Optional;



//**********************************************************************************************************************
public class UniquePoolElement
    extends SinglePoolElement
{
    //******************************************************************************************************************
    public static final MapCodec<UniquePoolElement> CODEC = RecordCodecBuilder.mapCodec(i -> i
        .group(
            Codec
                .<Either<ResourceLocation, StructureTemplate>>of(
                    UniquePoolElement::encodeTemplate,
                    ResourceLocation.CODEC.map(Either::left))
                .fieldOf("location")
                .forGetter(elm -> elm.template),
            SinglePoolElement   .processorsCodec(),
            StructurePoolElement.projectionCodec(),
            SinglePoolElement   .overrideLiquidSettingsCodec(),
            Codec.INT
                .optionalFieldOf("maxOccurrences", 1)
                .forGetter(elm -> elm.maxOccurrences))
        .apply(i, UniquePoolElement::new));
    
    //******************************************************************************************************************
    private static <T> DataResult<T> encodeTemplate(final Either<ResourceLocation, StructureTemplate> template,
                                                    final DynamicOps<T>                               ops,
                                                    final T                                           values)
    {
        return template.map(
            (location  -> ResourceLocation.CODEC.encode(location, ops, values)),
            (template1 -> DataResult.error(() -> "Can not serialize a runtime pool element"))
        );
    }
    
    //******************************************************************************************************************
    @Getter
    protected final int maxOccurrences;
    
    //******************************************************************************************************************
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public UniquePoolElement(final ResourceLocation                 template,
                             final Holder<StructureProcessorList>   processors,
                             final StructureTemplatePool.Projection projection,
                             final Optional<LiquidSettings>         overrideLiquidSettings,
                             final int                              maxOccurrences)
    {
        this(Either.left(template), processors, projection, overrideLiquidSettings, maxOccurrences);
    }
    
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private UniquePoolElement(final Either<ResourceLocation, StructureTemplate> template,
                              final Holder<StructureProcessorList>              processors,
                              final StructureTemplatePool.Projection            projection,
                              final Optional<LiquidSettings>                    overrideLiquidSettings,
                              final int                                         maxOccurrences)
    {
        super(template, processors, projection, overrideLiquidSettings);
        this.maxOccurrences = maxOccurrences;
        
        if (this.maxOccurrences < 1)
        {
            throw new IllegalArgumentException("max occurrences must be positive and above 0");
        }
    }
    
    //==================================================================================================================
    @Override public StructurePoolElementType<?> getType() { return ModStructurePoolElementTypes.UNIQUE.value(); }
    
    public ResourceLocation getId() { return this.template.left().orElseThrow(); }
}
