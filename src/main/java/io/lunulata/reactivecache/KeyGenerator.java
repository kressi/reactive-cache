package io.lunulata.reactivecache;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface KeyGenerator<T> {
  default Mono<T> keyMono(Object[] args) {
    return key(KeyType.MONO, args);
  }

  default Mono<T> keyFlux(Object[] args) {
    return key(KeyType.FLUX, args);
  }

  Mono<T> key(KeyType keyType, Object[] args);

  enum KeyType {
    MONO,
    FLUX
  }
}
