package sec04.building_maps

import atomictest.eq

fun main() {
    // associateBy()는 키가 유일하지 않은 경우 실패한다.
    // 즉, 원본의 값 중 일부가 사라진다.
    val ages = people().associateBy { it.age }
    ages eq mapOf(
        21 to Person("Franz", 21),
        15 to Person("Arthricia", 15),
        25 to Person("Bill", 25),
        42 to Person("Crocubot", 42),
        33 to Person("Revolio", 33),
    )
}

// 키와 연관된 값이 여럿 존재하는 경우, 같은 키를 내놓는 값 중 컬렉션에서 맨 나중에 나타나는 원소가 생성되는 Map에 포함된다.