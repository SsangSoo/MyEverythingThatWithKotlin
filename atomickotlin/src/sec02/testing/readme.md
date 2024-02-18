# 아토믹 코틀린 At 22. 테스트

## 용어

- 없음.

## 예약어 및 코틀린 개념

#### 중위 표기법(infix notation)
- `a.함수(b)` 호출을 `a 함수 b` 처럼 쓸 수 있게 해주는 기능이다.
- 다음과 같이 사용한다.

```kotlin
import Appendices.AtomicTest.Examples.*

fun main() {
    val v1 = 11
    val v2 = "Ontology"
    // 'eq'는 같다는 뜻
    v1 eq 11
    v2 eq "Ontology"
    // 'neq'는 같지 않다는 뜻
    v2 neq "Epistimology"
    // [Error] Epistimology != Ontology
    // v2 eq "Epistimology"
}
```


## 함수

- 없음.

## 참고

- **프로그램을 빠르게 개발하려면 지속적인 테스트가 필수**다
- **테스트는 중요한 개발 습관**이다.
- 코드의 올바름을 검증할 때 `println()`을 사용하는 것은 부실한 접근 방법.

#### 실전에서 쓰는 테스트 시스템
실전에서 쓰는 테스트 시스템은 다음과 같은 것이 있다.

- **JUnit** : 자바에서 가장 널리 쓰이는 **테스트 프레임워크**이며, 코틀린에서도 유용하게 쓸 수 있다.
- **코테스트(Kotest)** : 코틀린 전용으로 설계됐으며, 코틀린 언어의 여러 긴능을 살려서 작성됐다.
- **스펙(Spek)** 프레임워크 : **명세 테스트(specification test)**라는 다른 형태의 테스트를 제공한다.
- 콘솔에 출력을 내보내고 사람이 눈으로 결과가 이상한지 확인하는 방식은 신뢰성이 떨어진다.


#### 프로그램의 일부분인 테스트
- 테스트는 소프트웨어 개발 과정에 포함되어 있어야 가장 효과적이다.
- 테스트를 작성하면 원하는 결과를 확실히 얻을 수 있다..
  - 코드를 구현하기 전에 테스트를 작성하는 걸 권장하는 사람도 많다.
  - 코드를 작성하기 전에 테스트를 먼저 작성해 실패시킨 후, 나중에 테스트를 통과하도록 코드를 작성하는 기법을 **테스트 주도 개발(Test Driven Development, TDD)**이라고 한다.
- 테스트를 할 수 있게 코드를 작성하면 코드를 작성하는 방식이 달라진다는 이점이 있다.
  - 테스트를 염두에 둔다면 '이 결과를 어떻게 테스트하지?'라고 스스로에게 묻게 된다.
  - 함수를 만들 때 테스트 외의 다른 이유가 없더라도 테스트를 위해 함수가 무언가를 반환하도록 한다.

#### TDD방식 예제

`아톰 9 수타입`에서 살펴본 BMI 계산 예제를 구현한 간단한 TDD 예제다.

```kotlin
import Appendices.AtomicTest.Examples.*


fun main() {
    calculateBMI(160, 68) eq "Normal weight"
//    calculateBMI(100, 68) eq "Underweight"
//    calculateBMI(200, 68) eq "Overweight"

}

fun calculateBMI(weight: Int, height: Int) = "Normal weight"
```

주석을 해체하면 실패를 한다. 처음에는 실패한다.
다음으로는 몸무게가 어느 범주에 속하는지 검사하는 코드를 추가한다.
(이제 모든 테스트가 실패한다.)

```kotlin

fun main() {
    // 모든 테스트가 실패함.
    calculateBMI(160, 68) eq "Normal weight"
    calculateBMI(100, 68) eq "Underweight"
    calculateBMI(200, 68) eq "Overweight"
}


fun calculateBMI(
    weight: Int,
    height: Int
) : String {
    val bmi = weight  / (height * height) * 703.07
    return if (bmi < 18.5) "Underweight"
    else if(bmi < 25) "Normal weight"
    else "Overweight"
}
```

위의 주석을 해제하면, 테스트가 실패하며, 결과는 다음과 같다.

```kotlin

Underweight
[Error]: Underweight != Normal weight
Underweight
Underweight
[Error]: Underweight != Overweight
```


또한 Double이 아니라, Int를 사용했기 때문에 bmi를 계산한 결과는 항상 0이다.
**calculateBMI**를 해체하고 실행한 경우 표시되는 테스트 결과를 보면 무엇을 수정할지 알 수 있다.

다음 테스트를 통과하는 코드다.

```kotlin
fun main() {
    calculateBMI(160.0, 68.0) eq "Normal weight"
    calculateBMI(100.0, 68.0) eq "Underweight"
    calculateBMI(200.0, 68.0) eq "Overweight"
}

fun calculateBMI(
    weight: Double,
    height: Double
) : String {
    val bmi = weight  / (height * height) * 703.07
    return if (bmi < 18.5) "Underweight"
    else if(bmi < 25) "Normal weight"
    else "Overweight"
}
```
