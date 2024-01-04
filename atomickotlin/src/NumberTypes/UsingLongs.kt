package NumberTypes

fun main() {
    val i = Int.MAX_VALUE
    println(0L + i + i)    // 4294967294                // L을 명시함으로 Overflow 방지
    println(1_000_000 * 1_000_000L)  // 1000000000000   // L을 명시함으로 Overflow 방지

}