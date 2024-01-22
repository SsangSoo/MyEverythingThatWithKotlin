package Lists

import Appendices.AtomicTest.Examples.eq

// 반환 타입을 추론한다.
fun inferred(p: Char, q: Char) = listOf(p, q)

// 반환 타입을 명시한다.
fun explicit(p: Char, q: Char): List<Char> = listOf(p, q)
//fun explicit(p: Char, q: Char): List = listOf(p, q) // One type argument expected for interface List<out E>

fun main() {
    inferred('a', 'b') eq "[a, b]"
    explicit('y', 'z') eq "[y, z]"
}