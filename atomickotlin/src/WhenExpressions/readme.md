# 아토믹 코틀린 At 33. when 식

## 용어

- 없음.

## 예약어 및 코틀린 개념

#### when
- 어떤 값을 여러 가지 가능성과 비교해 선택한다.
  - 식은 when으로 시작하고, when 뒤에는 괄호 안에 있는 비교 대상 값이 오고, 그 뒤에는 값과 일치할 수 있는 여러 매치(match)가 들어있는 본문이 온다.
  - 각 매치는 식, 오른쪽 화살표(`->`)로 시작한다. 
    - 여기서 오른쪽 화살표는 `-`와 `>` 두 문자로 이뤄져 있으며, 중간에 공백을 넣으면 안 된다.
    - 화살표 오른쪽에는 결괏값을 계산하는 식이 온다.
  - when 식을 계산할 때는 비교 대상 값과 각 매치에 있는 화살표 왼쪽의 값을 순서대로 비교한다.
  - 일치하는 값이 있으면 화살표 오른쪽 값을 계산한 값이 전체 when 식의 결괏값이 된다.

다음 예제의 `ordinal()`은 독일어로 된 기수(수량을 세는 단어)로부터 서수(순서를 세는 단어)를 얻는다.
아래 코드는 정수를 미리 정해진 몇 가지 수와 비교해서 일반적인 규칙을 적용할지, 예외 규칙을 적용할지 검사한다(독일어에서는 고통스럽게도 예외 규칙을 적용해야 하는 경우가 흔하다.)

```kotlin
package WhenExpressions

import atomictest.eq

val numbers = mapOf(
    1 to "eins", 2 to "zwei", 3 to "drei",
    4 to "vier", 5 to "fuenf", 6 to "sechs",
    7 to "sieben", 8 to "acht", 9 to "neun",
    10 to "zhen", 11 to "elf", 12 to "zwoelf",
    13 to "dreizehn", 14 to "vierzehn",
    15 to "fuenfzehn", 16 to "sechzehn",
    17 to "siebzehn", 18 to "achtzehn",
    19 to "neunzehn", 20 to "zwanzig",
)

fun ordinal(i: Int): String =
    when(i) {                               // 1
        1 -> "erste"                        // 2
        3 -> "dritte"
        7 -> "siebte"
        8 -> "achte"
        20 -> "zwanzigste"
        else -> numbers.getValue(i) + "te"  // 3
    }

fun main() {
    ordinal(2) eq "zweite"
    ordinal(3) eq "dritte"
    ordinal(11) eq "elfte"
}
```

// 1. when 식은 i를 본문의 매치식과 비교한다.
// 2. 가장 먼저 일치하는 매치식에서 when 식의 실행이 끝난다. 여기서는 String 값이 생성되며, 이 값이 ordinal()의 반환값이 된다.
// 3. else 키워드는 일치하는 매치식이 없을 때 대안으로 사용할 식을 제공한다. else는 항상 매치 목록의 맨 마지막에 있어야 한다. 2를 사용해 테스트한 경우, 이 값은 1, 3, 7, 8, 20과 일치하지 않으므로 else가 선택된다.

위 예제에서 else 가지를 없애면 **"when" expression must be exhaustive, add necessary 'else' branch'** 컴파일 타입 오류가 발생한다(when이 가능한 모든 경우를 처리해야 하므로 else 가지를 추가하라는 뜻).
when 식을 문처럼 취급하는 경우(즉, when의 결과를 사용하지 않는 경우)에만 else 가지를 생략할 수 있다.
이 경우 매치와 일치하지 않으면 아무 일도 일어나지 않고 when 문이 끝난다.

<div align="left">
  <img src="https://velog.velcdn.com/images/tjdtn4484/post/31feb8cb-6b52-4dc1-9018-a31e0346d32e/image.png">
</div>


- when의 인자(when 다음에 있는 괄호 안에 올 값)로는 임의의 식이 올 수 있다.
그리고 매치 조건(`->`의 왼쪽)에도 아무 값이나 올 수 있다(꼭 상수가 아니어도 된다).

when 식과 if 식은 기능이 겹치는 부분이 있지만, **when이 더 유연하다**. 따라서 선택의 여지가 있다면 when을 사용하는 것을 더 권장한다.

- when에는 인자를 취하지 않는 특별한 형태도 있다.
  - 인자가 없으면 각 매치 가지를 Boolean 조건에 따라 검사한다는 뜻이다.
  - 따라서 인자가 없는 when에서는 화살표 왼쪽의 식에 항상 Boolean 타입의 식을 넣어야 한다.

> 사실 어떤 타입의 값이든 when을 사용해 비교할 수 있다.


## 함수

- 없음.

## 참고

- 없음.