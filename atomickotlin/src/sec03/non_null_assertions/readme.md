# 아토믹 코틀린 At 39. 널 아님 단언

## 용어

- 없음.

## 예약어 및 코틀린 개념

#### 널 아님 단어(non-null assertion)
널이 될 수 있는 타입을 처리하는 두 번째 접근 방법으로, 어떤 참조가 null이 될 수 없다는 사실을 특별히 알 수 있는 경우를 들 수 있다.
`널 아님 단어(non-null assertion)`은 null이 될 수 없다고 주장하기 위해 느낌표 두개(!!)를 쓴다.

```
이 연산이 뭔가 경고하는 것처럼 보인다면 제대로 본 것이다.
null과 관련된 모든 문제의 근원은 어떤 대상이 절대 null이 될 수 없다고 생각하기 때문이다.
(null과 관련된 문제의 또 다른 근원은 어떤 대상이 null일 수도 있음을 인식하지 못하는 것이다.
```

`x!!`는 'x가 null일 수도 있다는 사실을 무시하라. 내가 x가 null이 아니라는 점을 보증한다'라는 뜻이다.
`x!!`는 x가 null이 아니면 x를 내놓고, x가 null이면 오류를 발생시킨다.

```kotlin
import atomictest.*

fun main() {
    var x: String? = "abc"
    x!! eq "abc"
    x = null
    capture {
        val s: String = x!!
    } eq "NullPointerException"
}
```

`val s: String = x!!`라는 정의는 코틀린이 x에 대해 알고 있는 내용을 무시하고 그냥 **null이 될 수 없는 타입의 참조인 s에 대입하도록 명령**한다.
코틀린에서는 이런 코드에서 x가 null인 경우 `NullPointerException`을 던지게 하는 지원 기능이 있다.

일반적으로 `!!`를 그냥 쓰는 경우는 없다. 보통 다음과 같이 역참조와 함께 쓴다.

```kotlin

import atomictest.eq

fun main() {
    val s: String? = "abc"
    s!!.length eq 3
}
```

**널 아님 단언**을 한 줄에 하나씩만 사용하면 NPE 예외가 발생했을 때 line 번호를 보고 쉽게 오류 위치를 찾을 수 있다.
안전한 호출(`?.`)은 단일 연산자지만, 널 아님 단언 호출은 `널 아님 단언(!!)과 역참조(.)`로 이뤄진다.
혹은 널 아님 단언만 사용해도 된다.



## 함수

- 없음.

## 참고

널 아님 단언을 사용하지 않고 안전한 호출이나 명시적인 null 검사를 활용하는 쪽을 권장한다.
널 아님 단언은 **코틀린과 자바가 상호 작용하는 경우**와 **아주 드물지만 코틀린이 널 가능성을 제대로 검사하지 못하는데 대상이 null이 아님을 알 수 있는 경우를 위해 도입**됐다.


코드에서 같은 연산에 대해 널 아님 단언을 자줒 사용한다면 이 문제를 언급하는 적절한 단언과 함께 함수를 분리하는 것이 좋다.
예를 들어 프로그램 로직에서 Map에 특정 키가 꼭 존재해야하는데, 키가 없을 경우 아무일도 일어나지 않는 것보다는 예외가 발생하는 편이 좋다고 가정했을 때
값은 일반적인 방법(각괄호)으로 읽는 대신 `getValue()`를 사용하면 키가 없는 경우 `NoSuchElementException`이 던져진다.

```kotlin
import atomictest.*

fun main() {
    val map = mapOf(1 to "one")
    map[1]!!.uppercase() eq "ONE"
    map.getValue(1).uppercase() eq "ONE"
    capture {
        map[2]!!.uppercase()
    } eq "NullPointerException"
    capture {
        map.getValue(2).uppercase()
    } eq " NoSuchElementException: " +
            "Key 2 is missing in the map."
}
```

NoSuchElementException과 같이 구체적인 예외를 던지면 뭔가 잘못됐을 때 더 유용한 상세 정보를 얻을 수 있다.

최적의 코드는 항상 **안전한 호출과 자세한 예외를 반환하는 특별한 함수만 사용**한다.
**null이 아니라는 점을 단언한 호출은 꼭 필요할 때만 사용**하라.
널 아님 단언이 자바와 상호 작용하기 위해 추가된 것이긴 하지만, 자바와 상호 작용할 때 활용할 수 있는 더 나은 방법이 있다.
이데 대해서는 해당 책 '부록 B. 자바 상호 운용성'에서 살펴볼 수 있다.

