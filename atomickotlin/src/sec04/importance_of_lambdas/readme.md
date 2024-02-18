# 아토믹 코틀린 At 45. 람다의 중요성

## 용어

#### 클로저(closure)
- 함수가 자신이 속한 환경의 요소를 `포획(captuer)`하거나 `닫아버리는(close up)` 것을 의미한다.
  - 불행히도 일부 언어에서는 `클로저`라는 용어를 람다 개념과 혼동한다. 
  - 두 개념은 완전히 다른 개념이다. 
  - 클로저가 없는 람다가 있을 수 없으며, 람다가 없는 클로저도 있을 수 없다.



## 예약어 및 코틀린 개념

#### 람다의 중요성
- 람다는 문법 설탕처럼 보일 수 있다. 하지만 람다는 프로그래밍에 중요한 능력을 부여해준다.
- 코드가 컬렉션의 내용을 조작하는 경우가 종종 있다. 그리고 조작 방식을 약간 변경해서 조작을 반복하곤 한다.

- 다음은 리스트에서 짝수를 선택하는 예제다.
  - 컬렉션에 적용할 수 있는 풍부한 함수 라이브러리가 없다고 가정해보자. 
  - 이 경우 `filterEven()` 연산을 직접 구현해야 한다.

```kotlin
import atomictest.eq

fun filterEven(nums: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for(i in nums) {
        if(i % 2 == 0) {
            result += i
        }
    }
    return result
}

fun main() {
    filterEven(listOf(1,2,3,4,)) eq
            listOf(2,4)
}
```

- 어떤 원소를 2로 나눴을 때 나머지가 0이면 그 원소를 결과에 추가한다.
- 비슷한 리스트지만, 이번에는 2보다 큰 수만 선택하고 싶다고 가정해보자.
  - `filterEven()`을 복사해서 결과에 포함시킬 원소를 선택하는 부분만 수정할 수 있을 것이다.

```kotlin
import atomictest.eq

fun greaterThan2(nums: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for ( i in nums) {
        if(i > 2) {
            result += i
        }
    }
    return result
}

fun main() {
    greaterThan2(listOf(1,2,3,4)) eq
            listOf(3,4)
}
```

- 위 두 예제에서 눈에 띄는 차이는 원하는 원소를 선택하는 부분뿐이다.
- 람다를 사용하면 두 경우 모두 같은 함수를 쓸 수 있다.
  - 표준 라이브러리 함수 `filter()`는 보존하고 싶은 원소를 선택하는 술어(predicate)(**Boolean** 값을 돌려주는 함수)를 인자로 받는다.(함수 쪽에서도 설명한다.) 
  - 이 술어를 람다로 지정할 수 있다.

```kotlin
import atomictest.eq

fun main() {
    val list = listOf(1,2,3,4)
    val even = list.filter { it % 2 == 0 }
    val greaterThan2 = list.filter{ it > 2}
    even eq listOf(2,4)
    greaterThan2 eq listOf(3,4)
}
```

- 위처럼 반복을 피하면서 간결하고 명확한 코드를 작성할 수 있게 된다.
  - 위의 코드에서 정의했던 `even`과 `greaterThan2`는 모두 `filter()`를 사용하며, 술어 부분만 다르다.


- 람다를 `var`나 `val`에 담을 수 있다.
- 이렇게 하면 여러 함수에 같은 람다를 넘기면서 로직을 재사용할 수 있다.

```kotlin
import atomictest.eq

fun main() {
    val list = listOf(1,2,3,4)
    val isEven = { e: Int -> e % 2 == 0}
    list.filter(isEven) eq listOf(2,4)
    list.any(isEven) eq true
}
```

- `isEven`은 주어진 수가 짝수인지 판단하고, 이 참조를 `filter()`와 `any()`에 전달한다.
  - `isEven`을 정의할 때는 **코틀린 타입 추론기가 파라미터의 타입을 결정할 수 있는 문잭이 존재하지 않으므로 람다 파라미터의 타입을 명시해야 한다.**
- 람다의 또 다른 특징으로 자신의 영역 밖에 있는 요소를 참조할 수 있는 능력을 들 수 있다. 

- 언어가 클로저(용어부분에서 설명)를 지원하면 우리가 예상하는 방식으로 '그냥 동작'한다.


```kotlin
import atomictest.eq

fun main() {
    val list = listOf(1, 5, 7, 10)
    val divider = 5
    list.filter { it % divider == 0 } eq
            listOf(5, 10)
}
```

- 여기서 람다는 자신의 밖에 정의된 val divider를 `포획`한다.
- 람다는 포획한 요소를 읽을 우 있을 뿐 아니라 변경할 수도 있다.

```kotlin
import atomictest.eq

fun main() {
    val list = listOf(1, 5, 7, 10)
    var sum = 0
    val divider = 5
    list.filter{ it % divider == 0 }
        .forEach { sum += it}
    sum eq 15
}
```

- 위의 코드와 같이 람다가 가변 변수 sum을 포획할 수 있지만, 보통은 환경의 상태를 변경하지 않는 형태로 코드를 변경할 수 있다.

```kotlin
import atomictest.eq

fun main() {
    val list = listOf(1, 5, 7, 10)
    val divider = 5
    list.filter { it % divider == 0}
        .sum() eq 15
}
```

- `sum()`은 숫자 리스트에 적용 가능하며 리스트에 들어 있는 모든 원소를 합한 값을 돌려준다.(함수에서도 설명)

```kotlin
import atomictest.eq

var x = 100

fun useX() {
    x++
}

fun main() {
    useX()
    x eq 101
}
```

- `useX()`는 주변 환경의 x를 포획해서 변경한다.

## 함수

#### `filter()`

- 표준 라이브러리 함수로써 보존하고 싶은 원소를 선택하는 술어(predicate)(**Boolean** 값을 돌려주는 함수)를 인자로 받는다.
- 이 술어를 람다로 지정할 수 있다.
- 참고로 `filter()`는 엄격히 테스트된 함수이므로 버그가 생길 여지가 거의 없다.
- `filter()`는 컬렉션에서 원소를 선택하는 코드를 작성하면 우리가 직접 처리해야 했을 이터레이션을 처리해준다.
  - 이터레이션을 직접 관리하는 게 그다지 큰 수고는 아니지만, 어쨌든 루프도 실수할 여지가 있는 세부 사항이므로 버그가 발생할 수 있는 장소가 한 곳 더 늘어나는 셈이다.
  - 루프 처리는 너무 `뻔하기`때문에 루프를 작성하면서 실수를 저지르면 더 발견하기가 어렵다.
- 이 부분이 **함수형 프로그래밍**이 제공하는 특징이며, `map()`이나 `filter()`가 그 예다.

 
#### any()
- 주어진 술어를 만족하는 원소가 List에 하나라도 있는지 검사한다.

#### forEach()
- 지정한 동작을 컬렉션의 매 원소에 적용한다.


#### sum()
- 숫자 리스트에 적용 가능하며 리스트에 들어 있는 모든 원소를 합한 값을 돌려준다.

## 참고

#### 함수형 프로그래밍
- 함수형 프로그래밍은 문제를 작은 단계로 풀어나간다.
  - 아주 뻔해 보이는 작업을 함수가 수행하는 경우도 많다.
  - 이런 함수를 작성하는 건 `map()`이나 `filter()`를 사용하는 것보다 그리 어렵지 않다.
  - 하지만 이런 작고 디버깅이 잘 이뤄진 해법을 많이 갖추고 나면, 매번 디버깅할 필요 없이 이들을 쉽게 조합해서 사용할 수 있다.
  - 이를 통해 더 튼튼한 코드를 더 빨리 작성할 수 있다.
