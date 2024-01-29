package Overloading

import atomictest.eq

class My {
    fun foo() = 0
}

fun My.foo() = 1            // 1. 멤버와 시그니처가 중복되는 확장 함수를 호출해도 의미가 없다. 이런 확장 함수는 결코 호출될 수 없다.

fun My.foo(i : Int) = i + 2 // 2. 다른 파라미터 목록을 제공함으로써 멤버 함수를 확장 함수로 오버로딩할 수 있다.

fun main() {
    My().foo() eq 0
    My().foo(1) eq 3
}