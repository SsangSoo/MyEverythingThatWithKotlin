package sec02.objects_everywhere

fun fraction(numerator: Long, denom: Long) =
    numerator.toDouble() / denom


fun main() {
    val num = 1
    val den = 2
    val f = fraction(num.toLong(), den.toLong())
    println(f)  // 0.5
}
