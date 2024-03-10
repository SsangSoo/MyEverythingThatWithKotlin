# 아토믹 코틀린 At 64. 상속과 확장

## 용어

- 없음.
 
## 예약어 및 코틀린 개념

#### 상속과 확장
때로는 기존 클래스를 새로운 목적으로 확용하기 위해 새로운 함수를 추가해야 할 때가 있다.
이때 기존 클래스를 변경할 수 없으면 새 함수를 추가하기 위해 상속을 사용해야 한다.
이로 인해 코드를 이해하고 유지 보수하기 어려워진다.

#### 상속한 클래스에 함수 추가
- 현대 OO 프로그래밍(객체 지향)이 상속을 하는 동안 함수를 추가하는 것을 허용하지만, 이는 `코드 냄새(code smell)`다.
- 함수 추가는 타당하고 편리해 보이지만 함정에 빠뜨릴 수 있다.
- 무언가가 작동하는 것처럼 보인다고 해서 그게 반드시 좋은 아이디어는 아니다.
- 이후에 코드를 유지 보수해야하는 사람(코드를 처음 작성한 사람이 나일 수도 있다)에게 악영향을 끼칠 수 있는 아이디어라면 더 문제다.
  - 이런 문제를 **기술 부채(technical debt)**라고 부른다.

- **기반 클래스 인터페이스를 확장하기 위해 상속하는 대신, 확장 함수를 사용하면 상속을 사용하지 않고 기반 클래스의 인스턴스를 직접 확장할 수 있다.**

#### 관습에 의한 인터페이스
- 확장 함수를 함수가 하나뿐인 인터페이스를 만드는 것처럼 생각할 수도 있다.
- 코틀린 라이브러리에서는 `관습에 의한 인터페이스`를 광범위하게 사용한다. 
  - 특히 컬렉션을 다룰 때 더욱 그렇다.
  - 코틀린 컬렉션은 거의 모두가 자바 컬렉션이지만, 코틀린 라이브러리는 **다수의 확장 함수를 추가해서 자바 컬렉션을 함수형 스타일의 컬렉션으로 변모시켜준다.**
  - 예를 들어, 프로그래머들은 컬렉션과 비슷한 객체가 거의 대부분 `map()`과 `reduce()`를 제공하리라 예상하며, 이런 관습을 기대하면서 프로그래밍을 할 수 있다.
- 코틀린 표준 라이브러리의 `Sequence` 인터페이스에는 멤버 함수가 하나만 들어있다.
  - 다른 모든 `Sequence` 함수는 모두 확장이다.

#### 어댑터 패턴

라이브러이에서 타입을 정의하고 그 타입의 객체를 파라미터로 받는 함수를 제공하는 경우가 종종 있다.

```kotlin
package sec05.inheritanceextensions

interface LibType {
    fun f1()
    fun f2()
}

fun utility1(lt: LibType) {
    lt.f1()
    lt.f2()
}

fun utility2(lt: LibType) {
    lt.f2()
    lt.f1()
}
```

위의 라이브러리를 사용하려면 현재 만든 기초 클래스를 `LibType`으로 변환할 방법이 필요하다.
다음 코드에서 `MyClassAdaptedForLib`를 만들기 위해 기존 `MyClass`를 상속했다.
`MyClassAdaptedForLib`는 `LibType`을 구현하므로 `UsefulLibrary.kt`에 정의한 함수의 인자로 전달될 수 있다.

```kotlin
package sec05.inheritanceextensions

import sec05.usefullibrary.*
import atomictest.*

open class MyClass {
    fun g() = trace("g()")
    fun h() = trace("h()")
}

fun useMyClass(mc: MyClass) {
    mc.g()
    mc.h()
}

class MyClassAdaptedForLib :
    MyClass(), LibType {
    override fun f1() = h()
    override fun f2() = g()
}

fun main() {
    val mc = MyClassAdaptedForLib()
    utility1(mc)
    utility2(mc)
    useMyClass(mc)
    trace eq "h() g() g() h() g() h()"
}
```

위의 방식은 상속을 하는 과정에서 클래스를 확장하긴 하지만, 새 멤버 함수는 오직 `UsefulLibrara`에 연결하기 위해서만 쓰인다.
다른 곳에서는 `useMyClass()`에서와 같이 `MyClassAdaptedForLibrary`를 그냥 `MyClass`객체로 취급할 수 있다.
기반 클래스 사용자가 파생 클래스에 대해 꼭 알아야 하는 방식으로 `MyClassAdaptedForLibrary`클래스를 사용하는 코드는 없다.

`Adapter.kt`(바로 위의 코드)는 `MyClass`가 상속에 대해 열린 `open` 클래스라는 점에 의존한다.
우리가 `MyClass`를 수정할 수 없고 `MyClass`라 `open`도 아니라면 **합성을 사용해 어댑터(adapter)를 만들 수 있다. 
다음 코드에서는 `MyClassAdaptedForLib` 클래스 안에 `MyClass`필드를 추가했다.

```kotlin
package sec05.inheritanceextensions2

import sec05.inheritanceextensions.*
import sec05.usefullibrary.*
import atomictest.*

class MyClass { // open된 클래스가 아님
    fun g() = trace("g()")
    fun h() = trace("h()")

}

fun useMyClass(mc: MyClass) {
    mc.g()
    mc.h()
}

class MyClassAdaptedForLib : LibType {
    val field = MyClass()
    override fun f1() = field.h()
    override fun f2() = field.g()
}

fun main() {
    val mc = MyClassAdaptedForLib()
    utility1(mc)
    utility2(mc)
    useMyClass(mc.field)
    trace eq "h() g() g() h() g() h()"
}
```

이 코드는 `Adapter.kt`만큼 깔끔하지는 않으며, `useMyClass(mc.field)`호출처럼 명시적으로 `MyClass`객체에 접근해야 한다.
하지만 이런 방법은 여전히 기존 라이브러리를 새로운 인터페이스에 맞게 젛놘해 연결하는 문제를 쉽게 해결해준다.

**확장 함수는 어댑터를 생성할 때 아주 유용할 것 같지만, 불행히도 확장 함수를 모아서 인터페이스를 구현할 수는 없다.**

#### 멤버 함수와 확장 함수 비교
확장 함수 대신 멤버 함수를 써야하는 경우가 있다.
함수가 `private`멤버에 접근해야 한다면 멤버 함수를 정의할 수밖에 없다.

```kotlin

import atomictest.eq

class Z(var i: Int = 0) {
    private var j = 0
    fun increment() {
        i++
        j++
    }
}

fun Z.decrement() {
    i--
    // j-- 접근할 수 없음
}
```

j가 private이므로 `increment()`멤버 함수는 j를 변경할 수 있지만, `decrement()` 확장 함수는 j에 접근할 수 없다.

**확장 함수의 가장 큰 한계**는 **오버라이드할 수 없다**는 점이다.

```kotlin
package sec05.inheritanceextensions

import atomictest.*


open class Base {
    open fun f() = "Base.f()"
}

class Derived : Base() {
    override fun f() = "Derived.f()"
}

fun Base.g() = "Base.g()"
fun Derived.g() = "Derived.g()"

fun useBase(b: Base) {
    trace("Received ${b::class.simpleName}")
    trace(b.f())
    trace(b.g())
}

fun main() {
    useBase(Base())
    useBase(Derived())
trace eq
"""
Received Base
Base.f()
Base.g()
Received Derived
Derived.f()
Base.g()
"""
}
```

trace의 출력은 멤버 함수인 `f()`에서는 다형성이 작동하지만 확장 함수인 `g()`에서는 작동하지 않는다는 사실을 보여준다.
함수를 오버라이드할 필요가 없고 클래스의 공개 멤버만으로 충분할 때는 이를 멤버 함수로 구현할 수도 있고, 확장 함수로 구현할 수도 있다.
스타일의 문제일 뿐이므로, 코드의 명확성을 가장 크게 높일 수 있는 방법을 선택해야 한다.




## 함수

#### Sequence 함수

- `Sequence` 함수는 **필수적인 메서드만 정의해 포함하는 간단한 인터페이스를 만들고, 모든 부가 함수를 확장으로 정의하라**는 코틀린 철학이 됐다.

## 참고

- 멤버 함수는 타입의 핵심을 반영한다.
  - 그 함수가 없이는 그 타입을 상상할 수 없어야 한다.
  - 확장 함수는 타입의 존재에 필수적이지 않은 대상 타입을 지원하고 활용하기 위한 **외부** 연산이나 **편리**를 위한 연산이다.
  - 타입 내부에 외부 함수를 포함하면 타입을 이해하기가 더 여려워지지만, 일부 함수를 확장 함수로 정의하면 대상 타입을 깔끔하고 단순하게 유지할 수 있다.

#### 코틀린의 상속 철학(?)

 - 코틀린은 우리가 상속을 사용하지 않을 것이라고 가정한다.
 - 코틀린은 `open` 키워드를 통해 상속과 다형성을 의도적으로 허용하지 않는 한, 상속과 다형성의 사용을 적극적으로 막는다.
 - 이런 특성은 코틀린이 나아갈 방향에 대한 통찰을 제공한다.

> 함수만으로 충분한 경우가 많다.
> 가끔은 객체가 매우 유용하다. 
> 객체는 다양한 도구 중 하나일 뿐이며, 모든 경우에 적용할 수 있는 도구는 아니다.

구체적인 특정 상황에서 상속을 어떻게 사용할지 심사숙고하는 중이라면, 진짜 상속이 필요한지 여부를 고려하고 **상속보다는 확장 함수와 합성을 택하라**는 격언을 적용하자.