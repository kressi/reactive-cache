package io.lunulata.reactivecache;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.cache.interceptor.SimpleKey;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.function.*;
import reactor.test.StepVerifier;
import reactor.util.function.Tuples;

class ReactiveCacheTest {

  AtomicInteger counter;
  Cache<SimpleKey, Publisher<Integer>> cache;
  ReactiveCache<SimpleKey, Integer> underTest;

  @BeforeEach
  void setup() {
    counter = new AtomicInteger();
    cache = Caffeine.newBuilder().build();
    underTest = ReactiveCache.from(cache);
  }

  @Test
  void get0() {
    final Supplier<Mono<Integer>> fun = this::counterMono;
    assertSingleInvocationMono(fun, f -> underTest.get(f));
  }

  @Test
  void get1() {
    final Function<String, Mono<Integer>> fun = (a0) -> counterMono();
    assertSingleInvocationMono(fun, f -> underTest.get(f, "a0"));
  }

  @Test
  void get2() {
    final BiFunction<String, String, Mono<Integer>> fun = (a0, a1) -> counterMono();
    assertSingleInvocationMono(fun, f -> underTest.get(f, Tuples.of("a0", "a1")));
  }

  @Test
  void get3() {
    final Function3<String, String, String, Mono<Integer>> fun = (a0, a1, a2) -> counterMono();
    assertSingleInvocationMono(fun, f -> underTest.get(f, Tuples.of("a0", "a1", "a2")));
  }

  @Test
  void get4() {
    final Function4<String, String, String, String, Mono<Integer>> fun =
        (a0, a1, a2, a3) -> counterMono();
    assertSingleInvocationMono(fun, f -> underTest.get(f, Tuples.of("a0", "a1", "a2", "a3")));
  }

  @Test
  void get5() {
    final Function5<String, String, String, String, String, Mono<Integer>> fun =
        (a0, a1, a2, a3, a4) -> counterMono();
    assertSingleInvocationMono(fun, f -> underTest.get(f, Tuples.of("a0", "a1", "a2", "a3", "a4")));
  }

  @Test
  void get6() {
    final Function6<String, String, String, String, String, String, Mono<Integer>> fun =
        (a0, a1, a2, a3, a4, a5) -> counterMono();
    assertSingleInvocationMono(
        fun, f -> underTest.get(f, Tuples.of("a0", "a1", "a2", "a3", "a4", "a5")));
  }

  @Test
  void get7() {
    final Function7<String, String, String, String, String, String, String, Mono<Integer>> fun =
        (a0, a1, a2, a3, a4, a5, a6) -> counterMono();
    assertSingleInvocationMono(
        fun, f -> underTest.get(f, Tuples.of("a0", "a1", "a2", "a3", "a4", "a5", "a6")));
  }

  @Test
  void get8() {
    final Function8<String, String, String, String, String, String, String, String, Mono<Integer>>
        fun = (a0, a1, a2, a3, a4, a5, a6, a7) -> counterMono();
    assertSingleInvocationMono(
        fun, f -> underTest.get(f, Tuples.of("a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7")));
  }

  @Test
  void getMany0() {
    final Supplier<Flux<Integer>> fun = this::counterFlux;
    assertSingleInvocationFlux(fun, f -> underTest.getMany(f));
  }

  @Test
  void getMany1() {
    final Function<String, Flux<Integer>> fun = (a0) -> counterFlux();
    assertSingleInvocationFlux(fun, f -> underTest.getMany(f, "a0"));
  }

  @Test
  void getMany2() {
    final BiFunction<String, String, Flux<Integer>> fun = (a0, a1) -> counterFlux();
    assertSingleInvocationFlux(fun, f -> underTest.getMany(f, Tuples.of("a0", "a1")));
  }

  @Test
  void getMany3() {
    final Function3<String, String, String, Flux<Integer>> fun = (a0, a1, a2) -> counterFlux();
    assertSingleInvocationFlux(fun, f -> underTest.getMany(f, Tuples.of("a0", "a1", "a2")));
  }

  @Test
  void getMany4() {
    final Function4<String, String, String, String, Flux<Integer>> fun =
        (a0, a1, a2, a3) -> counterFlux();
    assertSingleInvocationFlux(fun, f -> underTest.getMany(f, Tuples.of("a0", "a1", "a2", "a3")));
  }

  @Test
  void getMany5() {
    final Function5<String, String, String, String, String, Flux<Integer>> fun =
        (a0, a1, a2, a3, a4) -> counterFlux();
    assertSingleInvocationFlux(
        fun, f -> underTest.getMany(f, Tuples.of("a0", "a1", "a2", "a3", "a4")));
  }

  @Test
  void getMany6() {
    final Function6<String, String, String, String, String, String, Flux<Integer>> fun =
        (a0, a1, a2, a3, a4, a5) -> counterFlux();
    assertSingleInvocationFlux(
        fun, f -> underTest.getMany(f, Tuples.of("a0", "a1", "a2", "a3", "a4", "a5")));
  }

  @Test
  void getMany7() {
    final Function7<String, String, String, String, String, String, String, Flux<Integer>> fun =
        (a0, a1, a2, a3, a4, a5, a6) -> counterFlux();
    assertSingleInvocationFlux(
        fun, f -> underTest.getMany(f, Tuples.of("a0", "a1", "a2", "a3", "a4", "a5", "a6")));
  }

  @Test
  void getMany8() {
    final Function8<String, String, String, String, String, String, String, String, Flux<Integer>>
        fun = (a0, a1, a2, a3, a4, a5, a6, a7) -> counterFlux();
    assertSingleInvocationFlux(
        fun, f -> underTest.getMany(f, Tuples.of("a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7")));
  }

  private <T> void assertSingleInvocationFlux(T fun, Function<T, Flux<Integer>> testFun) {
    Flux.just(fun)
        .repeat(1)
        .flatMap(testFun)
        .as(StepVerifier::create)
        .expectNext(1, 1)
        .verifyComplete();
    assertEquals(1, counter.get());
  }

  private <T> void assertSingleInvocationMono(T fun, Function<T, Mono<Integer>> testFun) {
    Flux.just(fun)
        .repeat(1)
        .flatMap(testFun)
        .as(StepVerifier::create)
        .expectNext(1, 1)
        .verifyComplete();
    assertEquals(1, counter.get());
  }

  private Mono<Integer> counterMono() {
    return Mono.just(counter.addAndGet(1));
  }

  private Flux<Integer> counterFlux() {
    return Flux.just(counter.addAndGet(1));
  }
}
