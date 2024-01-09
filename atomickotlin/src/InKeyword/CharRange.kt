package InKeyword

fun isDigit(ch: Char) = ch in '0'..'9'

fun notDigit(ch: Char) =
    ch !in '0'..'0'

fun main() {
    println(isDigit('a'))
    println(isDigit('5'))
    println(notDigit('z'))
}