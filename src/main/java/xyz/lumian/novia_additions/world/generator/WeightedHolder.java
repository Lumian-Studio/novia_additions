package xyz.lumian.novia_additions.world.generator;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.util.random.Weight;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;



//**********************************************************************************************************************
public sealed interface WeightedHolder<T>
    extends Consumer<Consumer<Holder<T>>>
    permits
        WeightedHolder.Single,
        WeightedHolder.Set
{
    //******************************************************************************************************************
    record Single<T>(Holder<T> holder, Weight weight)
        implements WeightedHolder<T>
    {
        //**************************************************************************************************************
        public static <T> Codec<Single<T>> createCodec(final ResourceKey<Registry<T>> registryKey)
        {
            return RecordCodecBuilder.create(i -> i
                .group(
                    RegistryFixedCodec.create(registryKey)
                        .fieldOf("name")
                        .forGetter(holder -> holder.holder),
                    Weight.CODEC
                        .fieldOf("weight")
                        .forGetter(Single::weight))
                .apply(i, Single::new));
        }
        
        //**************************************************************************************************************
        public Single(final Holder<T> holder, final int weight) { this(holder, Weight.of(weight)); }
        
        //**************************************************************************************************************
        private Single(final HolderGetter<T> lookup, final ResourceKey<T> key, final Weight weight)
        {
            this(lookup.getOrThrow(key), weight);
        }
        
        //==============================================================================================================
        @Override public void accept(final Consumer<Holder<T>> consumer) { consumer.accept(this.holder); }
    }
    
    record Set<T>(HolderSet<T> holders, Weight weight)
        implements WeightedHolder<T>
    {
        //**************************************************************************************************************
        public static <T> Codec<Set<T>> createCodec(final ResourceKey<Registry<T>> registryKey)
        {
            return RecordCodecBuilder.create(i -> i
                .group(
                    RegistryCodecs.homogeneousList(registryKey)
                        .fieldOf("entries")
                        .forGetter(Set::holders),
                    Weight.CODEC
                        .fieldOf("weight")
                        .forGetter(Set::weight))
                .apply(i, Set::new));
        }
        
        //**************************************************************************************************************
        public Set(final HolderSet<T> holders, final int weight) { this(holders, Weight.of(weight)); }
        
        //=====================================================ss=========================================================
        @Override public void accept(final Consumer<Holder<T>> consumer) { this.holders.forEach(consumer); }
    }
    
    //******************************************************************************************************************
    static <T> Codec<WeightedHolder<T>> createCodec(final ResourceKey<Registry<T>> registryKey)
    {
        return Codec.either(Single.createCodec(registryKey), Set.createCodec(registryKey)).xmap(
            Either::unwrap,
            (def -> (def instanceof Single<T> single ? Either.left(single) : Either.right((Set<T>) def))));
    }
    
    //******************************************************************************************************************
    static <T> Single<T> create(final Holder<T> holder, final Weight weight) { return new Single<>(holder, weight); }
    static <T> Single<T> create(final Holder<T> holder, final int weight) { return new Single<>(holder, weight); }
    static <T> Set<T> create(final HolderSet<T> set, final Weight weight) { return new Set<>(set, weight); }
    static <T> Set<T> create(final HolderSet<T> set, final int weight) { return new Set<>(set, weight); }
    
    static <T> Single<T> create(final T value, final Function<T, Holder<T>> wrapper, final Weight weight)
    {
        return new Single<>(wrapper.apply(value), weight);
    }
    
    static <T> Single<T> create(final T value, final Function<T, Holder<T>> wrapper, final int weight)
    {
        return new Single<>(wrapper.apply(value), weight);
    }
    
    static <T> Set<T> create(final Collection<T> values, final Function<T, Holder<T>> wrapper, final Weight weight)
    {
        return new Set<>(HolderSet.direct(wrapper, values), weight);
    }
    
    static <T> Set<T> create(final Collection<T> values, final Function<T, Holder<T>> wrapper, final int weight)
    {
        return new Set<>(HolderSet.direct(wrapper, values), weight);
    }
    
    //******************************************************************************************************************
    Weight weight();
}
