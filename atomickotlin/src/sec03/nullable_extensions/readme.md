# 아토믹 코틀린 At 40. 확장 함수와 널이 될 수 있는 타입

## 용어

#### 쇼트 서킷(short circuit)
쇼트 서킷 `||`에서 첫 번째 식이 true면 전체 식이 true로 결정되므로, 두 번째 식은 아예 계산을 하지 않는다.

## 예약어 및 코틀린 개념

#### 확장 함수와 널이 될 수 있는 타입

때로는 눈에 보이는 것과 실체가 다를 수도 있다. `s?.f()`는 s가 널이 될 수 있는 타입임을 암시한다. 그렇지 않다면 단순히 `s.f()`를 호출했을 것이다.
마찬가지로 `t.f()`는 t가 널이 될 수 없는 타입임을 암시하는 것처럼 보인다. 
코틀린에서는 널이 될 수 없는 타입에 대해 안전한 호출이나 null 여부 검사가 필요 없기 때문이다. 하지만 **t가 꼭 널이 될 수 없는 타입인 것은 아니다.**

코틀린 표준 라이브러리는 다음과 같이 String의 확장 함수를 제공한다. (함수쪽에서도 소개한다.)

- **isNullOrEmpty()**
  - 수신 String이 null이거나 빈 문자열인지 검사한다.
- **isNullOrBlank()**
  - `isNullOrEmpty()`와 같은 검사를 수행한다.
  - 수신 객체 String이 온전히 공백 문자(공백뿐 아니라 탭(`\t`)과 새줄 문자(`\n`)도 포함)로만 구성되어 있는지 검사한다.

다음은 위 함수를 간단히 테스트하는 코드다.

```kotlin
fun main() {
    val s1: String? = null
    s1.isNullOrEmpty() eq true
    s1.isNullOrBlank() eq true

    val s2 = ""
    s2.isNullOrEmpty() eq true
    s2.isNullOrBlank() eq true


    val s3: String = " \t\n"
    s3.isNullOrEmpty() eq false
    s3.isNullOrBlank() eq true
}
```

- 함수 이름은 보면 수신 객체가 널이 될 수 있는 타입인 것처럼 보인다.
- s1은 널이 될 수 있는 타입이지만, 안전한 호출을 사용하지 않고 `isNullOrEmpty()`나 `isNullOrBlank()`를 호출할 수 있다.
  - 왜냐하면 이들이 **널이 될 수 있는 타입 `String?`의 확장 함수로 정의되어 있기 때문이다.

`isNullOrEmpty()`를 널이 될 수 있는 `String? s`를 파라미터로 받는 비확장 함수로 다시 작성할 수 있다.

```kotlin
fun isNullOrEmpty(s: String?): Boolean =
    s == null|| s.isEmpty()

fun main() {
    isNullOrEmpty(null) eq true
    isNullOrEmpty("") eq true
}
```

s가 널이 될 수 있는 타입이므로 명시적으로 null 여부와 빈 문자열 여부를 검사할 수 있다.
`s == null || s.isEmpty()`는 **쇼트 서킷(short circuit)**을 사용**한다.
따라서 `s == null || s.isEmpty()` 식은 s가 null이어도 NPE가 발생하지 않는다.

**확장 함수**는 **this를 사용해 수신 객체(확장 대상 타입에 속하는 객체)를 표현**한다. 
이때 **수신 객체를 널이 될 수 있는 타입으로 지정하려면 확장 대상 타입 뒤에 ?를 붙이면 된다.**

```kotlin
fun String?.isNullOrEmpty(): Boolean =
    this == null || isEmpty()

fun main() {
    "".isNullOrEmpty() eq true
}
```

현재 본 코드보다 위에서 본 널이 될 수 있는 타입의 인자를 받는 `isNullOrEmpty()`보다 지금 코드의 확장 함수 `isNullOrEmpty()`가 더 읽기 편하다.

## 함수

#### isNullOrEmpty()
- 수신 String이 null이거나 빈 문자열인지 검사한다.

#### isNullOrBlank()
- `isNullOrEmpty()`와 같은 검사를 수행한다. 
- 수신 객체 String이 온전히 공백 문자(공백뿐 아니라 탭(`\t`)과 새줄 문자(`\n`)도 포함)로만 구성되어 있는지 검사한다.

## 참고

널이 될 수 있는 타입을 확장할 때는 조심해야 한다.
`isNullOrEmpty()`나 `isNullOrBlank()`와 같이 상황이 단순하고 함수 이름에서 수신 객체가 null일 수 있음을 암시하는 경우에는 널이 될 수 있는 타입의 확장 함수가 유용하다.
일반적으로는 **보통의(널이 될 수 없는) 확장을 정의하는 편이 낫다. 안전한 호출과 명시적인 검사는 수신 객체의 널 가능성을 더 명백히 드러내는** 반면, 
**널이 될 수 있는 타입의 확장 함수는 널 가능성을 감추고 코드를 읽는 독자(혹은 미래의 나 또는 우리(?))를 혼란스럽게 할 수 있다.**