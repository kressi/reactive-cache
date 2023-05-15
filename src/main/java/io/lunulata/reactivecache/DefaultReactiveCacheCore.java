package io.lunulata.reactivecache;

import static java.util.Objects.nonNull;

import com.github.benmanes.caffeine.cache.Cache;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.reactivestreams.Publisher;

@Slf4j
public record DefaultReactiveCacheCore<K, R>(Cache<K, Publisher<R>> cache)
    implements ReactiveCacheCore<K, R> {

  public <S extends Publisher<R>> Publisher<R> getPublisher(K key, Supplier<S> hotPublisher) {
    val cacheValue = cache.getIfPresent(key);
    if (nonNull(cacheValue)) {
      log.debug("Found entry for key: {}", key);
      return cacheValue;
    }
    val newValue = hotPublisher.get();
    log.debug("Adding entry for key: {}", key);
    cache.put(key, newValue);
    return newValue;
  }
}
