package sec04.higher_order_functions

import atomictest.*

fun main() {
    repeat(4) { trace("hi!") }
    trace eq "hi! hi! hi! hi!"
}