package sec04.higher_order_functions

import atomictest.eq

val helloWorld: () -> String =
    { "Hello, world!" }

val sum: (Int, Int) -> Int =
    { x, y -> x + y}

fun main() {
    helloWorld() eq "Hello, world!"
    sum(1, 2) eq 3
}