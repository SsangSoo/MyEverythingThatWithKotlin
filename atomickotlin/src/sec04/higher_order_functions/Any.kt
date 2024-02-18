package sec04.higher_order_functions

import atomictest.eq

fun <T> List<T>.any(                            // 1 : any()를 여러 타입의 List에 대해 호출할 수 있어야 하므로 제네릭 List<T> 타입의 확장 함수로 정의한다.
    predicate: (T) -> Boolean                   // 2 : predicate 함수를 리스트의 원소에 적용할 수 있어야 하므로 이 함수는 파라미터 타입 T를 인자로 받는 함수여야 한다.
): Boolean {
    for(element in this) {
        if(predicate(element))                  // 3 : predicate() 함수를 적용하면 선택 기준을 element가 만족하는지 알 수 있다. 람다의 파라미터 타입이 다르다. 4 에서는 Int이고, 5 에서는 String이다.
            return true
    }
    return false
}

fun main() {
    val ints = listOf(1, 2, -3)
    ints.any { it > 0 } eq true                 // 4
    val strings = listOf("abc", " ")
    strings.any { it.isBlank() } eq true        // 5
    strings.any(String::isNotBlank) eq true     // 6
}