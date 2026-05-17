package xyz.lumian.novia_additions.world.generator;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.Weight;

import net.minecraft.util.random.WeightedEntry;
import org.jspecify.annotations.Nullable;
import xyz.lumian.novia_additions.NoviaAdditions;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;



//**********************************************************************************************************************
public class WeightedHolderList<T>
{
    //******************************************************************************************************************
    public static class Wrapper<T>
    {
        //**************************************************************************************************************
        private final ResourceKey<? extends Registry<T>> registryKey;
        private final Holder<WeightedHolderList<?>>      list;
        
        //**************************************************************************************************************
        private Wrapper(final ResourceKey<? extends Registry<T>> registryKey,
                        final Holder<WeightedHolderList<?>>      holder)
        {
            this.registryKey = registryKey;
            this.list        = holder;
        }
        
        //==============================================================================================================
        public void validate()
        {
            if (!this.list.value().applicableTo(this.registryKey))
            {
                throw new IllegalStateException(list.unwrapKey().map(ResourceKey::toString).orElse("'Unbound'")
                    + " is not applicable to registry " + this.registryKey);
            }
        }
        
        public Holder<WeightedHolderList<?>> getHolder() { return this.list; }
        
        //==============================================================================================================
        @SuppressWarnings("unchecked")
        public WeightedHolderList<T> value()
        {
            this.validate();
            return (WeightedHolderList<T>) this.list.value();
        }
    }
    
    //******************************************************************************************************************
    public static final int DEFAULT_WEIGHT = 0;
    
    //------------------------------------------------------------------------------------------------------------------
    private static final Map<
        ResourceKey<? extends Registry<?>>,
        MapCodec<? extends WeightedHolderList<?>>
    > CACHED_STATIC = new HashMap<>();
    
    public static final Codec<WeightedHolderList<?>> CODEC = WeightedHolderList.dispatchCodec(ResourceLocation.CODEC
        .xmap(ResourceKey::createRegistryKey, ResourceKey::location));
    
    //******************************************************************************************************************
    public static <T> Wrapper<T> wrap(final ResourceKey<? extends Registry<T>> registryKey,
                                      final Holder<WeightedHolderList<?>>      holder)
    {
        final Wrapper<T> wrapper = new Wrapper<>(registryKey, holder);
        
        if (holder.isBound())
        {
            wrapper.validate();
        }
        
        return wrapper;
    }
    
    public static <T> Wrapper<T> wrap(final ResourceKey<? extends Registry<T>> registryKey,
                                      final WeightedHolderList<?>              list)
    {
        final Wrapper<T> wrapper = new Wrapper<>(registryKey, Holder.direct(list));
        wrapper.validate();
        return wrapper;
    }
    
    //==================================================================================================================
    private static <T> Codec<WeightedHolderList<?>> dispatchCodec(final Codec<ResourceKey<Registry<T>>> keyCodec)
    {
        return keyCodec.dispatch(
            "registry",
            (list -> WeightedHolderList.<T>convertHolderList(list).registryKey),
            (key -> RecordCodecBuilder.mapCodec(i -> i
                .group(
                    RegistryCodecs.homogeneousList(key).listOf()
                        .fieldOf("targets")
                        .forGetter(list -> WeightedHolderList.<T>convertHolderList(list).targets),
                    WeightedHolder.createCodec(key).listOf()
                                  .optionalFieldOf("distribution", List.of())
                                  .forGetter(list -> WeightedHolderList.<T>convertHolderList(list).distribution),
                    Codec.INT
                        .optionalFieldOf("defaultWeight", WeightedHolderList.DEFAULT_WEIGHT)
                        .forGetter(list -> list.defaultWeight.asInt()),
                    Codec.BOOL
                        .optionalFieldOf("override", false)
                        .forGetter(list -> list.overrideDuplicates))
                .apply(i, ((targets, dist, def, overrides)
                    -> new WeightedHolderList<>(key, targets, dist, def, overrides))))));
    }
    
    @SuppressWarnings("unchecked")
    private static <T2> WeightedHolderList<T2> convertHolderList(final WeightedHolderList<?> holderList)
    {
        return ((WeightedHolderList<T2>) holderList);
    }
    
    //==================================================================================================================
    private static <T> int getTotalWeight(final WeightedEntry.Wrapper<T>[] entries, final @Nullable Predicate<T> filter)
    {
        long result = 0;
        
        if (filter != null)
        {
            for (final var entry : entries)
            {
                if (filter.test(entry.data()))
                {
                    result += entry.getWeight().asInt();
                }
            }
        }
        else
        {
            for (final var entry : entries)
            {
                result += entry.getWeight().asInt();
            }
        }
        
        if (result > Integer.MAX_VALUE)
        {
            throw new IllegalStateException("total weight exceeds the integer limit");
        }
        
        return (int) result;
    }
    
    //******************************************************************************************************************
    private final ResourceKey<Registry<T>> registryKey;
    private final List<HolderSet<T>>      targets;
    private final List<WeightedHolder<T>> distribution;
    private final Weight                  defaultWeight;
    private final boolean                  overrideDuplicates;
    
    private int                                           total   = 0;
    private WeightedEntry.Wrapper<Holder<T>> @Nullable [] weights = null;
    
    //******************************************************************************************************************
    public WeightedHolderList(final ResourceKey<Registry<T>> registryKey,
                              final List<HolderSet<T>>       targets,
                              final List<WeightedHolder<T>> distribution,
                              final int                      defaultWeight,
                              final boolean                  overrideDuplicates)
    {
        this.registryKey        = registryKey;
        this.targets            = targets;
        this.distribution       = distribution;
        this.defaultWeight      = Weight.of(defaultWeight);
        this.overrideDuplicates = overrideDuplicates;
    }
    
    public WeightedHolderList(final ResourceKey<Registry<T>> registryKey,
                              final List<HolderSet<T>>       targets,
                              final List<WeightedHolder<T>> distribution,
                              final int                      defaultWeight)
    {
        this(registryKey, targets, distribution, defaultWeight, false);
    }
    
    public WeightedHolderList(final ResourceKey<Registry<T>> registryKey,
                              final List<HolderSet<T>>       targets,
                              final List<WeightedHolder<T>> distribution)
    {
        this(registryKey, targets, distribution, WeightedHolderList.DEFAULT_WEIGHT, false);
    }
    
    public WeightedHolderList(final ResourceKey<Registry<T>> registryKey,
                              final List<HolderSet<T>>       targets,
                              final int                      defaultWeight)
    {
        this(registryKey, targets, List.of(), defaultWeight, false);
    }
    
    public WeightedHolderList(final ResourceKey<Registry<T>> registryKey,
                              final List<HolderSet<T>>       targets)
    {
        this(registryKey, targets, List.of(), WeightedHolderList.DEFAULT_WEIGHT, false);
    }
    
    //==================================================================================================================
    public boolean has(final Holder<T> holder) { return this.targets.stream().anyMatch(set -> set.contains(holder)); }
    public boolean hasAll(final HolderSet<T> holders) { return holders.stream().allMatch(this::has); }
    public boolean hasAny(final HolderSet<T> holders) { return holders.stream().anyMatch(this::has); }
    
    public boolean hasAll(final Iterable<Holder<T>> holders)
    {
        return StreamSupport.stream(holders.spliterator(), false).allMatch(this::has);
    }
    
    public boolean hasAny(final Iterable<Holder<T>> holders)
    {
        return StreamSupport.stream(holders.spliterator(), false).anyMatch(this::has);
    }
    
    public boolean applicableTo(final ResourceKey<? extends Registry<?>> registryKey)
    {
        return this.registryKey.equals(registryKey);
    }
    
    public int numTargets()   { return this.targets.size(); }
    public int numOverrides() { return this.distribution.size(); }
    public boolean isEmpty()  { return this.targets.isEmpty(); }
    
    //==================================================================================================================
    public Optional<Holder<T>> getRandomHolder(final RandomSource random)
    {
        this.initWeights();
        assert (this.weights != null);
        
        if (this.total == 0)
        {
            return Optional.empty();
        }
        
        int index = random.nextInt(this.total);
        
        for (final var entry : this.weights)
        {
            index -= entry.getWeight().asInt();
            
            if (index < 0)
            {
                return Optional.of(entry.data());
            }
        }
        
        return Optional.empty();
    }
    
    public Optional<Holder<T>> getRandomHolder(final RandomSource random, final Predicate<Holder<T>> predicate)
    {
        this.initWeights();
        assert (this.weights != null);
        
        final int total = WeightedHolderList.getTotalWeight(this.weights, predicate);
        
        if (total == 0)
        {
            return Optional.empty();
        }
        
        int index = random.nextInt(total);
        
        for (final var entry : this.weights)
        {
            if (!predicate.test(entry.data()))
            {
                continue;
            }
            
            index -= entry.getWeight().asInt();
            
            if (index < 0)
            {
                return Optional.of(entry.data());
            }
        }
        
        return Optional.empty();
    }
    
    public Optional<Holder<T>> getRandomHolderOf(final RandomSource random, final Collection<Holder<T>> allowed)
    {
        return this.getRandomHolder(random, allowed::contains);
    }
    
    public Optional<Holder<T>> getRandomHolderOf(final RandomSource random, final HolderSet<T> allowed)
    {
        return this.getRandomHolder(random, allowed::contains);
    }
    
    //------------------------------------------------------------------------------------------------------------------
    private void initWeights()
    {
        if (this.weights == null)
        {
            this.weights = this.createWeights();
            this.total   = WeightedHolderList.getTotalWeight(this.weights, null);
        }
    }
    
    //==================================================================================================================
    @SuppressWarnings("unchecked")
    private WeightedEntry.Wrapper<Holder<T>>[] createWeights()
    {
        final Map<Holder<T>, Weight> weights = this.targets.stream()
            .flatMap(sets -> sets.stream().map(holder -> Pair.of(holder, this.defaultWeight)))
            .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
        final Set<Holder<T>> overrides = new HashSet<>();
        
        this.distribution.forEach(entry -> entry.accept(holder ->
        {
            if (weights.containsKey(holder))
            {
                if (this.overrideDuplicates || overrides.add(holder))
                {
                    weights.put(holder, entry.weight());
                }
                else
                {
                    NoviaAdditions.LOGGER.debug(
                    "Weighted block list, skipping duplicate override: {}",
                    holder.unwrapKey()
                        .map(key -> key.location().toString())
                        .orElseGet(() -> holder.value().toString()));
                }
            }
        }));
        
        return weights.entrySet().stream()
            .map(e -> new WeightedEntry.Wrapper<>(e.getKey(), e.getValue()))
            .toArray(WeightedEntry.Wrapper[]::new);
    }
}
