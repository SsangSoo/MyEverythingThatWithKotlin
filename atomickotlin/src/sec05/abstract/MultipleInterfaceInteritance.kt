package sec05.abstract2

interface Animal
interface Mammal: Animal
interface AquaticAnimal: Animal

class Dolphin: Mammal, AquaticAnimal