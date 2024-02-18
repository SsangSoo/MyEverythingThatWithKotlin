# 아토믹 코틀린 At 49. 리스트 조작하기

## 용어

- 없음.

## 예약어 및 코틀린 개념

### 리스트 조작하기

묶기(zipping)와 평평하게 하기(flattening)는 List를 조작할 때 흔히 쓰는 연산이다.

#### 묶기
`zip()`은 두 List의 원소를 하나씩 짝짓는 방식으로 묶는다.

```kotlin
import atomictest.eq

fun main() {
    val left = listOf("a", "b", "c", "d")
    val right = listOf("q", "r", "s", "t")

    left.zip(right) eq                  // 1
            "[(a, q), (b, r), (c, s), (d, t)]"

    left.zip(0..4) eq                   // 2
            "[(a, 0), (b, 1), (c, 2), (d, 3)]"

    (10.. 100).zip(right) eq            // 3
            "[(10, q), (11, r), (12, s), (13, t)]"
}
```

주석 1 : left와 right를 묶으면 Pair로 이뤄진 List가 생긴다. 이떄 left와 right에서 같은 위치(인덱스)에 있는 원소를 결합해준다.
주석 2 : List의 범위를 `zip()`할 수도 있다.
주석 3 : 범위(10..100)은 right보다 원소가 훨씩 많다. 하지만 두 시퀀스 중 어느 한쪽이 끝나면 묶기 연산도 끝난다.

`zip()`함수는 만들어진 Pair에 대해 연산을 적용하는 기능도 있다.

```kotlin
import atomictest.eq

data class Person (
    val name: String,
    val id: Int
)

fun main() {
    val names = listOf("Bob", "Jill", "Jim")
    val ids = listOf(1731, 9274, 8378)
    names.zip(ids) { name, id ->
        Person(name, id)
    }  eq "[Person(name=Bob, id=1731), " +
            "Person(name=Jill, id=9274), " +
            "Person(name=Jim, id=8378)]"
}
```

위의 코드에서 `names.zip(ids) { ... }`은 `이름-아이디 Pair`를 만든 다음 람다를 각 Pair에 적용한다.
결과는 초기화된 Person 객체들로 이뤄진 List다.

한 List에서 어떤 원소와 그 원소에 인접한 다음 원소를 묶으려면 `zipWithNext()`를 사용하하자.

```kotlin
import atomictest.eq

fun main() {
    val list = listOf('a', 'b', 'c', 'd')

    list.zipWithNext() eq listOf(
        Pair('a', 'b'),
        Pair('b', 'c'),
        Pair('c', 'd'))

    list.zipWithNext() { a, b -> "$a$b" } eq
            "[ab, bc, cd]"
}
```

#### 평평하게 하기

`flatten()`은 각 원소가 List인 List를 인자로 받아서 원소가 따로따로 들어있는 List를 반환한다.

```kotlin
import atomictest.eq

fun main() {
    val list = listOf(
        listOf(1, 2),
        listOf(4, 5),
        listOf(7, 8)
    )
    list.flatten() eq "[1, 2, 4, 5, 7, 8]"
}
```

`flatMap()`은 컬렉션에서 중요한 연산이며, 이를 이해하는데 **flatten**이 중요하다.
어떤 범위에 속한 Int로부터 가능한 모든 Pair를 만들어보면 다음과 같다.

```kotlin
import atomictest.eq


fun main() {
    val intRange = 1..3

    intRange.map { a ->         // 1
        intRange.map { b -> a to b }
    } eq "[" +
            "[(1, 1), (1, 2), (1, 3)], " +
            "[(2, 1), (2, 2), (2, 3)], " +
            "[(3, 1), (3, 2), (3, 3)]" +
            "]"

    intRange.map { a ->         // 2
        intRange.map { b -> a to b }
    }.flatten() eq "[" +
            "(1, 1), (1, 2), (1, 3), " +
            "(2, 1), (2, 2), (2, 3), " +
            "(3, 1), (3, 2), (3, 3)" +
            "]"

    intRange.flatMap { a ->     // 3
        intRange.map { b -> a to b }
    } eq "[" +
            "(1, 1), (1, 2), (1, 3), " +
            "(2, 1), (2, 2), (2, 3), " +
            "(3, 1), (3, 2), (3, 3)" +
            "]"
}
```

세 가지 경우 모두 람다는 같다. 
intRange에 속한 모든 원소를 intRange에 속한 모든 원소와 조합해 가능한 모든 a to b Pair를 만든다.

주석 2 : `flatten()` 함수를 적용해 결과를 평평하게 해서 부가적인 구조(내포된 List구조)를 없애고 단일 List를 만든다. 
주석 3 : `map()`과 `flatten()`을 모두 수행해주는 `flatMap()`을 통해서 List를 만든다.



## 함수

#### zip()
- 두 List의 원소를 하나씩 짝짓는 방식으로 묶는다.
- 만들어진 Pair에 대해 연산을 적용하는 기능도 있다.

#### zipWithNext()
한 List에서 어떤 원소와 그 원소에 인접한 다음 원소를 묶어준다.

#### flatten()
각 원소가 List인 List를 인자로 받아서 원소가 따로따로 들어있는 List를 반환한다.

#### flatMap()
`map()`과 `flatten()`을 모두 수행한다.


## 참고

- 없음.

