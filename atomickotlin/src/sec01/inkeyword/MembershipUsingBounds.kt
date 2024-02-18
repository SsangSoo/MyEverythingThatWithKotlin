package sec01.inkeyword

fun main() {
    val percent = 35
//    println(0 <= percent && percent <= 100)
    println(percent in 0..100) // 두 형태는 같다. 인텔리제이가 아래 형태로 바꾸라고 권장한다.
}