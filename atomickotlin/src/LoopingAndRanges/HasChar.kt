package LoopingAndRanges

fun hasChar(s: String, ch: Char): Boolean {
    for(c in s) {
        if(c == ch) return true
    }
    return false
}

fun main() {
    println(hasChar("kotlin", 't')) // true
    println(hasChar("kotlin", 'a')) // false
}