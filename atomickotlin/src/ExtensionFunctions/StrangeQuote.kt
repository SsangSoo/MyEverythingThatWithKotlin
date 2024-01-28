package ExtensionFunctions

import atomictest.eq

// singleQuote()를 두 번 적용해서 작은따옴표를 두 개 붙인다.
fun String.strangeQuote() =
    this.singleQuote().singleQuote() // 1 : this는 String 수신 객체 타입에 속하는 객체를 가리킨다.

fun String.tooManyQuotes() =
    doubleQuote().doubleQuote()      // 2 : 최초로 doubleQuote() 함수를 호출할 때 수신 객체(this)를 생략한다.

fun main() {
    "Hi".strangeQuote() eq "''Hi''"
    "Hi".tooManyQuotes() eq "\"\"Hi\"\""
}


