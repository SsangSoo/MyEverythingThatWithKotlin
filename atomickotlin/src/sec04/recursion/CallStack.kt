package sec04.recursion

fun illegalState() {
//     throw IllegalStateException()
}

fun fail() = illegalState()

fun main() {
    fail()
}