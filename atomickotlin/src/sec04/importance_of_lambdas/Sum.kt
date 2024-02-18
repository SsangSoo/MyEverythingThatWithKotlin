package sec04.importance_of_lambdas

import atomictest.eq

fun main() {
    val list = listOf(1, 5, 7, 10)
    val divider = 5
    list.filter { it % divider == 0}
        .sum() eq 15
}