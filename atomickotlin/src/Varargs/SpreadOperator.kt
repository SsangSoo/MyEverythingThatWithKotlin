package Varargs

import Appendices.AtomicTest.Examples.eq

fun main() {
    val array = intArrayOf(4, 5)
    sum(1, 2, 3, *array, 6) eq 21
    // 다음은 컴파일되지 않는다.
    // sum(1, 2, 3, array, 6)

    val list = listOf(9, 10, 11)
    sum(*list.toIntArray()) eq 30
}