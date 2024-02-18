package sec01.looping_and_ranges

fun main() {
    var sum = 0
    for(n in 10..100) {
        sum += n
    }
    println(sum)     // 5005
}