# 아토믹 코틀린 At 31. 이름 붙은 인자와 디폴트 인자

## 용어

- 없음.

## 예약어 및 코틀린 개념

#### 이름 붙은 인자
- 함수를 호출하면서 인자의 이름을 지정할 수 있따.
- 이름 붙은 인자를 사용하면 코드 가독성이 좋아진다.
- 인자 목록이 긴 경우 특히 그렇다.
- 이름 붙은 인자를 사용하면, 코드를 읽는 사람이 함수에 대한 문서를 살펴보지 않고도 코드를 이해할 정도로 코드가 명확해질 수 있다.

다음 예쩨에서 파라미터는 모두 Int이며, 이름 붙은 인자를 써서 각각의 의미를 명확히 할 수 있다.

```kotlin
package NamedAndDefaultArgs

import atomictest.eq


fun color(red: Int, green: Int, blue: Int) =
    "($red, $green, $blue)"

fun main() {
    color(1, 2, 3) eq "(1, 2, 3)" // 1. 이런 코드는 그다지 많은 정보를 전달하지 못하낟. 각 인자의 역할을 알아내려면 함수 문저를 살펴봐야 한다.
    color(
        red = 76,       // 2. 모든 인자의 의미가 명확하다.
        green = 89,
        blue = 0,
    ) eq "(76, 89, 0)"
    color(52, 34, blue = 0) eq // 3. 모든 인자에 이름을 붙이지 않아도 된다.
    "(52, 34, 0)"
}
```

이름 붙은 인자를 사용하면 색의 순서를 변경할 수 있다.
다음 코드에서는 blue를 먼저 지정한다.

```kotlin
package NamedAndDefaultArgs

import atomictest.eq

fun main() {
    color(blue = 0, red = 99, green = 52) eq
            "(99, 52, 0)"
    color(red = 255, 255, 0) eq
            "(255, 255, 0)"
}
```

이름 붙은 인자와 일반(위치 기반) 인자를 섞어서 사용할 수도 있다.
인자 순서를 변경하고나면, 인자 목록의 나머지 부분에서도 이름 붙은 인자를 사용해야 한다.
이는 가독성을 위해서만 아니라, 컴파일러가 (인자 순서가 바뀐 다음에 나타나는) 이름이 생략된 인자들의 위치를 알아내지 못할 수도 있기 때문이다.

이름 붙은 인자는 **디폴트 인자(default argument)**와 결합하면 더 유용하다.


#### 디폴트 인자(default argument)
- **파라미터의 디폴트 값을 함수 정의에서 지정하는 것**을 말한다.

```kotlin
package NamedAndDefaultArgs

import atomictest.eq

fun color2(
    red: Int = 0,
    green: Int = 0,
    blue: Int = 0,
) = "($red, $green, $blue)"

fun main() {
    color2(139) eq "(139, 0, 0)"
    color2(blue = 139) eq "(0, 0, 139)"
    color2(255, 165) eq "(255, 165, 0)"
    color2(red = 128, blue = 128) eq
            "(128, 0, 128)"
}

```

**함수 호출시 값을 지정하지 않은 인자는 자동으로 디폴트 값으로 지정**된다.
따라서 **디폴트 값과 다른 인자만 지정**하면 된다.
인자 목록이 긴 경우, 디폴트 인자를 생략하면 코드를 짧게 작성할 수 있으므로 코드 작성이 쉬워진다.
**더 중요한 건 코드의 가독성이 좋아진다는 점**이다.

다음 예제에서는 color3을 정의할 때 **덧붙은 콤마(trailing comma)**를 사용한다.
덧붙은 콤마는 마지막 파라미터(blue) 뒤에 콤마를 추가로 붙인 것을 말한다.
파라미터 값을 여러 줄에 걸쳐 ㅆ느느 경우에는 덧붙은 콤마가 유용하다.
덧붙은 콤마가 있으면, 콤마를 추가하거나 빼지 않아도 새로운 아이템을 추가하거나 아이템의 순서를 바꿀 수 있다.

이름 붙은 인자와 디폴트 인자는 (그리고 덧붙은 콤마도) 생성자에 써도 된다.

```kotlin
package color3
import atomictest.eq

class Color(
    val red: Int = 0,
    val green: Int = 0,
    val blue: Int = 0,
) {
    override fun toString() =
        "($red, $green, $blue)"
}

fun main() {
    Color(red = 77).toString() eq "(77, 0, 0)"
}
```

**객체 인스턴스를 디폴트 인자로 전달하는 경우**, **호출할 때마다 같은 인스턴스가 반복해서 전달**된다.
디폴트 인자로 함수 호출이나 생성자 호출 등을 사용하는 경우, **함수를 호출할 때마다 해당 객체의 새 인스턴스가 생기거나 디폴트 인자에서 호출하는 함수가 호출**된다.

**인자 이름을 붙였을 때 가독성이 향상되는 경우에만 인자 이름을 지정**한다.

디폴트 인자의 다른 예로 여러 줄 String의 형식을 맞춰주는 표준 라이브러리 `trimMargin()` 함수를 들 수 있다.


## 함수

#### joinToString()
- 디폴트 인자를 사용하는 표준 라이브러리 함수다.
- `joinToString()`은 이터레이션이 가능한 객체(리스트, 집합, 범위 등)의 내용을 String으로 합쳐준다.
- 이때 **원소 사이에 들어갈 문자열(구분자), 맨 앞에 붙일 문자열(접두사), 맨 뒤에 붙일 문자열(접미사)을 지정할 수도 있다.
- 디폴트 값으로 **separtator**에는 콤마, prefix와 postfix에는 빈 문자열이 지정되어 있다.

다음 예제에서는 이름 붙은 인자를 사용해 변경하고 싶은 인자를 지정했다.

```kotlin
fun main() {
    val list = listOf(1, 2, 3)
    list.toString() eq "[1, 2, 3]"
    list.joinToString() eq "1, 2, 3"
    list.joinToString ( prefix = "(",
        postfix = ")") eq "(1, 2, 3)"
    list.joinToString(separator = ":") eq "1:2:3"
}
```

#### trimMargin()
- 각 줄의 시작 부분을 인식하기 위한 경계를 표현하는 접두사 String을 파라미터로 받아 사용한다.
- 소스 String의 각 줄 맨 앞에 있는 공백들 다음에 지정한 접두사 String까지를 잘라내서 문자열을 다듬어준다.
- 그리고 여러 줄 문자열의 첫 번째 줄과 마지막 줄 중에 공백으로만 이뤄진 줄을 제거한다.
- `|`(파이프)가 경계 접두사의 디폴트 인자값이다. 

## 참고

- 없음.