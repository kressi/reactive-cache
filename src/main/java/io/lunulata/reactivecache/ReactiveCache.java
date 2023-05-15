package io.lunulata.reactivecache;

import com.github.benmanes.caffeine.cache.Cache;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cache.interceptor.SimpleKey;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.function.*;
import reactor.util.function.*;

@Slf4j
@AllArgsConstructor
public class ReactiveCache<K, R> {

  private final ReactiveCacheCore<K, R> reactiveCacheCore;
  private final KeyGenerator<K> keyGenerator;

  public Mono<R> get(Supplier<Mono<R>> f) {
    return keyGenerator.keyMono(new Object[] {}).flatMap(key -> reactiveCacheCore.get(key, f));
  }

  public <T1> Mono<R> get(Function<T1, Mono<R>> f, T1 arg) {
    return keyGenerator
        .keyMono(new Object[] {arg})
        .flatMap(key -> reactiveCacheCore.get(key, () -> f.apply(arg)));
  }

  public <T1, T2> Mono<R> get(BiFunction<T1, T2, Mono<R>> f, Tuple2<T1, T2> t) {
    return keyGenerator
        .keyMono(t.toArray())
        .flatMap(key -> reactiveCacheCore.get(key, () -> TupleUtils.function(f).apply(t)));
  }

  public <T1, T2, T3> Mono<R> get(Function3<T1, T2, T3, Mono<R>> f, Tuple3<T1, T2, T3> t) {
    return keyGenerator
        .keyMono(t.toArray())
        .flatMap(key -> reactiveCacheCore.get(key, () -> TupleUtils.function(f).apply(t)));
  }

  public <T1, T2, T3, T4> Mono<R> get(
      Function4<T1, T2, T3, T4, Mono<R>> f, Tuple4<T1, T2, T3, T4> t) {
    return keyGenerator
        .keyMono(t.toArray())
        .flatMap(key -> reactiveCacheCore.get(key, () -> TupleUtils.function(f).apply(t)));
  }

  public <T1, T2, T3, T4, T5> Mono<R> get(
      Function5<T1, T2, T3, T4, T5, Mono<R>> f, Tuple5<T1, T2, T3, T4, T5> t) {
    return keyGenerator
        .keyMono(t.toArray())
        .flatMap(key -> reactiveCacheCore.get(key, () -> TupleUtils.function(f).apply(t)));
  }

  public <T1, T2, T3, T4, T5, T6> Mono<R> get(
      Function6<T1, T2, T3, T4, T5, T6, Mono<R>> f, Tuple6<T1, T2, T3, T4, T5, T6> t) {
    return keyGenerator
        .keyMono(t.toArray())
        .flatMap(key -> reactiveCacheCore.get(key, () -> TupleUtils.function(f).apply(t)));
  }

  public <T1, T2, T3, T4, T5, T6, T7> Mono<R> get(
      Function7<T1, T2, T3, T4, T5, T6, T7, Mono<R>> f, Tuple7<T1, T2, T3, T4, T5, T6, T7> t) {
    return keyGenerator
        .keyMono(t.toArray())
        .flatMap(key -> reactiveCacheCore.get(key, () -> TupleUtils.function(f).apply(t)));
  }

  public <T1, T2, T3, T4, T5, T6, T7, T8> Mono<R> get(
      Function8<T1, T2, T3, T4, T5, T6, T7, T8, Mono<R>> f,
      Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> t) {
    return keyGenerator
        .keyMono(t.toArray())
        .flatMap(key -> reactiveCacheCore.get(key, () -> TupleUtils.function(f).apply(t)));
  }

  public Flux<R> getMany(Supplier<Flux<R>> f) {
    return keyGenerator
        .keyFlux(new Object[] {})
        .flatMapMany(key -> reactiveCacheCore.getMany(key, f));
  }

  public <T1> Flux<R> getMany(Function<T1, Flux<R>> f, T1 arg) {
    return keyGenerator
        .keyFlux(new Object[] {arg})
        .flatMapMany(key -> reactiveCacheCore.getMany(key, () -> f.apply(arg)));
  }

  public <T1, T2> Flux<R> getMany(BiFunction<T1, T2, Flux<R>> f, Tuple2<T1, T2> t) {
    return keyGenerator
        .keyFlux(t.toArray())
        .flatMapMany(key -> reactiveCacheCore.getMany(key, () -> TupleUtils.function(f).apply(t)));
  }

  public <T1, T2, T3> Flux<R> getMany(Function3<T1, T2, T3, Flux<R>> f, Tuple3<T1, T2, T3> t) {
    return keyGenerator
        .keyFlux(t.toArray())
        .flatMapMany(key -> reactiveCacheCore.getMany(key, () -> TupleUtils.function(f).apply(t)));
  }

  public <T1, T2, T3, T4> Flux<R> getMany(
      Function4<T1, T2, T3, T4, Flux<R>> f, Tuple4<T1, T2, T3, T4> t) {
    return keyGenerator
        .keyMono(t.toArray())
        .flatMapMany(key -> reactiveCacheCore.getMany(key, () -> TupleUtils.function(f).apply(t)));
  }

  public <T1, T2, T3, T4, T5> Flux<R> getMany(
      Function5<T1, T2, T3, T4, T5, Flux<R>> f, Tuple5<T1, T2, T3, T4, T5> t) {
    return keyGenerator
        .keyMono(t.toArray())
        .flatMapMany(key -> reactiveCacheCore.getMany(key, () -> TupleUtils.function(f).apply(t)));
  }

  public <T1, T2, T3, T4, T5, T6> Flux<R> getMany(
      Function6<T1, T2, T3, T4, T5, T6, Flux<R>> f, Tuple6<T1, T2, T3, T4, T5, T6> t) {
    return keyGenerator
        .keyMono(t.toArray())
        .flatMapMany(key -> reactiveCacheCore.getMany(key, () -> TupleUtils.function(f).apply(t)));
  }

  public <T1, T2, T3, T4, T5, T6, T7> Flux<R> getMany(
      Function7<T1, T2, T3, T4, T5, T6, T7, Flux<R>> f, Tuple7<T1, T2, T3, T4, T5, T6, T7> t) {
    return keyGenerator
        .keyMono(t.toArray())
        .flatMapMany(key -> reactiveCacheCore.getMany(key, () -> TupleUtils.function(f).apply(t)));
  }

  public <T1, T2, T3, T4, T5, T6, T7, T8> Flux<R> getMany(
      Function8<T1, T2, T3, T4, T5, T6, T7, T8, Flux<R>> f,
      Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> t) {
    return keyGenerator
        .keyMono(t.toArray())
        .flatMapMany(key -> reactiveCacheCore.getMany(key, () -> TupleUtils.function(f).apply(t)));
  }

  public static <R> ReactiveCache<SimpleKey, R> from(Cache<SimpleKey, Publisher<R>> cache) {
    return ReactiveCache.from(cache, new SimpleKeyGenerator());
  }

  public static <R> ReactiveCache<SimpleKey, R> from(
      Cache<SimpleKey, Publisher<R>> cache, KeyGenerator<SimpleKey> keyGenerator) {
    return new ReactiveCache<>(new DefaultReactiveCacheCore<>(cache), keyGenerator);
  }
}
