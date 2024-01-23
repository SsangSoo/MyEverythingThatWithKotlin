package Exceptions

import atomictest.capture

fun main() {
    capture {
        "i$".toInt()
    } eq "NumberFormatException: " +
            """For input string: "i$"""
}