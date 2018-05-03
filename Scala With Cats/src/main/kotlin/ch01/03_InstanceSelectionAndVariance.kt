package ch01

// 1.6 Controlling Instance Selection - working with case classes consider two issues of control for instance selection
// what is the relationship between an instance defined on a type and its subtypes
// JsonWrite<Option<Int>> is selected as Json.toJson(Some(1))
// How do we choose between type class instances when there are many available
// Define two JsonWriters for Person and we Json.toJson(aPers), which instance is selected

// 1.6.1 Variance - when we define type classes, add variance annotations to the type param to affact
// variance of type class and compilers ability to select instances during implict resolution
/// B is a subtype of A if we can use a value of B anywhere we expect A

// Co and contra variance annotations arise when working with type constructors

// Covariance F<B> is a subtype of F<A> if B is a subtype of A
// Useful for modelling many types, including collections like List/Option
// out keyword
sealed class Shape

class Circle(radius: Double) : Shape()

val circles = listOf<Circle>()
val shapes: List<Shape> = circles

// Contravariance - F<B> is a subtype of F<A> if A is a subtype of B! wow
// Usefull for modelling process
// in keyword
interface JWriter<in A> {
    fun write(value: A): Json
}

//consider

lateinit var shape: Shape
lateinit var circle: Circle

lateinit var shapeWriter: JWriter<Shape>
lateinit var circleWriter: JWriter<Circle>

fun <A> format(value: A, writer: JWriter<A>): Json = writer.write(value)

// thus we can pass a circleWriter with shape or circle
// but we cannot pass shapeWriter with circle

fun testPassing() {
    format(shape, shapeWriter)
//    format(shape, circleWriter) // type inference failed, cannot infer type parameter of A
    format(circle, shapeWriter)
    format(circle, circleWriter)
}

// Invariance - no in or out annotation thus F<A> is neither a sub or super type of F<B>

interface A
object B : A
object C : A
// will an instance defined on a supertype be selected if one is available, if we define an instance for A and have it
// work for values of type B and C
// will an instance for a subtype be selected in preference to that of a supertype. If we define instance for A and B,
//will the value of type B selected the instance for B instead of A. We cant have both.

//                              Invariant - Covariant - Contravariant
// Supertype Instance used?     No          No          Yes
// More specific type preferred?No          Yes         No

//Cats prefers to use invariant type classes to allow us to specify more specific instances for subtypes if we want
// if we have Some<Int>, type class instance Option is not used thus we type annotate it as Some(1):Option<Int> or smart
// constructors like Option.apply <- note this is scala smart constructor Option.empty, some and none methods