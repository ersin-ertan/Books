package Part2_EmbracingKotlin.H_HigherOrderFunctions

import p

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


// Inlining collection operations - most collection functions in standard lib take lambda expressions as argument

val people = listOf(Person("bill", 5), Person("tom", 3))

fun operate() {
    people.filter { it.number!! > 2 }.p() // filter is inline

    // vs
    val res = mutableListOf<Person>()
    for (person in people) {
        if (person.number!! > 3) res.add(person)
    }
}

// no need to worry about performance, even when chained, because bodies are inlined so no extra classes or objects are
// created. Thus if the number of elements is large since filter and map create intermediate collections, use a sequence
// via .asSequence but lambdas used to process a sequence aren't inlined, so each sequence is represented as an object
// storing a lambda in its field, and the terminal operation causes a chain of calls through each intermediate sequence
// to be performed. THus is not best for all chains of collection operations, only for large collections.

// Deciding when to declare functions as inline - can improve performance only with functions that take lambdas as
// paramaters, else you must do additional measuring/investigation.

// JVM does inlining at machine code level. The bytecode implmentation of each function is repeated only once, not copied
// everywhere as with inline, stack trace is cleaner.

// Otherwise the overhead you avoid through inlining is more significant, saving the call, but also the creation of extra
// class for each lambda and object for lambda instance, it may help JVM. Also allows for features impossible
// with regular lambdas: non local returns

// If code is too large, try extracting code not realated to lambda argument into a non inline function
