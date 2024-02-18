package sec04.importance_of_lambdas

import atomictest.eq

var x = 100

fun useX() {
    x++
}

fun main() {
    useX()
    x eq 101
}