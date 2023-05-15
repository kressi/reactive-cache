package io.lunulata.reactivecache;

import com.github.benmanes.caffeine.cache.Cache;
import java.util.function.Supplier;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveCacheCore<K, R> {

  Cache<K, Publisher<R>> cache();

  default Mono<R> get(K key, Supplier<Mono<R>> supplier) {
    final Supplier<Mono<R>> hotPublisher = () -> supplier.get().cache();
    return Mono.from(getPublisher(key, hotPublisher)).doOnError(ex -> this.invalidate(key));
  }

  default Flux<R> getMany(K key, Supplier<Flux<R>> supplier) {
    final Supplier<Flux<R>> hotPublisher = () -> supplier.get().cache();
    return Flux.from(getPublisher(key, hotPublisher)).doOnError(ex -> this.invalidate(key));
  }

  <S extends Publisher<R>> Publisher<R> getPublisher(K key, Supplier<S> hotPublisher);

  default void invalidate(K key) {
    this.cache().invalidate(key);
  }
}
