package ch01

import arrow.core.Option
import arrow.core.none
import arrow.core.some
import arrow.instances.IntEqInstance
import arrow.instances.OptionEqInstance
import arrow.typeclasses.Eq
import arrow.typeclasses.Show
import java.util.*

// question: why is the show only working within run/apply/...
/*
* about show
our encoding for typeclasses using extfuns requires applying them to access the extfuns declared inside them
apply/run/with
it's different than Scala's because we don't have implicits to do compile-time resolution
instead we rely on extfuns and the typeclass encoding
http://arrow-kt.io/docs/patterns/dependency_injection/

https://gitter.im/arrow-kt/Lobby?at=5aea8f3bf2d2d537045be894
*/

fun main(args: Array<String>) {
//    testingImplicits()
//    testingSyntax()
//    showEx()
    comparingWithEq()
}


fun testingImplicits() {
    val a = JsonInterfaceObject.toJson(Person("dave", "dave@gmail.com"), JsonWriterInstances.personWriter)
    println(a)
}

// kotlin does not have implicits thus, we must always provide an Instance

// Interface syntax - or use extension method to existing types with interface methods

object JsonSyntax {
    class JsonWriterOps<A>(val value: A) {
        fun A.toJson(w: JsonWriter<A>): Json = w.write(value)
    }
}

fun testingSyntax() {
    // NoSuchMehodException... thus JsonSyntax doesn't work
//    println(JsonSyntax.JsonWriterOps<Person>(Person("a", "b")).javaClass.getDeclaredField("value").type.getDeclaredMethod("toJson", JsonWriterInstances.personWriter.javaClass).invoke(Person("a", "b"), JsonWriterInstances.personWriter).toString())
//    Person("Dave", "da@g.com").toJson
//    Person("dave", "d@id.co").toJson(personWriter) // again doesn't work unless you make an extension function for all objects
    fun Person.toJson(w: JsonWriter<Person>): Json = w.write(this)
    Person("d", "d@D.d").toJson(JsonWriterInstances.personWriter)
}

// implicitly method - generic type class interface, to get the value from implicit scope
//fun implicitly<A>( implicit value:A):A = value
// implicitly<JsonWriter<String> but since cats provides other means to summon instances we don't need it

// working with implicits - packaging, scope, recursive implicit resolution

// 1.3 Exercise: Printable Library - define Printable type class to work around:
// 1. Define a type class Printable<A> containing a single method format accepting value of type a and return string
interface Printable<A> {
    fun format(value: A): String
}

// 2. create an object PrintableInstances containing instances of Printable fo String and Int
object PrintableInstances {
    val stringPrinter = object : Printable<String> {
        override fun format(value: String): String = value

    }
    val intPrinter = object : Printable<Int> {
        override fun format(value: Int): String = value.toString()
    }
}

object PrintableInterfaceObject {
    fun <A> format(value: A, printable: Printable<A>): String = printable.format(value)
    fun <A> print(value: A, printable: Printable<A>): Unit = println(printable.format(value))
}

// solution

class Cat(val name: String, val age: Int, val color: String)

fun solution() {
    val cat = Cat("Scratchy", 1, "Brown")

    val catPrintable = object : Printable<Cat> {
        override fun format(value: Cat): String {
            val name = PrintableInterfaceObject.format(value.name, PrintableInstances.stringPrinter)
            val age = PrintableInterfaceObject.format(value.age, PrintableInstances.intPrinter)
            val color = PrintableInterfaceObject.format(value.color, PrintableInstances.stringPrinter)
            return "$name is $age year-old $color value"
        }
    }

    PrintableInterfaceObject.print(cat, catPrintable)

//    or

    fun Cat.format(catPrintable: Printable<Cat>): String = catPrintable.format(this)
    fun Cat.print(catPrintable: Printable<Cat>): Unit = println(catPrintable.format(this))

    cat.print(catPrintable) // instead of using interface objects
}

// Meet cats - how to implement type classes in cats, ex cats.Show

//    interface Show<A>{
//        fun show(value:A):String
//    }

// the companion object of every cats type class has apply locating an instance for any type we specify

//  Show.apply { Int } // remember you need the instances package, but apply is already used in Kotlin so we use:
fun showEx() {

    // this might be typealiasing
//    val showInt:Show<Int> = Show.apply{Int} // now working
//    val intAsString:String = showInt.show(234)
    // using invoke and run???????? !! no need to use invoke just use the constructor
    val showInt = Show.invoke<Int> { it.toString() }
    val intAsString1 = showInt.run { 234.show() }
    println("intAsString1 = $intAsString1")

//    can define custom instance of show like so, these are working
    val dateShow: Show<Date> = object : Show<Date> {
        override fun Date.show(): String = "${this.time}ms since epoch"
    }
    // or constructor lambda
    val dateShow2: Show<Date> = Show({ "${it.time}ms since epoch" })

    val dd = dateShow2.run { Date().show() }
    val ddd = dateShow.run { Date().show() }
    println(dd)
    println(ddd)


    // but I still don't think this is correct
    val intShow: Show<Int> = Show { it.toString() }
    val intAsString = intShow.apply { 234.show() }

    // try using run

    // 4 re implement cat using show
    val catShow = Show<Cat> {
        val name = it.name
        val age = it.age
        val color = it.color
        "$name is a $age year old $color cat"
    }
    val c2 = ch01.Show<Cat> { "$name is a $age year old $color cat" }

    val cat = Cat("Tim", 23, "blue")
//    cat.show // this doesn't work, unless I create an extension method
//    fun Cat.show() = "$name is a $age year old $color cat"
    catShow.run { cat.show() } // but does within the run
}

// 1.5 Equality - type safe equality to address == operator
//listOf<Int>(1, 2, 3).map { Option(it) }.filter{item -> item == 1 }
// but == cannot be applied from Int to Option<Int>
// Eq is designed to add type safety to equality checks
// interface Eq<A> { fun eqv(a:A, b:A):Boolean

fun comparingWithEq() {

    val eqInt = Eq<Int> { a, b -> a == b }
    eqInt.run { 2.eqv(55) }
    //or
    val iei = object : IntEqInstance {}
    iei.run { 3.eqv(5) }


    val catEq = Eq<Cat> { c, cc -> c == cc }
    val catEq1 = ch01.Eq<Cat> { c, cc -> c.color == cc.color }

//    val isCatEq  = catEq1.run { Cat("2", 2, "3").eqv() } // doesnt work, must have two names, not receiver lambda

    val dateEq: Eq<Date> = Eq { d1, d2 -> d1.time == d2.time }
    val x = Date()
    val y = Date()

    val isEq: Boolean = dateEq.run { x.eqv(y) }
//    dateEq.Eq<Int>({ a, b -> a == b })

    // now comparing Option<Int> we need to compare instances of eq for Option and Int

    1.some() // .eqv( ... not yet defined

    val intEqInstance = object : IntEqInstance {}

    val optionEqInstance = object : OptionEqInstance<Int> {
        override fun EQ(): Eq<Int> = intEqInstance
    }

    println(optionEqInstance.run { 1.some().eqv(none()) })

    println(optionEqInstance.EQ())

    // 1.5.5 cat equality

    val c1 = Cat("A", 2, "brown")
    val c2 = Cat("B", 2, "teal")

    val optC1 = c1.some()
    val optC2 = Option.empty<Cat>()

    val catEqInstance = object : Eq<Cat> {
        override fun Cat.eqv(b: Cat): Boolean =
                (name == b.name) && (age == b.age) && (color == b.color)

    }

    val catOptionEqInstance = object : OptionEqInstance<Cat> {
        override fun EQ(): Eq<Cat> = catEqInstance
    }

    val areOptCatsEquals = catOptionEqInstance.run { optC1.eqv(optC2) }
    println("OptCats are $areOptCatsEquals")
}