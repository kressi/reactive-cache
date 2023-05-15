package io.lunulata.reactivecache;

import org.springframework.cache.interceptor.SimpleKey;
import reactor.core.publisher.Mono;

public class SimpleKeyGenerator implements KeyGenerator<SimpleKey> {
  @Override
  public Mono<SimpleKey> key(KeyType keyType, Object[] args) {
    return Mono.just(new SimpleKey(keyType, args));
  }
}
