# 아토믹 코틀린 At 44. 람다

## 용어

- 없음.

## 예약어 및 코틀린 개념

#### 람다

- 람다를 사용하면 이해하기 쉬운 간결한 코드를 작성할 수 있다.
- **람다(lambda)**는 부가적인 장식이 덜 들어간 함수다
  - 람다를 함수 리터럴(function literal)이라고 부르기도 한다.
- 람다에는 이름이 없고, 함수 생성에 필요한 최소한의 코드만 필요하며, 다른 코드에 람다를 직접 삽입할 수 있다.

- `map()`을 생각해보자(함수쪽에서도 설명)
  - `map()`은 List 같은 컬렉션에 작용하는 함수로, `map()`의 파라미터는 컬렉션의 모든 원소에 적용할 변환 함수다.
  - `map()`은 원본 List의 모든 원소에 변환 함수를 적용해 얻은 새로운 원소로 이뤄진 새 List를 반환한다.

```kotlin
import atomictest.eq

fun main() {
    val list = listOf(1,2,3,4)
    val result = list.map({ n: Int -> "[$n]"})
    result eq listOf("[1]", "[2]", "[3]", "[4]")
}
```

- result를 초기화할 때 **중괄호 사이에 쓴 코드가 람다**다. 
- 파라미터 목록과 함수 본문 사이에는 `->`가 들어간다.
- **함수 본문은 하나 이상의 식**이다.
- **식이 여럿인 경우 마지막 식이 람다의 결과**가 된다.
- 위의 코드는 완전한 람다 문법이지만, 이를 더 간단히 쓸 수도 있다.
  - 보통 람다가 필요한 위치에 바로 람다른 적는다. 
  - 이 말은 **코틀린이 람다의 타입을 추론할 수 있다는 뜻**이다.

```kotlin
import atomictest.eq

fun main() {
    val list = listOf(1,2,3,4)
    val result = list.map({ n -> "[$n]" })
    result eq listOf("[1]", "[2]", "[3]", "[4]")
}
```

- 람다를 `List<Int>`에 사용 중이므로 코틀린은 n의 타입이 Int라는 사실을 알 수 있다.
- **파라미터가 하나일 경우 코틀린은 자동으로 파라미터 타입을 it로 만든다.**
  - 이 말은 (파라미터가 하나고 it로 그 파라미터를 가리키는 한) 더 이상  `n ->`을 사용할 필요가 없다는 뜻이다.

```kotlin
import atomictest.eq

fun main() {
    val list = listOf(1, 2, 3, 4)
    val result = list.map({ "[$it]" })
    result eq listOf("[1]", "[2]", "[3]", "[4]")
}
```

- 함수의 파라미터가 람다뿐이면 람다 주변의 괄호를 없앨 수 있으므로, 더 깔끔하게 코드를 적을 수 있다.

```kotlin
import atomictest.eq

fun main() {
    val list = listOf('a','b','c','d')
    val result =
        list.map { "[${it.uppercase()}]" }
    result eq listOf("[A]", "[B]", "[C]", "[D]")
}
```

- 함수가 여러 파라미터를 받고 람다가 마지막 파라미터인 경우에는 람다를 인자 목록을 감싼 괄호 다음에 위치시킬 수 있다.
  - 예를 들어 `joinToString()`의 마지막 인자로 람다를 지정할 수 있다.
  - `joinToString()`은 이 람다로 컬렉션의 각 원소를 String으로 변환한고, 그렇게 변환한 모든 String을 구분자와 접두사/접미사를 붙여서 하나로 합쳐준다.

```kotlin
import atomictest.eq

fun main() {
    val list = listOf(9, 11, 23, 32)
    list.joinToString(" ") { "[$it]" } eq
            "[9] [11] [23] [32]"
}
```
 
- 람다를 이름 붙은 인자로 호출하고 싶다면 인자 목록을 감싸는 괄호 안에 람다를 위치시켜야 한다.

```kotlin
import atomictest.eq

fun main() {
    val list = listOf(9, 11, 23, 32)
    list.joinToString(
        separator = " ",
        transform = { "[$it]" }
    ) eq "[9] [11] [23] [32]"
}
```

다음은 파라미터가 둘 이상 있는 람다 구문이다.

```kotlin
import atomictest.eq

fun main() {
    val list = listOf('a', 'b', 'c')
    list.mapIndexed { index, element ->
        "[$index: $element]"
    } eq listOf("[0: a]", "[1: b]", "[2: c]")
}
```
 
- 위 예제는 `mapIndexed()`라이브러리 함수를 사용한다.(함수 쪽에서 설명)


- 람다가 특정 인자를 사용하지 않는 경우 밑줄을 사용할 수 있다.
- 밑줄을 쓰면 람다가 무슨 무슨 인자를 사용하지 않는다는 컴파일러 경고를 무시할 수 있다.

```kotlin
import atomictest.eq

fun main() {
    val list = listOf('a','b','c')
    list.mapIndexed { index, _ ->
        "[$index]"
    } eq listOf("[0]", "[1]", "[2]")
}
```

- `list.indeces`를 사용해 위의 코드를 다음과 같이 쓸 수 있다.

```kotlin
fun main() {
    val list = listOf('a', 'b', 'c')
    list.indices.map {
        "[$it]"
    } eq listOf("[0]", "[1]", "[2]")
}
```

- 람다에 파라미터가 없을 수도 있다. 
  - **이 경우 파라미터가 없다는 사실을 강조하기 위해 화살표를 남겨둘 수 있지만, 코틀린 스타일 가이드에서는 화살표를 사용하지 말라고 권장한다.**
  

- 일반 함수를 쓸 수 있는 모든 곳에 람다를 쓸 수 있다.
  - 하지만 **람다가 너무 복잡하면 이름 붙은 함수를 정의하는 편이 더 명확**하다.
- 람다를 단 한 번만 사용하는 경우라도 람다가 너무 크면 이름 붙은 함수로 작성하는게 더 낫다.



## 함수

#### map()
- `map()`은 List 같은 컬렉션에 작용하는 함수로, `map()`의 파라미터는 컬렉션의 모든 원소에 적용할 변환 함수다.
- `map()`은 원본 List의 모든 원소에 변환 함수를 적용해 얻은 새로운 원소로 이뤄진 새 List를 반환한다.
- `map`은 모든 타입의 List에 사용될 수 있다.
- 다음 코드에서 코틀린은 람다의 it이 Char 타입이라는 사실을 추론한다.

```kotlin
import atomictest.eq

fun main() {
    val list = listOf('a', 'b', 'c', 'd')
    val result =
        list.map({ "[${it.uppercase()}]" })
    result eq listOf("[A]", "[B]", "[C]", "[D]")
}
```

#### mapIndexed()
- list의 원소와 원소의 인덱스를 함께 람다에 전달하면서 각 원소를 변환한다.

```kotlin
import atomictest.eq

fun main() {
    val list = listOf('a', 'b', 'c')
    list.mapIndexed { index, element ->
        "[$index: $element]"
    } eq listOf("[0: a]", "[1: b]", "[2: c]")
}
```

- `mapIndexed()`에 전달할 람다는 인덱스와 원소 (리스트가 `List<Char>`이므로 원소 타입은 문자 타입이다)를 파라미터로 받아야 한다.

- `mapIndexed()`를 항상 `indices`와 `map()`으로 대신할 수 있는 게 아니라는 점에 유의해야 한다.

```kotlin
import atomictest.eq

fun main() {
    val list = listOf('a','b','c')
    list.mapIndexed { index, _ ->
        "[$index]"
    } eq listOf("[0]", "[1]", "[2]")
}
```

위의 코드의 경우 `mapIndexed()`에 전달한 람다가 원소 값은 무시하고 인덱스만 사용했기 때문에 indeces에 `map()`을 적용하는 것으로 대신할 수 있었지만, 일반적으로 `mapIndexed()`를 `indices`와 `map()`으로 대신할 수는 없다.

#### run

```kotlin
import atomictest.*

fun main() {
    run { -> trace("A Lambda") }
    run { trace("Without args") }
    trace eq
"""
A Lambda
Without args
"""
}
```

- 표준 라이브러리 함수인 `run()`은 단순히 자신에게 인자로 전달된 람다를 호출하기만 한다.

## 참고

- 없음.