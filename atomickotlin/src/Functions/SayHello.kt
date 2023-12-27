package Functions

fun sayHello() {    // 반환 타입 Unit = > 생략 가능
    println("Hallo!")
}

fun sayGoodbye(): Unit {    // 반환 타입 Unit
    println("Auf Wiedersehen!")
}

fun main() {        // main도 Unit
    sayHello()
    sayGoodbye()
}

// 결과
    // Hallo!
    // Auf Wiedersehen!