package SafeCallsAndElvis

fun main() {
    val s: String? = null
    // 컴파일되지 않는다.
//     s.length // Only safe (?.) or non-null asserted (!!.) calls are allowed on a nullable receiver of type String?
}