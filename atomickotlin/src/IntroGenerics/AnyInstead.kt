package IntroGenerics

import atomictest.eq

class AnyHolder(private val value: Any) {
    fun getValue(): Any = value
}

class Dog {
    fun bark() = "Ruff!"
}

fun main() {
    val holder = AnyHolder(Dog())
    val any = holder.getValue()
    // 컴파일 되지 않음.
    // any.bark()

    val genericHolder = GenericHolder(Dog())
    val dog = genericHolder.getValue()
    dog.bark() eq "Ruff!"
}