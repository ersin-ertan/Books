package ch03

import ch01.p
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

/*
mapping list option either apply functions eagerly - sequencing computations is more general

Futures - a functor sequencing asynch computations by queueing and applying as predecessors complete
Type signature of its map method has same shape as the signatures above

No guarantees about internal state, if future is complete then mapping is called, else thread pool queues the function
to be called later. We don't know when our functions are called, but we do know the order.

*/
val futureMappedWithin: Deferred<String> = async {
    val nums = 123
    nums.plus(1).times(2).toString().plus("!")
}

val futureMappedExternal = async {
    val nums = 123
}

fun main(args: Array<String>) {

    futureMappedWithin.p()
    futureMappedWithin.getCompleted().p()

}

suspend fun test() {
    val fme = futureMappedExternal.await() // .plus(1).times(2).toString().plus("!")
}

/*
Function composition is also sequencing, map is all about lazy queueing and when you do it
*/
val func1 = { i: Int -> i + 1 }
val func2 = { i: Int -> i * 2 }
val func3 = { i: Int -> i.toString() + "!" }

val num = func3(func2(func1(123)))

// interface Functor<F<A>> { fun <A,B>map(fa:F<A>, f:(A) -> B):F<B> }

// Functor laws
// - identity: calling map with identity function is the same as doing nothing
// - composition: mapping with two functions f and g is the same as mapping with f and then mapping with g