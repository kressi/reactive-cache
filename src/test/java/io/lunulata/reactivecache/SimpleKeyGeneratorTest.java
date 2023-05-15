package io.lunulata.reactivecache;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.cache.interceptor.SimpleKey;
import reactor.test.StepVerifier;

class SimpleKeyGeneratorTest {

  SimpleKeyGenerator underTest = new SimpleKeyGenerator();

  @ParameterizedTest
  @MethodSource
  void key(KeyGenerator.KeyType keyType, Object[] args, SimpleKey expected) {
    underTest.key(keyType, args).as(StepVerifier::create).expectNext(expected).verifyComplete();
  }

  static Object[][] key() {
    return new Object[][] {
      {
        KeyGenerator.KeyType.MONO,
        new Object[] {},
        new SimpleKey(KeyGenerator.KeyType.MONO, new Object[] {})
      },
      {
        KeyGenerator.KeyType.MONO,
        new Object[] {42},
        new SimpleKey(KeyGenerator.KeyType.MONO, new Object[] {42})
      },
      {
        KeyGenerator.KeyType.FLUX,
        new Object[] {"42", null},
        new SimpleKey(KeyGenerator.KeyType.FLUX, new Object[] {"42", null})
      }
    };
  }

  @Test
  void keyMono() {
    val expected = new SimpleKey(KeyGenerator.KeyType.MONO, new Object[] {});
    underTest
        .keyMono(new Object[] {})
        .as(StepVerifier::create)
        .expectNext(expected)
        .verifyComplete();
  }

  @Test
  void keyFlux() {
    val expected = new SimpleKey(KeyGenerator.KeyType.FLUX, new Object[] {});
    underTest
        .keyFlux(new Object[] {})
        .as(StepVerifier::create)
        .expectNext(expected)
        .verifyComplete();
  }
}
