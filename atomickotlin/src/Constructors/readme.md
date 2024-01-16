# 아토믹 코틀린 At 19. 생성자

## 용어

- 없음.


## 예약어 및 코틀린 개념

#### 생성자
- 새 객체를 초기화하는 특별한 멤버 함수와 비슷하다.
- 다음은 생성자의 가장 간단한 형태다.

```kotlin
class Wombat

fun main() {
  val wombat = Wombat()
}
```

위의 코드에서 `Wombat()`은 Wombat 객체를 생성한다.
코틀린에서는 자바와 다르게 `new` 키워드가 필요없다.
코틀린에서의 `new`는 불필요한 중복이다.

다음은 **파라미터를 받는 생성자**다.

```kotlin
class Alien(name: String) {
    val greeting = "Poor $name!"
}
```

위와 같은 생성자에는 String 타입의 name으로 받는 인자가 필요하다. 
하지만, 다음과 같은 코드로 생성자를 호출하면 에러가 발생한다.

```kotlin
val alien = Alien() // No value passed for parameter 'name'
```

또한 아래와 같이 파라미터 이름인 name에 접근하면 에러가 발생한다.

```kotlin
class Alien(name: String) {
    val greeting = "Poor $name!"
}

fun main() {
    val alien = Alien("Mr. Meeseeks")
//    alien.name // Error // Unresolved reference: name
}
```

만약 클래스 본문 밖에서 생성자 파라미터에 접근할 수 있으려면 파라미터 목록에 var나 val을 지정해야한다.
~~(이렇게 쓸 일이 있는지는 모르겠다.)~~

```kotlin
class MutableNameAlien(var name: String)

class FixedNameAlien(val name: String)

fun main() {
    val alien1 = 
        MutableNameAlien("Reverse Giraffe")
    val alien2 =
        FixedNameAlien("Krombopolis Michael")
    alien1.name = "Parasite" // var이라서 가능
//    alien2.name = "Parasite" // Val cannot be reassigned
}
```

- 생성자 파라미터 목록에 있는 name을 var나 val로 정의하면 해당 식별자가 프로퍼티로 바뀐다.
- 그리고 생성자 밖에서도 이 식별자에 접근할 수 있다.



## 함수

#### println()
- 문자열 대신 객체를 전달받은 경우 객체의 toString()을 호출한 결과를 출력한다.(자바와 똑같다.)
- 클래스에 직접 toString을 정의하지 않으면 디폴트 toString()이 호출된다.

```kotlin
class Scientist(val name: String) {
    override fun toString(): String {
        return "Scientist('$name')"
    }
}

fun main() {
    val zeep = Scientist("Zeep Xanflorp")
    println(zeep)
}
```

#### override
- 오버라이드 키워드
- 기능은 자바와 같이 상속받은 클래스의 메서드를 정의하는 클래스에 맞게 함수를 재정의하는 것이다.(자바와 똑같음)
- 이 키워드를 명시함으로써 코드의 의도를 더 명확히 할 수 있다.