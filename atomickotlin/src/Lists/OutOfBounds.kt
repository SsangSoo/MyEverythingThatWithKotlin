package Lists

import Appendices.AtomicTest.Examples.*

fun main() {
    val ints2 = listOf(1, 2, 3)
    capture {
        ints2[3]
    } contains
      listOf("ArrayIndexOutOfBoundException")
}