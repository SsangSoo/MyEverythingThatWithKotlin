package sec05.inheritance

import atomictest.eq

open class GreateApe {
    val weight = 100.0
    val age = 12
}

open class Bonobo: GreateApe()
class Chimpanzee : GreateApe()
class BonoboB : Bonobo()

fun GreateApe.info() = "wt: $weight age: $age"

fun main() {
    GreateApe().info() eq "wt: 100.0 age: 12"
    Bonobo().info() eq "wt: 100.0 age: 12"
    Chimpanzee().info() eq "wt: 100.0 age: 12"
    BonoboB().info() eq "wt: 100.0 age: 12"
}