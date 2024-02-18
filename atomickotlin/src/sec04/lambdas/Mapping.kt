package sec04.lambdas

import atomictest.eq

fun main() {
    val list = listOf('a', 'b', 'c', 'd')
    val result =
        list.map({ "[${it.uppercase()}]" })
    result eq listOf("[A]", "[B]", "[C]", "[D]")
}