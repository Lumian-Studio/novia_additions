package xyz.lumian.novia_additions.world.generator;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import xyz.lumian.novia_additions.Define;
import xyz.lumian.novia_additions.world.generator.structure.pool.DemithrilShrinePools;
import xyz.lumian.novia_additions.world.generator.structure.pool.VasquilanCatacombPools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;



//**********************************************************************************************************************
public class ModPools
{
    //******************************************************************************************************************
    @FunctionalInterface
    public interface ProcessorBuilder
    {
        //**************************************************************************************************************
        void add(final List<StructureProcessor> processors);
        
        //==============================================================================================================
        default ProcessorBuilder with(final StructureProcessor processor)
        {
            return (list ->
            {
                this.add(list);
                list.add(processor);
            });
        }
        
        default ProcessorBuilder withAll(final Collection<StructureProcessor> processors)
        {
            return (list ->
            {
                this.add(list);
                list.addAll(processors);
            });
        }
        
        default ProcessorBuilder withAll(final StructureProcessorList processors)
        {
            return this.withAll(processors.list());
        }
        
        default ProcessorBuilder withAll(final StructureProcessor ...processors)
        {
            return this.withAll(List.of(processors));
        }
        
        default ProcessorBuilder withAll(final ProcessorBuilder builder)
        {
            return (list ->
            {
                this.add(list);
                builder.add(list);
            });
        }
        
        default ProcessorBuilder withRule(final ProcessorRule rule)
        {
            return (list ->
            {
                this.add(list);
                list.add(new RuleProcessor(List.of(rule)));
            });
        }
        
        default ProcessorBuilder withAllRules(final Collection<ProcessorRule> rules)
        {
            return (list ->
            {
                this.add(list);
                list.add(new RuleProcessor(List.copyOf(rules)));
            });
        }
        
        default ProcessorBuilder withAllRules(final ProcessorRule ...rules)
        {
            return this.withAllRules(List.of(rules));
        }
        
        default Holder<StructureProcessorList> build()
        {
            final ArrayList<StructureProcessor> processors = new ArrayList<>();
            this.add(processors);
            return Holder.direct(new StructureProcessorList(processors));
        }
    }
    
    //==================================================================================================================
    public static ProcessorBuilder processorBuilder() { return (list -> {}); }
    
    //******************************************************************************************************************
    public static void bootstrap(final BootstrapContext<StructureTemplatePool> context)
    {
        DemithrilShrinePools  .bootstrap(context);
        VasquilanCatacombPools.bootstrap(context);
    }
    
    //==================================================================================================================
    public static ResourceKey<StructureTemplatePool> create(final String name)
    {
        return Define.key(Registries.TEMPLATE_POOL, name);
    }
    
    public static void register(final BootstrapContext<StructureTemplatePool> context,
                                final String                                  name,
                                final StructureTemplatePool                   pool)
    {
        context.register(ModPools.create(name), pool);
    }
    
    //==================================================================================================================
    public static ProcessorRule simpleRule(final RuleTest target, final BlockState replacement)
    {
        return new ProcessorRule(target, AlwaysTrueTest.INSTANCE, replacement);
    }
    
    public static Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>
    simpleElement(
        final ResourceLocation               id,
        final Holder<StructureProcessorList> processors,
        final LiquidSettings                 liquidSettings,
        final int                            weight
    )
    {
        return Pair.of(SinglePoolElement.single(id.toString(), processors, liquidSettings), weight);
    }
    
    public static Holder<StructureProcessorList> directProc(StructureProcessor ...processors)
    {
        return Holder.direct(new StructureProcessorList(List.of(processors)));
    }
}
