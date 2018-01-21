package C_DesiginingFunctionalDomainModels

import A_functionalDomainModeling.p
import arrow.optics.Lens
import arrow.optics.modify

// 3.3.5 Updating aggregates functionally with lenses

// Immutability/ref transparency what about updates - generate a new instance of the object with updated values
// scala has a nice way to do this with the copy function. But does it scale? when nested?

fun main(args: Array<String>) {
    Ex06().usage()
}

class Ex06 {


    class M(val a: Int, val b: Modify) {
        fun copy(a: Int = this.a, b: Modify = this.b): M = M(a, b)
        override fun toString(): String = "{a:${a}, b:$b}"
    }

    class Modify(val a: Int, val b: String) {
        fun copy(a: Int = this.a, b: String = this.b): Modify = Modify(a, b)
        override fun toString(): String = "[a:${a}, b:${b}]"


    }

    val aLens: Lens<Modify, Int> = Lens(
            get = { mod -> mod.a },
            set = { value -> { mod -> mod.copy(a = value) } }
    )

    val mALens: Lens<M, Int> = Lens(
            get = { m -> m.a },
            set = { value -> { m -> m.copy(a = value) } }
    )
    val mBLens: Lens<M, Modify> = Lens(
            get = { m -> m.b },
            set = { value -> { m -> m.copy(b = value) } }
    )


    fun usage() {
        val a = Modify(1, "2")
        val b = a.copy(b = "3")

        (a == Modify(1, "2")).p()
        (b == a).p()
        (b == Modify(1, "3")).p()

        (a.equals(Modify(1, "2"))).p()
        (b.equals(a)).p()
        (b.equals(Modify(1, "3"))).p()

        a.p()
        b.p()

        // ---

        val c = M(1, a)
        val d = c.copy(b = c.b.copy(b = "4"))

        // greater the nesting the more cluttered the syntax becomes. This algebraic functional abstraction is the lens.
        // parametricity: parametric on the type of the object to update, and on the type of the field updated thus
        // class Lens<Obj, Val>

        // one lens per field: every object needs a lens for every field, though it may be verbose, you can use libs that
        // have macros to get past this

        // getter: algebra of the lens need to publish a getter for accessing the current value of the field
        // seter: take objct and new value, return new object of same type with updated value

        /*
        class Lens<O,V>{ get:O->V set:(O,V)->O }
        Solves bidirectional transformation problems like view updates in relational setting/ transforming tree structured data
        See Scalaz, and Shapeless
        */

        "the lens get".p()
        val newA = aLens.get(a)
        newA.p()
        "old mod".p()
        a.p()
        "lens set".p()
        val newMod = aLens.set(a, 5)
        newMod.p()

        "lens modify".p()
        val newModd = aLens.modify(a) { it + it }
        newModd.p()

        // ---

        // how do we deal with nested, we compose because types align
        // Define a generic compose function, so there is no need to repeat the lenses code.

        /*
   fun <Outer, Inner, Value>compose(
           outer:Lens<Outer, Inner>,
           inner:Lens<Inner, Value>) = Lens<Outer, Value>(
           get = {outer.get andThen inner.get}
           set = {(obj,value)-> outer.set(obj,inner.set(outer.get(obj),value)}
   )
   now you can use the compose function to create a bigger lens that traverses the nested data
*/

        fun composeUsage() {

            "compose usage".p()
            val modA: Lens<M, Int> = mBLens compose aLens
            modA.p()
            val modAmod = modA.modify(c, { it + 10  })
            modAmod.p()
        }

        composeUsage()

    }

    // the client of your api cant directly access lower level nonroot aggregate elements
    // But you can use lens composition by exposing tob level lenses that allow transformation of lower
//    level objects only through the root element

    // commonly the composition with the state monad(a way to manage app states that change with time without using
    // in place mutation) use a lens to do the update part functionally

    // instead of using lenses for each value, consider @lenses for data classes
}

// Lens laws: adt lens defines an algebra of the abstraction. Every algebra comes with a set of laws to honour to ensure
// consistency. Informally there are three, worth documenting them and verifying with property based testing on new definitions

// Identity: get then set back with same value, object is unchanged
// Retention: set a value, perform get, you get what you had set with.
// Double set: set twice in succession, then get, you get back the last set.

// Thus lenses updating aggregates must satisfy this. see scalaCheck
