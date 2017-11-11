package Part2_EmbracingKotlin.I_Generics

// define types that have type parameters, when instance of the type is create, type parms are subistuted with specific
// types called type arguments

// empty lists require explicit types
val r: List<String> = listOf()
val rr = listOf<String>()

// no raw types, must always be defined

// Generic functions and properties -
fun <T> List<T>.slice(indices: IntRange): List<T> {
    return listOf()
}

val infer = listOf(1, 3).slice(1..4) // compiler infers the int
val expl = listOf("e", "a").slice<String>(1..4) // or explicit

fun <T> withLambda(theT: T, operation: (T) -> String): String {
    val theString = operation(theT)
    return theString
}

fun <T> List<T>.withlist(operation: (T) -> String): String {
    return operation(get(0))
}

val <T> List<T>.mustBeAnExtension: T
    get() = this.get(0)

// non extension preperties can't have type params
// val <T> x:T = TODO()