package sec05.downcasting

import atomictest.eq

fun main() {
    val humans1: List<Creature> =
        group.filter { it is Human }
    humans1.size eq 2
    val humans2: List<Human> =
        group.filterIsInstance<Human>()
    humans2 eq humans1
}