package xyz.lumian.novia_additions.data;

import com.mojang.datafixers.util.Function3;
import lombok.Getter;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jspecify.annotations.Nullable;
import xyz.lumian.novia_additions.Define;
import xyz.lumian.novia_additions.item.ModBannerPatterns;
import xyz.lumian.novia_additions.item.ModJukeboxSongs;
import xyz.lumian.novia_additions.registry.ModRegistries;
import xyz.lumian.novia_additions.world.ModPaintingVariants;
import xyz.lumian.novia_additions.world.generator.*;
import xyz.lumian.novia_additions.world.generator.structure.ModStructureSets;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;



//**********************************************************************************************************************
// this class is over-engineered! why, you ask? because I can
@EventBusSubscriber(modid = Define.MOD_ID)
public class ModDataGenerators
{
    //******************************************************************************************************************
    @FunctionalInterface
    public interface Factory<T extends DataProvider>
        extends Function3<PackOutput, CompletableFuture<HolderLookup.Provider>, ExistingFileHelper, T>
    {}
    
    @FunctionalInterface
    public interface FactorySet
    {
        //**************************************************************************************************************
        @FunctionalInterface
        interface LookupFunc<T extends DataProvider>
            extends BiFunction<PackOutput, CompletableFuture<HolderLookup.Provider>, T>
        {}
        
        @FunctionalInterface
        interface HelperFunc<T extends DataProvider> extends BiFunction<PackOutput, ExistingFileHelper, T> {}
        
        interface PackFunc<T extends DataProvider> extends Function<PackOutput, T> {}
        
        //**************************************************************************************************************
        static FactorySet of() { return ((run, reg) -> {}); }
        
        //**************************************************************************************************************
        void register(final boolean run, final Registrant reg);
        
        //==============================================================================================================
        default <T extends DataProvider> FactorySet with(final Factory<T> func)
        {
            return ((run, reg) -> {
                this.register(run, reg);
                reg.register(run, func);
            });
        }
        
        default <T extends DataProvider> FactorySet with(final LookupFunc<T> func)
        {
            return ((run, reg) ->
            {
                this.register(run, reg);
                reg.register(run, ((output, future, helper) -> func.apply(output, future)));
            });
        }
        
        default <T extends DataProvider> FactorySet with(final HelperFunc<T> func)
        {
            return ((run, reg) ->
            {
                this.register(run, reg);
                reg.register(run, ((output, future, helper) -> func.apply(output, helper)));
            });
        }
        
        default <T extends DataProvider> FactorySet with(final PackFunc<T> func)
        {
            return ((run, reg) ->
            {
                this.register(run, reg);
                reg.register(run, ((output, future, helper) -> func.apply(output)));
            });
        }
    }
    
    public static class Registrant
    {
        //**************************************************************************************************************
        @Getter private final PackOutput                               output;
        @Getter private final CompletableFuture<HolderLookup.Provider> lookup;
        @Getter private final ExistingFileHelper                       helper;
        
        private final DataGenerator generator;
        
        //**************************************************************************************************************
        public Registrant(final GatherDataEvent event, final @Nullable RegistrySetBuilder registrySet)
        {
            this.generator = event.getGenerator();
            this.output    = event.getGenerator().getPackOutput();
            this.helper    = event.getExistingFileHelper();
            
            if (registrySet != null)
            {
                this.lookup = event
                    .addProvider(new DatapackBuiltinEntriesProvider(
                        this.output,
                        event.getLookupProvider(),
                        registrySet,
                        Set.of(Define.MOD_ID)))
                    .getRegistryProvider();
            }
            else this.lookup = event.getLookupProvider();
        }
        
        //==============================================================================================================
        public void register(final boolean run, final Factory<?> factory)
        {
            this.generator.addProvider(run, factory.apply(this.output, this.lookup, this.helper));
        }
        
        public void registerAll(final boolean run, final Collection<Factory<?>> factories)
        {
            factories.forEach(factory -> this.register(run, factory));
        }
    }
    
    //******************************************************************************************************************
    private static final RegistrySetBuilder REGISTRY_DATA = (new RegistrySetBuilder())
        .add(Registries             .BANNER_PATTERN,       ModBannerPatterns::bootstrap)
        .add(Registries             .JUKEBOX_SONG,         ModJukeboxSongs::bootstrap)
        .add(Registries             .TEMPLATE_POOL,        ModPools::bootstrap)
        .add(Registries             .STRUCTURE,            ModStructures::bootstrap)
        .add(Registries             .STRUCTURE_SET,        ModStructureSets::bootstrap)
        .add(Registries             .CONFIGURED_FEATURE,   ModFeatures::bootstrapConfigured)
        .add(Registries             .PLACED_FEATURE,       ModFeatures::bootstrapPlaced)
        .add(Registries             .PAINTING_VARIANT,     ModPaintingVariants::bootstrap)
        .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS,      ModFeatures::bootstrapModifiers)
        .add(ModRegistries          .WEIGHTED_HOLDER_LIST, ModWeightedHolderLists::bootstrap);
    
    private static final FactorySet SERVER = FactorySet.of()
        .with(ModTagProvider.Blocks::new)
        .with(ModTagProvider.Items::new)
        .with(ModTagProvider.Biomes::new)
        .with(ModTagProvider.PlacedFeatures::new)
        .with(ModTagProvider.BannerPatterns::new)
        .with(ModRecipeProvider::new)
        .with(ModLootTableProvider::new);
    
    private static final FactorySet CLIENT = FactorySet.of()
        .with(ModBlockStateProvider::new)
        .with(ModItemModelProvider::new)
        .with(ModSoundDefinitionProvider::new);
    
    //******************************************************************************************************************
    @SubscribeEvent
    public static void gatherData(final GatherDataEvent e)
    {
        final Registrant reg = new Registrant(e, ModDataGenerators.REGISTRY_DATA);
        ModDataGenerators.SERVER.register(e.includeServer(), reg);
        ModDataGenerators.CLIENT.register(e.includeClient(), reg);
        
        e.createProvider(ModLanguageProvider::new);
    }
}
