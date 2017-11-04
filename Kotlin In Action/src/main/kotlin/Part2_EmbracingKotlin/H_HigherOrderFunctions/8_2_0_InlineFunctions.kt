package Part2_EmbracingKotlin.H_HigherOrderFunctions

// inline functions: removing the overhead of lambdas

// what about the performance of lambdas like with and apply
// lambdas are normally compiled to anonymous classes, so a class is created and he lambda captures variables,
// creating new objects on every invocation. A lambda is less efficient than a function with the same code

// kotlin compiler allows you to use inline, so it won't generate a function when the function is used and will replace
// every call to the function with the actual code implementing the function


// declare a function as inline, so its body is inlined(substituted instead of the function invocation)

// see book for synchronization example

inline fun inlineMe(op: (Int) -> Int) {
    var a = 1
    repeat(3) { a = op(a) }
}

//thus when I call inlineMe it is the same as

fun callInlineMe(int: Int, op: (Int) -> Int) {
    var a = int
    inlineMe { op(a) }
}

fun callInlineMe2(int: Int, op: (Int) -> Int) {
    var a = int
    repeat(3) { a = op(a) }
}

// TODO understand
// sometimes the lambdas code isn't available at the site where the inline function is called, thus it isn't inlined

// if you have two uses of an inline function in different locations with different lambdas, then the call sites are
// inlined independently.


// Restrictions on inline functions
// not every function that uses lambdas can be inlined
// when the function is inlined, the body of the lambda expression that's passed as an argument is substituted directly
// into the resulting code. If the parameter is called, it can be inlined.
// If the parameter is stored for further use, lambda expressions code can't be inlined, because there must be an object
// that contains the code.

//fun <T, R> Sequence<T>.map(transform: (T) -> R): Sequence<R> = TransformingSequence(this, transform)

// map function doesn't call the function passed as the transform param directly, it passes this function to the constructor
// of a class that stores it in a property. The lambda passed as the transform argument needs to be complied into a
// standard non inline representation, anon class implementing a func interface.
// Functions that take two or more lambdas as params may inline some by using noinline keyword on the param name

inline fun inl(inlined: () -> Unit, noinline not: () -> Unit) {}