# 아토믹 코틀린 At 52. 지역 함수

## 용어

#### 지역 함수(local function)

- 다른 함수 안에 정의된 이름 붙은 함수를 **지역 함수(local function)**라고 한다. 
- 반복되는 코드를 지역 함수로 추출하면 중복을 줄일 수 있다.
- 지역 함수 자신이 속한 함수의 내부에서만 지역 함수를 볼 수 있으므로, 지역 함수는 `우리의 이름 공간을 오염시키지 않는다.`
- 지역 함수는 **클로저**다.
  - 즉 지역 함수는 자신을 둘러싼 환경의 `var`나 `val`을 포획한다.
  - 포획을 쓰지 않으면 주변 환경의 값을 별도의 파라미터로 전달받아야 한다.

- **지역 확장 함수**를 정의할 수도 있다.

```kotlin
import atomictest.eq

fun main() {
    fun String.exclaim() = "$this!"
    "Hello".exclaim() eq "Hello!"
    "Hallo".exclaim() eq "Hallo!"
    "Bonjour".exclaim() eq "Bonjour!"
    "Ciao".exclaim() eq "Ciao!"
}
```

- 위와 같이 `main()` 안에서만 `exclaim()`을 사용할 수 있다.

#### 익명 함수(anonymous function)
- 익명 함수는 이름이 없다.
- 익명 함수는 지역 함수와 비슷하지만 fun 키워드를 사용해 정의한다.
- 익명 함수는 이름이 없는 일반 함수처럼 보인다.

#### 레이블

- 다음 코드에서 `forEach()`는 `return`을 포함하는 람다에 대해 작용한다.

```kotlin
import atomictest.eq

fun main() {
    val list = listOf(1, 2, 3, 4, 5)
    val value = 3
    var result = ""
    list.forEach {
        result += "$it"
        if(it == value) {
            result eq "123"
            return              // 1
        }
    }
    result eq "Never gets here" // 2
}
```

`return`식은 `fun`을 사용해 정의한 함수(따라서 람다는 제외된다)를 끝낸다.

- // 1에서 `return`은 `main()` 함수를 끝낸다는 뜻이다. (참고 2를 참고하자.)
- 이로 인해 // 2 번 라인은 실행되지 않고, 아무 출력도 내지 않는다.

람다를 둘러싼 함수가 아니라 람다에서만 반환해야 한다면 **레이블이 붙은(labeled) return**을 사용하자.


```kotlin
import atomictest.eq

fun main() {
    val list = listOf(1, 2, 3, 4, 5)
    val value = 3
    var result = ""
    list.forEach {
        result += "$it"
        if(it == value) return@forEach
    }
    result eq "12345"
}
```

- 이 코드에서 **레이블**은 **람다를 호출한 함수 이름**이다. 
- `return@forEach`라는 레이블이 붙은 return 문은 **람다를 레이블인 forEach까지만 반환시키라고 지정**한다.
- 람다 앞에 `레이블@`을 넣으면 새 레이블을 만들 수 있다. 
  - 이때 레이블 이름은 아무 이름이나 쓸 수 있다.


```kotlin
import atomictest.eq

fun main() {
    val list = listOf(1, 2, 3, 4, 5)
    val value = 3
    var result = ""
    list.forEach tag@{                  // 1
        result += "$it"
        if( it == value ) return@tag    // 2
    }
    result eq "12345"
}
```

- // 1 람다에 tag라는 레이블을 붙인다.
- // 2 `return@tag`는 `main()`이 아니라 람다를 반환시킨다.


#### 지역 함수 조작하기
- var나 val에 람다나 익명 함수를 저장할 수 있고, 이렇게 람다를 가리키게 된 식별자를 사용해 해당 함수를 호출할 수 있다.
- 지역 함수를 저장하려면 함수 참조를 사용한다.



## 예약어 및 코틀린 개념

- 없음.

## 함수

- 없음.

## 참고

#### 참고 1.
어디에서는 함수를 정의할 수 있다. 
심지어 함수 안에서도 함수를 정의할 수 있다.

#### 참고 2
원래 코틀린 람다 안에서는 return을 쓸 수 없다.
그런데 여기서는 어떻게 return 을 쓸 수 있을까?
코틀린에서는 인라인 함수가 람다를 인자로 받는 경우 해당 람다도 함께 인라인하게 되어 있으며, 이때 함께 인라인되는 람다 안에서 return을 쓸 수 있도록 허용한다.

인라인을 쓰면 `forEach()` 함수와 그 함수에 전달된 람다의 본문은 모두 `forEach()`를 호출한 위치(위의 예제에서는 `main()`의 내부)에 소스 코드를 복사한 것처럼 컴파일이 된다. 따라서 람다 안에서 return 을 써도 컴파일된 코드에서는 `main()` 함수 본문 안에 프로그래머가 직접 쓴 return과 구분되지 않고 똑같이 `main()`이 반환된다. 
인라인 함수가 람다를 인자로 받는 경우 등에 대한 설명은 이 책의 범위를 벗어난다.
따라서 코틀린 홈페이지(영문)의 인라인 함수 문서나 다른 코틀린 서적을 참조해보면 된다.