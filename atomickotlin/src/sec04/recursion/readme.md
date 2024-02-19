# 아토믹 코틀린 At 54. 재귀

## 용어

#### 재귀(recursion)
- 재귀는 함수 안에서 함수 자신을 호출하는 프로그래밍 기법이다.
- **재귀 함수는 이전 재귀 호출의 결과를 활용**한다.
  - **팩토리얼(factorial)(계승)이 일반적인 예**다.
    - `factorial(n)`은 1부터 n까지 모든 수를 곱한 값인데 다음과 같이 정의할 수 있다.
      - `factorial(1)`은 1이다.
      - `factorial(n)`은 `n * factorial(n-1)`이다.
    - `factorial()`이 재귀적인 이유는 자신에게 전달된 인자값을 변형한 값으로 자기 자신을 호출한 결과를사용하기 때문이다.
    - 다음은 `factorial()`을 재귀로 구현한 코드다.

```kotlin
import atomictest.eq

fun factorial(n: Long): Long {
    if(n <= 1) return 1
    return n * factorial(n - 1)
}

fun main() {
    factorial(5) eq 120
    factorial(17) eq 355687428096000
}
```

- 함수를 호출하면 함수와 인자에 대한 정보가 **호출 스택(call stack)**에 저장된다.
  - 예외가 던져져져서 **코틀린이 스택 트레이스(stack trace)를 표시하면 호출 스택의 모습을 볼 수 있다.**

```kotlin
fun illegalState() {
//     throw IllegalStateException()
}

fun fail() = illegalState()

fun main() {
    fail()
}
```

- 예외를 던지는 줄의 주석을 해체하면 콘솔에 다음과 같은 출력이 나온다.

```kotlin
Exception in thread "main" java.lang.IllegalStateException
	at sec04.recursion.CallStackKt.illegalState(CallStack.kt:4)
	at sec04.recursion.CallStackKt.fail(CallStack.kt:7)
	at sec04.recursion.CallStackKt.main(CallStack.kt:10)
	at sec04.recursion.CallStackKt.main(CallStack.kt)
```

- **스택 트레이스**는 예외가 던져진 순가의 호출 스택 상태를 보여준다.
  - 위의 코드의 경우 호출 스택은 세 가지 함수로 구성된다.

<div align="left">
  <img src="https://velog.velcdn.com/images/tjdtn4484/post/64eb2f7d-ccb2-4140-8678-049846bffcae/image.png">
</div>

- **재귀함수를 호출하면 매 재귀 호출이 호출 스택에 프레임을 추가**한다.
    - 이로 인해 `StackOverflowError`가 발생하기 쉽다.
    - **StackOverflowError**는 호출 스택을 너무 많이 써서 더 이상 스택에 쓸 수 있는 메모리가 없다는 뜻이다.


#### 꼬리 재귀(tail recursion)
- 꼬리 재귀는 일부 재귀 함수에 명시적으로 적용할 수 있는 최적화 방법이다.
- 호출 스택 넘침을 막기 위해 함수형 언어들은(코틀린도 포함) **꼬리 재귀**라는 기법을 사용한다.
- 꼬리 재귀의 목표는 호출 스택의 크기를 줄이는 것이다.
- 꼬리재귀는 `tailrec` 키워드를 사용해 만든다.
  - 이 키워드는 올바른 조건하에서 재귀 호출을 이터레이션으로 변환해 호출 스택 비용을 제거해준다.
- **꼬리 재귀**는 컴파일러가 수행하는 최적화이지만, 모든 재귀 함수에 적용할 수 있는 것은 아니다.
- `tailrec1`을 **성공적으로 사용하려면 재귀가 마지막 연산**이어야 한다.
  - 이 말은 재귀 함수가 자기 자신을 호출해 얻은 결괏값을 아무 연산도 적용하지 않고 즉시 반환해야 한다는 뜻이다.



#### 무한 재귀
- 프로그래머가 재귀 호출 연쇄를 제때 끝내지 않아서 **StackOverflowError**를 야기하는 경우가 자주 있는데, 이를 **무한 재귀**라고 한다.
- 무한 재귀는 항상 `StackOverflowError`로 끝난다.


#### 활성 레코드(activation record) (=스택 프레임)
- 함수를 호출할 때마다 스택에 쌓는 정보를 의미한다.
- 스택에 쌓이기 때문에 **스택 프레임**이라고도 한다.
- 함수가 반환될 때 돌아가야 할 주소(이 주소는 함수 호출 명령 바로 다음 명령의 주소다).
- 프레임 크기나 이전 프레임 포인터(함수마다 스택 프레임 크기가 달라서 함수 반환 시 스택에서 프레임을 제거할 때 필요함)가 필수로 들어간다.


## 예약어 및 코틀린 개념

#### tailrec
- 꼬리재귀를 만들 때 사용하는 키워드다..
  - 이 키워드는 올바른 조건하에서 재귀 호출을 이터레이션으로 변환해 호출 스택 비용을 제거해준다.
- `tailrec1`을 **성공적으로 사용하려면 재귀가 마지막 연산**이어야 한다.
- 다음은 `tailrec` 키워드를 함수의 fun 앞에 붙이면 볼 수 있는 오류다.
  - `A function is marked as tail-recursive but no tail calls are found` : 함수를 꼬리 재귀로 표시했지만 실제 함수 본문에은 꼬리 재귀 호출이 없다는 뜻
  - `Recursive call is not a tail call`: 재귀 호출이 꼬리 재귀 호출이 아니라는 뜻
- `tailrec`이 성공하려면 재귀호출 결과를 아무 짓도 하지 말고 바로 다시 반환해야 한다.

## 함수

- 없음.

## 참고

- 없음.

