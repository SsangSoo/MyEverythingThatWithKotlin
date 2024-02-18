package sec02.property_accessors

import atomictest.eq

class Hamster(val name: String)

class Cage(private val maxCapacity: Int) {
    private val hamsters =
        mutableListOf<Hamster>()
    val capacity: Int
        get() = maxCapacity - hamsters.size
    val full: Boolean
        get() = hamsters.size == maxCapacity
    fun put(haster: Hamster): Boolean =
        if(full)
            false
        else {
            hamsters += haster
            true
        }
    fun take(): Hamster =
        hamsters.removeAt(0)
}

fun main() {
    val cage = Cage(2)
    cage.full eq  false
    cage.capacity eq 2
    cage.put(Hamster("Allice")) eq true
    cage.put(Hamster("Bob")) eq true
    cage.full eq true
    cage.capacity eq 0
    cage.put(Hamster("Charlie")) eq false
    cage.take()
    cage.capacity eq 1
}