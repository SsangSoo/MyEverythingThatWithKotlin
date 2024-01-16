package Constructors

class Alien(name: String) {
    val greeting = "Poor $name!"
}

fun main() {
    val alien = Alien("Mr. Meeseeks")
    print(alien.greeting)
//    alien.name // Error //
//    val alien1 = Alien()


}