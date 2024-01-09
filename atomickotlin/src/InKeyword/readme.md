# 아토믹 코틀린 At 13. in 키워드

## 용어

- 없음. 

## 예약어 및 코틀린 개념

#### in
- in은 어떤 값이 주어진 범위 안에 들어있는지 검사한다.

```kotlin
fun main() {
    val percent = 35
    println(percent in 1..10)
}
```

해당 결과는 false다 35는 (1 <= 35 < 10) 의 결과가 false이기 때문이다.

```kotlin
fun main() {
    val percent = 35
    println(percent in 1..100)
}
```

반면에 이 결과는 true이다. (1 <= 35 < 100) 의 결과가 true이기 때문이다.

```kotlin
println(0 <= percent && percent <= 100)
println(percent in 0..100) 
```
위의 두 형태는 같은 것이다. 
인텔리제이가 아래 형태로 바꾸라고 권장한다.

그리고 옮긴이의 말에 따르면, `x in 0..100`이 0부터 100까지 101개의 정수를 계산해 메모리에 저장해두고 x와 비교하지 않으며 **시작, 끝, 증갓값만으로 이뤄진 순열 객체**를 만들고 이를 사용해 x가 순열에 포함되는지 계산하기 때문에 `0 <= x && x < 100`과 `x in 0..100`은 실행 속도도 거의 같다.


- in 키워드는 이터레이션과 원소 여부 검사에도 함께 사용된다. 
  - for문 안에 있는 in만 이터레이션을 뜻하지만, 나머지 in은 모두 원소인지 여부를 검사한다.


```kotlin
fun hasChar(s: String, ch: Char): Boolean {
    for(c in s) {
        if(c == ch) return true
    }
    return false
}

fun main() {
    println(hasChar("kotlin", 't')) // true
    println(hasChar("kotlin", 'a')) // false
}
```

[12. 루프와 범위](https://github.com/SsangSoo/MyEverythingThatWithKotlin/tree/main/atomickotlin/src/LoopingAndRanges)에서 HasChar.kt의 파일의 내용인데, 굳이 저렇게 할 것이 아니라, 다음과 같이 in을 사용해서 깔끔하게 판별이 가능하다.

```kotlin
fun main() {
    println('t' in "kotlin")
    println('a' in "kotlin")
}
```

다음처럼 String이 어떤 String 범위 안에 속하는지를 검사할 수도 있다.

```kotlin
fun main() {
    println("ab" in "aa".."az")  // true
    println("ba" in "aa".."az")  // false
}
```


#### !in

- in의 반대이므로, 어떤 값이 범위 안에 속하지 않으면 true를 반환한다.

#### ..<

- 부동 소수점에서 범위를 만들 때는 `..`만 사용할 수 있었지만, 코틀린 1.8부터는 반 열린 범위(semi open range)를 만들어내는 `..<`를 사용할 수 있다.
- `1.0 ..< 2.0`은 1.0 이상 2.0 미만인 범위를 만들어낸다.

## 함수

- 없음.

