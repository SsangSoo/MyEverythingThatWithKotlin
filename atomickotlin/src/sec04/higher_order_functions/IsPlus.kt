package sec04.higher_order_functions

import atomictest.eq

val isPlus: (Int) -> Boolean = { it > 0 }

fun main() {
    listOf(1,2, -3).any(isPlus) eq true
}