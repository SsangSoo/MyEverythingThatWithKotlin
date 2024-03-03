package sec05.baseclassinit

import atomictest.eq

open class GreateApe(
    val weight: Double,
    val age: Int
)

open class Bonobo(weight: Double, age: Int) : GreateApe(weight, age)

class Chimpanzee(weight: Double, age: Int) : GreateApe(weight, age)

class BonoboB(weight: Double, age: Int) : Bonobo(weight, age)

fun GreateApe.info() = "wt: $weight age: $age"

fun main() {
    GreateApe(100.0, 12).info() eq "wt: 100.0 age: 12"
    Bonobo(110.0, 13).info() eq "wt: 110.0 age: 13"
    BonoboB(130.0, 15).info() eq "wt: 130.0 age: 15"
}