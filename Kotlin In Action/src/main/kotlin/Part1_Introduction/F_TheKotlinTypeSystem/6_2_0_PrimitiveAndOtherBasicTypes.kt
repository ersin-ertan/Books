package Part1_Introduction.F_TheKotlinTypeSystem

// Int, Boolean and Any and more, Kotlin doesn't differentiate primitve types and wrappers. Correspondence of
// kotlin and java for types like Object, Void.

// Java distinguishes primitive types and reference types. Primitves hold heir value directly, reference store the
// memory location containing the object. Primitives stored and passed around efficiently, but no methods.
// Primitives are compiled to java primitives for runtime. Except for generic classes, ex. collection which stores
// the wrapped type

// Nullable primitive types - you need to compare if null, then the values if coming from java

// If you need a large collection of primitive types consider Trove4J for collections or store them in arrays

// Number conversions - kotlin doesn't automatically convert numbers from one type to the other
val ii = 1
//val l:Long = ii // error type mismatch
val myLong:Long = ii.toLong()

// conversion is implicit to avoid surprises when comparing boxed values since equals for two boxed values
// compare type not the value stored

val long:Long = 1L
val double:Double = 1.3e10
val float:Float = 1.3F
val hex = 0xCAFEBABE
val binary = 0b10101

// type conversion however is applied automatically if you use a number literal to initialize a variable of a known
// type or pass it as an argument to a function. Kotlins number range overflow is the same as java, no overhead for
// overflow checks

// Any Any? the root types for all non nullable types, whereas javas Object is the root of all reference types, excluding
// primitives. A primitive to Any is auto boxed. Wait notify are not inherited from javas Object, the type must be cast
// if you need those calls.

// Unit type - kotlins void. Can be used as the return type for functions that return nothing, and is implicitly done
// so if no return is specified. Useful when you override a function that returns a generic parameter and make it return
// a value of the Unit type

interface Processor<T> {
    fun process():T
}

class NoResult:Processor<Unit> {
    override fun process() {} // needs no return, its implicit
}

// nothing type - the function that never returns like a testing libraries fail function
// an infinite loop method too, thus we can return Nothing to enforce(at compile time) that it doesn't return.