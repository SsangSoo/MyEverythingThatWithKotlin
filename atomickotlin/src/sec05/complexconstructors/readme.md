# 아토믹 코틀린 At 56. 복잡한 생성자

## 용어

- 없음.
 
## 예약어 및 코틀린 개념

#### 생성자
- `생성자`는 **새 객체를 만드는 특별한 함수**다.

```kotlin
import atomictest.eq

class Alien(val name: String)

fun main() {
    val alien = Alien("Pencilvester")
    alien.name  eq "Pencilvester"
}
```

위 경우에는 생성자 코드를 쓰지 않았으며, **코틀린이 생성자 코드를 만들어줬다.**
생성 과정을 좀 더 제어하고 싶다면 **클래스 본문에 생성자 코드를 추가**하라.
객체 생성 중에 `init` 블록 안의 코드가 실행된다.

```kotlin
package sec05.complexconstructors

import atomictest.eq

private var counter = 0

class Message(text : String) {
    private val content: String
    init {
        counter += 10
        content = "[$counter] $text"
    }
    override fun toString() = content
}

fun main() {
    val m1 = Message("Big ba-ba boom!")
    m1 eq "[10] Big ba-ba boom!"
    val m2 = Message("Bzzzzt!")
    m2 eq "[20] Bzzzzt!"

}
```

- **생성자 파라미터에 var나 val이 붙어 있지 않더라도 init 블록에서 사용할 수 있다.**
- content는 val로 정의되어 있지만 **정의 시점체 초기화시키지 않았다.** 
  - 이런 경우 **코틀린은 생성자 안의 어느 지점에서 한 번(그리고 오직 한 번만) 초기화가 일어나도록 보장**한다.
  - content에 **값을 다시 할당하거나** content를 **초기화하는 일을 잊어버리면 오류가 발생**한다.
- **생성자**는 (클래스 본문에 들어오기 전에 초기화된)**생성자 파라미터 목록과 init 블록(또는 블록들)을 합친 것**이며, 이들은 **객체를 생성하는 동안 실행**된다. 
- 코틀린에서는 `init`절을 **여럿 정의할 수 있으며**, 각 **init** 블록은 **클래스 본문에 정의된 순서대로 실행**된다.


## 함수

- 없음.

## 참고

**크고 복잡한 클래스에서 `init`을 여기저기 분산시키면, `init` 블록을 하나만 쓰는 데 익숙한 프로그래머들에게 유지보수 문제를 야기할 수 있다.**
