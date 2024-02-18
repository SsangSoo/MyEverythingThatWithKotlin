package sec03.destructuring.compute

import atomictest.eq
import sec03.destructuring.compute

fun main() {
    val (value, description) = compute(7)
    value eq 14
    description eq "High"
}