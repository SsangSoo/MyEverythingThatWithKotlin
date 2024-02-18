# 아토믹 코틀린 At 26. 집합

## 용어

#### Set
- 일반적인 Set 연산은 in이나 contains()를 사용해 원소인지 검사하는 것이다.
- Set에 같은 원소를 중복해 넣으면 Set이 자동으로 중복을 없애고 하나만 남긴다.
- Set에서 **원소 순서는 중요하지 않다.** 내부에 같은 원소가 들어 있으면 같은 집합이다.
- 원소인지 여부를 검사하기 위해 in과 contains()를 모두 쓸 수 있다.
- 여러 가지 벤 다이어그램(Venn diagram) 연산을 수행할 수 있다.
  - 부분집합, 합집합, 교집합, 차집합 등을 점 표기법(set.union(other))을 사용해 수행하거나 중위 표기법(set union other)을 사용해 수행할 수 있다.
  - union, intersect, subtract를 중위 표기로 사용할 수 있다.
- 차집합 연산은 subtract() 함수나 뺄셈 연산자(-)로 표현할 수 있다.

```kotlin
package Sets

import atomictest.eq // atomictest.AtomicTest.kt

fun main() {
    val intSet = setOf(1, 1, 2, 3, 9, 9, 4)
    // 중복이 없다.
    intSet eq setOf(1, 2, 3, 4, 9)

    // 원소 순서는 중요하지 않다.
    setOf(1, 2) eq setOf(2, 1)

    // 원소인지 검사
    (9 in intSet) eq true
    (99 in intSet) eq false

    intSet.contains(9) eq true
    intSet.contains(99) eq false

    // 이 집합이 다른 집합을 포함하는가?
    intSet.containsAll(setOf(1, 9, 2)) eq true

    // 합집합
    intSet.union(setOf(3, 4, 5, 6)) eq
            setOf(1, 2, 3, 4, 5, 6, 9)

    // 교집합
    intSet intersect setOf(0, 1, 2, 7, 8) eq setOf(1, 2)

    // 차집합
    intSet subtract setOf(0, 1, 9, 10) eq
            setOf(2, 3, 4)
    intSet - setOf(0, 1, 9, 10) eq
            setOf(2, 3, 4)
}
```

- List에서 중복을 제거하려면 Set으로 변환하면 된다.
- 혹은 List를 반환하는 distinct()를 사용할 수도 있다. 
- String에 대해 toSet()을 호출하면 문자열에 들어있는 유일한 문자들의 집합을 얻을 수 있다.

- 두 가지 생성 함수를 제공한다.
- `setOf()`의 결과는 읽기 전용 집합이다.
- 가변 Set이 필요하면 mutableSetOf()를 사용한다.

```kotlin
fun main() {
    val mutableSet = mutableSetOf<Int>()
    mutableSet += 42
    mutableSet += 42
    mutableSet eq setOf(42)
    mutableSet -= 42
    mutableSet eq setOf<Int>()
}
```

위 예제에서 보는 바와 같이 List와 마찬가지로 +=과 -= 연산자는 Set에서 원소(또는 여러 원소)를 추가하거나 삭제할 수 있다.

## 예약어 및 코틀린 개념
- 없음.

## 함수

#### subtract()
- 차집합 연산을 할 수 있다.

#### intersect()
- 교집합 연산을 할 수 있다.

#### union()
- 합집합 연산을 할 수 있다.

#### containsAll(B집합)
- B집합을 포함하는지에 대해 알 수 있다. 

## 참고

- 없음.