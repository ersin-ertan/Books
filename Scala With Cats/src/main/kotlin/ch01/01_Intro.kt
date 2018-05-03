package ch01

import arrow.core.Option
import arrow.core.none
import arrow.core.some
import arrow.instances.IntEqInstance
import arrow.instances.OptionEqInstance
import arrow.typeclasses.Eq
import arrow.typeclasses.Show
import ch01.JsonWriterInstances.personWriter
import java.util.*

// Type classes - extend existing llibs with new functionality, without inheritance or altering original source
// Show and Eq examples, algebraic data types, pattern matching, value classes, and type aliases

// 1.1 Anatomy of Type class - type class, instances for particular types and interface methods exposed to user
// Type class is an interface/api representing functionality we want to implement,(trait with at least one type param)
interface Json // json ast, interface or sealed class???

class JsObject(val get: Map<String, Json>) : Json {
    override fun toString(): String = "${this.javaClass.simpleName}:$get" // here for convenience
}

class JsString(val get: String) : Json {
    override fun toString(): String = "${this.javaClass.simpleName}:$get" // here for convenience

}

class JsDouble(val get: Double) : Json {
    override fun toString(): String = "${this.javaClass.simpleName}:$get" // here for convenience
}

object JsNull : Json

interface JsonWriter<A> {
    fun write(value: A): Json
}

// type class instances - implementations for the types we care about(types from our domain model), define instances by
// creating concrete implementations of the type class and tagging them with implicit
class Person(val name: String, val email: String)

object JsonWriterInstances {
    val stringWriter: JsonWriter<String> = object : JsonWriter<String> {
        override fun write(value: String): Json = JsString(value)
    }
    val personWriter: JsonWriter<Person> = object : JsonWriter<Person> {
        override fun write(value: Person): Json =
                JsObject(mapOf(
                        "name" to JsString(value.name),
                        "email" to JsString(value.email)
                ))
    }
}

// Type class interfaces - functionality exposed to users, generic methods that accept instances of the type class as
// implicit prams - either as interface objects and interface syntax

// interface objects - methods in a singleton, to use object we import type class instance and call
object JsonInterfaceObject {
    fun <A> toJson(value: A, w: JsonWriter<A>): Json = w.write(value)
}

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
    Person("d", "d@D.d").toJson(personWriter)
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

// 1.6 Controlling Instance Selection - working with case classes consider two issues of control for instance selection
// what is the relationship between an instance defined on a type and its subtypes
// JsonWrite<Option<Int>> is selected as Json.toJson(Some(1))
// How do we choose between type class instances when there are many available
// Define two JsonWriters for Person and we Json.toJson(aPers), which instance is selected

// 1.6.1 Variance - when we define type classes, add variance annotations to the type param to affact
// variance of type class and compilers ability to select instances during implict resolution
/// B is a subtype of A if we can use a value of B anywhere we expect A

// Co and contra variance annotations arise when working with type constructors

// Covariance F<B> is a subtype of F<A> if B is a subtype of A
// Useful for modelling many types, including collections like List/Option
// out keyword
sealed class Shape

class Circle(radius: Double) : Shape()

val circles = listOf<Circle>()
val shapes: List<Shape> = circles

// Contravariance - F<B> is a subtype of F<A> if A is a subtype of B! wow
// Usefull for modelling process
// in keyword
interface JWriter<in A> {
    fun write(value: A): Json
}

//consider

lateinit var shape: Shape
lateinit var circle: Circle

lateinit var shapeWriter: JWriter<Shape>
lateinit var circleWriter: JWriter<Circle>

fun <A> format(value: A, writer: JWriter<A>): Json = writer.write(value)

// thus we can pass a circleWriter with shape or circle
// but we cannot pass shapeWriter with circle

fun testPassing() {
    format(shape, shapeWriter)
//    format(shape, circleWriter) // type inference failed, cannot infer type parameter of A
    format(circle, shapeWriter)
    format(circle, circleWriter)
}

// Invariance - no in or out annotation thus F<A> is neither a sub or super type of F<B>

interface A
object B : A
object C : A
// will an instance defined on a supertype be selected if one is available, if we define an instance for A and have it
// work for values of type B and C
// will an instance for a subtype be selected in preference to that of a supertype. If we define instance for A and B,
//will the value of type B selected the instance for B instead of A. We cant have both.

//                              Invariant - Covariant - Contravariant
// Supertype Instance used?     No          No          Yes
// More specific type preferred?No          Yes         No

//Cats preferts to use invariant type classes to allow us to specify more specific instances for subtypes if we want
// if we have Some<Int>, type class instance Option is not used thus we type annotate it as Some(1):Option<Int> or smart
// constructors like Option.apply <- note this is scala smart constructor Option.empty, some and none methods