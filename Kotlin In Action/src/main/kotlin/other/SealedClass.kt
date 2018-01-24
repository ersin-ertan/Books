package other

sealed class SealedClass // already abstract
sealed class SealedClassConst {
    constructor()
    constructor(int:Int):this()
    constructor(b:Boolean):this()
} // already abstract

// for restricted class hierarchies, when the value can have one of the types from a limited set, and can't have
// any other type. Like an extension of enum classes, restricted by type, and each constant a single instance, but
// subclass of sealed class can have multiple instances which can contain state

// can have subclasses, but must be declared in the same file

data class SealedClass1(val num:Int):SealedClass()

data class SealedClass2(val num:Int):SealedClass()

object SealedClass3:SealedClass() {
    val a:Int = 0
    val b:Int

    init {
        b = 0
    }
}

abstract sealed class SealedClassAbstract // sealed implies abstract

fun main(args:Array<String>) {
//    SealedClass
    SealedClass1(1)
    SealedClass2(2)
    SealedClass3.b
}

// not allowed to have non private constructors, are private by default

open class SealedClass4:SealedClass() // child class of this class (indirect inheritor) can be placed anywhere

// great for when as an expression(using the result), but not a statement, you don't need the else clause
// because like enums you can provide use cases for each type

fun sealed(sc:SealedClass):Int = when (sc) {
    is SealedClass -> 1
    is SealedClass1 -> 2
    is SealedClass2 -> 3
    is SealedClass3 -> 4
    is SealedClass4 -> 5
    is IndirectInheritor -> 6
// no need for else
}

sealed class SC(int:Int)


class SC1:SC(2)
class SC2(int:Int):SC(int)
object SC3:SC(3)

class SC4:SealedClass {
    constructor() // do nothing
    constructor(int:Int)
}

class Sc5:SealedClassConst {
    constructor(d:Double):super()
    constructor(int:Int):super(true)
    constructor(b:Boolean):super(true)
    constructor(b:Boolean, i:Int):super(3)
}
