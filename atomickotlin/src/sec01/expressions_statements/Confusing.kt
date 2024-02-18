package sec01.expressions_statements

fun main() {
    var i = 1
    println(i++ + ++i)  // 4 // 1  -> 2 -> ++2 =3
    // 윗줄 보다는 덜 복잡하지만, 아래 두 줄의 코드도 바람직하지는 않다.
    println(i++ + 10)   // 13 // 3 + 10 // i = 4
    println(20 * ++i)   // 100 // 20 * 4+1
}