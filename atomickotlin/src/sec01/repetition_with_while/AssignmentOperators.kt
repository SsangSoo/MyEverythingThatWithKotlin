package sec01.repetition_with_while

fun main() {
    var n = 10
    val d = 3
    print(n)
    while(n > d) {
        n -= d
        print(" - $d")
    }
    println(" = $n")
    var m = 10
    print(m)
    m %= d
    println(" % $d = $m")
}