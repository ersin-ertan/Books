package Part1_Introduction.B_KotlinBasics

import java.util.*

fun whileLoop(){
    while(true){}
    do{}while (true)
}

// ranges and progressions
val oneToTen = 1..10 // closed/inclusive both numbers are part of the range
// will print 1..10

// iteration is called a progression
fun progresssion(){
    for(i in 1..10) println(i)
}

fun p2(){
    for (i in 100 downTo 1 step 3) println(i)
}

fun p3(){
    val size = 3
    for(i in 0 until size)println(i)
}

fun maps(){
    val binaryReps = TreeMap<Char, String>()

    // for works with character ranges too
    for (c in 'A'..'F') binaryReps[c] = Integer.toBinaryString(c.toInt())
    for ((letter, binary) in binaryReps) println("$letter = $binary")
    // will unpack letter and binary

    // binaryReps[c] = Integer.toBin... is equivalent to .put(c, binary)
    // and is similar with output binaryReps[c]
}

// you don't need a separate variable to store the index and increment by hand
val list = arrayListOf("10", "11", "123")

fun indexing(){
    for((index, element) in list.withIndex()) println("$index:$element")
}

fun isLetterIn(c:Char) = c in 'a'..'z' || c in 'A'..'Z'
fun isNotDigit(c:Char) = c !in '0'..'9' || c in 'A'..'Z'

// you can use ranges anywhere where comparable interface is used, but string
// will only check equality

fun isInSet() = println("word" in setOf("words", "more")) // prints false