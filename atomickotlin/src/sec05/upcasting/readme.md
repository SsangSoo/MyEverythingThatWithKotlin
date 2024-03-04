# 아토믹 코틀린 At 61. 업캐스트

## 용어

#### 업캐스트(upcast)

- 객체 참조를 받아서 그 객체의 기반 타입에 대한 참조처럼 취급하는 것을 **업캐스트(upcast)**라고 한다.
 
## 예약어 및 코틀린 개념

```kotlin
package sec05.upcasting
import atomictest.*

interface Shape {
    fun draw(): String
    fun erase(): String
}

class Circle : Shape {
    override fun draw() = "Circle.draw"
    override fun erase() = "Circle.erase"
}

class Square : Shape {
    override fun draw() = "Square.draw"
    override fun erase() = "Square.erase"
    fun color() = "Square.color"
}

class Triangle : Shape {
    override fun draw() = "Triangle.draw"
    override fun erase() = "Triangle.erase"
    fun rotate() = "Triangle.rotate"
}

fun show(shape: Shape) {
    trace("Show : ${shape.draw()}")
}

fun main() {
    listOf(Circle(), Square(), Triangle())
        .forEach(::show)
    trace eq
"""
Show : Circle.draw
Show : Square.draw
Show : Triangle.draw
"""
}
```

- `show()`의 파라미터는 기반 클래스인 Shape 이므로, `show()`는 이 세가지 타입을 모두 허용한다.
- 각 타입은 모두 기반 Shape 클래스의 객체처럼 취급된다.
  - 이를 일컬어 구체적인 타입이 기반 타입으로 **업캐스트**됐다고 말한다.


<div align="left">
  <img src="https://velog.velcdn.com/images/tjdtn4484/post/cb157858-630f-4276-8b2a-74411f37d35f/image.png">
</div>

위와 같은 상속 계층 구조를 그릴 때는 보통 기반 클래스를 위에 둔다.

- 구체적인 타입을 더 일반적인 타입으로 다루는 게 상속의 전부다.
- 상속 메커니즘은 오직 기반 타입으로 업캐스트한다는 목적을 달성하기 위해 존재한다.
- 실제로 업캐스트를 사용하지 않는데 상속을 사용하는 거의 모든 경우는 상속을 잘못 사용하는 것이다.

```kotlin
package sec05.upcasting
import atomictest.*

fun trim(shape : Shape) {
    trace(shape.draw())
    trace(shape.erase())
    // 컴파일되지 않는다.
    // shape.color() // 1
    // shape.rotate() // 2
}

fun main() {
    trim(Square())
    trim(Triangle())
    trace eq
"""
Square.draw
Square.erase
Triangle.draw
Triangle.erase
"""
}
```

- `Square` 인스턴스를 `Shape`로 업캐스트했기 때문에 // 1에서 `color()`를 호출할 수 없다.
- 마찬가지로 `Triangle` 인스턴스를 `Shape`로 업캐스트했기 때문에 // 2에서는 `rotate()`를 호출할 수 없다.
- `trim()` 안에서 사용할 수 있는 멤버 함수는 모든 Shape에 공통으로 들어있는 멤버들, 즉 기반 타입 Shape에 정의된 멤버들뿐이다.
- `Shape`의 하위 타입 값을 직접 일반적인 `Shape` 타입 변수에 대입해도 마찬가지 현상이 일어난다.

- **어떤 멤버에 접근할 수 있는지는 변수에 지정된 타입이 결정한다.**
- **업캐스트를 한 다음에는 기반 타입의 멤버만 호출할 수 있다.**

## 함수

- 없음.

## 참고

- 업캐스트는 이전부터 상속 계층에 대해 표현할 때 기반 클래스를 위, 파생 클래스를 아래로 이야기했기 때문에 나온 용어다.
- 코틀린은 단일 상속 계층 내 여러 클래스에서 코드를 재사용할 수 있는 방식으로만 상속을 사용하게 한다.
- **치환 가능성**은 **리스코프 치환 원칙(Liskov Substitution Principle)**이라고도 부르는데, 업캐스트를 한 다음에는 파생 타입이 정확히 기반 타입과 똑같이(그 이상도 그 이하도 아닌) 취급될 수 있다고 말한다.
  - 이 말은 업캐스트가 파생 클래스에 추가된 멤버 함수를 '잘라버리는' 효과가 있다는 뜻이다.
- 파생 클래스에 추가된 멤버는 여전히 존재하지만, 기반 클래스의 인터페이스에 속해 있지 않는다.