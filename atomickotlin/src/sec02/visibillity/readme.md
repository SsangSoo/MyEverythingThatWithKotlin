# 아토믹 코틀린 At 20. 가시성 제한하기

## 용어

#### 리팩터링(refactoring)

- 코드를 고쳐 써서 더 읽기 좋고 이해하기 쉽게, 그래서 더 유지 보수하기 쉽게 만드는 것

#### 접근 변경자(access modifier)

(제어자가 아니라, 변경자라고 표현한다. 접근 제어자도 영어로는 access modifier이다. 접근제어자라고 생각하면 된다.)

- 가시성을 제어하기 위한 목적으로 코틀린이나 다른 언어에서 제공하는 기능

#### 모듈(module)

- 코드 기반상에서 논리적으로 독립적인 각 부분을 뜻한다.
- inernal 정의는 그 정의가 포함된 모듈 내부에서만 접근할 수 있다.
  - internal 요소는 라이브러리 내부에서 사용 가능하지만, 라이브러리를 소비하는 쪽에서는 접근할 수 없다.
- 모듈은 고수준 개념이다.

## 예약어 및 코틀린 개념

#### 코틀린에서의 접근 변경자(access modifier)

변경자는 변경자가 붙어 있는 그 정의의 가시성만 제한할 수 있다.
변경자를 지정하지 않으면 자동으로 public이 된다. 그렇기 때문에 public 변경자는 기술적으로는 불필요한 중복이다.

#### private

- 클래스, 함수, 프로퍼티 정의 앞에 위치한다.
- private의 정의는 숨겨져 있으며, 같은 클래스에 속한 다른 멤버들만 이 정의에 접근할 수 있다.
- private의 정의를 변경하거나 삭제하더라도 클라이언트에게는 직접적인 영향이 없다.
- private이 붙은 클래스, 최상위 함수, 최상위 프로퍼티는 그 정의가 들어있는 파일 내부에서만 접근이 가능하다.(자바와 같음)
- 코틀린은 private 최상위 요소가 정의된 파일과 다른 파일에서 해당 요소에 접근하지 못하도록 막으며, 그 선언이 파일에 선언된 private 선언이라는 사실을 알려준다.

**private 우회**

다음과 같이 private으로 선언된 프로퍼티, 클래스, 함수가 있는 `RecordAnimals.kt`가 있다.

```kotlin
private var index = 0

private class Animal(val name: String)

private fun recordAnimal(
    animal: Animal
) {
    println("Animal #$index: ${animal.name}")
    index++
}

fun recordAnimals() {
    recordAnimal(Animal("Tiger"))
    recordAnimal(Animal("Antelope"))
}

fun recordAnimalsCount() {
    println("$index animals are here!")
}
```

이를 다른 kt 파일인 `ObserveAnimals.kt`에서 다음과 같이 접근할 수 있다.

```kotlin
fun main() {
    recordAnimals()         // private이 아님.
    recordAnimalsCount()    // private이 아님.
}
```

결과는 다음과 같다.

```kotlin
Animal #0: Tiger
Animal #1: Antelope
2 animals are here!
```

- 내부 구현을 노출시켜야 하는 경우를 제외하고는(이런 경우는 생각보다 훨씬 드물지만) 프로퍼티를 private으로 만들어라.

#### internal

- inernal 정의는 그 정의가 포함된 모듈 내부에서만 접근할 수 있다.
- private와 public 사이에 위치한다.
- 어떤 요소를 private으로 정의하자니 너무 제약이 심하다고 느껴지고, public으로 지정해 공개 API의 일부분으로 포함시키기는 애매한 경우 internal을 사용하면 된다.
- internal 요소는 라이브러리 내부에서 사용 가능하지만, 라이브러리를 소비하는 쪽에서는 접근할 수 없다.
- 단, 이 책의 예제나 연습 문제에서는 internal을 사용하지 않는다.

#### public

- public 정의는 모든 사람이 볼 수 있다.
  - 특히 컴포넌트를 사용하려면 클라이언트 프로그래머가 public wjddmlfmf qhf tn dlTEk.
- public 정의를 변경하려면 클라이언트 코드에 영향을 줄 수 있다.
- 변경자를 지정하지 않으면 정의는 자동으로 public이 된다.
  - 프로그래머는 경우에 따라 굳이 붙이지 않아도 되는 public을 중복으로 정의 앞에 붙이기도 한다.

#### 에일리어싱(aliasing)

- 한 객체에 대해 참조를 여러 개 유지하는 경우

## 함수

- 없음.

## 참고

#### 소프트웨어 설계시

- 소프트웨어 설계시 일차적으로 고려해야 할 내용은 다음과 같다.
  - **변화해야 하는 요소와 동일하게 유지되어야 하는 요소를 분리**

책에서 **프로그래머가 가져야 할 생각과 고민**을 지나가는 말로 해놓은 경우가 있다.
앞으로는 참고라는 항목에 그런 내용을 기록하겠다.
