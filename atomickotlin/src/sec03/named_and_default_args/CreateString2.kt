package sec03.named_and_default_args

import atomictest.eq

fun main() {
    val list = listOf(1, 2, 3)
    list.joinToString(". ", "", "!") eq
            "1. 2. 3!"
    list.joinToString(separator = ". ", postfix = "!") eq "1. 2. 3!"
}