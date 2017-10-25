package Part1_Introduction.G_OperatorOverloading

// Overloading comparision operators

// equals checks a==b and whether a isn't null, calling a.equals(b), true if both are null references.

// override is not applicable to top level function if used as an extension method, because it is implemented by
// Any thus must be in the class itself
data class Point1(val x: Int, val y: Int) {
    override fun equals(other: Any?): Boolean = when {
        other === this -> true // not tripple equals, checks both argument references, and if primitives types by value
        other !is Point1 -> false
        else -> other.x == this.x && other.y == this.y
    }
}

fun e() {
    var b = Point1(1, 2) == (Point1(2, 3))
    var bb = Point1(1, 2) != (Point1(2, 3))
}

// Ordering operators: compareTo

// java requires .compareTo calls, kotlin can use <,>,>=,<= operators
// p1 < p2 is equal to p1.compareTo(p2) < 0

class Person(val fn: String, val ln: String) : Comparable<Person> {
    // operator keyword is implied
    override fun compareTo(other: Person): Int {
        return compareValuesBy(this, other, Person::ln, Person::fn)
        // function calls each callback to compare values, if different returns result, else same proceeds to next callback
        // can be lambdas or property references
    }
}

fun c() {
    val p = Person("a", "b")
    val p1 = Person("c", "d")
    val b = p < p1
    val bb = p > p1
}