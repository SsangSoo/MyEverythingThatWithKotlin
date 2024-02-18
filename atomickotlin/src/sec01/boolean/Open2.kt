package sec01.boolean

fun isOpen2(hour : Int) {
    val open = 9
    val closed = 20
    println("Operating hours: $open - $closed")
    val status = hour >= open && hour <= closed
    println("Open: $status")
}

fun main() = isOpen2(6) // false