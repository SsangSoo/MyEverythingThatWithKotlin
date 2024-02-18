package sec03.nullable_extensions

import atomictest.eq

fun isNullOrEmpty(s: String?): Boolean =
    s == null|| s.isEmpty()

fun main() {
    isNullOrEmpty(null) eq true
    isNullOrEmpty("") eq true
}