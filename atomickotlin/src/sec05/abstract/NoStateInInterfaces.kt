package sec05.abstractclasses

interface IntList {
    val name: String
    // 컴파일 되지 않는다.
    // val list = listOf(0) // Property initializers are not allowed in interfaces
}