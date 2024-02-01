# 아토믹 코틀린 At 35. 데이터 클래스

## 용어

- 없음.

## 예약어 및 코틀린 개념

#### 데이터 클래스(data class)
- 데이터 저장만 담당하는 클래스가 필요하면 data 클래스를 사용해 코드 양을 줄이면서 여러 가지 공통 작업을 편하게 수행할 수 있따.
- data 클래스를 정의할 때는 `data`라는 키워드를 사용한다.
- data 키워드는 몇 가지 기능을 클래스에 추가하라고 코틀린에게 지시한다. 
- 데이터 클래스는 모든 생성자 파라미터를 var나 val로 선언해야 한다.

```kotlin
package DataClasses

import atomictest.eq

data class Simple(
    val arg1: String,
    val arg2: Int
)


fun main() {
    val s1 = Simple("Hi", 29)
    val s2 = Simple("Hi", 29)
    s1 eq "Simple(arg1=Hi, arg2=29)" // 1
    s1 eq s2
}
```

위 예제를 보면 data 클래스의 두 가지 특징을 보여준다.

- data 클래스에 의해 만들어진 문자열은 파라미커 이름과 객체에 담기 데이터 내용이 표시된다.
- `toString()` 코드를 추가로 작성하지 않아도 data 클래스는 객체를 더 읽기 쉽고 보기 좋은 형식으로 표현해준다.
- 같은 데이터를 포함(모든 프로퍼티의 값이 같음)하는 같음 data 클래스 인스턴스를 두 개 만들면, 두 인스턴스가 동등(`==` 연산이true를 반환)하다고 기대할 것이다.
  - 일반적인 클래스에서 이런 동작을 구현하려면 인스턴스를 비교하는 `equals()`라는 특별한 멤버 함수를 정의해야 한다.
  - data 클래스에서는 `equals()`가 자동으로 생성되며, 이 `equals()` 함수는 생성자 파라미터에 열거된 모든 프로퍼티가 같은지 검사하는 식으로 구현된다.

- 모든 data 클래스에 생성되는 또 다른 유용한 함수로 `copy()`가 있다. (밑에서 소개)


#### HashMap과 HashSet
- data 클래스를 만들면 객체를 HashMap이나 HashSet에 넣을 때 키로 사용할 수 있는 **해시 함수**를 자동으로 생성해준다.
- HashMap이나 HashSet에서는 `hashCode()`를 `equals()`와 함께 사용해 Key를 빠르게 검색한다.

## 함수

#### copy()
- `copy()`는 현재 객체의 모든 데이터를 포함하는 새 객체를 생성해준다.
- `copy()`를 사용한 새 객체를 생성할 때 몇몇 값을 새로 지정할 수 있다.

```kotlin
package DataClasses

import atomictest.eq

data class DetailedContact(
    val name: String,
    val surname: String,
    val number: String,
    val address: String
)

fun main() {
    val contact = DetailedContact(
        "Miffy",
        "Miller",
        "1-234-567890",
        "1600 Amphitheatre Parkway")
    val newContact = contact.copy(
        number = "098-765-4321",
        address = "Brandschenkestrasse 110")
    newContact eq DetailedContact(
        "Miffy",
        "Miller",
        "098-765-4321",
        "Brandschenkestrasse 110")
}
```

- `copy()`의 **파라미터 이름은 생성자 파라미터의 이름과 같다.** 
- 모든 인자에는 각 프로퍼티의 현재 값이 디폴트 인자로 지정되어 있다.
  - 변경하고 싶은 인자만 (이름 붙은 인자로) 지정하면 된다.

## 참고

- 없음.