package sec05.polymorphism
import atomictest.eq

open class Pet {
    open fun speak() = "Pet"
}

class Dog : Pet() {
    override fun speak() = "Bark!"

}

class Cat : Pet() {
    override fun speak() = "Meow"
}

fun talk(pet: Pet) = pet.speak()


fun main() {
    talk(Dog()) eq "Bark!"
    talk(Cat()) eq "Meow"

}