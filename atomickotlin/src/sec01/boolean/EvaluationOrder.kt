package sec01.boolean

fun main() {
    val sunny = true
    val hoursSleep = 6
    val exercise = false
    val temp = 55

    // 1
    val happy1 = sunny && temp > 50 ||
            exercise && hoursSleep > 7
    println(happy1) // false >>> true 우선순위 && 가 || 보다 먼저다.

    // 2
    val sameHappy1 = (sunny && temp > 50) ||
            (exercise && hoursSleep > 7)
    println(sameHappy1) // true

    // 3
    val notSame =
        (sunny && temp > 50 || exercise) && hoursSleep > 7
    println(notSame) // false
}