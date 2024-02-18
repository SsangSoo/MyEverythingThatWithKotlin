package sec03.intro_generics

import atomictest.eq

class GenericHolder<T>(
    private val value: T
) {
    fun getValue(): T = value;
}

fun main() {
    val h1 = GenericHolder(Automobile("Ford"))
    val a: Automobile = h1.getValue()
    a eq "Automobile(brand=Ford)"

    val h2 = GenericHolder(1)
    val i: Int = h2.getValue()
    i eq 1

    val h3 = GenericHolder("Chartreuse")
    val s: String = h3.getValue()
    s eq "Chartreuse"
}