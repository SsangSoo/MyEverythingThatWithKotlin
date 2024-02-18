package sec01.expressions_statements

fun unitFun() = Unit

fun main() {
    println(unitFun())
    val u1: Unit = println(42)
    println(u1)
    val u2 = println(0) // 타입 추론
    println(u2)

}