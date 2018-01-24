package Part2_EmbracingKotlin.G_OperatorOverloading

// using symbolic operators like +, -, * which are called conventions

// Overloading arithmetic operators

data class Point(val x: Int, val y: Int) {

    operator fun plus(other: Point): Point = Point(x + other.x, y + other.y)

}

fun plus() {
    val newPoint = Point(1, 1) + Point(2, 3)
}

// overloadable binary arithmetic operators are: times *, div /, mod %, plus +, minus -
// and have the same precedence as the standard numeric types, with *,/,% at the top

fun Point.times(scale: Double): Point = Point((x * scale).toInt(), (y * scale).toInt())

// but remember that Point.times(2.0 is not the same as 2.0 times Point, you must define both if required

// No special operators for bitwise operations, they are used like regular functions, supporting infix call syntax
// shl signed shift left, shr signed shift right, ushr unsigned shift right, and bitwise, or bitwise, xor bitwise, inv bitwise inversion


// Compound assignment operators

fun p() {
    var p = Point(1, 1)
    p += Point(1, 1)
}

// sometimes you don't want to reassign the reference, just modify it
// method must be defined with the Unit return type

//operator fun <T> MutableCollection<T>.plusAssign(element: T) = Unit.also { this.add(element) } // or
operator fun <T> MutableCollection<T>.plusAssign(element: T) { // implicit unit
    this.add(element)
}

// + and - work with collections returning a new collection, while += and -= can mutate for mutable, and modified copy
// for read-only(if declared var), you can use individual elements, or other collections with matching typees

fun l() {
    val list = arrayListOf(1)
    list += 2
    val newList = list + listOf(3, 4)
}


// Overloading unary operators: unaryPlus, unaryMinus, not, inc, dec

operator fun Point.unaryMinus(): Point = Point(-x, -y)

operator fun Point.inc(): Point = Point(this.x + 1, this.y + 1)

fun main(args: Array<String>) {
    var v = Point(2, 3)
//    print(v-) // cant do this
    println(-v)
    println(v++) // both pre and post are generated
    println(v)
    println(++v)
}