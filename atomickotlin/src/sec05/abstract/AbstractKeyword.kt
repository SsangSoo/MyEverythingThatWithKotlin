package sec05.abstractClasses

abstract class WithProperty { // Abstract property 'x' in non-abstract class 'WithProperty'
    abstract val x: Int
}

abstract class WithFunctions {
    abstract fun f(): Int
    abstract fun g(n: Double)
}
