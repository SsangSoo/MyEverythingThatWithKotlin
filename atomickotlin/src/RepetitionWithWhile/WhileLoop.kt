package RepetitionWithWhile

fun condition1(i : Int) = i < 100

fun main() {
    var i = 0
    while(condition1(i)) {
        print(".")
        i += 10
    }
}