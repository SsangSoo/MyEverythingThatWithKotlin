package StringTemplates

fun main() {
    val condition = true
    println(
        "${if (condition) 'a' else 'b'}")
    val x = 11
    println("$x + 4 = ${x + 4}") // x가 ${} 안 에 들어가면 String 타입의 11이 된다.
}