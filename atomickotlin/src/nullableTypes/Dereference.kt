package nullableTypes

import atomictest.eq

fun main() {
    val s1: String = "abc"
    val s2: String? = s1

    s1.length eq 3  // 1
    // 컴파일되지 않는다.
//     s2.length    // 2  // Only safe (?.) or non-null asserted (!!.) calls are allowed on a nullable receiver of type String?

}