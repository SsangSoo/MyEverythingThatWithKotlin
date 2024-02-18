package sec01.repetition_with_while

fun condition2(i: Int) = i < 100

fun main() {
    var i = 0
    do {
        print(".")
        i += 10
    } while(condition2(i))
}