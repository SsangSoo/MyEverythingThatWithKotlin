# 아토믹 코틀린 At 27. 맵

## 용어

#### Map
- Map은 키(key)와 값(value)을 연결하고, 키가 주어지면 그 키와 연결된 값을 찾아준다.
- 키-값 쌍을 mapOf()에 전달해 Map을 만들 수 있다.
- 키와 값을 분리하려면 to를 사용한다.

```kotlin
fun main() {
    val constants = mapOf(
        "Pi" to 3.141,
        "e" to 2.718,
        "phi" to 1.618
    )
    constants eq
            "{Pi=3.141, e=2.718, phi=1.618}"

    // 키에 해당하는 값을 찾는다
    constants["e"] eq 2.718
    constants.keys eq setOf("Pi", "e", "phi")
    constants.values eq "[3.141, 2.718, 1.618]"

    var s = ""
    // 키-값 쌍을 이터레이션 한다
    for(entry in constants) {
        s += "${entry.key}=${entry.value}, "
    }
    s eq "Pi=3.141, e=2.718, phi=1.618,"
    s = ""
    // 이터레이션을 하면서 키와 값을 분리한다.
    for ((key, value) in constants)
        s += "$key=$value, "
    s eq "Pi=3.141, e=2.718, phi=1.618,"

}
```

- Map에서 **각 키는 유일**하다.
- Map에 대해 이터레이션을 수행하면 맵 항목(entry)으로 키-값 쌍을 전달받는다.
- **일반 Map은 읽기 전용**이다.
  - 상태 변경을 허용하지 않는다.
- MutableMap은 가변이다.

- `map[key] = value`는 key와 연관된 value를 추가하거나 변경한다.
- `map += key to value`를 통해 **키-값 쌍을 명시적으로 추가**할 수 있다.
- `mapOf()`와 `mutableMapOf()`는 원소가 Map에 전달된 **순서를 유지**해준다. 
- 다른 Map 타입에서는 이 순서가 보장되지 않을 수도 있다.

- Map에서 `+`연산은 기존 맵의 원소와 더해진 원소를 포함하는 **새로운 Map**을 만들지만, 원래의 Map에는 영향을 미치지 않는다.
- 읽기 전용 Map에 원소를 **추가**하는 유일한 방법은 새로운 Map을 만드는 것 뿐이다.
- 주어진 키에 해당하는 원소가 포함되어 있지 않으면 Map은 null을 반환한다. 
  - null이 될 수 없는 결과를 원한다면 `getValue()`를 사용하면 된다.
- 클래스 인스턴스를 Map의 값으로 저장할 수도 있다.(자바도 그렇다)
- 키와 값을 연관(연결)시켜주기 때문에 **연관 배열(associative array)**이라고 부르기도 한다.

## 예약어 및 코틀린 개념

- 없음.

## 함수

#### keys
- 모든 키를 얻을 수 있다.
- keys를 호출하면 Set이 생긴다.


#### values
- 모든 값을 얻을 수 있다.

#### getValue()
- 키가 맵에 들어있지 않으면 `NoSuchElementException`이 발생한다.

#### getOrDefault()
- 있으면 값이 null이거나, 없는 경우 두 번째 파라미터의 값을 반환한다.

## 참고

- 없음.