# 아토믹 코틀린 At 43. break와 continue

## 용어

- 없음.

## 예약어 및 코틀린 개념

### break와 continue
- **break**와 **continue**를 사용하면 루프 안에서 `점프`할 수 있다.
- 코틀린은 **break**와 **continue**를 사용해 **제한적인 점프**를 제공한다.
- 이들은 `for`,`while`,`do-while` 루프 요소와 섞여 있다. 
  - 즉, 이런 루프 안에서만 **break**나 **continue**를 사용할 수 있다.
- 실전 코틀린 코드에서 쓰는 일은 드물다.
  - 이 둘은 초기 언어의 유물이다. 
- 가끔 유용하긴 하지만, 코틀린은 더 나은 메커니즘을 제공한다.

#### 레이블
- 단순한 break와 continue는 자신이 속한 루프의 범위보다 더 밖으로 점프할 수 없다.
- 그러나 **레이블**을 사용하면 **break와 continue가 자신을 둘러싼 여러 루프의 경계 중 한군데로 점프할 수 있어서 현재 실행 중인 맨 안쪽 루프의 영역에 제한되지 않고 점프할 수 있다.**
- `레이블@`과 같이 레이블 이름 다음에 `@`을 사용해 레이블을 붙일 수 있다.
  - 다음 코드에서 레이블 이름은 `outer`이다.

```kotlin
import atomictest.eq

fun main() {
    val strings = mutableListOf<String>()
    outer@ for( c in 'a'..'e') {
        for(i in 1..9) {
            if (i == 5) continue@outer
            if("$c$i" == "c3") break@outer
            strings.add("$c$i")
        }
    }

    strings eq listOf("a1", "a2", "a3", "a4", "b1", "b2", "b3", "b4", "c1", "c2")
}
```

- 레이블이 붙은 `continue@outer`는 `outer@`이 붙은 루프의 실행을 계속한다.
- 레이블이 붙은 `break@outer`는 `outer@`이 붙은 루프의 마지막을 찾아서 거기서부터 실행을 계속한다.


break와 continue를 사용하면 코드가 복잡해지고 유지 보수가 어려워질 수 있다.
break와 continue를 사용하는 대신 이터레이션 조건을 명시적으로 작성할 수 있는 경우가 있다.
다른 경우에는 코드 구조를 재구성해 새로운 함수를 도입할 수도 있다.
루프 본문을 별도의 함수로 추출하면 break나 continue를 **return 으로 대체할 수 있다.**

함수형 프로그래밍 쪽에서 break와 continue 없이 더 깔끔하게 코드를 작성하는 방법을 배운다.

## 함수

- 없음.

## 참고


- 여러 가지 다른 접근 방법을 찾아보고 더 간단하고 읽기 좋은 해법을 선택하자.
- 더 간단하고 읽기 좋은 해법에는 보통 break나 continue가 없는 경우가 많다.