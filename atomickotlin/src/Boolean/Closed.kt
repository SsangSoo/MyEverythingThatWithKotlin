package Boolean

fun isClosed(hour: Int) {
    val open = 9
    val closed = 20
    println("Operating hours: $open - $closed")
    val status = hour < open || hour > closed
    println("Closed: $status") // true 닫혀있음.
}

fun main() = isClosed(6)
