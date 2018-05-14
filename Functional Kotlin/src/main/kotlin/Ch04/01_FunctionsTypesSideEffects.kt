package Ch04

// 4 Functions, Function types, and Side Effects

// Functional programming - immutability and functions
// Functions in kotlin, function types, lambdas, high order functions, side effects/pure functions

// substeps, code reuse, clean and organized, unit testing easy

// nested functions
// lambda - anon function
// lambda expression
interface Me {
  fun work()
}

fun doSomething() {}
fun invokeSomething(doWork: () -> Unit) = doSomething() // higher order function

fun usage() {
  invokeSomething(
    {
      println("hello")
    }
  )
}

val sum = { x: Int, y: Int -> x + y } // function as a property

fun returnFunc(int: Int): (Int) -> Int = { i -> i + int }
val returnFunc = Ch04.returnFunc(1)(2)


// pure functions and side effects - modify global static properties, arguments, raise exception
// wrie data to display or file, call another function which has side effects

// Pure function - dependent on its aruguments/params