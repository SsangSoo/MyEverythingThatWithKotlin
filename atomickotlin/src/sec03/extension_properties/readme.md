# 아토믹 코틀린 At 42. 확장 프로퍼티

## 용어

#### 스타 프로젝션(star projection)

- `*`를 가리킨다.

## 예약어 및 코틀린 개념

#### 확장 프로퍼티
- 확장 함수를 정의할 수 있는 것처럼 확장 프로퍼티를 정의할 수도 있다.
- 확장 프로퍼티의 수신 객체 타입을 지정하는 방법도 확장 함수의 경우와 비슷하다.
- 확장 대상 타입이 함수나 프로퍼티 이름 바로 앞에 온다.

```kotlin
fun ReceiverType.extensionFunction() { ... }
val ReceiverType.extensionProperty: PropType
    get() { ... }
```

- **확장 프로퍼티에는 커스텀 게터가 필요**하다.
- **확장 프로퍼티에 접근할 때마다 프로퍼티 값이 계산**된다.

- 파라미터가 없는 확장 함수는 항상 확장 프로퍼티로 변환할 수 있지만, 먼저 그래도 될지 생각해보는 것이 좋다.
- [아톰 28. 프로퍼티 접근자](https://ssangsu.tistory.com/186)에서 프로퍼티와 함수 중 하나를 선택하는 기준에 대해 설명한 내용이 확장 프로퍼티에도 적용된다.~~(포스팅에는 뭐가 없다.)~~
  - 참고 쪽에서 확인바람.
- 기능이 단순하고 가독성을 향상시키는 경우에만 프로퍼티를 권장한다.
- 제너릭 확장 프로퍼티를 정의할 수도 있다.
  - 다음 코드는 [아톰 41. 제네릭스 소개](https://github.com/SsangSoo/MyEverythingThatWithKotlin/blob/main/atomickotlin/src/IntroGenerics/GenericListExtensions.kt)에서 본 `firstOrNull()` 함수를 **프로퍼티로 다시 구현한 코드**다.

```kotlin
package ExtensionProperties

import atomictest.eq

val <T> List<T>.firstOrNull: T?
    get() = if (isEmpty()) null else this[0]

fun main() {
    listOf(1, 2, 3).firstOrNull eq 1
    listOf<String>().firstOrNull eq null
}
```

- 코틀린 스타일 가이드는 **함수가 예외를 던질 경우 프로퍼티보다는 함수를 사용하는 것을 권장**한다.
- 제네릭 인자 타입을 사용하지 않으면 `*`로 대신할 수 있다. 이를 **스타 프로젝션(star projection)**이라고 한다.

```kotlin
val List<*>.indices: IntRange
    get() = 0 until size
```


- `List<*>`를 사용하면 List에 담긴 원소의 타입 정보를 모두 잃어버린다.
  - 예를 들어 `List<*>`에서 얻은 원소는 `Any?`에만 대입할 수 있다.

```kotlin
package ExtensionProperties

import atomictest.eq

fun main() {
    val list: List<*> = listOf(1, 2)
    val any: Any? = list[0]
    any eq 1
}
```

- `List<*>`에 저장된 값이 널이 될 수 있는 타입인지에 대해서도 아무 정보가 없다. 따라서 이런 경우 해당 값을 `Any?` 타입의 변수에만 대입할 수 있다.

## 함수

- 없음.

## 참고

#### 프로퍼티와 함수 중 하나를 선택하는 기준

코틀린 스타일 가이드에서는 계산 비용이 많이 들지 않고 객체 상태가 바뀌지 않는 한 같은 결과를 내놓는 함수의 경우 프로퍼티를 사용하는 편이 낫다고 안내한다.
프로퍼티 접근자는 프로퍼티에 대한 일종의 보호 수단을 제공한다.
많은 객체 지향 언어가 프로퍼티에 대한 접근을 제어하기 위해 물리적인 필드를 **private**으로 정의하는 방식에 의존한다.
프로퍼티 접근자를 사용하면 필드 접근과 같은 방식으로 쉽게 프로퍼티에 접근하도록 허용하면서 동시에 프로퍼티 접근을 제어하거나 변경할 수 있는 코드를 쉽게 추가할 수 있다.