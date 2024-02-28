# 아토믹 코틀린 At 55. 인터페이스

## 용어

#### 인터페이스
- **인터페이스(interface)**는 `타입(type)`이라는 개념을 기술한다.
- **인터페이스**는 그 인터페이스를 구현하는 모든 클래스의 `프로토타입(prototype)`이다.
- **인터페이스**는 **클래스가 무엇을 하는지는 기술**하지만, **그 일을 어떻게 하는지는 기술하지 않는다.**
- **인터페이스**는 (클래스의) **형태를 제시**하지만, **일반적으로 구현은 포함되지 않는다.**
- **인터페이스**는 **객체의 동작을 지정**하지만, **그 동작을 어떻게 수행하는지에 대한 세부 사항은 제시하지 않는다.**
- **인터페이스**는 **존재의 목표나 임무를 기술**하며, 클래스는 세부적인 구현 사항을 포함한다.

한 사전에는 **인터페이스**를 `독립적이고 관련이 없는 여러 시스템이 만나서 서로 작용하거나 의사소통하는 장소`라고 정의한다. 
즉 **인터페이스**는 **시스템의 여러 부품이 서로 의사소통하는 수단**이다.

**애플리케이션 프로그래밍 인터페이스(Application Programming Interface, API)**는 **여러 소프트웨어의 구성 요소 사이에 명확히 정의된 통신 경로의 집합**이다.
**객체 지향 프로그래밍에서 객체의 API는 그 객체가 다른 객체와 상호 작용할 때 사용하는 공개 멤버의 집합**이다.

특정 인터페이스를 사용하는 코드는 그 인터페이스에서 호출할 수 있는 함수가 무엇인지에 대한 정보만 알고 있다.
인터페이스는 클래스 사이의 **프로토콜(protocol)(절차)를 확립**한다(일부 객체 지향 언어에서는 protocol이라는 키워드로 이 개념을 표현하기도 한다).

## 예약어 및 코틀린 개념
 
#### 인터페이스  - interface

인터페이스는 class 키워드 대신 **interface**키워드를 사용해 만든다.
어떤 **인터페이스를 구현하는 클래스를 정의**하려면 `클래스 이름 뒤에 콜론(:)과 인터페이스 이름`을 넣으면 된다.

```kotlin
package sec05.interfaces

import atomictest.eq

interface Computer {
    fun prompt(): String
    fun calculateAnswer(): Int
}

class Desktop : Computer {
    override fun prompt() = "Hello!"
    override fun calculateAnswer() = 11
}

class DeepThought : Computer {
    override fun prompt() = "Thinking..."
    override fun calculateAnswer() = 42
}

class Quantum : Computer {
    override fun prompt() = "Probably..."
    override fun calculateAnswer() = -1
}

fun main() {
    val computers = listOf(
        Desktop(), DeepThought(), Quantum()
    )
    computers.map { it.calculateAnswer() } eq
            "[11, 42, -1]"
    computers.map { it.prompt() } eq
            "[Hello!, Thinking..., Probably...]"
}
```

위의 코드에서 `Computer`는 `prompt()`와 `calculateAnswer()`를 **선언(declare)**하지만 아무 구현도 제공하지 않는다.
**이 인터페이스를 구현하는 클래스**는 **인터페이스가 선언한 모든 멤버를 구현하는 본문을 제공해야 한다.**
이를 통해 **각 함수가 구체적(concrete)인 함수(또는 구상 함수라고도 한다)**가 된다.
`main()`에서 인터페이스를 다르게 구현한 클래스가 함수 정의를 통해 서로 다른 동작을 표현하고 있음을 볼 수 있다.
**인터페이스 멤버를 구현*할 때는 반드시 `override`변경자를 붙여야 한다. `override`는 코틀린에게 일부러, **인터페이스(또는 기반 클래스)에 있는 이름과 똑같은 이름을 의도적으로 사용한다는 사실**, 
즐 **실수로 함수를 오버라이드하지 않는다는 점을 알려준다.**

**인터페이스가 프로퍼티를 선언**할 수도 있다.
**이런 인터페이스를 구현하는 클래스**는 **항상 프로퍼티를 오버라이드**해야 한다.

```kotlin
package sec05.interfaces
import atomictest.eq

interface Player {
    val symbol: Char
}

class Food : Player {
    override val symbol = '.'
}

class Robot : Player {
    override val symbol get() = 'R'
}

class Wall(override val symbol: Char) : Player

fun main() {
    listOf(Food(), Robot(), Wall('|')).map {
        it.symbol
    } eq "[., R, |]"
}
```

각 하위 클래스는 `symbol` 프로퍼티를 다른 방법으로 오버라이드 한다.

- Food : symbol 값을 직접 다른 값으로 바꾼다.
- Robot : 값을 반환하는 커스텀 게터를 사용한다.
- Wall : 생성자 인자 목록에서 `symbol`을 오버라이드한다.

**이넘도 인터페이스를 구현**할 수 있다.

```kotlin
package sec05.interfaces
import atomictest.*

interface Hotness {
    fun feedback() : String
}

enum class SpiceLevel : Hotness {
    Wild {
        override fun feedback() =
            "It adds flavor!"
    },
    Medium {
        override fun feedback() =
            "Is it warm in here?"
    },
    Hot {
        override fun feedback() =
            "I'm suddenly sweating a lot."
    },
    Flaming {
        override fun feedback() =
            "I'm in pain. I am suffering."
    }
}

fun main() {
    SpiceLevel.values().map { it.feedback() } eq
            "[It adds flavor!, " +
            "Is it warm in here?, " +
            "I'm suddenly sweating a lot., " +
            "I'm in pain. I am suffering.]"
}

```

컴파일러는 각각의 이넘 원소들이 `feedback()`의 **구현을 제공하는지 확인**한다.


#### SAM 변환
**단일 추상 메서드(Single Abstract Method, SAM)** 인터페이스는 자바 개념으로, 자바에서 멤버 함수를 '메서드'라고 부른다.
**코틀린**에는 **SAM 인터페이스를 정의**하는 `fun interface`라는 특별한 문법이 있다.

```kotlin
package sec05.interfaces

fun interface ZeroArg {
    fun f() :  Int
}

fun interface OneArg {
    fun g(n: Int): Int
}

fun interface TwoArg {
    fun h(i: Int, j: Int): Int
}
```

`fun interface`라고 쓰면 컴파일러는 그 안에 **멤버 함수가 하나만 들어있는지 확인**한다.
`SAM 인터페이스`를 일반적인 번거로운 방식**(클래스를 통해)으로 구현**할 수도 있고, **람다를 넘기는 방식으로 구현**할 수도 있다.
람다를 사용하는 경우를 `SAM 변환(SAM conversion)`이라고 한다.
`SAM 변환`을 쓰면 **람다가 인터페이스의 유일한 메서드를 구현하는 함수**가 된다.
다음은 앞에서 본 인터페이스를 두 가지 방법을 모두 사용해 구현한 코드다.

```kotlin
package sec05.interfaces

import atomictest.eq

class VerboseZero : ZeroArg {
    override fun f() = 11
}


val verboseZero = VerboseZero()

val samZero = ZeroArg { 11 }

class VerboseOne : OneArg {
    override fun g(n: Int) = n + 47
}

val verboseOne = VerboseOne()

val samOne = OneArg { it + 47 }

class VerboseTwo : TwoArg {
    override fun h(i: Int, j: Int) = i + j
}

val verboseTwo = VerboseTwo()

val samTwo = TwoArg{ i, j -> i + j }

fun main() {
    verboseZero.f() eq 11
    samZero.f() eq 11
    verboseOne.g(92) eq 139
    samOne.g(92) eq 139
    verboseTwo.h(11, 47) eq 58
    samTwo.h(11, 47) eq 58
}
```

**번거로운 구현**과 **SAM 구현**을 비교해보면, 자주 쓰이는 구문을 **SAM 변환을 사용해 더 간결한 구문으로 작성할 수 있다는 점**을 알 수 있다.
게다가 객체를 한 번만 쓰는 경우, 겨우 **한 객체를 만들기 위해 클래스를 억지로 정의할 필요가 없어진다.**
**람다를 SAM 인터페이스가 필요한 곳에 넘길 수도 있다.**
- 이때 굳이 먼저 객체로 람다를 둘럴쌀 필요도 없다.

```kotlin
package sec05.interfaces

import atomictest.trace

fun interface Action {
    fun act()
}

fun delayAction(action: Action) {
    trace("Delaying...")
    action.act()
}

fun main() {
    delayAction { trace("Hey!") }
    trace eq "Delaying... Hey!"
}
```

`main()`에서는 `Action` **인터페이스를 구현하는 객체 대신에 람다를 바로 전달**한다.
코틀린은 람다로부터 자동으로 `Action` 객체를 만들어준다.

## 함수
- 
- 없음.

## 참고

- 없음.

