package destructuring.compute

import atomictest.eq
import destructuring.compute

fun main() {
    val (value, description) = compute(7)
    value eq 14
    description eq "High"
}