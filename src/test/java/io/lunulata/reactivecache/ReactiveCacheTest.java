package io.lunulata.reactivecache;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class ReactiveCacheTest {

  ReactiveCache<Object> underTest = new ReactiveCache<>();

  @Test
  void cacheReturnsEmpty() {
    underTest.get().as(StepVerifier::create).verifyComplete();
  }

  @Test
  void cacheReturnsManyEmpty() {
    underTest.get().as(StepVerifier::create).verifyComplete();
  }
}
