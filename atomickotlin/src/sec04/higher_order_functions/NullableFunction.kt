package sec04.higher_order_functions

import atomictest.eq

fun main() {
    val returnTypeNullable: (String) -> Int? =
        { null }
    val mightBeNull: ((String) -> Int)? = null
    returnTypeNullable("abc") eq null
    // 널 검사를 하지 않으면 컴파일이 되지 않는다.
    // mightBeNull("abc")
    if(mightBeNull != null) {
        mightBeNull("abc")
    }
}