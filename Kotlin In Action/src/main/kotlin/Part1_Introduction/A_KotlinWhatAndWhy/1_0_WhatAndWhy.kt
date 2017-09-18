package Part1_Introduction.A_KotlinWhatAndWhy

// data class

data class Person(val name: String, val age: Int? = null /*default value arg*/)

fun aTasteOfWhy(): Unit {

        val persons = listOf(Person("Alice"), Person("bob", age = 32 /*named arg*/))

        val oldestPerson = persons.maxBy { it.age ?: 0 /*lambda expression; elvis op
    to return 0 if age is null*/ }

        println("oldest is: $oldestPerson"/*string templating*/) // auto gen toString
}

fun staticallyType():Unit{
    // but type declaration may be inferred
    val x = 1 // as int

    // why static typing, performant before runtime, reliable for verification, maintainable
    // and tool support

    // bonus support for nullable types and functional types
}

fun functionalAndObjectOriented():Unit{ // what is unit? return type of nothing
    // need not be explicitly declared, even the returns are optional

    /* first class functions - behaviour as values, stored in variables, passed as
    parameters, returned to other functions

    Imutability - immutable objects, guaranteed state non modification

    no side efects - pure fuctions return the same result given the same inputs, don't
    modify state of other objects or interact with outside world


    Functional avoid code duplication,' is concise, more power to abstraction
    safe multithreading no need for synchronization, easier testing no side effects,
    easier to isolate

    How to support functional programming - functional types, lambda expressions(
     pass around blocks of code with minimum boilerplate), data crlasses(concise syntax
     for creating immutable value objects), rich apis in standard lib for working
     with objects and collections in function style

    */

//    fun findAlice() = findPerson { it.name == "alice"}
//    fun findBob() = findPerson { it.name == "bob"}




    return
    return Unit

}

/*
fun findPerson(personList: AbstractList<Person>, criteria : Function):Person{

}*/


fun onServerSide(){
    /* including web applications returning HTML to browser, backends for mobile apps
    * exposing json api over HTTP, microservices that communicate with other micro-
    * services over an RPC protocol
    *
    * java interoperability and support for builder pattern to create any object graph
    * */

    /*createHTML().table { // functions that map to HTML tags
        for(person in persons) { // using regular kotlin loops
            tr{
                td{+person.name}
                td{+person.age}
            }
        }
    }
    just use dls, no more templating languages, especially with SQL and queries with
    type checking

    */
}

fun android(){
    /*
    * common tasks can be done or generated via anko
    *
    * verticalLayout{
    * val name = editText()
    * button("say hi"){
    * onClick{ toast("hi, ${name.text}!")}
    * */

    val nullable: String? = null
    val hasValue: String = "" // cant be null

    // kotlin helps eliminate NullPointerExceptions and ClassCastExceptions,
    // cast and check are a single op

    if(hasValue is String)
        println(hasValue.toUpperCase())

    // kotlin can be called from java!
}

fun compiling(){
    /*
    * kotlinc <source file or directory> -include-runtime -d <jar name>
java -jar <jar name>
    * */

    // kotlin build process *.kt and *.java->kotlin compiler and java compiler->*.class->*.jar and kotlin runtime->(Application
}