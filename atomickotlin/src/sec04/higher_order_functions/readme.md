# 아토믹 코틀린 At 48. 고차 함수

## 용어

#### 고차 함수
프로그래밍 언어에서 **함수를 다른 함수의 인자로 넘길 수 있거나, 함수가 반환 값으로 함수를 돌려줄 수 있으면,** 언어가 **고차 함수(higher-order function)**을 지원한다고 말한다.

고차 함수는 함수형 프로그래밍에서 필수적이다.



## 예약어 및 코틀린 개념

#### 고차 함수

지금까지 다룬 아톰에서 이미 `filter()`, `map()`, `any()` 등의 고차 함수를 살펴봤다.

람다는 참조에 저장할 수 있다.
우선 이렇게 람다를 저장한 변수의 타입을 살펴보자.

```kotlin
import atomictest.eq

val isPlus: (Int) -> Boolean = { it > 0 }

fun main() {
    listOf(1,2, -3).any(isPlus) eq true
}
```

`(Int) -> Boolean`은 함수 타입이다.

#### 함수 타입
함수 타입은ㅇ 0개 이상의 파라미터 타입 목록을 둘러싼 괄호로 시작하며, 화살표(`->`)가 따라오고 화살표 뒤에 반환 타입이 온다.

```kotlin
(파라미터타입1, ..., 파라미터타입N) -> 반환타입
```

참조를 통해 함수를 호출하는 구문은 일반 함수를 호출하는 구문과 똑같다.

```kotlin
import atomictest.eq

val helloWorld: () -> String =
    { "Hello, world!" }

val sum: (Int, Int) -> Int =
    { x, y -> x + y}

fun main() {
    helloWorld() eq "Hello, world!"
    sum(1, 2) eq 3
}
```

**함수가 함수 파라미터를 받는 경우 해당 인자로 람다나 함수 참조를 전달할 수 있다.**

다음과 같이 함수의 반환 타입이 널이 될 수 있는 타입일 수도 있다.

```kotlin
import atomictest.eq

fun main() {
    val transform: (String) -> Int? =
        { s: String -> s.toIntOrNull() }
    transform("112") eq 112
    transform("abc") eq null
    val x = listOf("112", "abc")
    x.mapNotNull(transform) eq "[112]"
    x.mapNotNull { it.toIntOrNull() } eq "[112]"
}
```

`toIntOrNull()`은 null을 반환할 수 있다.
따라서 `transform()`은 String 을 파라미터로 받아서 널이 될 수 있는 `Int?` 타입의 값을 반환한다.
`mapNotNull()`은 List의 각 원소를 널이 될 수 있는 값으로 변환하고 변환 결과에서 null을 제외시킨다.

반환 타입을 널이 될 수 있는 타입으로 만드는 것과
함수 전체의 타입을 널이 될 수 있는 타입으로 만드는 것의 차이점에 주의하자

```kotlin
import atomictest.eq

fun main() {
    val returnTypeNullable: (String) -> Int? =
        { null }
    val mightBeNull: ((String) -> Int)? = null
    returnTypeNullable("abc") eq null
    // 널 검사를 하지 않으면 컴파일이 되지 않는다.
    // mightBeNull("abc")
    if(mightBeNull != null) {
        mightBeNull("abc")
    }
}
```

`mightBeNull`에 저장된 함수를 호출하기 전에 함수 참조 자체가 null이 아닌지 반드시 검사해야 한다.(if문)


## 함수

#### repeat()
- 두 번째 인자로 함수를 받는다.
- 두 번째 인자로 받은 동작을 첫 번째 인자로 받은 Int 값이 지정한 횟수만큼 반복한다.

```kotlin
import atomictest.*

fun main() {
    repeat(4) { trace("hi!") }
    trace eq "hi! hi! hi! hi!"
}
```

#### toIntOrNull()
- null을 반환할 수 있다.

#### mapNotNull()
List의 각 원소를 널이 될 수 있는 값으로 변환하고 변환 결과에서 null을 제외시킨다.
이는 `map()`을 호출해 얻은 결과 리스트에 `filterNotNull()`을 호출한 것과 같다.

## 참고

- 없음.

