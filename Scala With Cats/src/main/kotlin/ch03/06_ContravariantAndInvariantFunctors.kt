package ch03

import p

/*
3.6 Contravariant and Invariant Functors

Functor's map is like appending a transformation to a chain. What about prepending, and bidirection chains, aka
Contravariant and invariant functors

Contramap method
Box<Star> contramap Circle->Star --> Box<Circle>

Contravariant and invariant functors are important for Semigroupal and Applicative

Contravariant functors(prepending and opperation to achain) and contramap method

Contramap makes sense for data types representing transformations. Can't define for option because there is no way to
feed a value into an Option<B> backwards through a function A->B, but we can for printable

interface Printable<A> fun format(value:A):String

printable represents a transformation from A to string, contramap method accepts a function of type B->A and creates a
new printable<B>

fun contramap<B>(func:B->A):Printable<B> = ...

fun <A> format(value:A, p:Printable<A>):String = p.format(value)

*/

interface Printable<A> {
    fun format(value: A): String
    fun <B> contramap(func: (B) -> A): Printable<B> = object : Printable<B> {
        override fun format(value: B): String = this@Printable.format(func(value))
    }
}

fun <A> format(value: A, implicitPrintable: Printable<A>): String = implicitPrintable.format(value)

val stringPrintable = object : Printable<String> {
    override fun format(value: String): String = """\$value\"""
}
val booleanPrintable: Printable<Boolean> = object : Printable<Boolean> {
    override fun format(value: Boolean): String = if (value) "yes" else "no"
}

fun main(args: Array<String>) {
    testPrintables()
}

fun testPrintables() {

    format("hello", stringPrintable).p()
    format(true, booleanPrintable).p()

    class Box<A>(val value: A)

    fun <A> boxPrintable(implicitPrintable: Printable<A>) = object : Printable<Box<A>> {
        override fun format(value: Box<A>): String = implicitPrintable.format(value.value)
    }

    fun <A> boxPrintable2(implicitPrintable: Printable<A>) = implicitPrintable.contramap<Box<A>> { it.value }

    boxPrintable(stringPrintable).format(Box("hello world")).p()
    boxPrintable2(stringPrintable).format(Box("hello world2")).p()
    boxPrintable2(booleanPrintable).format(Box(true)).p()
}

