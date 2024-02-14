package Lambdas

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