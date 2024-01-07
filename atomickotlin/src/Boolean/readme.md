# 아토믹 코틀린 At 10. 불리언


## 용어

- 없음

## 예약어 및 코틀린 개념

#### &&

- **연산자 오른쪽과 왼쪽의 Boolean 식이 모두 true일 때** true를 반환

#### ||

- **연산자 오른쪽과 왼쪽의 Boolean 식 중 하나라도 true**라면 true를 반환

```kotlin
    val happy1 = sunny && temp > 50 ||
            exercise && hoursSleep > 7
    println(happy1) // false >>> true 우선순위 && 가 || 보다 먼저다.
```

위의 식을 결과를 순차대로 생각하여 false라고 생각했는데, `&&`를 먼저 연산하고, `||`를 연산한다.
즉 우선순위가 `&&`가 `||`보다 높다.

## 함수

- 없음.

