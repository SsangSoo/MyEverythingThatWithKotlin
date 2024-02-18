package sec04.higher_order_functions

import atomictest.*

fun repeat(
    times: Int,
    action: (Int) -> Unit                       // 1 : repeat()는 (Int) -> Unit 타입의 함수를 action 파라미터로 받는다.
) {
    for(index in 0 until times) {
        action(index)                           // 2 : action()을 현재의 반복 횟수 index를 사용해 호출한다.
    }
}

fun main() {
    repeat(3) { trace("#$it") }       // 3 : repeat()을 호출할 때 람다 내부에서 반복 인덱스를 it로 참조할 수 있다.
    trace eq "#0 #1 #2"
}