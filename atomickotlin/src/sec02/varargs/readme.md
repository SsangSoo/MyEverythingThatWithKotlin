# 아토믹 코틀린 At 25. 가변 인자 목록

## 용어

#### 명령줄 인자

- 명령줄에서 프로그램을 시작할 때 프로그램에 원하는 만큼 인자를 전달할 수 있다.
- 프로그램이 명령줄 인자를 받게 하려면 main() 함수에 미리 정해진 파라미터를 지정해야 한다.

```kotlin
fun main(args: Array<String>) {
    for(a in args) {
        println(a)
    }
}
```

- 파라미터의 이름은 전통적으로 args로 짓는다.
  - 다른 이름을 붙여도 문제는 없다.
- args의 타입은 반드시 **Array<String>**이어야 한다.
- kotlinc 컴파일러를 사용해 명령줄 프로그램을 생성할 수도 있다.

**인텔리제이로 실행**하려면 다음과 같이 하면 된다.

<div align="left">
  <img src="https://velog.velcdn.com/images/tjdtn4484/post/4b56973b-7322-4379-b696-7f59f96889d7/image.png">
</div>

<div align="left">
  <img src="https://velog.velcdn.com/images/tjdtn4484/post/46c8f504-28a8-4cbf-83c7-3170673bfdfc/image.png">
</div>

## 예약어 및 코틀린 개념

#### vararg

- `listOf`처럼 임의의 길이로 인자를 받을 수 있는 함수를 정의할 수 있다.
- **가변 인자 목록(variable argument list)**의 줄인 말이다.

```kotlin
fun v(s: String, vararg d: Double) {}

fun main() {
    v("abc", 1.0, 2.0)
    v("def", 1.0, 2.0, 3.0, 4.0)
    v("ghi", 1.0, 2.0, 3.0, 4.0, 5.0, 6.0)
}
```

- 함수 정의에는 **vararg**로 선언된 인자가 최대 하나만 있어야 한다.
- 파라미터 목록에서 어떤 위치에 있는 파라미터든지 vararg로 선언할 수 있지만, **일반적으로는 마지막 파라미터를 vararg로 선언하는 게 간편**하다.
- vararg를 사용하면 **함수에 임의의 개수만큼(0을 포함) 인자**를 전달할 수 있다.
- 모든 인자는 지정한 타입에 속해야 한다.
- 함수 본문에서는 **파라미터 이름을 통해 vararg 인자에 접근**할 수 있고, 이때 **이 파라미터는 Array로 취급**된다.
  - 따라서 **vararg**로 선언된 파라미터 위치의 인자에 Array를 넘길 수 있다.

## 함수

#### arrayOf()

- List를 listOf()으로 만드는 것처럼 Array를 만들기위해 사용하는 함수다.

## 참고

#### Array와 List는 비슷해 보이지만 전혀 다르게 구현된다.

- List는 일반적인 라이브러리 클래스.
- Array에는 특별한 저수준 지원이 필요하다.
- Array는 자바 같은 다른 언어와 호환되어야 한다는 코틀린의 요구 사항에 의해 생겨난 타입이다.
- 간단한 시퀀스가 필요하다면 List를 사용한다.
- 서드파티(외부) API가 Array를 요구하거나 vararg를 다뤄야하는 경우에만 Array를 쓴다.

#### Array

- **Array는 항상 가변 객체**다.
- Array를 인자 목록으로 변환하고 싶으면 `스프레드 연산자(*)`를 사용하면 된다.
  - **스프레드 연산자는 배열만 사용 가능**하다.
- 스프레드 연산자는 vararg로 받은 파라미터를 다시 다른 vararg를 요구하는 함수에 전달할 때 유용하다.

#### 원시 타입의 Array전달

- 구체적인 타입 이름이 지정된 Array 생성 함수를 사용해야 한다.

```kotlin
// JVM에서 Byte, Char, Short, Int, Long, Float, Double, Boolean이 원시타입이다.
// String은 코틀린 기본 타입이긴 하지만 원시 타입은 아니다.
// 원시 타입의 경우 IntArray, ByteArray, BooleanArray 등과 같은 특별한 배열 타입을 지원하며 이런 배열은 해당 원시 타입의 2진 표현 값을 직접 저장한다.
// 반면 Array<Int>는 정숫값이 담겨 있는 Int 객체에 대한 참조를 모아둔 배열로, IntArray보다 훨씬 더 메모리를 많이 차지하고 처리 속도도 늦다.
```
