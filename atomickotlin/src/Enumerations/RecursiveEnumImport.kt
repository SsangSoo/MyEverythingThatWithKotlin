package Enumerations

import atomictest.eq
import Enumerations.Size.*                      // 1. Size 정의가 들어 있는 파일에서 Size 안의 이름을 Size 정의보다 먼저 임포트한다.

enum class Size {
    Tiny, Small, Medium, Large, Huge, Gigantic
}

fun main() {
    Gigantic eq "Gigantic"                      // 2. import를 하고 나면 아넘 이름을 한정시키지 않아도 된다.
    Size.values().toList() eq                   // 3. values()를 사용해 이넘의 값을 이터레이션할 수 있다. values()는 Array를 반환하므로 toList()를 호출해서 배열을 List로 만든다.
        listOf(Tiny, Small, Medium, Large, Huge, Gigantic)
    Tiny.ordinal eq 0                           // 4. 한 enum 안에서 맨 처음 정의된 상수에 0이라는 ordinal 값이 지정된다. 그 다음부터는 순서대로 1씩 증가된 ordinal 값이 각 상수에 부여된다.
    Huge.ordinal eq 4
}