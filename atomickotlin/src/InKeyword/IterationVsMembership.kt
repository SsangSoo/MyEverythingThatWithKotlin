package InKeyword

fun main() {
    val valus = 1..3
    for(v in valus) {
        println("iteration $v")
    }

    val v = 2
    if(v in valus) {
        println("$v is a member of $valus")
    }
}