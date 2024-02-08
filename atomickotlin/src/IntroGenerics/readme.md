# 아토믹 코틀린 At 41. 제네릭스 소개

## 용어

#### 제네릭스

- '제네릭스(generics)'는 명사, '제네릭(generic)'은 형용사다. 
  - '제네릭(generic)'이라는 용어는 **여러 가지 클래스에 적합한/여러 가지 클래스와 관계 있는'이란 뜻이다. 
- 파라미터화한 타입을 사용하는 기법 전체를 가리킬 때는 '제네리스'라는 용어를 쓰고, 파라미터화한 타입이나 클래스, 함수 등에 대해 따로따로 이야기할 때는 '제네틱 타입'처럼 '제네릭'으로 대상을 수식한다.

#### 제네릭 플레이스홀더(generice placeholder)
- placeholder는 텍스트에서 다른 어떤 것을 대신해 자리를 차지하고 있는 존재를 뜻한다.
- 이 존재를 나중에 구체적인 존재로 바꿀 수 있다. 
  - 예를 들어 문서 양식에서 신청자 이름을 대신해 표시된 '홍길동' 같은 부분이 플레이스홀더다.

#### 유니버셜 타입(univeral type)
- 유니버셜 타입(univeral type)은 모든 타입의 부모 타입이다.
  - 코틀린에서는 `Any`가 유니버셜 타입이다.
  - `Any`라는 이름의 뜻대로 `Any`타입은 모든 타입의 인자를 허용한다.

## 예약어 및 코틀린 개념

#### 제네릭스

- 제네릭스는 파라미터화한 타입을 만든다.
- 파라미터화한 타입은 여러 타입에 대해 작동할 수 있는 컴포넌트다. 

프로그래밍 언어에서 제너릭스의 원래 의도는 클래스나 함수를 작성할 때 타입 제약을 느슨하게 해서 프로그래머에게 표현력을 최대로 제공하는 것이다.
**제네릭스 도입을 촉발한 중요한 동기** 중 하나는 이 책의 예제에 사용한 **List, Set, Map 같은 컬렉션 클래스를 만드는 것**이었다.
**컬렉션은 다른 객체를 저장하는 객체**다. 여러 프로그램에서 같은 타입의 객체로 이뤄진 그룹을 저장하고 사용해야 하므로, **컬렉션은 라이브러리 중 가장 재사용성이 좋은 클래스**다.

객체를 하나만 담는 클래스를 살펴보자. 이 클래스는 저장할 원소의 정확한 타입을 지정한다.

```kotlin
data class Automobile(val brand: String)

class RigidHolder(private val a: Automobile) {
    fun getValue() = a
}


fun main() {
    val holder = RigidHolder(Automobile("BMW"))
    holder.getValue() eq "Automobile(brand=BMW)"
}
```


`RigidHolder`는 그다지 재사용성이 좋지 않다. 
이 객체는 `Automobile`밖에 담을 수 없다.
이보다는 여러 다른 타입에 대해 각 타입에 맞는 새로운 타입의 보관소 클래스를 만들 수 있으면 좋을 것이다.
이를 위해 Automobile 대신 **타입 파라미터**를 사용한다.

제너릭 타이입을 정의하려면 클래스 이름 뒤에, 내부에 하나 이상의 `제네릭 플레이스홀더(generic placeholder)`가 들어 있는 부등호(`<>`)를 추가한다.

예제 코드에서 `T`라는 플레이스홀더는 지금은 알 수 없는 어떤 타입을 대신하며, 제네릭 클래스 안에서는 일반 타입처럼 쓰인다.

```kotlin
import atomictest.eq

class GenericHolder<T>(                         // 1
    private val value: T
) {
    fun getValue(): T = value;
}

fun main() {
    val h1 = GenericHolder(Automobile("Ford"))
    val a: Automobile = h1.getValue()       // 2
    a eq "Automobile(brand=Ford)"

    val h2 = GenericHolder(1)
    val i: Int = h2.getValue()              // 3
    i eq 1

    val h3 = GenericHolder("Chartreuse")
    val s: String = h3.getValue()           // 4
    s eq "Chartreuse"
}
```


- // 1 : GenericHolder는 T 타입의 객체를 저장하며, 멤버 함수 `getValue()`는 T 타입의 값을 반환한다.
- // 2,3,4에서 `getValue()`를 호출할 때 결과 타입이 자동으로 올바른 타입으로 지정된다.

이 문제를 **유니버셜 타입(univeral type)**으로 해결할 수도 있을 것 같다.
(코틀린에서 유니버셜 타입은 Any다.)

어떤 함수에 여러 타입의 값을 넘겨야 하는데, 각 타입 사이에 공통점이 없다면 `Any`가 문제를 해결해준다.

위의 코드를 T 대신 다음과 같이 Any를 써도 될 것 같다.

```kotlin
import atomictest.eq

class AnyHolder(private val value: Any) {
    fun getValue(): Any = value
}

class Dog {
    fun bark() = "Ruff!"
}

fun main() {
    val holder = AnyHolder(Dog())
    val any = holder.getValue()
    // 컴파일 되지 않음.
    // any.bark()

    val genericHolder = GenericHolder(Dog())
    val dog = genericHolder.getValue()
    dog.bark() eq "Ruff!"
}
```

간단한 경우에는 Any가 작동하지만, 구체적인 타입이 필요해지면(예를 들어 Dog의 `bark()`를 호출) 제대로 작동하지 않는다.
객체를 Any 타입으로 대입하면서 객체 타입이 구체 타입(예 Dog)이라는 사실을 더 이상 추적할 수 없기 때문이다. 구체적인 타입을 Any로 전달하기 때문에 결과는 그냥 Any이고, 
Any는 구체타입의 메서드(예 `bark()`)를 제공하지 않는다.

여기서 제네릭스를 사용하면 실제 컬렉션에 구체 타입을 담고 있다는 정보를 유지할 수 있다.
즐 `getValue()`가 돌려주는 값에 대해 구체적인 타입의 메서드를 적용할 수 있다는 뜻이다.

#### 제네릭 함수
제네릭 함수를 정의하려면 부등호로 둘러싼 제네릭 타입 파라미터를 함수 이름 앞에 붙이다.

```kotlin
import atomictest.eq

fun <T> identity(arg: T): T = arg

fun main() {
    identity("Yellow") eq "Yellow"
    identity(1) eq 1
    val d: Dog = identity(Dog())
    d.bark() eq "Ruff!"
}
```


`identity()`가 T 타입의 값을 반환하는 제네릭 함수이므로 d는 Dog 타입이다.

코틀린 표준 라이브러리는 컬렉션을 위한 여러 제네릭 함수를 제공한다.
제네릭 확장 함수를 쓰려면 수신 객체 앞에 제네릭 명세(괄호로 둘러싼 타입 파라미터 목록)을 위치시켜야 한다.
다음 예시 코드에서 `first()`나 `firstOrNull()` 정의를 살펴보자.

```kotlin
import atomictest.eq

fun <T> List<T>.first(): T{
    if(isEmpty())
        throw NoSuchElementException("Empty List")
    return this[0]
}

fun <T> List<T>.firstOrNull(): T? =
    if(isEmpty()) null else this[0]

fun main() {
  listOf(1, 2, 3).first() eq 1
    //    val i: Int =                       // 1
  val i: Int? =                       // 1
    listOf(1, 2, 3).firstOrNull()
  i eq 1
    //    val s: String =                    // 2
  val s: String? =                    // 2
    listOf<String>().firstOrNull()
  s eq null
}
```

`first()`와 `firstOrNull()`은 모든 List에 대해 작동할 수 있다.
T 타입의 값을 반환하기 위해서는 제네릭 함수로 이 두 함수를 정의해야만 한다.

`firstOrNull()`에서 어떻게 반환 타입을 널이 될 수 있는 타입으로 명시했는지 보면 다음과 같다.

```kotlin
fun <T> List<T>.firstOrNull(): T? =         // T?
```

- // 1 : **List<Int>**에 대해 `firstOrUll()`을 호출하면 `Int?`가 반환되는 모습을 보여준다.
- // 2 : **List<String>**에 대해 같은 함수를 호출해서 `String?`을 받는다.

코틀린은 `// 1` 과 `// 2`에서 모두 식별자 타입에 `?`를 요구한다. 
반면 `?`를 제거하면 다음과 같은 오류가 발생한다.

- // 1 : `Type mismatch: inferred type is Int? but Int was expected`
- // 2 : `Type mismatch: inferred type is String? but String was expected`
- "널이 될수 있는 타입이 와야하는데, 널이 될 수 없는 타입을 기대하고 있다"고 한다.


## 함수

- 없음.

## 참고

- 없음.