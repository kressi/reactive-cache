package io.lunulata.reactivecache;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactiveCache<T> {
  public Mono<T> get() {
    return Mono.empty();
  }

  public Flux<T> getMany() {
    return Flux.empty();
  }
}
