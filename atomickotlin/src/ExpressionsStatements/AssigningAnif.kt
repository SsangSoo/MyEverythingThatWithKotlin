package ExpressionsStatements

fun main() {
    val result1 = if (11 > 42) 9 else 5
    val result2 = if (1 < 2) {
        val a = 11
        a + 42  // if쪽 블록의 마지막에 있는 식의 결과가 if 전체의 결과가 된다.
    } else 42

    val result3 =
        if('x' < 'y')
            println("x < y")
        else
            println("x > y")

    println(result1)
    println(result2)
    println(result3)
}