package io.lunulata.reactivecache;

import java.util.function.Function;
import lombok.AllArgsConstructor;
import org.springframework.cache.interceptor.SimpleKey;
import reactor.core.publisher.Mono;
import reactor.util.context.ContextView;

@AllArgsConstructor
public class ContextualKeyGenerator implements KeyGenerator<SimpleKey> {

  private final Function<ContextView, Object> contextKey;

  @Override
  public Mono<SimpleKey> key(KeyType keyType, Object[] args) {
    return Mono.deferContextual(ctxView -> Mono.just(contextKey.apply(ctxView)))
        .map(ctxKey -> new SimpleKey(keyType, args, ctxKey));
  }
}
