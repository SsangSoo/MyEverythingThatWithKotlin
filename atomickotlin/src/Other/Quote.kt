package Other
import atomictest.eq
import ExtensionFunctions.doubleQuote
import ExtensionFunctions.singleQuote

fun main() {
    "Single".singleQuote() eq "'Single'"
    "Double".doubleQuote() eq "\"Double\""
}