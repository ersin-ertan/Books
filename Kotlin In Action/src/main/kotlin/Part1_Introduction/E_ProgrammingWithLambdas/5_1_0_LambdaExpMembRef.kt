package Part1_Introduction.E_ProgrammingWithLambdas

// lambda expressions and member ref, collections with functional style, sequences and lazy operators, java functional
// interfaces, lambdas with receivers

// lambda expressions are small chunks of code that can be passed to other functions. Good for extracting common code structures
// into library functions, esp with collections. Lambda receivers are where the body is executed in a different context than the
// surrounding code.

// lambda expressions and member references
// anonymous inner class, but requires verbose syntax, enter functional programming, functions as values

fun but() {
    // button.setOnClickListener(new OnClickListener(){
    // @Override public void onClick(View v){ doS()}}); vs

    // button.setOnclickListener{ doAction() }
}

data class Person1(val name:String, val age:Int)

val personList = listOf(Person1("tom", 4), Person1("phil", 5))

fun findOldest(personList:List<Person1>) {
    var maxAge = 0
    var oldestPerson:Person1? = null

    for (person in personList) {
        if (person.age > maxAge) {
            maxAge = person.age
            oldestPerson = person
        }
    }
    println(oldestPerson)
}

// vs kotiln way

val oldestPerson = personList.maxBy { it.age } // single line, one variable, functional flow
// if a lambda delegates to a method or property, replace it with a member reference
val oldestPerson1 = personList.maxBy(Person1::age)

// without syntax shortcuts
val pers:Person1? = personList.maxBy({ p:Person1 -> p.age })
val pers1:Person1? = personList.maxBy() { p:Person1 -> p.age }
val pers2:Person1? = personList.maxBy { p:Person1 -> p.age }
val pers3:Person1? = personList.maxBy { p:Person1 -> p.age }
val pers4:Person1? = personList.maxBy { p -> p.age }
val pers5:Person1? = personList.maxBy { it.age } // nested lambdas can get confusing, name the it variable
val pers6 = { p:Person1 -> p.age } // lambdas stored in a variables don't have context to infer argument types, you must
// specify they explicitly

// syntax for lambda expressions

// { x:Int, y:Int -> x + y } // params and body
val sum = { x:Int, y:Int -> x + y }

val lambda = { println(43) } // but doesn't make sense
val run = kotlin.run { println(34) } // run calls the specified function and returns the result

// lambdas with multiple statements
val sum1 = { x:Int, y:Int ->
    println("x and y sum is in the variable")
    x + y
}

// Accessing variables in scope - capturing variables from the context
// can access local variables

fun printMessWithPrefix(messages:Collection<String>, prefix:String) {
    messages.forEach { println("$prefix $it") }
}

fun printProblemCounts(responses:Collection<String>) {
    var clientErrors = 0
    var serverErrors = 0
    responses.forEach {
        if (it.startsWith("4")) clientErrors++
        else if (it.startsWith("5")) serverErrors++
    }
    println("$clientErrors ce and $serverErrors se")
} // kotlin unlike java allows access to non final variables to modify them in the lambda
// when you capture a final variable, its value is stored together with the lambda code
// for non final variables, the value is enclosed in a special wrapper that lets you change it, and the reference to the
// wrapper is stored with the lambda

// java captures final variables only, thus you must use a one element array to store the mutable value, or create an
// instance of the wrapper class which stores the reference that can be changed
// kotlin can mutate the variable directly, but if a lambda is used as an event handler or executed async, modification to
// local variables will occur only when the lambda is executed

//fun incorrectButtonClickCounter(but:Button1):Int {
//    var clicks:Int = 0
//    but.onClick() { clicks++ }
//    return clicks // will return 0, the value of clicks is modified but unobservable because the handler is called after
// the function returns. A correct implementation of the function would need to store the click count not in a local variable
// but one accessible outside the function, like a property of a class
//}