package Part2_EmbracingKotlin.G_OperatorOverloading

import java.time.LocalDate

// conventions for collections and ranges

// accessing by index get and set

// can define get with multiple parameters
operator fun Point.get(index: Int): Int = when (index) { // not limited to Int return
    0 -> x
    1 -> y
    else -> throw IndexOutOfBoundsException("Point.get($index) Invalid coordinate")
}

fun get() {
    Point(1, 3)[0]
}

// ex, a single array
class A {
    val a = arrayOf<Int>(
            0, 1, 2, 3,
            4, 5, 6, 7,
            8, 9, 10, 11)
}

operator fun A.get(row: Int, col: Int): Int = if (row > 2 || col > 3) throw IndexOutOfBoundsException() else a[row * 4 + col]


var b = A()[1, 2]

fun print() {
    println(b) // 6
}

// the same pattern is used for 'set'

// in convention
data class Rectangle(val upperLeft: Point, val lowerRight: Point)

operator fun Rectangle.contains(p: Point): Boolean =
        p.x in upperLeft.x until lowerRight.x && // until builds an open range, end non inclusive
                p.y in upperLeft.y until lowerRight.y


// rangeTo method as ..
// If the class implements comparable, you don't need that you can create a range of any comparable elements via std lib

// lib defines rangeTo func colled on any comparable, returning a range allowing you to check if the element is in it

val now = LocalDate.now()
val vac = now..now.plusDays(10)

fun printVac() {
    println(now.plusWeeks(1) in vac) // returns true
}

// rangeTwo cas lower priorty than arithmetic operators, thus use parentheses for its arguments to avoid confusion
// println(0..(n+1)) is 0..10
// can also do (0..n).forEach{ print(it)}

// Iteration for for loop, explicit iteration using list.iterator(0, hasNext next methods called, can iterate on strings
// because of the extension function iterator on CharSequence(superclass of string)

/*class LinkedList(val head: Node) {
    class Node(val a: Any, var next: Node? = null) : Comparable<Node> {
        override fun compareTo(other: Node): Int =
                if (a.toString().length > other.toString().length) 1 else 0

    }

    fun add(a: Any) {
        var prev = head
        while (prev.next != null) {
            prev = prev.next!!
        }
        prev.next = Node(a)
    }
}

operator fun ClosedRange<LinkedList.Node>.iterator(): Iterator<LinkedList.Node> = object : Iterator<LinkedList.Node> {
    var cur = start
    override fun hasNext(): Boolean = cur <= endInclusive

    override fun next(): LinkedList.Node = cur.apply { cur.next }
}

fun list() {
    val ll = LinkedList(LinkedList.Node("a", LinkedList.Node("aa", LinkedList.Node("aaa"))))
    for(n in ll.head)
}*/

// apply this pattern to LocalDate, to which you for(day in daysOff){ println(day.fullDate) }

// review

fun main(args: Array<String>) {
    print()
    println(Point(3, 6) in Rectangle(Point(3, 4), Point(3, 8)))
}