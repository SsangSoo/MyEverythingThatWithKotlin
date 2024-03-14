# 아토믹 코틀린 At 66. 다운 캐스트

## 용어

- 없음.
 
## 예약어 및 코틀린 개념

### 다운 캐스트
- **다운캐스트는 이전에 업캐스트했던 객체의 구체적인 타입을 발견한다.**
- 기반 클래스가 파생 클래스보다 더 큰 인터페이스를 가질 수 없으므로 업캐스트는 항상 안전하다.
- 모든 기반 클래스 멤버가 존재한다고 보장할 수 있으며 멤버를 호출해도 안전하다.
- 다운캐스트는 실행 시점에 일어나며 **실행 시점 타입 식별**(Run-Time Type Identification, RTTI)이라고 한다.

### 스마트 캐스트
- 코틀린의 스마트 캐스트는 **자동 다운캐스트**다.
- `is`키워드는 **어떤 객체가 특정 타입인지 검사**한다.
- 이 검사 영역 안에서는 해당 객체를 검사에 성공한 타입이라고 간주한다.

```kotlin
package sec05.downcasting


fun main() {
    val b1: Base = Derived1() // 업캐스트
    if(b1 is Derived1)
        b1.g() // 'is' 검사의 영역 내부
    val b2: Base = Derived2() // 업캐스트
    if(b2 is Derived2)
        b2.h() // 'is' 검사의 영역 내부
}
```

- b1이 Derived1 타입이면 `g()`를 호출할 수 있다. b2가 Derived2 타입이면 `h()`를 호출할 수 있다.
- 스마트 캐스트는 `is`를 통해 `when`의 인자가 어떤 타입인지 검색하는 `when`식 내부에서 아주 유용하다.


```kotlin
package sec05.downcasting

import atomictest.eq

interface Creature

class Human : Creature {
    fun greeting() = "I'm Human"
}

class Dog : Creature {
    fun bark() = "Yip!"
}

class Alien : Creature {
    fun mobility() = "Three legs"
}

fun what(c: Creature): String =
    when(c) {
        is Human -> c.greeting()
        is Dog -> c.bark()
        is Alien -> c.mobility()
        else -> "Something else"
    }

fun main() {
    val c: Creature = Human()
    what(c) eq "I'm Human"
    what(Dog()) eq "Yip!"
    what(Alien()) eq "Three legs"
    class Who : Creature
    what(Who()) eq "Something else"
}
```

- main()에서 Human을 Creature에 대입할 때와 Dog, Alien, Who 각각을 `what()`에 넘길 때 업캐스트가 일어났다.
- 전통적으로 클래스 계층을 그릴 때 기반 클래스를 맨 위에 두고, 파생 클래스를 그 아래로 위치시킨다.
  - `what()`은 이미 업캐스트된 Creature를 받아서 정확한 타입을 찾는다. 그리고 Creature 객체를 상속 계층에서 정확한 타입, 더 일반적인 기반 클래스에서 더 구체적인 파생 클래스로 다운 캐스트한다.
- 값을 만들어내는 `when`식에서는 나머지 가능성을 모두 잡아내기 위해 `else`가지가 필요하다.


### 변경 가능한 참조
- **자동 다운캐스트는 특히 대상이 상수여야만 제대로 작동**한다.
- 대상 객체를 가리키는 기반 클래스 타입의 참조가 변경 가능(var)하다면 타입을 검증한 시점과 다운캐스트한 객체에 대해 어떤(구체적 타입의 멤버인) 함수를 호출한 시점 사이에 참조가 가리키는 객체가 바뀔 가능성이 있따.
  - 이 말은 타입 검사와 사용 지점 사이에 객체의 구체적인 타입이 달라질 수 있다는 뜻이다.

```kotlin
package sec05.downcasting

class SmartCast1(val c: Creature) {
    fun contact() {
        when(c) {
            is Human -> c.greeting()
            is Dog -> c.bark()
            is Alien -> c.mobility()
        }
    }
}

class SmartCast2(var c: Creature) {
    fun contact() {
        when (c) {      // SmartCast1과 같이 하면 오류 발생
            is Human -> c.greeting()
            is Dog -> c.bark()
            is Alien -> c.mobility()
        }
    }
}
// 오류 1. Smart cast to 'Human' is impossible, because 'c' is a mutable property that could have been changed by this time
// 오류 2. Smart cast to 'Dog' is impossible, because 'c' is a mutable property that could have been changed by this time
// 오류 3. Smart cast to 'Alien' is impossible, because 'c' is a mutable property that could have been changed by this time
```

- SmartCast2는 var로 c를 받고 있는데, when의 괄호 식을 제거하고 SmartCast1 처럼 c만 넣으면 "c가 이 지점에서 변경했을 가능성이 있으므로 스마트 캐스트가 불가능하다는 오류가 발생한다.
  - 오류 메세지에서 이야기하는 값의 변화는 보통 **동시성(concurrency)**을 통해 일어난다.
  - 동시성이 사용되는 코드에서는 여러 독립적인 작업이 예측할 수 없는 시간에 c를 바꿀 수 있다.
- 복잡한 식도 해당 식이 재계산될 수 있으면 스마트 캐스트가 되지 않는다. 
  - 상속을 위해 open된 프로퍼티도 하위 클래스에서 오버라이드를 할 수 있고, 그로 인해 프로퍼티에 접근할 때마다 항상 같은 값을 내놓는다고 보장할 수 없으므로 스마트 캐스트가 되지 않는다.

### as 키워드
- `as`키워드는 일반적인 타입을 구체적인 타입으로 강제 변환한다.

```kotlin
package sec05.downcasting

import atomictest.*

fun dogBarkUnsafe(c: Creature) =
    (c as Dog).bark()

fun dogBarkUnsafe2(c: Creature): String {
    c as Dog
    c.bark()
    return c.bark() + c.bark()
}

fun main() {
    dogBarkUnsafe(Dog()) eq "Yip!"
    dogBarkUnsafe2(Dog()) eq "Yip!Yip!"
    (capture {
        dogBarkUnsafe(Human())
    }) contains listOf("ClassCastException")
}
```

- `dogBarkUnsafe2()`는 `as`의 다른 형태를 보여준다.
- `c as Dog`이라고 쓴 이후 영역에서는 c를 Dog처럼 취급할 수 있다.

- `as`가 실패하면 `ClassCastException`을 던진다. 
- 일반 `as`를 **안전하지 않은 캐스트**라고 부른다.
  - **안전한 캐스트**인 `as?`는 실패해도 예외를 던지지 않는 대신 `null`을 반환한다. 
    - 나중에 `NullPointerException`을 방지하려면 뭔가 적절한 조치를 취해야 한다.
      - 보통은 엘비스 연산자가 가장 간단하고 직접적인 처리 방법이다.

### 리스트 원소의 타입 알아내기

- 술어에서 `is`를 사용하면 `List`나 다른 **이터러블(iterable)(**이터레이션을 할 수 있는 대상 타입**)의 원소가 주어진 타입의 객체인지 알 수 있다.

## 함수

#### filterIsInstance()
- 지정한 타입에 속하는 모든 원소를 돌려준다.
- `filter()`와 `is`를 사용한 경우와 같은 결과를 내놓지만 가독성이 더 좋다.
  - 다만 결과 타입은 다르다.

```kotlin
package sec05.downcasting

import atomictest.eq

fun main() {
    val humans1: List<Creature> =
        group.filter { it is Human }
    humans1.size eq 2
    val humans2: List<Human> =
        group.filterIsInstance<Human>()
    humans2 eq humans1
}
```

- `filter()`는 (반환값의 모든 원소가 Human임에도 불구하고) Creature의 List를 내놓는 반면, `filterIsInstance()`는 대상 타입인 Human의 리스트를 반환하다.

**참고**
  - 옮긴이의 말에 따르면 `mapNotNull{it as? Dog}`으로도 같은 결과를 얻을 수 있다. 
  - is와 스마트캐스트를 같이 쓰는 것과 `as?`를 쓰고 null을 걸러내는 것이 비슷한 효과를 낼 수 있다는 점을 기억하면 코드를 작성할 때 여러 가지 선택지가 생긴다.

## 참고

- 컴파일러는 하위 타입에 추가된 함수 중에 어떤 함수를 호출해도 안전한지를 결정할 수 없다.

