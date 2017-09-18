package Part1_Introduction.C_DefiningAndCallingFunctions

// collections: varargs, infix calls, lib support

// varar    declare function taking arbitrary num of args
// infix notation lets you call some one-arg functions without ceremony
// destructure declarations that allow you to unpack a single composite value into multiple

// extending java collections api - last/max are extension functions
//fun <T> List<T>.last():T {return last element}
//fun Collection<Int>.max():Int{ find max in collection }

// code completion shows both extended and regular methods

// varags
val myList = listOf(2,6,7,8,9)
// function is declared via
//fun listOf<T>(vararg values:T):List<T>{...make list}

// kotlin uses the vararg modifier on the param

// java may pass in the array, kotlin requires you to explicitly unpack it, called spread operator
fun unpacking(){
    val myArray = arrayOf(1,7,8,9,0)
    val list = listOf("args", *myArray, "test") // put the star in front of it
    println(list) // now you can combine values of array and fixed
}

// infix to improve readability working with maps
val map = mapOf(1 to "one", 4 to "four")
// to is a method invocation called an infix call placed between the target object name
// and the param. These two calls are equivalent

// to   Creates a tuple of type [Pair] from this and [that], creating [Map] literals with less noise

fun eq(){
    1.to("one") // regular function call
    1 to "one" // infix notation
    // can be used with regular methods and extension functions that have one req param

    // simplified version of to
//    infix fun Any.to(other:Any)=Pair(this, other)

    // to returns an instance of a pair (using generics),

    // assign a pair of elements to two variables directly
    val (number, name) = 1 to "one" // destructuring declaration

    Pair(1, "One") // is populated into to val(number, name)
    // create a pair using the to function and unpack it with a destructuring declaration
    // which is not limited to pairs, consider
    val collection:List<String> = listOf("a", "b")
    for((index, element) in collection.withIndex()){ // adds the index value, to be like a map
        println(("$index:$element"))
    }

    // to is a extension function, to create a pair of any elements, one to anything else

//    fun <K, V> mapOf(vararg values:Pair<K, V>):Map<K, V>
    // accepts variable number of arguments, pairs of keys and values
}