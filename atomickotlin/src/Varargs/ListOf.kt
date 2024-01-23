package Varargs

import Appendices.AtomicTest.Examples.eq

fun main() {
    listOf(1) eq "[1]"
    listOf("a","b") eq "[a, b]"
}