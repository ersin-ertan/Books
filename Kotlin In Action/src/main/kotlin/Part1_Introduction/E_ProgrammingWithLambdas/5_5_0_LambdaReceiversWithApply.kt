package Part1_Introduction.E_ProgrammingWithLambdas

// Lambdas with receivers: with and apply
// with and apply kotlin standard library
// kotlin ta call methods of a different object in the body of a lambdda without any additional qualifiers.
// These are called lambdas with receivers - with uses a lambda with a receivers

// with - a statement to perform multiple operations on the same object without repeating its name

fun alpha():String {
    val result = StringBuilder()
    for (letter in 'A'..'Z') result.append(letter)

    result.append("\nThis is alpha1")
    return result.toString()
} // several different methods are called on the result instance

fun alphaWith():String {
    val stringBuilder = StringBuilder()
    return with(stringBuilder) {
        // receiver value to call methods on
        for (letter in 'A'..'Z') this.append(letter) // explicit this
        append("\n this is with") // omits this call
        toString()
    }
} // with takes in two params, the value and the lambda with(stringbuilder,{}) or put the lambda on the outside

// with converts the first argument into a receiver of the lambda of the second arg. Access the receiver via this, or
/// omit it to access methods or properties of the value

// lambdas with receiver and extension functions
// in the body of an extension function, this refers to the instance of the type the function is extending, it can be
// omitted to give you direct access to the receivers members. An extension function is a function with a receiver.

// regular function - regular lambda
// extension function - lambda with a receiver

// lambda is a way to define behaviour similar to a regular function, and a lambda with a receiver is a way to define
// behaviour similar to an extension function

// more refactoring
fun alphaWithRefactor():String = with(StringBuilder()) {
    // returning the result of the lambda
    ('A'..'Z').forEach { append(it) } // invisible this. call
    append("done")
    toString()
}

// to return the receiver object, not the result of the executing lambda, call apply

// apply always returns the object passed to it as a parameter

fun alphaApply():StringBuilder = StringBuilder().apply {
    ('A'..'Z').forEach { append(it) } // invisible this. call
    append("done")
} // or return string and apply .toString here

val aaSb:StringBuilder = alphaApply()
val aa:String = alphaApply().toString()

// useful when creating an instance of an object with preinit properties, done in java with a Builder object
// kotlin uses apply without special support=

// fun createViewWithCustomAttributes(contex:Context) = TextView(context).apply{ text = "test"; textSize = 25; setPadding(10,0,0,10) }

// with and apply are generic examples of using lambdas with receivers.

fun alphaRefact() = buildString { ('A'..'Z').forEach { append(it) } ; append("done") }
// buildString is in the standard library, creating a string builder and calling toString for you