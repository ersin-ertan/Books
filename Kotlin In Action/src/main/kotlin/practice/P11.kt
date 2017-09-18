package practice

import java.time.Instant

fun log() {
    println(Instant.now())
}

fun main(args:Array<String>) {

//    nameVerification()
//    listProperties()
//    extensionProperrties()
}

fun extensionProperrties() {
    "hello".withEx.p() // this is a property of the string
//    name.withEx// this does not work

    val t = T(1)
    t.int.p()
    t.n.p()

    t.int = 3
    t.int.p()
    t.n.p()
}

fun listProperties() {
    val list = listOf(1, 2, 3, 4, 5)
    list.max()?.p() ?: "max is null".p()
    list.first().p()
    list.last().p()
    list.drop(2).dropLast(1).toString().p()
}

fun nameVerification() {
    try { // try catche
        verifyName("Tom")
        verifyName("J4ck")
    } catch (iae:IllegalArgumentException) {
        println(iae.message)
    } finally { // finally
        log()
    }

    // try as expression
    val name = try {
        verifyName("W1ll")
    } catch (iae:IllegalArgumentException) {
        "null"
    }
    println(name)

    name.javaClass.simpleName.p()
}

data class T(var int:Int)

val T.n:Int get() = this.int + int

val String.withEx:String
    get() {
        return this.plus('!')
    }

fun verifyName(name:String = "default") { // must be non null by function
    println("verifying $name")
//    println(name.any { !it.equals('a'..'z') }) don't work
//    println(name.any { it !in 'a'..'z' })
    val legalLetterRange = 'a'..'z'
    name.toLowerCase().forEach { if (it !in legalLetterRange) throw IllegalArgumentException("Error: verifyName($name) not $legalLetterRange") }
} // not in and throw