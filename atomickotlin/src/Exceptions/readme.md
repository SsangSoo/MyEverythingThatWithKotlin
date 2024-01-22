# 아토믹 코틀린 At 23. 예외

## 용어

#### 프로덕션(production)

- 작성한 코드를 빌드하고 실전에 투입해 운용되는 경우를 뜻한다.
- **앱을 실제 사용자가 설치해 사용하는 환경이나 실제 서비스를 사용자에게 제공하는 웹 환경 등 소프트웨어가 만들어진 최종 목적에 따라 쓰이는 환경**이 **프로덕션 환경**이다.


## 예약어 및 코틀린 개념

#### 예외(exception)
- 예외는 오류가 발생한 지점에서 '던져지는' 객체이다.

```kotlin
package Exceptions

fun erroneousCode() {
    // 예외를 보려면 다음 줄의 주석을 해제
     val i = "i$".toInt()
}

fun main() {
    erroneousCode()
}
```

위의 코드에서 Exception이 발생하는데, 다음과 같다.
참고로 정수값을 표현하지 않는 String을 Int로 변환시키려 해서 발생하는 예외다.

```kotlin
Exception in thread "main" java.lang.NumberFormatException: For input string: "i$"
at java.base/java.lang.NumberFormatException.forInputString(NumberFormatException.java:67)
at java.base/java.lang.Integer.parseInt(Integer.java:661)
at java.base/java.lang.Integer.parseInt(Integer.java:777)
at Exceptions.ToIntExceptionKt.erroneousCode(ToIntException.kt:5)
at Exceptions.ToIntExceptionKt.main(ToIntException.kt:9)
at Exceptions.ToIntExceptionKt.main(ToIntException.kt)
```

~~Exception이 java네.. 겁나 신기..~~

예외가 던져지면 실행 경로가 중단되고, 예외 객체는 현재 호출된 함수 라인(책에서는 `문맥`이라고 표현되어있다.)을 벗어난다.
즉 위의 코드에서 erroneousCode() 의 문맥을 벗어나 main()의 문맥으로 들어간다.
그리고 예외에 대한 정보를 표시하며, 프로그램을 종료한다.

예외를 잡아내지(catch) 않으면 프로그램이 중단되면서 예외에 대한 상세 정보가 들어있는 **스택 트레이스(stack trace)**가 출력된다.

함수에서 잘못된 인자가 전달될 경우 결괏값을 돌려주는 대신 예외를 던질 수 있다.
예외를 던지면 함수를 종료하면서 프로그램의 다른 부분에서 이 문제를 처리하도록 강제한다.


#### 스택 트레이스(stack trace)
- 예외가 발생한 파일과 위치 등과 같은 상세 정보를 표시한다.

#### throw(예외 던지기)

```kotlin
import Appendices.AtomicTest.Examples.*

fun averageIncome3(income: Int, months: Int) =
    if(months == 0)
        throw IllegalArgumentException(
            "Months can't be zero")
    else
        income / months

fun main() {
    averageIncome3(3300, 3) eq 1100
    capture {
        averageIncome3(5000, 0)
    } eq "IllegalArgumentException : " +
            "Months can't be zero"
}
```

예외를 던질 때 **throw** 키워드 다음에 던질 예외 이름을 넣고 그 뒤에 예외에 필요한 인자들을 추가한다.
~~new 빼고는 자바와 똑같다.~~

**IllegalArgumentException**은 표준 예외이다.

## 함수

#### toIntOrNull()
- 코틀린 표준 라이브러리가 제공하는 함수이다.
- String에 정수가 들어있으면 정수를, 정수로 변환할 수 없는 문자열이 들어 있으면 null을 돌려준다.
- String의 함수다.

````kotlin
/**
 * Parses the string as an [Int] number and returns the result
 * or `null` if the string is not a valid representation of a number.
 */
@SinceKotlin("1.1")
public fun String.toIntOrNull(): Int? = toIntOrNull(radix = 10)
````

위의 doc에 따르면, String을 Int로 파싱(변환)해서 값을 반환하는데, 만약 String의 수 표현이 유효하지 않으면 null을 반환한다고 한다.

## 참고

이후에 **직접 예외 타입을 정의하는 방법**과 **예외가 발생한 환경에 더 잘 들어맞도록 예외를 정의하는 방법**도 배운다. 