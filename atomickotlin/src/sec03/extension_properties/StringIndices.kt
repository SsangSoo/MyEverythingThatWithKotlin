package sec03.extension_properties

import atomictest.eq

val String.indices: IntRange
    get() = 0 until length

fun main() {
    "abc".indices eq 0..2
}
