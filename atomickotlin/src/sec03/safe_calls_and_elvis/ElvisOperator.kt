package sec03.safe_calls_and_elvis

import atomictest.eq

fun main() {
    val s1: String? = "abc"
    (s1 ?: "---") eq "abc"
    val s2: String? = null
    (s2 ?: "---") eq "---"
}