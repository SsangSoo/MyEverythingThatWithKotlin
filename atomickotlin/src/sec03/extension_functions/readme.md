# 아토믹 코틀린 At 30. 확장 함수

## 용어

-없음

## 예약어 및 코틀린 개념

#### 확장 함수(extension function)
만약 필요한 모든 것을 거의 다 제공하는 라이브러리를 발견했다고 해보자. 이 라이브러리에 멤버 함수만 한두 가지 더 있으면 문제를 완벽하게 해결할 수 있을 것 같다.
하지만 이 라이브러리는 우리의 코드가 아니다. 소스 코드에 접근할 수도 없고, 소스 코드 변경을 마음대로 제어할 수도 없다.
수정한 내용을 새로운 버전이 나올 때마다 다시 반복해서 적용해야 한다.

**확장 함수(extension function)**는 기존 클래스에 멤버 함수를 추가하는 것과 같은 효과를 낸다.
확장할 대상 타입은 **수신 객체 타입(receiver type)**이라고 한다.
확장 함수를 정의하기 위해서는 함수 이름 앞에 수신 객체 타입을 붙여야 한다.

```kotlin
fun 수신타입.확장함수() { ... }
```

다음과 같이 하면 String 클래스에 확장 함수를 두 가지 정의할 수 있다.

```kotlin
package ExtensionFunctions
import atomictest.eq

fun String.singleQuote() = "'$this'"
fun String.doubleQuote() = "\"$this\""

fun main() {
    "Hi".singleQuote() eq "'Hi'"    // eq는 책에서 제공하는 테스트 함수다.
    "Hi".doubleQuote() eq "\"Hi\""
}
```

확장 함수를 마치 수신 객체 타입의 멤버 함수인 것처럼 호출할 수 있다.
이 확장 함수를 확장 함수가 정의되지 않은 다른 패키지에서 사용하려면 임포트해야 한다.

```kotlin
package Other
import atomictest.eq
import ExtensionFunctions.doubleQuote
import ExtensionFunctions.singleQuote

fun main() {
    "Single".singleQuote() eq "'Single'"
    "Double".doubleQuote() eq "\"Double\""
}
```

this 키워드를 사용해 멤버 함수나 다른 확장에 접근할 수 있다.
- 클래스 내부에서 this를 생략할 수 있었던 것처럼 확장 함수 안에서도 this를 생략할 수 있다.
- 명시적으로 멤버를 한정 시킬 필요가 없다.

때로는 다신의 클래스에 대해 확장을 정의하는 것이 더 간단한 코드를 생성하기도 한다.

```kotlin
package ExtensionFunctions

import atomictest.eq

class Book(val title: String)

fun Book.categorize(category: String) =
    """title: "$title", category: $category"""

fun main() {
    Book("Dracula").categorize("Vampire") eq
            """title: "Dracula", category: Vampire"""
}
```

위의 코드에서 `categorize()` 안에서 아무것도 한정하지 않고 title 프로퍼티를 사용할 수 있다.
확장 함수는 확장 대신 타입(수신 객체 타입)의 **public 원소에만 접근**할 수 있다.
따라서 **확장은 일반 함수가 할 수 있는 일만 처리할 수 있다.**
확장 함수를 사용하는 이유는 오로지 this를 사용함으로써(또는 생략함으로써) 구문적 편의를 얻기 때문이다.
하지만 이런 문법 설탕(syntax sugar)은 강력하다.
호출하는 코드에서 확장함수는 멤버 함수와 똑같아 보이고, IDE는 객체에 대해 점 표기법으로 호출할 수 있는 함수 목록에 확장을 포함시켜준다.

## 함수

- 없음.

## 참고

- 없음.