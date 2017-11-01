package Part2_EmbracingKotlin.H_HigherOrderFunctions

import p

// Default and null value for parameters with function types

// in the previous joinToString example, the toString method is flexible, relying on toString to do the object to string
// conversion, but this is not always needed. You can pass a lambda to specify how values are converted into strings.
// But all callers cannot pass in the lambda, as must are ok with default value, so define a parameter of a function
// type and specify a default value for its lambda

fun <T> defaultFuncParam(input: T, operation: (T) -> String = { it.toString() }): String {
    return operation(input) // wil default it.toString
}

fun testDefault() {
    defaultFuncParam(1).p()
    defaultFuncParam(2, { (it * it).toString() }).p()
}

// use generics to enforce the type parameter for input and lambda

// or via a nullable function parameter

fun nullable(inp: Int, op: ((Int) -> String)?): String {
    return op?.let {
        // if op is not null
        op(inp)
    } ?: "default"
}

// or a variable of a function type is an instance of a class implementing the corresponding FunctionN interface, with
// invoke method which can be called via callback?.invoke(), thus

fun invoke(inp: Int, op: ((Int) -> String)?): String {
    return op?.invoke(inp) ?: "default"
}

fun main(args: Array<String>) {
    testDefault()
}