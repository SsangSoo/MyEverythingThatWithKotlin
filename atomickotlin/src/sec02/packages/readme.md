# 아토믹 코틀린 At 21. 패키지

## 용어

- 없음.

## 예약어 및 코틀린 개념

#### 패키지(package)

- 연관 있는 코드를 모아둔 것.
- 보통 특정 문제를 풀기 위해 고안되며, 여러 함수와 클래스를 포함하곤 한다.
- 파일에서 코드의 가장 앞부분에 위치해야 한다.
- 관례적으로 패키지 이름에는 **소문자**만 이용한다.
- 코틀린에서는 패키지 이름도 아무 이름이나 선택할 수 있다. (**그러나 패키지 이름과 패키지 파일이 들어 있는 디렉터리의 경로를 똑같이 하는 게 좋은 스타일이다.**)
- 코틀린에서 패키지 이름은 패키지 내용물 파일이 위치한 디렉터리 경로와 무관하다.

**코틀린과 자바 프로젝트를 섞어 쓰는 경우**

- 코틀린 스타일 가이드는 자바의 관습을 유지하는 것을 권장한다.

**순수 코틀린 프로젝트의 경우**

- 프로젝트 디렉터리 구조의 최상위에 libraryname을 위치시킨다.

#### import

- 다른 파일에 정의된 코드를 **재사용할 수 있다.**
- 패키지에 있는 모든 내용을 임포트 하려면 별표(`*`)를 사용하면 된다.

```kotlin
import kotlin.math.*

...
```

- import 문은 하나 이상의 이름을 현재 이름 공간(namespace)에 도입한다.

#### as

- as 키워드를 하면 임포트하면서 이름을 변경할 수 있다.

다음과 같은 코드가 있을 때

```kotlin
import kotlin.math.PI
import kotlin.math.cos // 코사인

fun main() {
    println(PI)
    println(cos(PI))
    println(cos(2 * PI))
}
```

as 키워드를 사용하면 다음과 같이 할 수 있다.

```kotlin
import kotlin.math.PI as circleRatio
import kotlin.math.cos as cosine

fun main() {
  println(circleRatio)
  println(cosine(circleRatio))
  println(cosine(2 * circleRatio))
}
```

- as는 라이브러리에서 이름을 잘못 선택했거나 이름이 너무 길 때 유용하다.

#### 서드파티(third-pargy) 라이브러리

- 코틀린 개발사나 우리가 아닌 제 3자가 만든 라이브러리를 의미

#### 코틀린에서 파일 이름에 대하여..

다음과 같은 코틀린 파일이 있다.

```kotlin
package Packages

import kotlin.math.sqrt

class RightTriangle(
    val a: Double,
    val b: Double
) {
    fun hypotenuse() = sqrt(a * a + b * b)
    fun area() = a * b /2
}
```

해당 kt 파일이름은 `PythagoreanTheorem.kt`이다.

그런데 **파일 이름이 항상 클래스 이름과 같아야 하는 자바**와 달리, **코틀린에서는 소스 코드 파일 이름으로 아무 이름이나 붙여도 좋다.**

## 함수

#### toInt()

~~(이전에 소개한 거 같지만,)~~

- Int 형으로 타입을 변환해준다.

#### roundToInt()

- Double 타입의 수를 가장 가까운 Int로 반올림해준다.

## 참고

- 프로그래밍에서 근본적인 원칙은 **반복하지 말라(DRY(Don't Repeat Yourself))**는 의미를 지닌 약자로 나타낼 수 있다.
- 코드에서 같은 내용이 여러 번 반복되면, 이를 수정하거나 개선할 때마다 더 많은 유지 보수가 필요해진다.
- 코드가 중복되는 건 이런 추가 노력이 필요할 뿐 아니라 반복이 추가될 때마다 실수할 가능성이 커진다는 뜻이기도 하다.
- 코드를 재사용 하려면 package 키워드를 사용해 패키지를 만들어라.
