package other
import atomictest.eq
import sec03.extension_functions.doubleQuote
import sec03.extension_functions.singleQuote

fun main() {
    "Single".singleQuote() eq "'Single'"
    "Double".doubleQuote() eq "\"Double\""
}