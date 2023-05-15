package io.lunulata.reactivecache;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.cache.interceptor.SimpleKey;
import reactor.test.StepVerifier;

class ContextualKeyGeneratorTest {

  ContextualKeyGenerator underTest = new ContextualKeyGenerator(ctxView -> ctxView.get("language"));

  @Test
  void key() {
    val expected = new SimpleKey(KeyGenerator.KeyType.MONO, new Object[] {"a", 0}, "en");
    underTest
        .key(KeyGenerator.KeyType.MONO, new Object[] {"a", 0})
        .contextWrite(ctxView -> ctxView.put("language", "en"))
        .as(StepVerifier::create)
        .expectNext(expected)
        .verifyComplete();
  }
}
