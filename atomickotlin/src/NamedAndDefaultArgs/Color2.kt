package NamedAndDefaultArgs

import atomictest.eq

fun color2(
    red: Int = 0,
    green: Int = 0,
    blue: Int = 0,
) = "($red, $green, $blue)"

fun main() {
    color2(139) eq "(139, 0, 0)"
    color2(blue = 139) eq "(0, 0, 139)"
    color2(255, 165) eq "(255, 165, 0)"
    color2(red = 128, blue = 128) eq
            "(128, 0, 128)"
}
