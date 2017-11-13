package Part2_EmbracingKotlin.I_Generics

// define types that have type parameters, when instance of the type is create, type parms are subistuted with specific
// types called type arguments

// empty lists require explicit types
val r: List<String> = listOf()
val rr = listOf<String>()

// no raw types, must always be defined

// Generic functions and properties -
fun <T> List<T>.slice(indices: IntRange): List<T> {
    return listOf()
}

val infer = listOf(1, 3).slice(1..4) // compiler infers the int
val expl = listOf("e", "a").slice<String>(1..4) // or explicit

fun <T> withLambda(theT: T, operation: (T) -> String): String {
    val theString = operation(theT)
    return theString
}

fun <T> List<T>.withlist(operation: (T) -> String): String {
    return operation(get(0))
}

val <T> List<T>.mustBeAnExtension: T
    get() = this.get(0)

// non extension preperties can't have type params
// val <T> x:T = TODO()


// Declaring generic classes
interface L<T> {
    operator fun get(index: Int): T
}

// new type param A is not the same type of T
class AL<A> : L<A> {
    val a: A? = null
    override fun get(index: Int): A = a!!
}

class ExplType : L<Int> {
    override fun get(index: Int): Int = 1
}


// Type parameter constraints - restrict types that can be used as a type argument
// upper bound as a type constraint thus type must be of type or a sub type

fun <T : Number> sum(): T {
    val t: T? = null
    return t!!
}

val num = sum<Int>()
val num1 = sum<Double>()

// rare case to specify multiple constrains on a type parameter
fun <A, B> myFun(a: A) where A : CharSequence, B : Appendable {}

class C1 {}
class C2 {}
interface C3 {}
open class C4 {}

//fun <T> ensureTraillingPeriod(seq:T) where T:C1, T:C2{} // only one of the upper bounds can be a class
//fun <T> ensureTraillingPeriod(seq: T) where T : C1, T : C3 {} // upper bounds of T has an empty intersection
// because C1 is a final type thus it can only be C1

fun <T> ensureTraillingPeriod(seq: T) where T : C4, T : C3 {}

open class CC : C4(), C3 {
}

val cc = ensureTraillingPeriod<CC>(object : CC() {})


// Making type parameters non null

class P<T> {
    fun p(value: T) {
        value?.hashCode() // is nullable thus ? for safe access
    }
}

// T can be nullable
val p = P<String?>()
val pp = p.p(null) // null value
val ppp = p.p("not")

// apply the upperbound of any to make the value non null
class N<T:Any>{
    fun n(value:T){
        value.hashCode()
    }
}

//val n = N<String?>() // type argument is not within boundsn
val nn = N<String>()