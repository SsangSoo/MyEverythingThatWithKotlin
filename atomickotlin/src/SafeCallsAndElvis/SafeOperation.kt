package SafeCallsAndElvis

import atomictest.*

fun String.echo() {
    trace(uppercase())
    trace(this)
    trace(lowercase())
}


fun main() {
    val s1: String? = "Howdy!"
    s1?.echo()                  // 1
    val s2: String? = null
    s2?.echo()                  // 2
    trace eq
"""
HOWDY!
Howdy!
howdy!
"""
}