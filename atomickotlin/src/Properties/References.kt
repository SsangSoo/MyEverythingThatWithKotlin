package Properties

class Kitchen {
    var table: String = "Round table"
}

fun main() {
    val kitchen1 = Kitchen()
    val kitchen2 = kitchen1
    println("kitchen1: ${kitchen1.table}")
    println("kitchen2: ${kitchen2.table}")
    kitchen1.table = "Square table"
    println("kitchen1: ${kitchen1.table}")
    println("kitchen2: ${kitchen2.table}")
}