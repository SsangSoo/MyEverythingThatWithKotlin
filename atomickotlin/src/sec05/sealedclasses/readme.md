# 아토믹 코틀린 At 67. 봉인된 클래스

> 클래스 계층을 제한하려면 상위 클래스를 sealed로 선언하라!

```kotlin
package sec05.sealedclasses

import atomictest.eq

open class Transport

data class Train(
    val line: String
): Transport()

data class Bus(
    val number: String,
    val capacity: Int
): Transport()

fun travel(transport: Transport) =
    when(transport) {
        is Train ->
            "Train ${transport.line}"

        is Bus ->
            "Bus ${transport.number}: " +
                    "size ${transport.capacity}"

        else -> "$transport is in limbo!"
    }

fun main() {
    listOf(Train("S1"), Bus("11", 90))
        .map(::travel) eq
            "[Train S1, Bus 11: size 90]"
}
```

Train과 Bus는 Transport 유형에 따라 다른 세부 사항을 저장한다.

`travel()`에는 정확한 `transport` 타입을 찾아내는 `when` 식이 있다.
Transport 클래스에 다른 하위 타입이 있을 수도 있으므로 코틀린은 else 가지를 디폴트로 요구한다.

`travel()`은 다운캐스트가 근본적인 문제가 될 수 있는 부분이라는 점을 알려준다.
Transport를 상속한 Tram이라는 클래스를 새로 정의했다고 가정했을 때 `travel()`이 여전히 컴파일되고 실행도 되기 때문에 Tram 추가에 맞춰 `travel()`의 `when`을 바꿔야 한다는 아무런 단서가 없다.

코드에서 다운캐스트가 여기저기 흩어져 있다면 이런 변경으로 인해 유지 보수가 힘들어진다.
이 상황은 `sealed` 키워드를 사용해 개선할 수 있다.

## 용어 

위의 경우와 같이 `sealed` 키워드로 **상속을 제한한 클래스**를 **봉인된 클래스**라고 부른다.



## 예약어 및 코틀린 개념

#### 봉인된 클래스


```kotlin
package sec05.sealedclasses

import atomictest.eq

sealed class Transport

data class Train(
    val line: String
) : Transport()


data class Bus(
    val number: String,
    val capacity: Int
) : Transport()


fun travel(transport: Transport) =
    when(transport) {
        is Train ->
            "Train ${transport.line}"
        is Bus ->
            "Bus ${transport.number}: " +
                    "size ${transport.capacity}"
    }

fun main() {
    listOf(Train("S1"), Bus("11", 90))
        .map(::travel) eq
            "[Train S1, Bus 11: size 90]"

}
```

-  위의 코드와 달리 else문이 없다.
- seale 클래스를 직접 상속한 하위 클래스는 반드시 기반 클래스와 같은 패키지와 모듈 안에 있어야 한다.
- 코틀린은 `when`식이 모든 경우를 검사하도록 강제하지만 `travel()`의 `when`은 더 이상 `else` 가지를 요구하지 않는다.
- `Transport`의 하위 클래스가 존재할 수 없다는 사실을 확인할 수 있기 때문이다.
- `when`문 가능한 모든 경우를 다 처리하므로 `else` 가지가 필요 없다.

- `sealed` 계층을 도입하면 새 하위 클래스를 선언할 때 오류를 발견한다.
    - 새 하위 클래스를 도입하면 기존 타입 계층을 사용하던 모든 코드를 손봐야 한다.
- `Sealed` 클래스는 Tram 같은 하위 클래스를 도입했을 때 변경해야 하는 모든 지점을 표시해준다.
- `sealed` 키워드는 다운캐스트를 더 쓸만하게 만들어준다.
    - 하지만 다운캐스트를 과도하게 사용하는 설계를 본다면 여전히 미심쩍게 생각해야 한다.
    - 보통은 다형성을 써서 코드를 더 깔끔하게 잘 작성할 수 있는 방법이 있다.

#### sealed와 abstract 비교

```kotlin
package sec05.sealedclasses

abstract class Abstract(val av: String) {
    open fun concreteFunction() {}
    open val concreteProperty = ""
    abstract fun abstractFunction(): String
    abstract val abstractProperty: String
    init {}
    constructor(c: Char) : this(c.toString())
}

open class Concrete() : Abstract("") {
    override fun concreteFunction() {}
    override val concreteProperty = ""
    override fun abstractFunction() = ""
    override val abstractProperty = ""
}

sealed class Sealed(val av: String) {
    open fun concreteFunction() {}
    open val concreteProperty = ""
    abstract fun abstractFunction(): String
    abstract val abstractProperty: String
    init {}
    constructor(c: Char) : this(c.toString())
}

open class SealedSubclass() : Sealed("") {
    override fun concreteFunction() {}
    override val concreteProperty = ""
    override fun abstractFunction() = ""
    override val abstractProperty = ""
}

fun main() {
    Concrete()
    SealedSubclass()
}
```

- `sealed`클래스는 기본적으로 하위 클래스가 모두 같은 파일 안에 정의되어야 한다는 제약이 가해진 `abstract`클래스다.
- `sealed`클래스의 간접적인 하위 클래스를 별도의 파일에 저장할 수 있다.

```kotlin
package sec05.sealedclasses

class ThirdLevel : SealedSubclass()
```

- `sealed interface`도 유용하다.
- 코틀린에 정의한 `sealed interface`를 자바 쪽에서 구현할 가능성이 있어 `sealed interface`를 금지했지만, 자바 15에 `sealed interface`가 도입되면서 JVM 수준에서 `sealed interface`를 지원하게 됐으므로 코틀린 1.5부터 `sealed interface`를 **허용**한다.


#### 하위 클래스 열거하기

- 어떤 클래스가 `sealed`인 경우 모든 하위 클래스를 쉽게 이터레이션할 수 있다.

```kotlin
package sec05.sealedclasses
import atomictest.eq

sealed class Top
class Middle1 : Top()
class Middle2 : Top()
open class Middle3 : Top()
class Bottom3 : Middle3()

fun main() {
    Top::class.sealedSubclasses.map { it.simpleName } eq "[Middle1, Middle2, Middle3]"
}
```
- 참고로 제대로 실행안되면 인텔리제의 경고에 따라 reflection api를 다운받으면 된다.

- 클래스를 생성하면 **클래스 객체**가 생성된다.
- 이 클래스 객체의 프로퍼티와 멤버 함수에 접근해서 클래스에 대한 정보를 얻고, 클래스에 속한 객체를 생성하거나 조작할 수 있따.
- `sealedSubclasses`는 이런 봉인된 클래스의 모든 하위 클래스를 돌려준다. 이때 Top의 직접적인 하위 클래스만 결과에 들어있다는 점에 유의하라.
- `sealedSubclasses`는 리플렉션(reflection)을 사용한다.
  - `kotlin-reflection.jar`라는 의존 관계가 클래스 경로(classpath)에 있어야 리플렉션을 쓸 수 있다.
  - 리플렉션은 클래스를 동적으로 찾아내고 찾아낸 클래스의 특성을 동적으로 사용하는 방법을 제공한다.
- `sealedSubclass`는 하위 클래스를 실행 시점에 찾아내므로 시스템의 성능에 영향을 끼칠 수 있다.


## 함수

- 없음.

## 참고

#### "seale 클래스를 직접 상속한 하위 클래스는 반드시 기반 클래스와 같은 패키지와 모듈 안에 있어야 한다."에 대한 옮긴이의 말
원래는 기반 클래스의 내부 클래스로만 정의할 수 있었지만 곧 같은 파일 안에 정의되어야 한다고 제약이 완화됬고, 코틀린 1.5부터는 같은 모듈 안, 같은 패키지 안에 정의하도록 더 완화됐다.
코틀린에서 모듈이란 한 번에 같이 컴파일되는 모든 파일을 묶어서 부르는 개념이다.
코틀린 컴파일러가 `sealed`클래스를 상속한 하위 클래스 정의를 보면 상위 클래스가 현재 컴파일 중인 모듈의 같은 패키지 안에 정의되어 있는지 찾아보고 그렇지 않은 경우 오류를 표시해줌으로써, 다른 모듈 안에 들어 있는 봉인된 상위 클래스를 상속하지 못하게 한다.