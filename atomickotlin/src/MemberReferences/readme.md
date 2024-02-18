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



## 함수

- 없음.

## 참고

- 없음.

