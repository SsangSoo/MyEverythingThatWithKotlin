package sec05.downcasting

class SmartCast1(val c: Creature) {
    fun contact() {
        when(c) {
            is Human -> c.greeting()
            is Dog -> c.bark()
            is Alien -> c.mobility()
        }
    }
}

class SmartCast2(var c: Creature) {
    fun contact() {
        when (val c = c) {      // SmartCast1과 같이 하면 오류 발생
            is Human -> c.greeting()
            is Dog -> c.bark()
            is Alien -> c.mobility()
        }
    }
}
// 오류 1. Smart cast to 'Human' is impossible, because 'c' is a mutable property that could have been changed by this time
// 오류 2. Smart cast to 'Dog' is impossible, because 'c' is a mutable property that could have been changed by this time
// 오류 3. Smart cast to 'Alien' is impossible, because 'c' is a mutable property that could have been changed by this time