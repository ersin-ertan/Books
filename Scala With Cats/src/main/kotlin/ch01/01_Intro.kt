package ch01

import ch01.JsonWriterInstances.personWriter

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
    testingImplicits()
    testingSyntax()
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