package Lists

import atomictest.eq

fun main() {
    // 'val'/'var'에 가변 리스트를 대입하는 경우 :
    val list1 = mutableListOf('A') // or 'var'
    list1 += 'A' //  다음 줄과 같다.
    list1.plusAssign('A')

    // 'val'에 불변 리스트를 대입하는 경우 :
    val list2 = listOf('B')
//    var list2 = listOf('B')
//    list2 += 'B' // Val cannot be reassigned
//    list2 = list2 + 'B' // Val cannot be reassigned

    // 'var'에 불변 리스트를 대입하는 경우 :
    var list3 = listOf('C')
    list3 += 'C' // 다음 줄과 같다.
    val newList = list3 + 'C'
    list3 = newList

    list1 eq "[A, A, A]"
    list2 eq "[B]"
    list3 eq "[C, C, C]"
}