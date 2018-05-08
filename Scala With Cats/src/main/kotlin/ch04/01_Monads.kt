package ch04

import arrow.core.*
import arrow.data.fix
import arrow.instances.ListKMonadInstance
import arrow.typeclasses.binding
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import p

/*
Monad, most common abstraction. Anything with constructor and flatMap method. Functors are monads, including Option,
List, Future. Special syntax for monad support for comprehensions(in Arrow aka bindings)

Monad - a way to sequence computation... but functors were a control mechanism for the same thing...
Functors are limited in that they only allow this complication to occur once at the beginning of the sequence, not
accounting for complications at each step in the sequence.

Monads flatMap allows us to specify what happens next(intermediate complication). The function passed to flatMap specifies
the application specific part of the computation. FlatMap takes care of allowing us to flatMap again.

Options -
*/

fun parseInt(str: String): Option<Int> = Try { str.toInt() }.toOption()
fun divide(a: Int, b: Int): Option<Int> = if (b == 0) None else Some(a / b)
fun divide(a: Option<Int>, b: Option<Int>): Option<Int> = Option(1) // for display purposes only

// each may fail by returning None, flatmap allows us to ignore this when we sequence operations

fun stringDivideBy(strA: String, strB: String): Option<Int> =
        parseInt(strA).flatMap { numA ->
            parseInt(strB).flatMap { numB -> divide(numA, numB) }
        }

// first parseInt returns None or some, if some flatmap calls function and passes the int numA, the second call to
// parseInt returns None or Some, if some flatmap calls function and passes numB, the divide returns None or Some, result

// each step is dependent on the flatmap calling the next function, using option, results are fail fast with None stopping
// the computation

fun testDivide() {
    stringDivideBy("3", "1") // Some(3)
    stringDivideBy("3", "0") // None
    stringDivideBy("3", "Hello") // None
}

// every monad is also a functor, so we can rely both on flatmap and map to sequence computations that do and don't
// introduce a new monad

fun stringDivideByBinding(strA: String, strB: String) = Option.monad().binding {
    val a = parseInt(strA)
    val b = parseInt(strB)
    val div = divide(a, b)
    div
}

// Lists - think of lists as sets of intermediate results, flatmap becomes a construct calculating permutations and
// combinations

fun testingLists() {
    object : ListKMonadInstance {}.binding {
        (1..3).map { (4..5).map { itt -> Pair(it, itt) } }.flatten()
    }.fix().p()
} // or something similar

// three possible values of x and two for y, six values of xy, .flatmap is generating these
/// combinations from our code which states the sequence of get x get y create tuple

fun main(args: Array<String>) {
//    testingLists()
//    val l = runBlocking {
//        doSomethingVeryLongRunning().await().p()
//    }
//    monad()
//    t2()
}


// Futures - supposedly each future in our sequence is created by a function that receives the result
//from the previous future, each step can only start once the previous has finished, we can run futures in parallel
// monads for sequencing

fun doSomethingLongRunning() = async { delay(1000);1 }
fun doSomethingLongRunning1() = async { delay(2000); 2 }
fun doSomethingVeryLongRunning() = async {
    "starting async".p()
    doSomethingLongRunning().await() + doSomethingLongRunning1().await()
    // this should have been a double flatmap example but I'm not sure how to with asyncs
}


fun testChannel() {
    runBlocking<Unit> {
        val channel = Channel<Int>()
        launch {
            // this might be heavy CPU-consuming computation or async logic, we'll just send five squares
            for (x in 1..5) channel.send(x * x)
        }
        // here we print five received integers:
        repeat(5) { println(channel.receive()) }
        println("Done!")
    }
}

suspend fun doLongRunning(delay: Int, int: Int): Option<Int> =
        Option(int + int.also { "S:$int".p(); delay(delay); "E:$int".p(); })

fun monad() {
    Option.monad().binding {
        val a = runBlocking { doLongRunning(100, 1) }.bind()
//        val a = none<Int>().bind()
        val b = runBlocking { doLongRunning(500, a) }.bind()
        val c = runBlocking { doLongRunning(200, b) }.bind()
        c // he problem is the eager evaluation
    }
}

//fun plusMe(int: Int) = produce<Option<Int>> {
//    Option.monad().just()
//}
//
//fun t2() {
//    Option.monad().binding {
//        val squares = plusMe(1)
//        runBlocking { squares.consumeEach { it } }
//    }.fix().p()
//}

// 4.1.1 Definition of a Monad
// monadic behaviour is formally captured in two operations
// pur of type A -> F<A> and flatMap of type (F<A>, A->F<B>) -> F<B>

// pure abstracts over constructors, creating new monadic context from a plain value, flatmap provides
// the sequencing step extracting the value from the context and genrating the next context in the sequence

// trait Monad<F<*>>{ fun <A> pure(value:A):F<A>  fun <A,B>flatmap(value:F<A>, func: A->F<B>):F<B>

// pure and flat map must obey las allowing us to sequence operations freely without side effects
// left identity pure(a).flatmap(func) == func(a)
// right m.flatmap(pure) == m
// associativity m.flatmap(f).flatmap(g) == m.flatmap(x -> f(x).flatmap(g))