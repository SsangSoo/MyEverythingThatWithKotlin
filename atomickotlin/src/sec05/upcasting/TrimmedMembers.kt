package sec05.upcasting
import atomictest.*

fun trim(shape : Shape) {
    trace(shape.draw())
    trace(shape.erase())
    // 컴파일되지 않는다.
    // shape.color() // 1
    // shape.rotate() // 2
}

fun main() {
    trim(Square())
    trim(Triangle())
    trace eq
"""
Square.draw
Square.erase
Triangle.draw
Triangle.erase
"""
}