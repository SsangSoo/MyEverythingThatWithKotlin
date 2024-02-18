package sec01.ifexpressions

fun main() {
    val n: Int = -11

    if(n > 0)
        println("It's positive")
    else if(n == 0)
        println("It's zero")
    else
        println("It's negative")
}