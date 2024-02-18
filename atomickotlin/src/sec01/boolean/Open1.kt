package sec01.boolean

fun isOpen1(hour : Int) {
    val open = 9
    val closed = 20
    println("Operating hours: $open - $closed")
    val status =
        if(hour >= open && hour <= closed)
            true
        else
            false
    println("Open: $status") // false
}

fun main() = isOpen1(6)