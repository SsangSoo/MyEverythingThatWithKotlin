package NumberTypes

fun main() {
    val i = 0        // Int 타입을 추론
    val l1 = 0L      // L을 사용해 Long 타입으로
    val l2: Long = 0 // 명시적으로 타입을 저장
    println("$l1 $l2") // 0 0
}