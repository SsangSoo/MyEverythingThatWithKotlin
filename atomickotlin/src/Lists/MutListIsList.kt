package Lists

import Appendices.AtomicTest.Examples.eq

fun getList(): List<Int> {
    return mutableListOf(1, 2, 3)
}

fun main() {
    // getList()는 읽기 전용 List를 만든다.
    val list = getList()
    list eq listOf(1,2,3)
}