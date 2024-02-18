# 아토믹 코틀린 At 38. 안전한 호출과 엘비스 연산자

## 용어

- 없음.

## 예약어 및 코틀린 개념

#### 안전한 호출(Safe call)

코틀린은 널 가능성을 편리하게 처리할 수 있도록 여러 연산자를 제공한다.
널이 될 수 있는 타입에는 여러 제약이 가해진다.
널이 될 수 있는 타입의 참조를 단순히 역참조할 수 없다.

```kotlin
fun main() {
    val s: String? = null
    // 컴파일되지 않는다.
//     s.length // Only safe (?.) or non-null asserted (!!.) calls are allowed on a nullable receiver of type String?
}
```

`s.length`의 주석을 해체하면 컴파일 오류가 발생한다.
(String? 타입의 널이 될 수 있는 수신 객체애는 안전한(?.)호출이나 널 아닌 단언(!!.) 호출만 사용할 수 있다는 뜻이다. 널 아닌 단언(!!.)은 추후 아톰에서 소개한다.)

**안전한 호출(Safe call)**은 **일반 호출에 사용하는 점(.)을 물음표와 점(?.)으로 바꾼 것**이다(단 물음표와 점 사이에 공백이 있으면 안 된다).
안전한 호출을 사용하면 **널이 될 수 있는 타입의 멤버에 접근하면서 아무 예외(구체적으로는 NPE)도 발생하지 않게 해준다.**
안전한 호출은 **수신 객체가 null이 아닐 때만 연산을 수행**한다.

~~(와우... NPE를 안 보게 해준다니..)~~

```kotlin
fun main() {

    ...

    val s2: String? = null
    s2?.echo()                  // 

    ...
}
```

위와 같이 s2의 수신 객체가 null이라면 아무 일도 하지 않는다.
**안전한 호출을 사용하면 깔끔하게 결과를 얻을 수 있다.**

```kotlin
fun checkLength(s: String?, expected: Int?) {
    val length1 =
        if (s != null) s.length else null   // 1
    // val length1 = s?.length // 위와 같음. // 아래 식과 같은 형태
    val length2 = s?.length                 // 2
}

fun main() {
    checkLength("abc",3)
    checkLength(null,null)
}
```

1번 라인과 2번 라인은 같은 효과를 낸다.
**수신 객체가 null이 아니면 일반적인 접근(s.length)를 수행**한다.
**수신 객체가 null이면 두 코드 모두 s.length 호출을 수행(만약 이 연산을 수행하면 예외가 발생할 것이다.)하지 않고 식의 결과로 null을 내놓는다.**


수신 객체가 null일 때 `?.`의 결과로 null을 만들어내는 것 이상의 일이 필요하다면 이 때는 **엘비스(Elvis) 연산자**가 대안을 제공한다.

#### 엘비스(Elvis) 연산자

엘비스(Elvis) 연산자는 물음표 뒤에 `콜론을 붙인(?:) 연산자`다.
(물음표와 콜론 사이에 공백이 있으면 안 된다.)

'엘비스'라는 이름은 미국 가수 엘비스 프레슬리(Elvis Presley)의 이모티콘과 비슷하기 때문에 붙은 이름이다.
엘비스라는 이름은 'else-if(영어 발음을 연음 시켜 그대로 적으면 '엘시프'라서 '엘비스'와 비슷함)'라는 용어를 응용한 언어 유희이기도 하다.

`?:`의 왼쪽 식의 값이 null이 아니면 왼쪽 식의 값이 전체 엘비스 식의 결괏값이 된다.
왼쪽 식이 null이면 `?:`의 오른쪽 식의 값이 전체 결괏값이 된다.

```kotlin
import atomictest.eq

fun main() {
    val s1: String? = "abc"
    (s1 ?: "---") eq "abc"
    val s2: String? = null
    (s2 ?: "---") eq "---"
}
```

- s1은 null이 아니므로 엘비스 연산자가 "abc"를 결과로 내놓는다.
- s2는 null이므로 엘비스 연산자가 "---"라는 대안값을 내놓는다.

**보통은 다음 예처럼 엘비스 연산자를 안전한 호출 다음에 사용**한다.
**안전한 호출이 null 수신 객체에 대해 만들어내는 null 대신 디폴트 값을 제공하기 위해서다.**

```kotlin
fun checkLength(s: String?, expected: Int) {
    val length1 =
        if(s != null) s.length else 0   // 1
    val length2 = s?.length ?: 0        // 2
    length1 eq expected
    length2 eq expected
}

fun main() {
    checkLength("abc",3)
    checkLength(null, 0)
}
```

호출을 연쇄(프로퍼티를 타고타는 형태)시키는 중간에 null이 결과로 나올 수 있는데, 최종 결과에만 관심이 있는 경우 안전한 호출을 사용하면 여러 호출을 간결하게 연쇄시킬 수 있다.
안전한 호출을 사용해 여러 멤버(프로퍼티)에 대한 접근을 연쇄시키는 경우(자바로 설명하면 객체에 객체를 타고 또 타고 들어가는 형태) 중간에 어느 하나라도 null을 내놓으면 전체 결과가 null이 된다.

다음 예시 코드를 보자.

```kotlin
import atomictest.eq

class Person(
    val name: String,
    var friend: Person? = null
)

fun main() {
    val alice = Person("Alice")
    alice.friend?.friend?.name eq null
    val bob = Person("Bob")
    val charlie = Person("Charlie", bob)
    bob.friend = charlie
    bob.friend?.friend?.name eq "Bob"
    (alice.friend?.friend?.name
        ?: "Unknown") eq "Unknown"
}

// 결과
//null
//Bob
//Unknown

```


## 함수

- 없음.

## 참고

- 상당수의 프로그래밍 언어가 코틀린 엘비스 연산자와 같은 역할을 하는 **널 복합 연산자(null coalescing operator)를 제공한다.
- **eq** : 예제 코드에서 보여주는 eq는 아토믹 코틀린 본책에서 제공하는 테스트 메서드 같은 것이다. 값을 비교하여 같으면 출력해주고, 다르면 다르다는 의미를 출력해준다.
