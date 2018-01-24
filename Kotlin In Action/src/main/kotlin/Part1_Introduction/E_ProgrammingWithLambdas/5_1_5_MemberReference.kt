package Part1_Introduction.E_ProgrammingWithLambdas

import Part1_Introduction.D_ClassesObjectsAndInterfaces.Person1

// how do you pass a parameter that is already defined as a function, you pass a lambda that calls the function, or
// pass the function directly via a member reference, a short syntax for creating a function value that calls exactly
// one method or accesses a property

val getAge = Part1_Introduction.E_ProgrammingWithLambdas.Person1::age // double colon, and has the same type as a lambda
// or
val getAge1 = { person:Part1_Introduction.E_ProgrammingWithLambdas.Person1 -> person.age }

// a reference to a function thats declared at top level, but isn't a member of a class
fun salute() = println("bonjour")

fun runLambda() {
    run(::salute) // reference to the top level function can omit the class name
}

// this is great for multi parameters
fun sendEmail(per:Person1, message:String) {}

val action = { per:Person1, message:String -> sendEmail(per, message) } // vs
val action1 = ::sendEmail


// to store or postpone creating an instance of a class, use a constructor reference
fun constRef() {
    val createP = ::Person1 // thus the action of creating an instance of a person is saved as a value
    val p = createP("tom", 345)

    fun Part1_Introduction.E_ProgrammingWithLambdas.Person1.isAdult() = age > 21 // you can also reference extension functions the same way
    val predicate = Part1_Introduction.E_ProgrammingWithLambdas.Person1::isAdult
}

// Bound references
// when you take a reference to a method or property of a class, you need to provide an instance of that class when you
// call the reference - but support for bound method references which allow you to use the method reference syntax to
// capture a reference to the method on a specific object instance is planned for kotlin 1.1

val p = Part1_Introduction.E_ProgrammingWithLambdas.Person1("tom", 34)

val personAgeFunction = Part1_Introduction.E_ProgrammingWithLambdas.Person1::age
fun printPAge() {
    println(personAgeFunction(p))
}

// vs
val myAgeFunction = p::age

fun printPAge1() {
    println(myAgeFunction())
}

// person age function is a one argument function, returning the age of the given person, where myagefunc is a zero arg
// function returning the age of the specified person, we use the bound member reference p::age