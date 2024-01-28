package NamedAndDefaultArgs

import atomictest.eq


fun color(red: Int, green: Int, blue: Int) =
    "($red, $green, $blue)"

fun main() {
    color(1, 2, 3) eq "(1, 2, 3)" // 1. 이런 코드는 그다지 많은 정보를 전달하지 못하낟. 각 인자의 역할을 알아내려면 함수 문저를 살펴봐야 한다.
    color(
        red = 76,       // 2. 모든 인자의 의미가 명확하다.
        green = 89,
        blue = 0,
    ) eq "(76, 89, 0)"
    color(52, 34, blue = 0) eq // 3. 모든 인자에 이름을 붙이지 않아도 된다.
    "(52, 34, 0)"
}