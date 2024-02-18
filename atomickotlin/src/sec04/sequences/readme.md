# 아토믹 코틀린 At 51. 시퀀스

## 용어

#### 즉시 계산
- 즉시 계산은 직관적이고 단순하지만 최적은 아니다.
- **수평적 평가**라고도 한다.



#### 지연 계산
- 지연 계산은 결과가 필요할 때만 계산을 수행한다.
- 시퀀스에 대해 지연 계싼을 수행하는 경우를 **수직적 평가**라고도 한다.



지연 계산을 사용하면 어떤 원소와 연관된 값이 진짜

## 예약어 및 코틀린 개념

### 시퀀스(Sequence)
- 코틀린 Sequence는 List와 비슷하지만, Sequence를 대상으로 해서는 이터레이션만 수행할 수 있다.
  - 인덱스를 써서 Sequence의 원소에 접근할 수는 없다. 
  - 이 제약으로 인해 시퀀스에서 연산으로 아주 효율적으로 연쇄시킬 수 있따.

- 코틀린 Sequence를 다른 함수형 언어에서는 스트림(stream)이라고 부른다.
  - 다른 이름을 선택한 이유는 자바 8의 Stream 라이브러리와 호환성을 유지하기 위해서다.

- List에 대한 연산은 즉시 계산된다.
  - 함수를 호출하자마자 모든 원소에 대해 바로 계산이 이뤄진다는 뜻이다.
  - List 연산을 연쇄시키면 첫 번쨰 연산의 결과가 나온 후에야 다음 연산을 적용할 수 있다.


#### 즉시 계산

- `EagerEvaluation.kt`에서 `any()`를 만족하는 첫 번째 원소를 만나서 적용한 뒤 연쇄적인 연산을 멈출 수 있다면 더 합리적일 것이다. 
- 시퀀스가 긴 경우, 이런 최적화가 모든 원소에 대해 연산을 적용한 다음 일치하는 원소를 하나 찾아내는 것보다 훨씬 더 빠르다.

<div align="left">
  <img src="https://velog.velcdn.com/images/tjdtn4484/post/f329dac5-98bb-4b91-8908-f0e96455ce16/image.png">
</div>

- 첫 번째 줄은 최초 리스트 내용을 보여주고, 다음에 오는 각 줄은 이전 연산의 결과를 보여준다.
- 다음 줄에 있는 연산을 수행하기 전에 현재 수평적 수준에 있는 모든 원소에 대해 연산이 처리되어야 한다.

즉시 계산의 대안은 **지연 계산**이다. 

#### 지연 계산

- 지연 계산은 **결과가 필요할 때만 계산을 수행**한다.
- 시퀀스에 대해 지연 계산을 수행하는 경우를 **수직적 평가**라고도 한다.

<div align="left">
  <img src="https://velog.velcdn.com/images/tjdtn4484/post/a0c026da-97e3-44c6-aed5-daf140d4805c/image.png">
</div>

- 지연 계산을 사용하면 어떤 원소와 값이 진짜 필요할 때만 그 원소와 연관된 연산을 수행한다. 
- **원본 컬렉션의 마지막 원소를 처리하기 전에 최종 결과를 찾아내면 나머지 원소는 처리되지 않는다.**

- 인덱싱을 제외한 모든 List 연산을 Sequence에도 사용할 수 있다.

```kotlin
import atomictest.*

fun Int.isEven(): Boolean {
    trace("$this.isEven()")
    return this % 2 == 0
}

fun Int.squre(): Int {
    trace("$this.square()")
    return this * this
}

fun Int.lessThanTen(): Boolean {
    trace("$this.lessThanTen()")
    return this < 10
}

fun main() {
    val list = listOf(1, 2, 3, 4)
    trace(">>> List:")
    trace(
        list
            .filter(Int::isEven)
            .map(Int::squre)
            .any(Int::lessThanTen)
    )
    trace(">>> Sequence:")
    trace(
        list.asSequence()
            .filter(Int::isEven)
            .map(Int::squre)
            .any(Int::lessThanTen)
    )

    trace eq
"""
>>> List:
1.isEven()
2.isEven()
3.isEven()
4.isEven()
2.square()
4.square()
4.lessThanTen()
true
>>> Sequence:
1.isEven()
2.isEven()
2.square()
4.lessThanTen()
true
"""
}
```

- 위의 코드에서 두 접근 방법의 차이는 `asSequence()`를 추가로 호출한 것뿐이다.
  - Sequence보다 List 쪽이 더 많은 원소에 대해 연산을 수행한다.

- `filter()`나 `map()`을 Sequence에 대해 호출하면 다른 Sequence가 생기며, 계산 결과를 요청할 때까지는 아무 일도 벌어지지 않는다.
  - 대신 새 Sequence는 지연된 모든 연산에 대한 정보를 저장해두고, 필요할 때만 저장해둔 연산을 실행한다.

- Sequence 연산에는 **중간(intermidiate) 연산**과 **최종(terminal) 연산**, 두 가지가 있다.
  - 중간 연산은 결과로 다른 Sequence를 내놓는다. `filter()`와 `map()`의 중간 연산이다.
  - 최종 연산은 Sequence가 아닌 값을 내놓는다. 결괏값을 얻기 위해 최종 연산은 저장된 모든 계산을 수행한다.
    - 앞의 예에서 `any()`는 Sequence를 받아 Boolean을 내놓기 때문에 최종 연산이다.

- Sequence는 (중간)연산을 저장해두기 때문에 각 연산을 원하는 순서로 호출할 수 있고, 그에 따라 지연 계산이 발생한다.
- Sequence는 한 번만 이터레이션을 할 수 있다.
  - 이터레이션을 또 시도하면 예외가 발생하며, Sequence를 여러 번 처리하고 싶다면 우선 시퀀스를 Collection 타입 중 하나로 변환해야 한다.



## 함수

#### asSequence()
- List를 `asSequence()`를 사용해 Sequence로 변경하면 지연 계산이 활성화된다.

#### toList()

- Sequence를 List로 변환하며 시퀀스를 처리하는 과정에서 저장된 모든 연산을 실행한다.

```kotlin
import atomictest.*

fun main() {
    val list = listOf(1, 2, 3, 4)
    trace(list.asSequence()
        .filter(Int::isEven)
        .map(Int::square)
        .toList())
    trace eq
"""
1.isEven()
2.isEven()
2.square()
3.isEven()
4.isEven()
4.square()
[4, 16]
"""
}
```

#### generateSequence()
- 첫 번째 인자는 시퀀스의 첫 번째 원소이고, 두 번째 인자는 이전 원소로부터 다음 원소를 만들어내는 방법을 정의하는 람다다.

- 오버로딩 버전도 있는데, 이는 첫 번째 인자를 요구하지 않고, Sequence의 다음 원소를 반환하는 람다만 받는다.
  - 람다는 더 이상 원소가 없으면 null을 내놓는다.
    - 실제로는 첫 번째 인자로 초깃값을 요구하는 `generateSequence()`에서도 람다가 null을 반환하면 시퀀스가 끝난다.

#### removeAt(Int)
- List의 Int번째 원소를 제거하고 그 원소를 반환한다.

#### takeIf()
- 수신 객체가 주어진 술어를 만족하면 수신 객체를 반환하고, 술어를 만족하지 않으면 null을 반환한다.
- `take()` 대신 항상 일반 `if`를 쓸 수 있다.
  - 하지만 식별자가 추가로 필요하기 때문에 if 식이 지저분해 보인다.
  - `takeIf()` 쪽은 더 함수형 표현이고, 특히 호출 연쇄 중간에 자연스럽게 사용할 수 있다.

## 참고

#### 시퀀스 이터레이션
- 사용 중인 시퀀스의 종류에 따라서는 반복 이터레이션이 가능한 경우도 있다. 
- 하지만 반복 이터레이션을 사용하는 시퀀스의 경우 처음부터 모든 연산을 다시 실행할 가능성이 있으므로 반복 계산을 피하기 위해서는 본문에서 설명한 대로 시퀀스를 컬렉션으로 변환해 저장해둬야 한다. 
- 참고로 어떤 시퀀스에 대해 `constrainOnce()`를 호출하면 **단 한 번만 이터레이션을 허용하는 제약이 걸려 있는 시퀀스**를 얻을 수 있다. 
- 개발 시 시퀀스를 두 번 순회하는 로직 오류를 잡아내고 싶다면 `constrainOnce()`를 호출해 사용하면 도움이 된다.
