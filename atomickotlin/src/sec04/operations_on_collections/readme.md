# 아토믹 코틀린 At 46. 컬렉션에 대한 연산

## 용어

- 없음.

## 예약어 및 코틀린 개념

#### 컬렉션에 대한 연산

**함수형 프로그래밍의 능력들 중에서 객체 컬렉션에 대한 연산을 한꺼번에 수행할 수 있는 능력이 매우 중요하다.**

- 대부분의 함수형 언어는 컬렉션을 다룰 수 있는 강력한 수단을 제공한다. 코틀린도 예외는 아니다.
  - 이미 `map()`, `filter()`, `any()`, `forEach()`를 살펴봤었다.
- 해당 아톰에서는 List와 그 외 컬렉션에 사용할 수 있는 다른 연산을 다룬다.

먼저 List를 만들어내는 여러 가지 방법을 살펴보자.
다음 예제에서는 람다를 사용해 List를 초기화한다.

```kotlin
import atomictest.eq

fun main() {
    // 람다는 인자로 추가할 원소의 인덱스를 받는다.
    val list1 = List(10) { it }
    list1 eq "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]"

    // 한 값으로만 이뤄진 리스트
    val list2 = List(10) { 0 }
    list2 eq "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]"

    // 글자로 이뤄진 리스트
    val list3 = List(10) {'a' + it}
    list3 eq "[a, b, c, d, e, f, g, h, i, j]"

    // 정해진 순서를 반복
    val list4 = List(10) { list3[it % 3] }
    list4 eq "[a, b, c, a, b, c, a, b, c, a]"
}
```

이 List 생성자에는 인자가 두 개 있다.
첫 번째 인자는 생성할 List의 크기이고, 
두 번째 인자는 생성한 List의 각 원소를 초기화하는 람다다(이 람다는 원소의 인덱스를 전달받으며, it로 간편하게 전달받은 인덱스를 사용할 수 있다.).

**람다가 함수의 마지막 원소인 경우 람다를 인자 목록 밖으로 빼내도 된다는 점을 기억**하자.

MultableList도 마찬가지 방법으로 초기화할 수 있다.

다음 코드는 인자 목록 내부(mutableList1)와 인자 목록 외부(mutableList2)에 람다를 위치시킨 모습이다.

```kotlin
import atomictest.eq

fun main() {
    val mutableList1 =
        MutableList(5, { 10 * (it +1) })
    mutableList1 eq "[10, 20, 30, 40, 50]"
    val mutableList2 =
        MutableList(5)  { 10 * (it + 1) }
    mutableList2 eq "[10, 20, 30, 40, 50]"
}
```

`List()`와 `MutableList()`는 생성자가 아니라 함수다.
두 함수의 이름을 지을 때 일부러 List라고 대문자로 시작해서 마치 생성자인 것처럼 보일 뿐이다.

다양한 컬렉션 함수가 술어를 받아서 컬렉션의 원소를 검사한다. 

```kotlin
import atomictest.eq

fun main() {
    val list = listOf(-3, -1, 5, 7, 10)

    list.filter { it > 0 } eq listOf(5, 7, 10)
    list.count { it > 0 } eq 3

    list.find { it > 0 } eq 5
    list.firstOrNull { it > 0 } eq 5
    list.lastOrNull { it < 0 } eq -1

    list.any { it > 0 } eq true
    list.any { it != 0 } eq true

    list.all { it > 0 } eq false
    list.all { it != 0 } eq true

    list.none { it > 0 } eq false
    list.none { it == 0 } eq true
}
```

`filter()`와 `count()`는 모든 원소에 술어를 적용하지만 `any()`나 `find()`는 결과를 찾자마자 이터레이션을 중단한다.
예를 들어 첫 번쨰 원소가 술어를 만족시키면, `any()`는 바로 true를 반환하고 `find()`는 일치한 원소(이 경우 첫 번째 원소)를 반환한다.
주어진 술어를 만족하는 원소가 하나도 없는 경우에만 모든 원소를 처리한다.

`filter()`는 주어진 술어를 만족하는 원소들을 반환한다.
또는 반대쪽 그룹, 즉 술어를 만족하지 안흔 원소들의 그룹이 필요할 때도 있다.
`filterNot()`은 이런 그룹을 돌려주며, `partition()`은 동시에 양쪽 그룹(술어를 만족하는 원소들과 만족하지 않는 원소들)을 만들어낸다.

```kotlin
import atomictest.eq

fun main() {
    val list = listOf(-3, -1, 5, 7, 10)
    val isPositive = { i: Int -> i > 0 }

    list.filter(isPositive) eq "[5, 7, 10]"
    list.filterNot(isPositive) eq "[-3, -1]"
    val (pos, neg) = list.partition { it > 0 }
    pos eq "[5, 7, 10]"
    neg eq "[-3, -1]"
}
```

`partition()`은 List가 들어 있는 Pair 객체를 만든다. 
구조 분해 선언을 사용하면, 괄호로 둘러싼 여러 var나 val을 선언하면서 동시에 Pair의 원소로 초기화할 수 있다.
다음 코드에서는 커스텀 함수의 반환값에 대해 **구조 분해 선언을 사용**한다.

```kotlin
import atomictest.eq

fun createPair() = Pair(1, "one")

fun main() {
    val (i, s) = createPair()
    i eq 1
    s eq "one"
}
```

`filterNotNull()`은 null을 제외한 원소들로 이뤄진 새 List를 돌려준다.(함수에서도 설명)

```kotlin
import atomictest.eq

fun main() {
    val list = listOf(1, 2, null)
    list.filterNotNull() eq "[1, 2]"
}
```

이전에 [리스트 아톰](https://ssangsu.tistory.com/182)에서는 수 타입의 리스트에 대해 `sum()`이나 비교 가능한 원소로 이뤄진 리스트에 적용할 수 있는 `sorted()`를 살펴봤다.
리스트의 원소가 숫자가 아니거나 비교 가능하지 않으면 이 두 함수를 호출할 수 없지만, 각각에 대응하는 `sumBy()`와 `sortedBy()`가 있다.
이들은 덧셈이나 정렬 연산에 사용할 특성값을 돌려주는 함수(보통은 람다)를 인자로 받는다.

```kotlin
import atomictest.eq

data class Product (
    val description: String,
    val price: Double
)

fun main() {
    val products = listOf(
        Product("bread", 2.0),
        Product("wine", 5.0)
    )
    products.sumOf { it.price } eq 7.0 // sumByDouble : Deprecated

    products.sortedByDescending { it.price } eq
            "[Product(description=wine, price=5.0)," +
            " Product(description=bread, price=2.0)]"
    products.minByOrNull { it.price } eq
            Product("bread", 2.0)
}
```

함계를 구할 때 `sumOf()` 함수를 사용했다.


참고로 본문의 예제에 나오는 `sumBy()`와 `sumByDouble()`은 `sumOf()`으로 대체했다.
현재 Deprecated 되어있다.


`sorted()`와 `sortedBy()`는 컬렉션을 오름차순으로 정렬하는 반면 `sortedDescending()`과 `sortedByDescending()`은 컬렉션을 내림차순으로 정렬한다.
`minByOrNull()`은 주어진 대소 비교 기준에 따라 찾은 최솟값을 돌려주며, 리스트가 비어 있는 경우 null을 반환한다.

`take()`와 `drop()`은 각각 첫 번째 원소를 취하고, 첫 번째 원소를 제거한다. 
`takeLast()`와 `dropLast()`는 각각 마지막 원소를 취하거나 제거한다.

이 네 가지 함수에는 모두 취하거나 제거할 대상을 지정하는 람다를 받는 버전이 존재한다.
다음 예시를 보면 된다.

```kotlin
import atomictest.eq

fun main() {
    val list = listOf('a', 'b', 'c', 'X', 'Z')
    list.takeLast(3) eq "[c, X, Z]"
    list.takeLastWhile { it.isUpperCase() } eq
            "[X, Z]"
    list.drop(1) eq "[b, c, X, Z]"
    list.dropWhile { it.isLowerCase() } eq
            "[X, Z]"
}
```




## 함수

#### filter()
주어진 술어와 일치하는(술어가 true를 반환하는) 모든 원소가 들어 있는 새 리스트를 만든다.

#### any()
원소 중 어느 하나에 대해 술어가 true를 반환하면 true를 반환한다.

#### all()
모든 원소가 술어와 일치하는지 검사한다.

#### none()
술어와 일치하는 원소가 하나도 없는지 검사한다.

#### find() , firstOrNull()
모두 술어와 일치하는 첫 번째 원소를 반환한다.
원소가 없을 때 `find()`는 예외를 던지고, `findOrNull()`은 null을 반환한다.

#### lastOrNull()
술어와 일치하는 마지막 원소를 반환하며, 일치하는 원소가 없으면 null을 반환한다.

#### count()
술어와 일치하는 원소의 개수를 반환한다.

#### filterNotNull()
null을 제외한 원소들로 이뤄진 새 List를 돌려준다.

#### sumOf()
파라미터로 오는 타입에 따른 합계를 구한다.

#### sorted(), sortedBy()
컬렉션을 오름차순으로 정렬한다.

#### sortedDescending()`, `sortedByDescending()`
컬렉션을 내림차순으로 정렬한다.

#### take(), drop()
각각 첫 번째 원소를 취하고, 첫 번째 원소를 제거한다.

#### takeLast(), dropLast()
각각 마지막 원소를 취하거나 제거한다.

#### mapByOrNull()
컬렉션이 비어 있으면 null을 반환한다.


## 참고

현재 `sumByDouble()`는 Deprectated 되었다.
그리고 `sumOf()`라는 함수의 매개 타입을 식별해서 합계를 구하는 방식으로 구현되어 있다.

```kotlin
@Deprecated("Use sumOf instead.", ReplaceWith("this.sumOf(selector)"))
@DeprecatedSinceKotlin(warningSince = "1.5")
public inline fun <T> Iterable<T>.sumBy(selector: (T) -> Int): Int {
    var sum: Int = 0
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

@Deprecated("Use sumOf instead.", ReplaceWith("this.sumOf(selector)"))
@DeprecatedSinceKotlin(warningSince = "1.5")
public inline fun <T> Iterable<T>.sumByDouble(selector: (T) -> Double): Double {
    var sum: Double = 0.0
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

/**
 * Returns the sum of all values produced by [selector] function applied to each element in the collection.
 */
@SinceKotlin("1.4")
@OptIn(kotlin.experimental.ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
@kotlin.jvm.JvmName("sumOfDouble")
@kotlin.internal.InlineOnly
public inline fun <T> Iterable<T>.sumOf(selector: (T) -> Double): Double {
    var sum: Double = 0.toDouble()
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

/**
 * Returns the sum of all values produced by [selector] function applied to each element in the collection.
 */
@SinceKotlin("1.4")
@OptIn(kotlin.experimental.ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
@kotlin.jvm.JvmName("sumOfInt")
@kotlin.internal.InlineOnly
public inline fun <T> Iterable<T>.sumOf(selector: (T) -> Int): Int {
    var sum: Int = 0.toInt()
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

/**
 * Returns the sum of all values produced by [selector] function applied to each element in the collection.
 */
@SinceKotlin("1.4")
@OptIn(kotlin.experimental.ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
@kotlin.jvm.JvmName("sumOfLong")
@kotlin.internal.InlineOnly
public inline fun <T> Iterable<T>.sumOf(selector: (T) -> Long): Long {
    var sum: Long = 0.toLong()
    for (element in this) {
        sum += selector(element)
    }
    return sum
}
```

컬렉션에 들어있는 값을 모두 더해도 넘침(overflow)이 발생하지 않는다고 확신할 때만 사용해야 한다.
`sumBy()`는 넘침이 일어나도 겉보기에는 평범한 정수가 나오기 때문에 오류를 눈치채지 못할 수 있다.
이런 문제로 인해 직접 비슷한 역할을 하면서 `java.math.BigInteger/java.math.BigDecimal`을 사용하는 비슷한 합계 함수를 작성해 사용하는 경우가 많았고, 
코틀린 언어 개발자들도 이를 인식하고 **코틀린 1.5**부터는 `sumBy()` 함수를 사용 금지 처리(Deprecated)하면서 대안으로 `sumOf()`함수를 추가했다.
`sumOf()`는 `sumBy()`와 마찬가지로 람다를 인자로 받지만, 이 람다가 반환하는 값의 타입을 사용해 합계를 계산하기 때문에 넘침이 발생할 여지가 있는 경우 람다가 값을 `java.math.BigInteger/java.math.BigDecimal`로 변환해 돌려주면 된다.
예를 들어 `products.sumByDouble { it.price }`가 너무 커질경우 `products.sumOf { it.price.toBigDecimal() }`이라고 하면 큰 실수로 합계를 구할 수 있다.

### Set
List에서 본 연산 중 다음과 같은 연산은 Set에도 사용할 수 있다.

```kotlin
import atomictest.eq

fun main() {
    val set = setOf("a", "ab", "ac")
    set.maxByOrNull { it.length }?.length eq 2
    set.filter {
        it.contains('b')
    } eq listOf("ab")
    set.map { it.length } eq listOf(1, 2, 2)
}
```

`mapByOrNull()`은 컬렉션이 비어 있으면 null을 반환하므로 결과 타입이 `널이 될 수 있는 타입`이다.
`filter()`와 `map()`을 Set에 적용하면 List를 결과로 받는다.

대부분의 연산을 사용할 수 있긴 하지만, `findFirst()`와 같이 컬렉션에 저장된 원소 순서에 따라 결과가 달라질 수 있는 연산은 실행할 때마다 다른 결과를 내놓을 수도 있다는 점에 유의해야 한다.
