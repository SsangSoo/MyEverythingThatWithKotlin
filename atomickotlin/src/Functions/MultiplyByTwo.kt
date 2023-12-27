package Functions

fun multiplyByTwo(x: Int): Int {
    println("Inside multiplyByTwo")
    return x * 2
}

fun main() {
    val r = multiplyByTwo(5)
    println(r)
}

// 결과
    // Inside multiplyByTwo
    // 10