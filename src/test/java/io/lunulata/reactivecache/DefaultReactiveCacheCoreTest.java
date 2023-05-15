package io.lunulata.reactivecache;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.cache.interceptor.SimpleKey;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class DefaultReactiveCacheCoreTest {

  static final SimpleKey key00 = new SimpleKey("Pickle", "Rick");
  static final SimpleKey key01 = new SimpleKey("Pickle", "Rick");
  static final SimpleKey key02 = new SimpleKey("Pickle", "Rick");
  static final SimpleKey key03 = new SimpleKey("Pickle", "Rick");
  static final SimpleKey key04 = new SimpleKey("Pickle", "Rick");
  static final SimpleKey key10 = new SimpleKey("42");
  static final SimpleKey key11 = new SimpleKey("42");
  static final SimpleKey key20 = new SimpleKey();
  static final SimpleKey key21 = new SimpleKey();

  Cache<SimpleKey, Publisher<String>> cache;
  ReactiveCacheCore<SimpleKey, String> underTest;

  @BeforeEach
  void setup() {
    cache = Caffeine.newBuilder().build();
    underTest = new DefaultReactiveCacheCore<>(cache);
  }

  @Test
  void getOk() {
    val counter = new AtomicInteger();
    final Supplier<Mono<String>> fun = () -> Mono.just(Integer.toString(counter.addAndGet(1)));
    Flux.just(key00, key10, key01, key02)
        .flatMap(k -> underTest.get(k, fun))
        .as(StepVerifier::create)
        .expectNext("1", "2", "1", "1")
        .verifyComplete();
    assertEquals(2, counter.get());
  }

  @Test
  void getFail() {
    val counter = new AtomicInteger();
    final Supplier<Mono<String>> fun =
        () -> {
          val c = counter.addAndGet(1);
          if (c % 2 == 0) return Mono.error(new Throwable("Not odd"));
          return Mono.just(Integer.toString(c));
        };
    underTest.get(key00, fun).as(StepVerifier::create).expectNext("1").verifyComplete();
    underTest.get(key10, fun).as(StepVerifier::create).verifyErrorMessage("Not odd");
    underTest.get(key11, fun).as(StepVerifier::create).expectNext("3").verifyComplete();
    underTest.get(key20, fun).as(StepVerifier::create).verifyErrorMessage("Not odd");
    underTest.get(key21, fun).as(StepVerifier::create).expectNext("5").verifyComplete();
    underTest.get(key01, fun).as(StepVerifier::create).expectNext("1").verifyComplete();
    assertEquals(5, counter.get());
  }

  @Test
  void getManyOk() {
    val counter = new AtomicInteger();
    final Supplier<Flux<String>> fun = () -> Flux.just(Integer.toString(counter.addAndGet(1)));
    Flux.just(key00, key10, key01, key02)
        .flatMap(k -> underTest.getMany(k, fun).collectList())
        .as(StepVerifier::create)
        .expectNext(List.of("1"), List.of("2"), List.of("1"), List.of("1"))
        .verifyComplete();
    assertEquals(2, counter.get());
  }

  @Test
  void getManyFail() {
    val counter = new AtomicInteger();
    final Supplier<Flux<String>> fun =
        () -> {
          val c = counter.addAndGet(1);
          if (c % 2 == 0) return Flux.error(new Throwable("Not odd"));
          return Flux.just(Integer.toString(c));
        };
    underTest.getMany(key00, fun).as(StepVerifier::create).expectNext("1").verifyComplete();
    underTest.getMany(key10, fun).as(StepVerifier::create).verifyErrorMessage("Not odd");
    underTest.getMany(key11, fun).as(StepVerifier::create).expectNext("3").verifyComplete();
    underTest.getMany(key20, fun).as(StepVerifier::create).verifyErrorMessage("Not odd");
    underTest.getMany(key21, fun).as(StepVerifier::create).expectNext("5").verifyComplete();
    underTest.getMany(key01, fun).as(StepVerifier::create).expectNext("1").verifyComplete();
    assertEquals(5, counter.get());
  }

  @Test
  void invalidate() {
    val counter = new AtomicInteger();
    final Supplier<Mono<String>> fun = () -> Mono.just(Integer.toString(counter.addAndGet(1)));
    underTest.get(key00, fun).as(StepVerifier::create).expectNext("1").verifyComplete();
    underTest.get(key01, fun).as(StepVerifier::create).expectNext("1").verifyComplete();
    underTest.get(key20, fun).as(StepVerifier::create).expectNext("2").verifyComplete();
    underTest.invalidate(key02);
    underTest.get(key03, fun).as(StepVerifier::create).expectNext("3").verifyComplete();
    underTest.get(key21, fun).as(StepVerifier::create).expectNext("2").verifyComplete();
    underTest.get(key04, fun).as(StepVerifier::create).expectNext("3").verifyComplete();
    assertEquals(3, counter.get());
  }
}
