package ch03

import p

/*
3.6.2 Invariant functors and the imap method

imap - informally equal to a combination of mmap and contramap.
Map generates new type class insntances by appending function, contramap by prepending, and imap via a pair of
bidirectional transformations.
Encoding and decoding data.
 */

interface Codec<A> {
    fun encode(value: A): String
    fun decode(value: String): A
    fun <B> imap(de: (A) -> B, en: (B) -> A): Codec<B> = object : Codec<B> {
        override fun encode(value: B): String = this@Codec.encode(en(value))
        override fun decode(value: String): B = de(this@Codec.decode(value))
    }
}

fun <A> encode(value: A, implicitCodec: Codec<A>): String = implicitCodec.encode(value)
fun <A> decode(value: String, implicitCodec: Codec<A>): A = implicitCodec.decode(value)

// Type chart for imap
// F<Circle> imap Circle->Star, Star->Circle --> F<Star>

val stringCodec: Codec<String> = object : Codec<String> {
    override fun <B> imap(de: (String) -> B, en: (B) -> String): Codec<B> = object : Codec<B> {
        override fun encode(value: B): String = en(value) // not sure
        override fun decode(value: String): B = de(value)// not sure
    }

    override fun encode(value: String): String = value
    override fun decode(value: String): String = value
}

val intCodec: Codec<Int> = stringCodec.imap({ it.toInt() }, Int::toString) // method ref vs lambda'd it ref
val booleanCodec: Codec<Boolean> = stringCodec.imap(String::toBoolean, Boolean::toString)

// coping with failure - decode method type class doesn't account for failures
// modeling sophisticated relationships can move from functors to lenses and optics

// 3.6.2.1 Transformative Thinking with imap

val doubleCodec = stringCodec.imap<Double>(String::toDouble, Double::toString)
fun testDoubleCodec() {
    doubleCodec.decode("3.9").times(1).p()
    doubleCodec.encode(2.8).plus("!").p()
}

fun implementationForBox() {

    class Box<A>(val value: A) {
        override fun toString(): String = "${this.javaClass.simpleName}:$value"
    }

    fun <A> boxCodec(implicitCodec: Codec<A>) = implicitCodec.imap(::Box, { it.value })

    encode(123.3, doubleCodec).p()
    decode("234.3", doubleCodec).p()

    encode(Box(234.3), boxCodec(doubleCodec)).p()
    decode("234.2", boxCodec(doubleCodec)).p()
}

fun main(args: Array<String>) {
//    testDoubleCodec()
    implementationForBox()
}

// whats with the names - subtyping can be viewed as a conversion. If B is a subtype of A, then we can always convert
// a B to A

// If B is a subtype of A, if there exists a function of A -> B Covariant functor captures this
// if F is a covariant functor, where we have F<A> and a conversion A->B then we can convert F<B>

// Contravariant functor captures the opposite case. F is contravariant functor, when we have F<A> and a B->A
// conversion function then we can convert  it to F<B>

// Invariant functors capture case where we convert F<A> to F<B> via A->B and vice versa via B->A