# 아토믹 코틀린 At 37. 널이 될 수 있는 타입

## 용어

- 없음.

## 예약어 및 코틀린 개념

#### 코틀린에서의 null
- null 참조를 처음 생각해낸 토니 호어(Tony Hoare)는 이를 '자신의 100만 불짜리 실수'라고 이야기했다.

**null의 문제점**
- 자바에서는 null 또는 의미 있는 값이 결과가 되도록 허용한다.
- 그러나 불행히도 null을 정상적인 갑과 같은 방식으로 다루면 극적인 실패가 발생한다.
  - 자바에서는 NullPointerException이 발생하며, C와 같은 원시적인 언어에서는 null 포인터를 사용하면 프로세스가 오류로 끝나버리거나 심지어 운영체제 또는 기계가 멈추는 경우도 있다.

이러한 문제에 대한 언어적 해법 중 하나는 애초에 null을 허용하지 않는 것이다.
그 대신에 특별한 '값 없음'을 표시하는 표지를 도입할 수 있다. 코틀린에서도 이런 선택을 하고 싶었던 것 같다.
하지만 코틀린은 자바와 상호 작용해야 하는데, 자바는 null을 쓴다.

코틀린의 해법은 아마도 두 접근 방식을 가장 잘 절충한 방법일 것이다.
코틀린에서는 모든 타입은 기본적으로 널이 될 수 없는(non-nullable) 타입이다. 하지만 무언가 null 결과를 내놓을 수 있다면, **타입 이름 뒤에 물음표(?)를 붙여서 결과가 null이 될 수도 있음을 표시**해야 한다.

```kotlin
import atomictest.eq

fun main() {
    val s1 = "abc"              // 1

    // 컴파일 오류
//     val s2: String = null    // 2

    // 널이 될 수 있는 정의들
    val s3: String? = null      // 3
    val s4: String? = s1        // 4

    // 컴파일 오류
//    val s5: String = s4      // 5
    val s6 = s4                 // 6

    s1 eq "abc"
    s3 eq null
    s4 eq "abc"
    s6 eq "abc"

}
```

// 1. s1은 null 참조일 수 없다. 지금까지 이 책에서 정의했던 모든 var와 val은 자동으로 널이 될 수 없다.
// 2. 오류 메세지는 `Null can not be a value of a non-null type String`이다 (널이 될 수 없는 타입인 String 타입의 값으로 null을 지정할 수 없다는 뜻).
// 3. null 참조를 저장할 수 있는 식별자를 정의하려면 타입 이름 뒤에 `?`를 붙인다. 이렇게 정의된 식별자는 null이나 정상적인 값을 모두 담을 수 있다.
// 4. null과 정상적인 값을 널이 될 수 있는 타입의 식별자에 대입할 수 있다.
// 5. **널이 될 수 있는 타입의 식별자**를 **널이 될 수 없는 타입의 식별자**에 대입할 수 는 없다. 코틀린은 `Type mismatch: inferred type is String? but String was expected` 라는 오류를 표시한다(String? 타입이 추론됐는데 이 위치에는 String 타입의 값이 필요하다는 뜻). 여기서는 실제 값이 널이 아닌 값이지만("abc"라는 사실을 안다). 코틀린은 타입이 다르기 때문에 대입을 허용하지 않는다.
// 6. 타입 추론을 사용하면 코틀린이 적절한 타입을 만들어낸다. 여기서는 s4가 널이 될 수 있는 타입이므로 s6도 널이 될 수 있는 타입이다.

타입 이름 끝에 `?`를 붙여서 기존 타입을 살짝 바꾼 것처럼 보이지만, 실제로는 **다른 타입**을 지정한 것이다.
예를 들어 String과 String? 는 **서로 다른 타입**이다.

- 자바에서는 거의 대부분의 값이 null이 될 수 있기 때문에 결과가 null인지 검사하는 코드를 작성하거나, null 가능성을 차단해주는 코드의 다른 부분에 의존해야 한다.
- 코틀린에서는 null이 될 수 있는 타입을 단순히 **역참조(dereference)(즉, 멤버 프로퍼티나 멤버 함수에 접근)할 수 없다.

```kotlin
import atomictest.eq

fun main() {
    val s1: String = "abc"
    val s2: String? = s1

    s1.length eq 3  // 1
    // 컴파일되지 않는다.
//     s2.length    // 2  // Only safe (?.) or non-null asserted (!!.) calls are allowed on a nullable receiver of type String?

}
```

// 1. 여기서는 널이 될 수 없는 타입의 멤버에 접근할 수 있다.
// 2. 2와 같이 널이 될 수 있는 타입의 멤버를 참조하는 경우, 코틀린은 오류를 발생시킨다.

대부분 타입의 값은 메모리에 있는 객체에 대한 참조로 저장된다.
**역참조**의 의미가 바로 이것이다. 객체에 접근하기 위해서는 메모리에서 객체를 가져와야 한다.

널이 될 수 있는 타입을 역참조해도 **NullPointerException**이 발생하지 않도록 보장하는 가장 단순한 방법은 명시적으로 참조가 null인지 검사하는 것이다.

```kotlin
import atomictest.eq

fun main() {
    val s: String? = "abc"
    if(s != null)
        s.length eq 3
}
```

명시적으로 if 검사를 수행하고 나면 코틀린이 널이 될 수 있는 객체를 참조하도록 허용해준다.
하지만 널이 될 수 있는 값을 다루는 일은 매우 흔하기 때문에 매번 이런 if 검사를 수행하면 너무 산만하고 어수선해진다. 
코틀린은 이 문제를 해결할 간결한 구문을 제공한다.(이 내용은 뒤에서 다룬다.)

새 클래스를 정의할 때마다 코틀린은 자동으로 널이 될 수 있는 타입과 널이 될 수 없는 타입을 추가해준다.

```kotlin
class Amphibian

enum class Species {
  Frog, Toad, Salamander, Caecilian
}

fun main() {
  val a1: Amphibian = Amphibian()
  val a2: Amphibian? = null
  val at1: Species = Species.Toad
  val at2: Species? = null
}
```

위를 보면 "널이 될 수 없는 타입"에 대응하는 "널이 될 수 있는 타입"을 정의하기 위해 무언가 특별한 일을 할 필요는 없다.
널이 될 수 없는 타입을 정의하면 널이 될 수 있는 타입도 자동으로 사용할 수 있게 된다.

## 함수

- 없음.

## 참고

- 없음.