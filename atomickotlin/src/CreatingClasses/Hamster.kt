package CreatingClasses

class Hamster {
    fun speak() = "Squeak! "
    fun exsercise() =
        this.speak() +      // this로 한정
                speak() +   // this 없이 호출
                "Running on wheel"
}

fun main() {
    val hamster = Hamster()
    println(hamster.exsercise())
}