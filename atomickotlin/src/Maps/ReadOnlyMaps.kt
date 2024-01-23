package Maps

import atomictest.eq

fun main() {
    val m = mapOf(5 to "five", 6 to "six")
    m[5] eq "five"
    // m[5] = "5ive"        // 실패함
    // m += (4 to "four")   // 실패함
    m + (4 to "four")       // m을 바꾸지 ㅏㅇㄶ음
    m eq mapOf(5 to "five", 6 to "six")
    val m2 = m + (4 to "four")
    m2 eq mapOf(
        5 to "five", 6 to "six", 4 to "four")
}