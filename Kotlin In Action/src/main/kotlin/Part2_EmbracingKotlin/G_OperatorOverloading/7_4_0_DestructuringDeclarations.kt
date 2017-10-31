package Part2_EmbracingKotlin.G_OperatorOverloading

// Destructuring declarations and component functions
fun dest() {
    val p = Point(1, 2)

    val (x, y) = p
    println("x:$x and y:$y")

    val x1 = p.component1()
}

// for each variable in a destructuring declaration a function named componentN is called, where N is the position of the
// variable in the declaration, thus a = p.component1()

// data classes generate a componentN function for every property declared in the primary constructor
// they can also be defined manually

operator fun Point.component1() = x

// destructuring is useful to unpack values from a function, also useful if used with arrays and collections of known size
// you can access the first 5 elements via componentN, or use Pair or Triple for wrapper classes

val m = mapOf(1 to "a", 2 to "b")

fun de() {
    for ((key, value) in m) {
        println(key)
        println(value)
    }

    // or

    for(entry in m.entries){
        println(entry.component1())
        println(entry.component2())
    }
}