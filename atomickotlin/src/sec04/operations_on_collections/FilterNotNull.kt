package sec04.operations_on_collections

import atomictest.eq

fun main() {
    val list = listOf(1, 2, null)
    list.filterNotNull() eq "[1, 2]"
}