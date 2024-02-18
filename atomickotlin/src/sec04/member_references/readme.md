# 아토믹 코틀린 At 47. 멤버 참조

## 용어

- 없음.

## 예약어 및 코틀린 개념

#### 멤버 참조

함수 인자로 멤버 참조를 전달할 수 있다.
함수, 프로퍼티, 생성자에 대해 만들 수 있는 **멤버 참조(member reference)**는 해당 함수, 프로퍼티, 생성자를 호출하는 뻔한 람다를 대신할 수 있다.

멤버 함수나 프로퍼티 이름 앞에 그들이 속한 클래스 이름과 2중 콜론(`::`)을 위치시켜서 멤버 참조를 만들 수 있다.
다음 코드에서는 `Message::isRead`가 멤버 참조다.

```kotlin
import atomictest.eq

data class Message(
    val sender: String,
    val text: String,
    val isRead: Boolean
)

fun main() {
    val message = listOf(
        Message("Kitty", "Hey!", true),
        Message("Kitty", "Where are you?", false))
    val unread =
        message.filterNot(Message::isRead)
    unread.size eq 1
    unread.single().text eq "Where are you?"
}
```

읽지 않은 메세지를 거르려면 술어를 받는 라이브러리 함수 `filterNot()`을 사용해야 한다.
이 경우 필요한 술어는 메시지를 이미 읽었는지 판단하는 술어이며, 람다를 넘길 수도 있지만 `Message::isRead`라는 프로퍼티 참조를 넘겼다.

**객체의 기본적인 대소 비교를 따르지 않도록 정렬 순서를 지정해야 할 때는 프로퍼티 참조가 유용**하다.

#### 함수 참조
List에 읽지 않은 메시지뿐 아니라 중요한 메시지가 들어 있는지도 검사하고 싶다고 가정하자.
이런 경우 람다를 별도의 함수로 추출하면 코드를 더 이해하기 쉽다.
**코틀린에서는 함수 타입이 필요한 곳에 함수를 바로 넘길 수 없지만, 그 대신 함수헤 대한 참조는 넘길 수 있다.**

```kotlin
import atomictest.eq

data class Message(
  val sender: String,
  val text: String,
  val isRead: Boolean,
  val attachments: List<Attachment>
)

data class Attachment (
  val type: String,
  val name: String
)

fun Message.isImportant(): Boolean =
  text.contains("Salary increase") ||
          attachments.any {
            it.type == "image" &&
                    it.name.contains("cat")
          }

fun main() {
  val messages = listOf(Message(
    "Boss", "Let's discuss goals " +
            "for next year", false,
    listOf(Attachment("image", "cute cats"))))
  messages.any(Message::isImportant) eq true
}
```

새 Message 클래스에 attachments라는 프로퍼티와 `Message.isImportant()`라는 확장 함수가 추가됐다.
`message.any()` 호출에서 이 확장 함수에 대한 참조를 전달한다.
**참조를 만들 수 있는 대상이 멤버 함수로만 제한되어 있지는 않다.**

Message를 **유일한 파라미터로 받는 최상위 수준 함수가 있으면 이 함수를 참조로 전달**할 수 있다.
최상위 수준 함수에 대한 참조를 만들 때는 클래스 이름이 없으므로 `::function`처럼 쓴다.

```kotlin
import atomictest.eq

fun ignore(message: Message) =
    !message.isImportant() &&
            message.sender in setOf("Boss", "Mom")

fun main() {
    val text = "Let's discuss goals " +
            "for the next year"
    val msgs = listOf(
        Message("Boss", text, false, listOf()),
        Message("Boss", text, false, listOf(
            Attachment("image", "cute cats"))))
    msgs.filter(::ignore).size eq 1
    msgs.filterNot(::ignore).size eq 1
}
```

#### 생성자 참조

클래스 이름을 사용해 생성자에 대한 참조를 만들 수도 있다.
다음 코드에서 `names.mapIndexed()`는 생성자 참조 `::Student`를 받는다.


```kotlin
import atomictest.eq

data class Student(
    val id: Int,
    val name: String
)

fun main() {
    val names = listOf("Alice", "Bob")
    val students =
        names.mapIndexed { index, name ->
            Student(index, name)
        }
    students eq listOf(Student(0, "Alice"),
        Student(1, "Bob"))
    names.mapIndexed(::Student) eq students
}
```

람다 아톰에서 소개한 `mapIndexe()` 함수는 names의 각 원소의 인덱스를 원소와 함께 람다에 전달해준다.
students 정의에서는 이 두 값(인덱스와 원소)을 명시적으로 생성자에 넘겼다.
하지만 `name.mapIndexed(::Student)`를 통해 똑같은 효과를 얻을 수 있다.
**따라서 함수와 생성자 참조를 사용하면 단순히 람다로 전달되기만 하는 긴 파라미터 리스트를 지정하는 수고를 하지 않아도 된다.**
따라서 람다를 사용할 때보다 더 가독성이 좋아진다.

#### 확장 함수 참조

```kotlin
import atomictest.eq

fun Int.times47() = times(47)

class Frog
fun Frog.speak() = "Ribbit!"

fun goInt(n: Int, g: (Int) -> Int) = g(n)

fun goFrog(frog: Frog, g: (Frog) -> String) =
    g(frog)

fun main() {
    goInt(12, Int::times47) eq 564
    goFrog(Frog(), Frog::speak) eq "Ribbit!"
}
```

`goInt()`에서 g는 Int를 인자로 받아 Int를 반환하는 함수다. 
`goFrog()`에서 g는 Frog를 인자로 받아 String을 반환한다.

## 함수

#### sortedWith()
**비교기(comparator)**를 사용해 리스트를 정렬한다.

- **비교기(comparator)**
  - 비교기(comparator)는 **두 원소를 비교하는 객체**다.

#### compaerBy()
- 파라미터로 주어진 술어 목록에 따라 비교기를 생성한다.
- 인자를 하나만 넘기고 `sortedWith()`에 전달하면 `sortedBy()`와 같은 결과를 얻을 수 있다.

## 참고

- 없음.

