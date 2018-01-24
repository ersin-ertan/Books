package Part2_EmbracingKotlin.H_HigherOrderFunctions

// Declaring higher order functions - function that takes another function as an arguments or returns one

// lambdas are great for building abstractions

fun ex() {
    listOf(1, 3).filter { it > 3 }
}

// function types - compiler inferred
val sum = { x: Int, y: Int -> x + y }
val action = { println(43) }

// pram types, return type
val summ: (Int, Int) -> Int = sum
val actionn: () -> Unit = action

val nullReturn: () -> Int? = { if (true) null else 1 }

// nullable variable of a function type
var funOrNull: ((Int, Int) -> Int)? = null

fun twoAndThree(op: (Int, Int) -> Int) {
    val res = op(2, 3)
    println(res)
}

fun String.filter(pred: (Char) -> Boolean): String {
    val sb = StringBuilder()
    for (index in 0..length - 1) {
        val element = get(index)
        if (pred(element)) sb.append(element)
    }
    return sb.toString()
}

fun f() = println("ab1c".filter { it in 'a'..'z' })

// using function types from Java

// use the interface new Function1<Integer, Integer>(){ @Override public Integer invoke(Integer number){ return num+1}});

// see book, you can use a function from the kotlin standard library in java, but you have to return a value of unit type

