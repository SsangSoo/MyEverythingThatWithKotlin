# 아토믹 코틀린 At 65. 클래스 위임

## 용어

- 없음.
 
## 예약어 및 코틀린 개념

#### 클래스 위임

- 합성과 상속은 모두 새 클래스 안에 하위 객체를 심는다. 
  - 합성에서는 하위 객체가 명시적으로 존재하고, 상속에서는 암시적으로 존재한다.
- 합성은 내포된 객체의 기능을 사용하지만 인터페이스를 노출하지는 않는다.
  - 클래스가 기존의 구현을 재사용하면서 동시에 인터페이스를 구현해야 하는 경우, 상속과 **클래스 위임a(class delegation)**이라는 두 가지 선택지가 있다.

- **클래스 위임**은 상속과 합성의 중간 지점이다.
  - 합성과 마찬가지로 새 클래스 안에 멤버 객체를 심고, 상속과 마찬가지로 심겨진 하위 객체의 인터페이스를 노출시킨다.
  - 새 클래스를 하위 객체의 타입으로 업캐스트할 수 있다.
  - **코드를 재사용**하기 위해 **클래스 위임**은 **합성을 상속만큼 강력하게 만들어준다.**

```kotlin
package sec05.classdelegation

interface Controls {
    fun up(velocity: Int): String
    fun down(velocity: Int): String
    fun left(velocity: Int): String
    fun right(velocity: Int): String
    fun forward(velocity: Int): String
    fun back(velocity: Int): String
    fun turboBoost(): String
}

class SpaceShipControls : Controls {
    override fun up(velocity: Int) =
        "up $velocity"
    override fun down(velocity: Int) =
        "down $velocity"
    override fun left(velocity: Int) =
        "left $velocity"
    override fun right(velocity: Int) =
        "right $velocity"
    override fun forward(velocity: Int) =
        "forward $velocity"
    override fun back(velocity: Int) =
        "back $velocity"
    override fun turboBoost() = "turbo boost"

}
```

`Controls`의 멤버 함수를 노출하려면 `SpaceShipControls`의 인스턴스를 프로퍼티로 하고 `Controls`의 모든 멤버 함수를 명시적으로 `SpaceShipControls` 인스턴스에 위임해야 한다.
아래는 그에 대한 코드다.

```kotlin
package sec05.classdelegation
    import atomictest.eq

class ExplicitControls : Controls {
    private val controls = SpaceShipControls()
    // 수동으로 위임 구현하기
    override fun up(velocity: Int) =
        controls.up(velocity)
    override fun back(velocity: Int) =
        controls.back(velocity)
    override fun down(velocity: Int) =
        controls.down(velocity)
    override fun forward(velocity: Int) =
        controls.forward(velocity)
    override fun left(velocity: Int) =
        controls.left(velocity)
    override fun right(velocity: Int) =
        controls.right(velocity)
    // 변형한 구현
    override fun turboBoost(): String =
        controls.turboBoost() + "... booooooost!"
}

fun main() {
    val controls = ExplicitControls()
    controls.forward(100) eq "forward 100"
    controls.turboBoost() eq
            "turbo boost... booooooost!"
}
```

- 모든 함수를 내부에 있는 `Controls` 객체에 바로 전달했다.
  - 이 클래스가 제공하는 인터페이스는 일반적인 상속에서 사용해야 하는 인터페이스와 동일하다. 
  - `turboBoost()`처럼 일부 변경한 구현을 제공할 수도 있다.

코틀린은 이런 **클래스 위임** 과정을 자동화해준다. 
따라서 `ExplicitDelegation.kt`처럼 직접 함수 구현을 작성하는 대신, 위임에 사용할 객체를 지정하기만 하면 된다.

클래스를 위임하려면 `by` 키워드를 인터페이스 이름 뒤에 넣고, `by` 뒤에 위임할 멤버 프로퍼티의 이름을 넣는다.

```kotlin
package sec05.classdelegation

interface AI
class A : AI

class B(val a: A) : AI by a

```

이 코드는 **클래스 B는 AI 인터페이스를 a 멤버 객체를 사용해(by) 구현한다**라고 읽는다.
인터페이스에만 위임을 적용할 수 있다.
따라서 `A() by a`라고 `by` 앞에 클래스 이름을 쓸 수는 없다.
위임 객체(a)는 생성자 인자로 지정한 프로퍼티여야 한다.

- 위임을 하면 별도로 코드를 작성하지 않아도 멤버 객체의 함수를 외부 객체를 통해 접근할 수 있다.

- 코틀린은 다중 클래스 상속을 허용하지 않지만, 클래스 위임을 사용해 다중 클래스 상속을 흉내낼 수는 있다.
- 일반적으로 다중 상속은 전혀 다른 기능을 가진 여러 클래스를 하나로 묶기 위해 쓰인다.



## 함수

- 없음.

## 참고

#### 상속은 제약이 될 수 있다.
- 예를 들어 상위 클래스가 open이 아니거나, 새 클래스가 다른 클래스를 이미 상속하고 있는 경우에는 다른 클래스를 상속할 수 없다.
- 클래스 위임을 사용하면 이런 제약을 포함한 여러 제약을 피할 수 있다.

#### 클래스 위임을 조심히 사용하라.
- 상속, 합성, 클래스 위임이라는 세 가지 방법 중에 합성을 먼저 시도하라.
  - 합성은 가장 단순한 방법이며 대부분의 유스케이스(use case)를 해결해준다. 
  - 타입 계층과 이 계층에 속한 타입 사이의 관계가 필요할 때는 상속이 필요하다.
  - 이 두 가지 선택이 모두 적합하지 않을 때 위임을 쓸 수 있다.
