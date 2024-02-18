package sec04.lambdas

import atomictest.*

fun main() {
    run { -> trace("A Lambda") }
    run { trace("Without args") }
    trace eq
"""
A Lambda
Without args
"""
}