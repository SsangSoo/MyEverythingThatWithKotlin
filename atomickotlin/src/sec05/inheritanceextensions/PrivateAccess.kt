package sec05.inheritanceextensions
import atomictest.eq

class Z(var i: Int = 0) {
    private var j = 0
    fun increment() {
        i++
        j++
    }
}

fun Z.decrement() {
    i--
    // j-- 접근할 수 없음
}