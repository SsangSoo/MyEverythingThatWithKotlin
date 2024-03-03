# 아토믹 코틀린 At 60. 추상 클래스

## 용어

- 없음.
 
## 예약어 및 코틀린 개념

#### 추상 클래스(abstract class) 
- **추상 클래스는 하나 이상의 프로퍼티나 함수가 불완전하다는 점을 제외하면 일반 클래스와 같다.**
- **본문이 없는 함수 정의나 초깃값 대입을 하지 않는 프로퍼티 정의가 불완전한 정의**다.

- 클래스 멤버에서 본문이나 초기화를 제거하려면 `abstract` 변경자를 해당 멤버 앞에 붙여야 한다.
- `abstract`가 붙은 멤버가 있는 클래스에는 반드시 `abstract`를 붙여야 한다.

다음 코드에서 각 요소의 `abstract` 를 제거하면 `Abstract property 'x' in non-abstract class 'WithProperty'` 오류 메세지가 나온다.

```kotlin
package sec05.abstractClasses

abstract class WithProperty { // Abstract property 'x' in non-abstract class 'WithProperty'
    abstract val x: Int
}

abstract class WithFunctions {
    abstract fun f(): Int
    abstract fun g(n: Double)
}
```

- 코틀린에서는 초기화 코드가 없으면 해당 참조를 `abstract`로 선언애햐 한다.
  - 그 참조가 속한 클래스에도 `abstract` 변경자를 붙여야 한다.
  - 초기화 코드가 없으면 코틀린이 해당 참조의 타입을 추론할 방법이 없으므로 `abstract` 참조에는 반드시 타입을 지정해야 한다.

위의 코드에서 `WithFunctions` 같은 경우 두 함수 모두 정의를 제공하지는 않는다.
따라서 이 경우에도 두 함수 앞에 반드시 `abstract` 를 붙여야 하고, 클래스 앞에도 `abstract`를 붙여야만 한다.
(만약 위의 g()처럼 `abstract` 함수의 반환 타입을 적지 않으면 코틀린은 반환 타입을 Unit이라고 간주한다.)

- 추상 클래스를 상속하는 상속 관계를 따라가다 보면, 어딘가에 궁극적으로 추상 함수와 프로퍼티의 정의가 있는 (이를 '추상 멤버를 **구체화**(또는 **구상화**)한다'고 말한다.) 클래스가 존재해야 한다.

- 인터페이스에 정의된 함수나 프로퍼티는 모두 기본적으로 **추상 멤버**다.
  - 인터페이스는 추상 클래스와 비슷하다.
- 인터페이스에 함수나 프로퍼지 정의가 있을 때는 `abstract`가 불필요한 중복이므로 이를 생략할 수 있다.

아래의 두 인터페이스는 같다.

```kotlin
package sec05.abstract

interface Redundant {
    abstract val x: Int
    abstract fun f(): Int
    abstract fun g(n: Double)
}

interface Removed {
    val x: Int
    fun f(): Int
    fun g(n: Double)
}
```

`인터페이스`와 `추상 클래스`의 차이점은 **추상 클래스에는 상태가 있지만 인터페이스에는 상태가 없다는 점**이다.
상태는 프로퍼티 안에 저장된 데이터를 뜻한다.
- **인터페이스도 프로퍼티를 선언할 수 있지만, 데이터는 실제로 해당 인터페이스를 구현하는 클래스 안에서만 저장**될 수 있다.
  - 인터페이스 안에서 프로퍼티에 값을 저장하는 것은 금지되어 있다.

```kotlin
package sec05.abstractclasses

interface IntList {
    val name: String
    // 컴파일 되지 않는다.
    // val list = listOf(0) // Property initializers are not allowed in interfaces
}
```

- **인터페이스와 추상 클래스 모두 구현이 있는 함수를 포함**할 수 있다.
  - 이런 함수에서 다른 `abstract`멤버를 호출해도 된다.
- **인터페이스와 추상 클래스는 해당 타입의 객체가 생성되기 전에 모든 추상 프로퍼티와 함수가 구현되도록 보장한다.**
  - 그리고 객체를 생성하기 전에는 클래스의 멤버를 호출할 수 없다.

- 인터페이스가 함수 구현을 포함할 수 있으므로, 내부에 정의된 프로퍼티가 상태를 바꿀 수 없는 경우에는 인터페이스도 프로퍼티의 커스텀 게터를 포함할 수 있다. 

```kotlin
package sec05.abstract

import atomictest.eq

interface PropertyAccessor {
    val a: Int
        get() = 11
}

class Impl : PropertyAccessor

fun main() {
    Impl().a eq 11
}
```


- 여러 인터페이스를 상속하다 보면 시그니처(함수 이름과 파라미터 목록을 합친 정보)가 같은 함수를 둘 이상 동시에 상속할 때가 있다. 
  - 함수나 프로퍼티의 시그니처가 충돌하면 다음 크드의 C 클래스처럼 프로그래머가 직접 충돌을 해결해야 한다.

```kotlin
package sec05.abstract

import atomictest.eq

interface A {
    fun f() = 1
    fun g() = "A.g"
    val n: Double
        get() = 1.1
}

interface B {
    fun f() = 2
    fun g() = "B.g"
    val n: Double
        get() = 2.2
}

class C : A, B {
    override fun f() = 0
    override fun g() = super<A>.g()
    override val n: Double
        get() = super<A>.n + super<B>.n
}

fun main() {
    val c = C()
    c.f() eq 0
    c.g() eq "A.g"
    c.n eq 3.3
}
```

## 함수

- 없음.

## 참고

- 인터페이스는 추상 클래스와 비슷하지만, 인터페이스에는 추상 클래스와 달리 상태가 없다
- **정의**는 함수 본문이나 변수의 초깃값까지 포함하는 선언이며, **선언**은 본문과 초깃값이 없이 함수 시그니처와 반환 타입만 적거나 변수의 타입만 적는 경우를 뜻한다.
- **코틀린에서는 클래스가 오직 한 기반(부모, 상위) 클래스만 상속할 수 있다.**

- 자바는 다중 상속을 금지하는 대신 다중 인터페이스 상속은 허용한다. 코틀린도 그와 같다.
  - 아래는 그 예제다.


```kotlin
package sec05.abstract2

interface Animal
interface Mammal: Animal
interface AquaticAnimal: Animal

class Dolphin: Mammal, AquaticAnimal
```


- 코틀린은 식별자가 같은데 타입이 다른 식으로 충돌이 일어나는 경우를 허용하지 않으며, 이를 해결해주지도 않고 해결해줄 수도 없다.

