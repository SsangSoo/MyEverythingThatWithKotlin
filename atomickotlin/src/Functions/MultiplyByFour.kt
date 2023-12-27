package Functions

// 코틀린은 식 본문을 사용하는 함수의 반환 타입을 추론한다.
fun multiplyByFour(x : Int) = x * 4

fun main() {
    val result : Int = multiplyByFour(5)
    println(result) // 20
}