package CreatingClasses

class Cat {
    fun meow() = "mrrrow!"
}

fun main() {
    val cat = Cat()
    // 'cat'에 대해 `meow()`를 호출한다.
    val m1 = cat.meow()
    println(m1)
}