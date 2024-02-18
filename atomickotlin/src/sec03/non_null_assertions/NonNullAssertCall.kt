package sec03.non_null_assertions

import atomictest.eq

fun main() {
    val s: String? = "abc"
    s!!.length eq 3
}