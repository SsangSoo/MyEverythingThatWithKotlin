# 아토믹 코틀린 At 50. 맵 만들기

## 용어

- 없음.

## 예약어 및 코틀린 개념

#### Map

Map은 매우 유용한 프로그래밍 도구이며, 만드는 방법도 아주 다양하다.

[이전 아톰인 리스트 조작하기](https://ssangsu.tistory.com/216)에서는 반복적인 데이터 집합을 만들기 위해 두 List를 하나로 묶으면서 생기는 Pair에 대해 람다 안에서 생성자를 호출하는 방식으로 `List<Person>`을 만들었다.

```kotlin
data class Person (
    val name: String,
    val age: Int
)

val names = listOf("Alice", "Arthricia",
    "Bob", "Bill", "Birdperson", "Charlie",
    "Crocubot", "Franz", "Revolio")

val ages = listOf(21, 15, 25, 25, 42, 21 ,
    42, 21, 33)

fun people(): List<Person> = 
    names.zip(ages) { name, age ->
        Person(name, age)
    }
```

**Map을 사용하면 키를 사용해 값에 빠르게 접근할 수 있다.**
라이브러리 함수 `groupBy()`는 이런 Map을 만드는 방법 중 하나다.

```kotlin
import atomictest.eq

fun main() {
    val map: Map<Int, List<Person>> =
        people().groupBy(Person::age)
    map[15] eq listOf(Person("Arthricia", 15))
    map[21] eq listOf(
        Person("Alice", 21),
        Person("Charlie", 21),
        Person("Franz", 21))
    map[22] eq null
    map[25] eq listOf(
        Person("Bob", 25),
        Person("Bill", 25))
    map[33] eq listOf(Person("Revolio", 33))
    map[42] eq listOf(
        Person("Birdperson", 42),
        Person("Crocubot", 42))
}
```

그룹이 두 개만 필요한 경우에는 술어에 의해 컬렉션 내용을 두 그룹으로 나누는 `partition()`함수가 더 직접적이다.
`groupBy()`는 **결과 그룹이 세 개 이상인 경우 적합**하다.

리스트에 대해 `associateWith()`을 사용하면 리스트 원소를 키로 하고 `associateWith()`에 전달된 함수(여기서는 람다)를 리스트 원소에 적용한 반환값을 값으로 하는 Map을 만들 수 있다.


### 맵에 연산 적용하기 


## 함수

#### groupBy()
- `groupBy()`의 파라미터는 원본 컬렉션의 원소를 분류하는 기준이 되는 키를 반환하는 람다다.
- `groupBy()`는 원본 컬렉션의 각 원소에 이 람다를 적용해 키 값을 얻은 후 맵에 넣어준다. 
  - 이때 키가 같은 값이 둘 이상 있을 수 있으므로 맵의 값은 원본 컬렉션의 원소 중에 키에 해당하는 값의 리스트가 되어야 한다.

- `filter()` 함수를 사용해도 똑같은 그룹을 계산할 수 있지만, 그룹 분류를 단 한 번에 할 수 있으므로 `groupBy()`가 더 낫다.
  - `filter()`를 사용하면 새로운 키가 나타날 때마다 그룹을 만드는 작업을 반복해야 한다.
    
#### associateWith()
- 리스트에 대해 `associateWith()`을 사용하면 리스트 원소를 키로 하고 `associateWith()`에 전달된 함수를 리스트 원소에 적용한 반환값을 값으로 하는 Map을 만들 수 있다.

```kotlin
import atomictest.eq

fun main() {
    val map: Map<Person, String> =
        people().associateWith { it.name }
    map eq mapOf(
        Person("Alice", 21) to "Alice",
        Person("Arthricia", 15) to "Arthricia",
        Person("Bob", 25) to "Bob",
        Person("Bill", 25) to "Bill",
        Person("Birdperson", 42) to "Birdperson",
        Person("Charlie", 21) to "Charlie",
        Person("Crocubot", 42) to "Crocubot",
        Person("Franz", 21) to "Franz",
        Person("Revolio", 33) to "Revolio",
    )
}
```

#### associateBy()
- `associateWith()`가 만들어내는 연관 관계를 반대 방향으로 뒤집는다.
  - 셀렉터가 반환한 값이 키가 된다.


```kotlin
import atomictest.eq

fun main() {
    val map: Map<String, Person> =
        people().associateBy { it.name }
    map eq mapOf(
        "Alice" to Person("Alice", 21),
        "Arthricia" to Person("Arthricia", 15),
        "Bob" to  Person("Bob", 25),
        "Bill" to Person("Bill", 25),
        "Birdperson" to Person("Birdperson", 42),
        "Charlie" to Person("Charlie", 21),
        "Crocubot" to Person("Crocubot", 42),
        "Franz" to Person("Franz", 21),
        "Revolio" to Person("Revolio", 33)
    )
}
```

- `associateBy()`의 셀렉터가 유일한 키 값을 만들어내야 한다.
  - 키 값이 유일하면 `associateBy()`가 반환하는 맵이 키와 값을 하나씩 연결시켜줄 수 있다.

#### getOrElse()
- Map에서 값을 찾는다. 
- 키가 없을 때 디폴트 값을 계산하는 방법이 담긴 람다를 인자로 받는다.
  - 이 파라미터가 람다이므로 필요할 때만 디폴트 값을 계산할 수 있다.

#### getOrPut()
- `MutableMap`에만 적용할 수 있다.
- `getOrPut()`은 키가 있으면 그냥 연관된 값을 반환한다.
  - 맵에서 키를 찾을 수 없으면, 값을 계산한 후 그 값을 키와 연관시켜 맵에 저장하고 저장한 값을 반환한다.

#### filter(), filterKeys(), filterValues()
- 모두 술어를 만족하는 원소들로 이뤄진 새 맵을 반환한다.
  - `filterKeys()`는 술어를 맵의 키에 적용한다.
  - `filterValues()`는 술어를 맵의 값에 적용한다.

```kotlin
import atomictest.eq

fun main() {
    val map = mapOf(1 to "one",
        2 to "two", 3 to "three", 4 to "four")

    map.filterKeys { it % 2 == 1 } eq
            "{1=one, 3=three}"

    map.filterValues { it.contains('o') } eq
            "{1=one, 2=two, 4=four}"

    map.filter { entry ->
        entry.key % 2 == 1 &&
                entry.value.contains('o')
    } eq "{1=one}"
}
```

#### map(), mapKeys(), mapValues()

```kotlin
import atomictest.eq

fun main() {
  val even = mapOf(2 to "two", 4 to "four")
  even.map {                             // 1
    "${it.key}=${it.value}"
  } eq listOf("2=two", "4=four")

  even.map{ (key, value) ->               // 2
    "$key=$value"
  } eq listOf("2=two", "4=four")

  even.mapKeys {(num,_) -> -num }         // 3
    .mapValues { (_, str) -> "minus $str" } eq
          mapOf(-2 to "minus two",
            -4 to "minus four")

  even.map { (key, value) ->
    -key to "minus $value"
  }.toMap() eq mapOf(-2 to "minus two",   // 4
    -4 to "minus four")
}
```

주석 1에서는  `map()`은 `Map.Entry`를 인자로 받는 람다를 파라미터로 받는다. `Map.Entry`의 내용을 `it.key`와 `it.value`로 접근할 수 있다.

#### any()
- Map의 원소 중에 주어진 술어를 만족하는 원소가 하나라도 있는지 검사한다.

#### all()
- Map의 모든 원소가 술어를 만족해야 true를 반환한다.

#### maxByOrNull()
- 주어진 기준에 따라 가장 큰 원소를 찾는다. 
- 가장 큰 원소가 없을 수도 있으므로 이 **함수의 결과는 널이 될 수 있는 타입**이다.

## 참고

- 없음.

