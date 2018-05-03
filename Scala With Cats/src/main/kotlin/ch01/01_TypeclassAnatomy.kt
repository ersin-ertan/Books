package ch01

fun Any.p() = println(this)
fun Any.pp() = print(this)

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