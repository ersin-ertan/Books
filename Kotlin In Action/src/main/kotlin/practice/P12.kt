package practice

import p

infix fun Any.and(any:Any) {
    print(this)
    print(" ")
    println(any)
}

fun Any.fields() {
    "Declared Fields:".p()
    this.javaClass.declaredFields.forEach { println("\t${it.name}:${it.type}") }
    println()
}

fun Any.methods() {
    "Declared Methods:".p()
    this.javaClass.declaredMethods.forEach { println("\t${it.name}():${it.returnType.simpleName}") }
    println()
}

private class P12 {

    interface I {
        fun f()
    }

    val test = 3

    fun testInfix() {

        "Strings" and listOf<Int>()

        listOf(1) and "hello"
    }

    fun withParams(myInt:Int, d:Double):Pair<Int, Double> = Pair(myInt, d)

}

internal class MyInternalClass {} //

fun main(args:Array<String>) {
    P12().testInfix()

    P12().fields()
    P12().methods()

    var (a, b) = P12().withParams(1, 2.3)

    a and b
}
// java encapsulation can be broken if external code defines classes in the same packages used by code thrrough package
// local declarations

// kotlin visibility modifiers
/*
* modifier          class member            top level declaration
* public(default)   visible everywhere      visible everywhere
* internal          visible in module       visible in module
* protected         visible in subclasses   ---
* private           visible in class        visible in file
* */

// for protected, a member is only visible in the class and subclasses. Excluding extension functions.


private class OuterClass {

    // innerclass does not have access to outer class
    class InnerClass {}

    // using an inner class in java implicitly has a reference to the outer class, thus attempting to serialize
    // the inner class, which is a helper class for state will attempt serialize the out too, which will throw
    // NotSerializableException, thus we make the inner class static

    // kotlins inner class has no reference to the outer class unless specified

    inner class InnerWithOuterRef() {
        val theOuterRef = this@OuterClass // no need to encapsulate the variable, just showing it
    }
}

private sealed class Sealed {
    // can only make subclasses within this file

    open fun c() = println(0) // requires explicit open

    class A:Sealed() {
        override fun c() = println(1)
    }

    class B:Sealed() {
        override fun c() = println(2)
    }


    class SealCaller() {

        fun callUse() {
            useSealed(A())
            useSealed(B())
//        useSealed(Sealed()) // sealed types cannot be instantiated
        }

        fun <T:Sealed> useSealed(t:T) {
            when (t) {
                is Sealed.A -> t.c() // why am I not being asked to cover Sealed.B
            // no need for else
            }
        }
    }
}