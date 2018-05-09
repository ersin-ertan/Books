package ch04

import arrow.core.Option
import arrow.core.fix
import arrow.core.monad
import arrow.data.ListK
import arrow.data.fix
import arrow.data.monad
import arrow.typeclasses.binding

// Every monad is also a functor, we can define map in the same way for every monad using existing methods
// flatmap and pure

//interface Monad<F<A>>{
//    fun <A>pure(a:A):F<A>
//    fun <A,B>flatmap(value:F<A>, func1:(A)->F<B>):F<B>
//    fun <A,B>map(value:F<A>, func2:A->B):F<B> = flatmap(value, pure(func2))

// 4.2.1 The Monad Type Class - extends two other type classes: flatMap for flatmap, and applicative for pure
// Applicative also extends functor, giving monads a map method

val opt1 = Option.monad().just(3)
//val opt2 = Option.monad().flatMap(opt1, { a -> Some(a + 2) })
//val opt3 = Option.monad(opt2) { a -> 100 * a }

// arrow way - not quite sure
val opt2 = Option.monad().binding {
    opt1.map { it + 2 }.bind()
}.fix()
val opt3 = Option.monad().binding {
    opt2.map { 100 * it }.bind()
}

val list1 = ListK.monad().just(3)
//val list2 = ListK.monad().flatmap(listOf(1,3)){a->List(a, a*10)}
//val list3 = ListK.monad().map(list2){a->a+123}
val list2 = ListK.monad().binding {
    ListK(listOf<Int>(1, 3, 4).map { listOf(it, it * 10) }.flatten()).bind()
}.fix()
val list3 = ListK.monad().binding {
    ListK(list2.map { it + 123 }).bind()
}.fix()

// default instances for all monads - something about futures and bringing execution context into scope fixes the implicit
// resolution to summon the context

// monad syntax
//fun <A:Int, F : Monad<A>> sumSquare(a: F, b:F):F = a.binding { b.map{ y -> y*y} } // not quite sure about this

// 4.3 The Identity Monad