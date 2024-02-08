package NonNullAssertions

import atomictest.*

fun main() {
    var x: String? = "abc"
    x!! eq "abc"
    x = null
    capture {
        val s: String = x!!
    } eq "NullPointerException"
}