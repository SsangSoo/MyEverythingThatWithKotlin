package sec05.abstract

open class Animal
open class Mammal : Animal()
open class AquaticAnimal : Animal()

// 기반 클래스가 둘 이상이면 컴파일이 되지 않는다.
//class Dolphin: Mammal(), AquaticAnimal() // Only one class may appear in a supertype list