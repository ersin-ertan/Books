package Part1_Introduction.F_TheKotlinTypeSystem

// Learn about nullable types, syntax for nulls
// Primitive types and their correspondence to the java types
// Kotlin collections and relationship to java

// Nullability to avoid NullPointerException errors, goal is to convert runtime errors to compile time errors. Nullability
// is part of the type system.

// Nullable types must explicitly use ? after the type, now the variable can store a null reference. Null can't be
// assigned to non null types.

val verbose = if (Integer(1).toString() != null) 1 else 2
val safeCall = Integer(1)?.toString() ?: 2

// type is a classification determining the possible values for the type, thus the operations that can be done on the values

// @Nullable @NotNull annotations are hard to use everywhere, Optional is verbose wrapper, affecting performance, not
// consistently used everywhere

// can chain safe calls, the ?: elvis/nul coalescing operator is for the whole statement

// Safe cast as? the as throws a ClassCastException if the value is not of the type, use is or as? which returns the cast
// object or null

val l:Collection<Int>? = listOf<Int>()
val asList2 = l!! as List<Int>
val asList = l as? List<Int>


val ll:Collection<Int> = listOf<Int>()
val asList1 = l as List<Int>

// Not null assertions !! a simple and blunt tool that converts a value to a non null type, else a NullPointerException
val a:Int? = 1
val b = a!!.toString()
// useful if you always check for null in a preceding function, and don't wish to do it again, as with many action classes
// in UI, where methods separate updating the state of the action, like enable/disable for executing.
// don't chain not null assertions, as you cannot know where the null is coming from, the stack trace identifies with
// the line number not the expression

// let function makes it easier to deal with nullable expressions, with safe call you can evalueate the expression, check
// for null, and store the result in a variable, all in a single concise expression
val email:String? = "a"

fun sendEmail(email:String) {}

val e = if (email != null) sendEmail(email) else null // requires explicit null check
val le = email?.let { e -> sendEmail(e) } // same semantic, will either do operation or nothing since the value is null

// calling multiple nested lets for multiple null check is verbose, use if and check them all together

// Properties that are effectively non null, but can't be init with a non null value in the constructor can use
// Late initialized properties - init object in dedicated methods after the object instance is created onCreate
// if you cant provide the value you have to use a nullable type instead forcing you to check null or assert !!

private class A {
    lateinit var a:Integer // not allowed on primitives, and only for var not val, because the value is changed outside
    // the constructor
    // Common use case for lateinit properties is dependency injection. For java compatibility, kotlin generates a field
    // with the same visibility as the lateinit property

    fun onCreate() {
        a = Integer(1)
    }
}

// Extensions for nullable types - more powerful to deal with null values before method call, call with null as a receiver
// deal with null in the function - only possible for extension functions; regular member calls are dispatched
// throughout the object instance and can never be performed when the instance is null

fun verifyInput(input:String?) {
    // note no safe call
    if (input.isNullOrBlank()) println("enter something")
}

val c = verifyInput("hello")
val d = verifyInput(null) // allows null
val f:String? = null
val g = f.isNullOrBlank() // we know its null, no need for !! or ?.

// because the extension function has ? you can call it with null values
fun String?.isNullOrBlank():Boolean = this == null// explicit check, this can be null
        || this.isBlank() // this is auto smart cast

// but careful with let
val h = f.let { theFIsNull -> println() } // not a safe call, thus 'it' or theFIsNull is null
val i = f?.let { theFIsNotNull -> println("else i wont run") }
// extension functions can be non-null, but change to null iff it turns out it's used mostly on nullable values thus you
// deal with null explicitly