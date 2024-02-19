# 아토믹 코틀린 At 53. 리스트 접기

## 용어

- 없음.

## 예약어 및 코틀린 개념

- 없음.

## 함수

#### fold()
- 리스트의 모든 원소를 순서대로 조합해 결괏값을 하나 만들어낸다.
- 다음 예제에서 `fold()`를 사용해 컬렉션의 합계를 구한다.

```kotlin
import atomictest.eq

fun main() {
    val list = listOf(1, 10, 100, 1000)
    list.fold(0) { sum, n ->
        sum + n
    } eq 1111
}
```

- `fold()`는 초깃값(첫 번째 인자, 여기서는 0)을 받고, 지금까지 누적된 값과 현재 원소에 대해 연산(람다로 표현됨)을 연속적으로 적용시킨다.
- `fold()`는 더 이상 리스트에 원소가 없을 때 연산을 중단한다.
- `fold()`는 현재의 원소와 누적값을 조합하는 `operation`을 연속적으로 적용한다.
- `fold()`가 중요한 개념이자 순수 함수형 언어에서 값을 누적시키는 유일한 방법이긴 하지만, 코틀린에서는 여전히 일반 for 루프를 사용하는 경우가 있다.
- `fold()`는 원소를 왼쪽에서 오른쪽으로 처리한다.

#### foldRight()
- `foldRight()`는 오른쪽에서 시작해 왼쪽 방향으로 춴소를 처리한다.
- 둘의 차이를 다음 예제에서 살펴보자.

```kotlin
import atomictest.eq

fun main() {
    val list = listOf('a', 'b', 'c', 'd')
    list.fold("*") { acc, elem ->
        "($acc) + $elem"
    } eq "((((*) + a) + b) + c) + d"
    list.foldRight("*") { elem, acc ->
        "$elem + ($acc)"
    } eq "a + (b + (c + (d + (*))))"
}
```

- `fold()`와 `foldRight()`는 첫 번째 파라미터를 통해 명시적으로 누적값을 받는다.
- 하지만 첫 번째 원소가 누적값의 초깃값이 될 수 있는 경우도 많다.

#### reduce(), reduceRight()

- 다음은 각각 `fold()`, `foldRight()`와 비슷하지만 **첫 번째 원소가 마지막 원소를 초깃값으로 사용**한다.

```kotlin
import atomictest.eq

fun main() {
    val chars = "A B C D E".split(" ")
    chars.fold("*") { acc, e -> "$acc $e"} eq
            "* A B C D E"
    chars
        .foldRight("*") { e, acc -> "$acc $e" } eq
        "* E D C B A"
    chars.reduce{ acc, e -> "$acc $e"} eq
            "A B C D E"
    chars.reduceRight { e, acc -> "$acc $e"} eq
            "E D C B A"
}
```

#### runningFold(), runningReduce() 
- 계산되는 모든 중간 단계 값을 포함하는 List를 만들어낸다.

```kotlin
import atomictest.eq

fun main() {
   val list = listOf(11, 13, 17, 19)
   list.fold(7) { sum, n ->
       sum + n
   }  eq 67
   list.runningFold(7) { sum, n ->
       sum + n
   } eq "[7, 18, 31, 48, 67]"
    list.reduce{ sum, n ->
        sum + n
    } eq 60
    list.runningReduce { sum, n ->
        sum + n
    } eq "[11, 24, 41, 60]"
}
```

`runningFold()`는 우선 초깃값(7)을 저장하고, 각각의 중간 단계 값을 추가한다.
`runningReduce()`는 각 sum의 값을 저장한다.


## 참고

- 없음.