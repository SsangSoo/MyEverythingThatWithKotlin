# 아토믹 코틀린 At 12. 루프와 범위


## 용어

#### 

## 예약어 및 코틀린 개념

#### in

- 주어진 어떤 범위를 통해 값을 하나하나 가져와 사용한다.

```kotlin
for (v in 값들) {
    // v를 사용해 어떤 일을 수행한다.
}
```

루프를 돌 때마다 v에는 값들의 다음 원소가 들어간다.
(자바의 `향상된 for문`의 타입에 대한 변수와 유사하다. 단 코틀린은 타입을 선언하진 않는 것 같다...? (후에 나올 듯))

for문의 선언부다.

`for(i in 1..3) {`

#### 범위(range)
- 양끝을 표현하는 두 정수를 사용해 구간을 정의한 것이다.
- 범위를 정의하는 방법은 두 가지가 있다.
  - `start..end` : 양 끝 값을 포함한 범위를 만든다.
  - 'start until end' : until 다음에 오는 값을 제외한 범위를 만든다.

#### downTo
- 감소하는 범위를 만든다.

#### step
- 범위의 간격을 변경한다. 1이 아니라, 지정하고 싶은 수를 지정할 수 있다.
- 참고로 until, downTo와 같이 사용할 수 있다.

```kotlin
fun showRange(r: IntProgression) {
    for(i in r) {
        print("$i ")
    }
    print(" // $r")
    println()
}

fun main() {
    showRange(1..5)
    showRange(0 until 5)
    showRange(5 downTo 1)
    showRange(0..9 step 2)
    showRange(0 until 10 step 3)
    showRange(9 downTo 2 step 3)
}
```




#### 루프
참고로 Loop는 문자도 가능하다.

```kotlin
fun main() {
    for(c in 'a'..'z') {
        print(c)
    }
}
```

정수, 문자처럼 하나하나 값이 구분되는 양에 대해서만 가능하다. 부동소수점 수에 대해서는 할 수 없다.

#### 문자열 인덱스에 접근하기
- 대괄호를 사용하면 숫자 인덱스를 통해 문자열의 문자에 접근할 수 있다.

예를 들면 다음과 같다.

```kotlin
String s = "abc"

// s[0] = a
// s[1] = b
// s[2] = c
```

#### 코틀린의 문자는 아스키 코드에 해당하는 숫자 값으로 저장된다.

```kotlin
fun main() {
    val s = "abc"
    for ( i in 0..s.lastIndex) {
        print(s[i] + 1) 
    }
}
```

위의 결과는 "bcd"이다. 아스키코드가 반영된 것이다.
정수를 문자에 더하면 새 코드 값에 해당하는 새로운 문자를 얻을 수 있다.(자바와 같음)


## 함수 및 객체

#### lastIndex - 문자열 함수
- 문자열에 사용하는 함수로써 문자열의 마지막 인덱스 값을 알 수 있다.


#### IntProgression - 객체 
- Int 범위를 포함한다.
- 코틀린이 기본 제공하는 타입이다.
- 입력한 값을 표준적인 형태로 변환해준다.


#### repeat()
- 어떤 동작을 정해진 횟수만큼 반복하고 싶다면 for 루프 대신 repeat()을 사용해도 된다.