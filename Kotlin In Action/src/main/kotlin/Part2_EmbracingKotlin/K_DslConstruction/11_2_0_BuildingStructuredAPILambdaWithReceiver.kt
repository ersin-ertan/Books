package Part2_EmbracingKotlin.K_DslConstruction

// 11.2.0 Building Structured APIs: Lambdas with receivers in DSLs

/*
Build api with structure. lambdas with receiver and extension function types
buildString, with, apply
*/

fun buildString1(builderAction: (StringBuilder) -> Unit): String {
    val sb = StringBuilder()
    builderAction(sb)
    return sb.toString()
}

val bs = buildString1 {
    it.append("a")
    it.append("b")
//    append("c")
}

fun defineOwn(iAmAFunction: (s: StringBuilder) -> Unit): String {
//    what is the s here
    val sb = StringBuilder()
//    iAmAFunction(s = sb) // named arguments are not allowed for function types
    return sb.toString()
}

val use = defineOwn {
    it.append("a")
}

// declares a parameter of a function type, passes a stringbuilder as an argument to the lambda, uses 'it' to refer to the
// StringBuilder instance, can define your own parameter instead of it

// main purpose of lambda is to fill the StringBuilder methods directly, replacing it.append with append
// convert the elambda into a lambda with receiver, give one of the parameters of the lambda special status to
// refer to its members directly without any qualifier





fun buildString2(builderAction: StringBuilder.() -> Unit): String {
    val sb = StringBuilder()
    sb.builderAction()
    return sb.toString()
}

val bs2 = buildString2 {
    this.append("a")
    append("b")
}

// declares a parameter of function type with receiver, passes a stringbuilder as a receiver to the lambda, this keyword
// refers to the string builder instance, you can omit this and refer to the string builder implicitly

// You are using an extension function type instead of a regular function type to declare lambda parameter
// Declaring an extension function type pulls one of the function type parameters out of the parentheses and puts it in front
// special type is receiver type, value of that type passed to the lambda becomes the receiver object

// String.(Int, Int) -> Unit
// receiver type, parameter types, return type
// why extension function type, accessing members of external type without explicit qualifier remind you of extension functions.
// where you can define your own methods for classes defined elsewehre in the code.

// extension functions and lambdas with receiver have receiver object, provided when the function is called and is available
// inside its body. Extension function type describes a block of code that can be called as an extension function.

/*
this and builder action are he argument and parameter

buildString { this.append("a") }

fun buildString( builderAction : StringBuilder.() -> Unit): String {
    val sb = StringBuilder()

    sb.builderAction() // sb is the this

*/