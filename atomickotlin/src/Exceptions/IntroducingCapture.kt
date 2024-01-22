package Exceptions

import Appendices.AtomicTest.Examples.*

fun main() {
    capture {
        "i$".toInt()
    } eq "NumberFormatException: " +
            """For input string: "i$"""
}