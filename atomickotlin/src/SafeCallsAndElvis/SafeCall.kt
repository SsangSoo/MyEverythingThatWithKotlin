package SafeCallsAndElvis

import atomictest.eq

fun checkLength(s: String?, expected: Int?) {
    val length1 =
        if (s != null) s.length else null   // 1
    // val length1 = s?.length // 위와 같음. // 아래 식과 같은 형태
    val length2 = s?.length                 // 2
    length1 eq expected
    length2 eq expected
}

fun main() {
    checkLength("abc",3)
    checkLength(null,null)
}