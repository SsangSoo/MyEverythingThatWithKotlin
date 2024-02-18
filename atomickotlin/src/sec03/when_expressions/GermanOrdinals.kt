package sec03.when_expressions

import atomictest.eq

val numbers = mapOf(
    1 to "eins", 2 to "zwei", 3 to "drei",
    4 to "vier", 5 to "fuenf", 6 to "sechs",
    7 to "sieben", 8 to "acht", 9 to "neun",
    10 to "zhen", 11 to "elf", 12 to "zwoelf",
    13 to "dreizehn", 14 to "vierzehn",
    15 to "fuenfzehn", 16 to "sechzehn",
    17 to "siebzehn", 18 to "achtzehn",
    19 to "neunzehn", 20 to "zwanzig",
)

fun ordinal(i: Int): String =
    when(i) {                               // 1. when 식은 i를 본문의 매치식과 비교한다.
        1 -> "erste"                        // 2. 가장 먼저 일치하는 매치식에서 when 식의 실행이 끝난다. 여기서는 String 값이 생성되며, 이 값이 ordinal()의 반환값이 된다.
        3 -> "dritte"
        7 -> "siebte"
        8 -> "achte"
        20 -> "zwanzigste"
        else -> numbers.getValue(i) + "te"  // 3. else 키워드는 일치하는 매치식이 없을 때 대안으로 사용할 식을 제공한다. else는 항상 매치 목록의 맨 마지막에 있어야 한다. 2를 사용해 테스트한 경우, 이 값은 1, 3, 7, 8, 20과 일치하지 않으므로 else가 선택된다.
    }

fun main() {
    ordinal(2) eq "zweite"
    ordinal(3) eq "dritte"
    ordinal(11) eq "elfte"
}