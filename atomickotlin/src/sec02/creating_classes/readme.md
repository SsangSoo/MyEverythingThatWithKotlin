# 아토믹 코틀린 At 17. 클래스 만들기


## 용어

- 없음.

## 예약어 및 코틀린 개념

#### class
- 새로운 유형의 객체를 만들 때 사용
- 클래스 이름의 **첫 번째 글자는 대문자로 표기**하며, val이나 var로 쓰이는 이름의 첫 번째 글자는 소문자로 표기한다.

```kotlin
// Animals.kt
// class들을 정의한다.
class Giraffe
class Bear
class Hippo

fun main() {
    // 객체를 만든다.
    val g1 = Giraffe()
    val g2 = Giraffe()
    val b = Bear()
    val h = Hippo()
    println(g1) // CreatingClasses.Giraffe@312b1dae
    println(g2) // CreatingClasses.Giraffe@7530d0a
    println(h) // CreatingClasses.Hippo@27bc2616
    println(b)  // CreatingClasses.Bear@3941a79c
}
```

- `@`의 앞은 클래스 이름이고,
- `@`의 뒤는 해당 객체가 메모리의 어느 위치에 있는지 보여준다.
  - 문자가 섞여 있지만, 실제로는 숫자다.
  - 16진 표기법으로 표현되있다.

- 위의 예제에서는 다음과 같이 클래스를 최대한으로 단순화한 것이다.

더 복잡한 클래스를 정의할 때는 중괄호({})를 사용하여 클래스 본문(class body)을 정의한다.

```kotlin
class Giraffe
class Bear
class Hippo
```



#### 인스턴스
- 정의한 클래스에 속하는 객체
  - 위의 예시에선, g1, g2, b, h 총 4 개의 인스턴스를 생성했다.
- 프로그램이 생성한 객체(인스턴스)는 모두 자신만의 고유한 주소를 부여받는다.

#### 멤버 함수
- 클래스 본문(class body) 안에 정의된 함수를 의미한다.
  - 자바에서는 메서드라고 하지만, **코틀린 설계자들은 메소드라는 용어를 채택하지 않고**, 코틀린의 함수적인 특성을 강조하기 위해 **함수**라는 표현을 채택했다.
- 호출할 때는 `객체이름.함수이름(파라미터 목록)`처럼 호출한다.
- 어떤 클래스에 속한 특정 **인스턴스에 대해 작용**한다.
- 멤버 함수 호출시 코틀린은 참조(reference : 객체를 가리킴)를 함수에 전달해서 관심사의 대상이 되는 객체를 추적한다.

#### this
- 멤버 함수 안에서는 this라는 이름으로 이 참조(생성된 객체를 가리)에 접근할 수 있다.
- 멤버 함수는 클래스에 속한 다른 요소들을 특별한 방법, 즉 객체를 지정하지 않고 **멤버 이름만 사용하는 방법으로 접근할 수 있다.**

다음과 같은 예시를 볼 수  있다.

```kotlin
class Hamster {
    fun speak() = "Squeak! "
    fun exsercise() =
        this.speak() +      // this로 한정
                speak() +   // this 없이 호출
                "Running on wheel"
}

fun main() {
    val hamster = Hamster()
    println(hamster.exsercise())
}
```

- this를 반드시 적어야 하거나 this를 적는 스타일을 권장하는 다른 언어를 사용했던 프로그래머는 이런 방식으로 코드를 작성하곤 한다.
- 어떤 특성이 불필요한데도 사용한다면, 코드를 읽는 사람은 "왜 코드를 이런 식으로 작성했는지" 괜히 한번 고민하느라 시간을 보내거나 헷갈릴 수 있다.
- 불필요한 this는 사용하지 않는 것이 좋다.

#### 최상위(top-level) 함수
- 클래스에 속하지 않은 함수

## 함수

- 없음.
