# 아토믹 코틀린 At 24. 리스트

## 용어

#### 컨테이너(container)
- 컬렉션이라고도 한다. 

## 예약어 및 코틀린 개념

#### List
- 컨테이너(=컬렉션)으로써, 다른 객체를 담는 객체에 속한다.
- - 자바의 List와는 달리 코틀린에서 List는 **읽기 전용**이다.
- List는 표준 코틀린 패키지에 들어 있기 때문에 import를 할 필요가 없다.
- List의 마지막 원소의 인덱스보다 더 큰 인덱스를 사용하면 코틀린은 항상 **ArrayIndexOutOfBoundException**을 던진다.
- List에는 다른 타입의 값을 저장할 수 있다.


List 타입에 저장한 **원소의 타입을 지정**하는 방법은 다음과 같다.

```kotlin
fun main() {
    // 타입을 추론한다.
    val numbers = listOf(1, 2, 3)
    val strings =
        listOf("one", "two", "three")

    // 똑같은 코드지만 타입을 명시했다.
    val numbers2: List<Int> = listOf(1, 2, 3)
    val strings2: List<String> = listOf("one", "two", "three")

}
```

참고로 반환 타입에 단순히 List만 사용하면 다음과 같은 에러 메세지를 알려준다.

```kotlin
One type argument expected for interface List<out E>
```

타입 파라미터를 반드시 명시해야 한다.

#### MutableList
- 읽기 전용 List와 달리 변경이 가능하다.
- **MutableList**도 **List**로 취급할 수 있다. 
  - 다만, 이런 경우 그 내용을 변경할 수는 없다. 
- **List**를 **MutableList**로 취급할 수는 없다.

#### +=

- `+=` 연산자를 쓰면 불변 리스트가 마치 가변 리스트인 것처럼 보인다.

```kotlin
fun main() {
    var list = listOf('X') // 불변 리스트
    list += 'Y'            // 가변 리스트처럼 보임
    list eq "[X, Y]"
}

```

위의 코드에서 `listOf()`는 불변 리스트를 생성하지만, `list += 'Y'`는 생성한 리스트를 변경하는 코드처럼 보인다.
이런 것이 가능한 이유는 위의 list가 `var`라서 가능하다.

```kotlin
fun main() {
    // 'val'/'var'에 가변 리스트를 대입하는 경우 :
    val list1 = mutableListOf('A') // or 'var'
    list1 += 'A' //  다음 줄과 같다.
    list1.plusAssign('A')

    // 'val'에 불변 리스트를 대입하는 경우 :
    val list2 = listOf('B')
//    list2 += 'B' // Val cannot be reassigned
//    list2 = list2 + 'B' // Val cannot be reassigned

    // 'var'에 불변 리스트를 대입하는 경우 :
    var list3 = listOf('C')
    list3 += 'C' // 다음 줄과 같다.
    val newList = list3 + 'C'
    list3 = newList
  
}
```

- 가변 객체(list1)일 경우 컴파일러는 `plusAssign(파라미터)` 호출로 변환해준다. 
  - `plusAssign()`로 번역되는 경우에는 리스트가 생성된 이후 다른 리스트가 재대입되는 일이 없기 때문에 list1 변수가 `val`이든, `var`이든 상관없다.
  - 인텔리제이는 var를 val로 바꾸는 편이 더 낫다고 제안한다.
- list2의 경우 val로 선언되었기 때문에 새로운 리스트를 재대입할 수 없다.
  - var로 선언된 경우에는 가능하다. 
- list3은 기존 불변리스트를 변경하지 않고, newList를 생성한다.
  - list3은 var이므로, 새로 생성한 newList를 재대입할 수 있다. 

(지금 텍스트로만 보고 있기 때문에 잘 이해가 사실 안 갔는데, 그냥 다음 그림으로 직관적으로 이해할 수 있을 거 같다.)

<div align="left">
  <img src="https://velog.velcdn.com/images/tjdtn4484/post/6db20455-074a-4c27-8112-b6bdd5c7d276/image.png">
</div>

그림이 병맛인데, 
그냥 val 변수는 다른 값으로 변경하지 못하는 것이고, 
var 변수는 다른 값으로 변경할 수 있다는 것이다.



## 함수

#### listOf()

```kotlin
/**
* Returns a new read-only list of given elements.  The returned list is serializable (JVM).
* @sample samples.collections.Collections.sec02.Lists.readOnlyList
  */
  public fun <T> listOf(vararg elements: T): List<T> = if (elements.size > 0) elements.asList() else emptyList()
```

- Collections.kt 에서 다음과 같이 되어있다.
- 주어진 요소들의 새로운 읽기 전용 리스트를 반환한다. 
- 반환된 리스트들은 동기화되어있다고 한다.

- 파라미터 안의 값들로 상태를 변화시키는 함수가 들어있지 않은 읽기 전용 List를 만들어준다.

만약 List를 점진적으로 만든다면 `mutableListOf()`을 사용하면 된다.

#### mutableListOf()

- 변경할 수 있는 **MutableList**를 돌려준다.

```kotlin
fun main() {
    val list = mutableListOf<Int>()

    list.add(1)
    list.addAll(listOf(2, 3))

    list += 4
    list += listOf(5, 6)

}
```

위와 같이 `add()`와 `addAll()`을 사용해서 MutableList에 원소나 다른 컬렉션의 원소들을 추가할 수 있다.
또는 `+=`로 짧게 쓸 수도 있다.

그리고 초기화시 `mutableListOf<Int>`와 같이 어떤 타입의 원소를 담을지 명시해줘야 한다.




#### sorted()

```kotlin
/**
 * Returns a list of all elements sorted according to their natural sort order.
 * 
 * The sort is _stable_. It means that equal elements preserve their order relative to each other after sorting.
 */
public fun <T : Comparable<T>> Iterable<T>.sorted(): List<T> {
    if (this is Collection) {
        if (size <= 1) return this.toList()
        @Suppress("UNCHECKED_CAST")
        return (toTypedArray<Comparable<T>>() as Array<T>).apply { sort() }.asList()
    }
    return toMutableList().apply { sort() }
}
```

- `_Collections.kt` 파일에 정의되어 있다. 
  - 동일한 요소가 정렬된 후에도 서로 상대적인 순서를 유지한다.(번역기 참고)
- 원본 요소들을 (원소에 따라 정해지는 자연스러운 대소 비교에 따라) 정렬한 새로운 list로 반환한다.
- 즉 원래의 List는 그대로 남아있따.
  - 원래의 List의 순서를 정렬하고, 원본을 유지하지 않는 것은 `sort()`이다.
  - `reserved()`, `reserve()` 도 마찬가지다. 


## 참고

되도록 var보다는 val을 이용하자!