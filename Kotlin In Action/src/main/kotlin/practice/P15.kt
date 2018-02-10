package practice

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

fun main(args: Array<String>) {

    class L {
        val a by lazy {
            println("Lazy start")
            Thread.sleep(300)
            println("Lazy end")
            "lazy return value"
        }
    }

    class O(input: String = "O input") {
        var s: String by Delegates.observable(input, { p: KProperty<*>, oldV: String, newV: String ->
            println("old:''$oldV'' " +
                    "and " +
                    "new:''$newV ''" +
                    "and " +
                    "property:${p}")
        })
    }

    fun count() {
        println("Lazy created")
        val l = L()
        val o = O()
        println("count start")
        (1..10).forEach {
            Thread.sleep(100)
            println(it)

            if (it == 6) {
                println("count print return value" + l.a)
                println("o.s ${o.s}")
                o.s = "o.s new value"
                println("o.s ${o.s}")
            }

        }
        println("Count end")
    }

//    count()

    open class A {
        val maker:A = object:A() {
            fun makeA():A = A()
        }
    }

    class B : A()
    class C

    open class D<in A>
    open class E<in B>

    D<A>()
    E<A>()

    D<B>()
    E<B>()

    open class F<out A>
    open class G<out B>

    F<A>()
    G<A>()

    F<B>()
    G<B>()

    // this is all ok, until

    class H<in A> {
        fun aIn(a: A) {            /* can use the A type as the in*/
        }

//        fun aOut(a: A): A = a // but cannot return it, Type parameter is declared in, not out
    }

    class I : D<A>() {

        fun aIn(a: A) {
            // can use the A type as the in
        }

        fun aOut(a: A): A = a // and even return it when extended
    }

    class J<out A : Any> { // out read only

//        lateinit var a: A // lateinit is not allowed with nullable upper bound thus we bound the A
        // type parameter is out but occurs as invariant, thus not allowed
//        fun aIn(a: A) {} // type parameter is A but is declared out

        val a
        get() = A() // after bounding the A to a non nullable, now we can return it.
    }


}


