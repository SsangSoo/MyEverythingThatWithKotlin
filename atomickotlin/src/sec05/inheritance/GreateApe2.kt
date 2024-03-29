package sec05.inheritance.ape2

import atomictest.eq

open class GreateApe {
    protected var energy = 0
    open fun call() = "Hoo!"
    open fun eat() {
        energy += 10
    }
    fun climb(x: Int) {
        energy -= x
    }
    fun energyLevel() = "Energy: $energy"
}

class Bonobo : GreateApe() {
    override fun call() = "Eep!"
    override fun eat() {
        // 기반 클래스의 프로퍼티를 변경한다.
        energy += 10
        // 기반 클래스의 함수를 호출한다.
        super.eat()
    }
    // 함수를 추가한다.
    fun run() = "Bonobo run"
}

class Chimpanzee : GreateApe() {
    // 새 프로퍼티
    val additionalEnergy = 20
    override fun call() = "Yawp!"
    override fun eat() {
        energy += additionalEnergy
        super.eat()
    }
    // 함수를 추가한다.
    fun jump() = "Chimp jump"
}

fun talk(ape: GreateApe): String {
    // ape.run()  // ape의 함수가 아니다.
    // ape.jump() // 역시 ape의 함수가 아니다.
    ape.eat()
    ape.climb(10)
    return "${ape.call()} ${ape.energyLevel()}"
}

fun main() {
    // 'energy'에 접근할 수 없다.
    // GreateApe().energy
    talk(GreateApe()) eq "Hoo! Energy: 0"
    talk(Bonobo()) eq "Eep! Energy: 10"
    talk(Chimpanzee()) eq "Yawp! Energy: 20"
}