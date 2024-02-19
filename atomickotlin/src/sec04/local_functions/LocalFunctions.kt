package sec04.local_functions

import atomictest.eq

fun main() {
    val logMsg = StringBuilder()
    fun log(message: String) =
        logMsg.appendLine(message)
    log("Starting computation")
    val x = 42 // 계산을 흉내 냄
    log("Computation result: $x")
    logMsg.toString() eq
"""
Starting computation
Computation result: 42
"""
}