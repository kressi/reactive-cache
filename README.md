# Reactive Cache

Simple integration of reactor Mono and Flux into Caffeine.

## Usage

Simple caching by method arguments

```java
class Calculator {

  private ReactiveCache<SimpleKey, Integer> resultCache;

  public Calculator(Caffeine<Object, Object> caffeine) {
    this.cache = ReactiveCache.from(caffeine.build());
  }

  public Mono<Integer> calculate(Integer x, Integer y) {
    return resultCache.get(this::calculateExpensive, Tuples.of(x, y));
  }

  public Flux<Integer> numbers(Integer x) {
    return resultCache.getMany(this::numbersUncached, x);
  }

  private Mono<Integer> calculateExpensive(Integer x, Integer y) {
    return Mono.just(x + y);
  }

  private Flux<Integer> numbersUncached(Integer x) {
    return Mono.just(42).repeat(x);
  }

}
```

Create reactive cache with contextual values.

```java
KeyGenerator<SimpleKey> keyGenerator = new KeyGenerator(contextView -> contextView.get("language"));
ReactiveCache<SimpleKey, ValueClass> valueCache = ReactiveCache.from(caffeine.build(), keyGenerator);
```
