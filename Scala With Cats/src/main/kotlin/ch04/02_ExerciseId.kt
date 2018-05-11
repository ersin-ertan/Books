package ch04

import arrow.core.Id
import arrow.core.Option
import arrow.core.fix
import arrow.core.monad
import arrow.data.ListK
import arrow.data.fix
import arrow.data.monad
import arrow.typeclasses.binding
import p

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

// useful if we could use sumSquare with params in or not in a monad. Thus we can abstract over monadic and non code.
// We can do so with Id

//fun <F<*>: Monad> sumSquare(a:F<Int>, b:F<Int>):F<Int> = ... yield x*x + y*y
// sumSquare(3,4)
// sumSquare(3:Id<Int>, 4:Id<Int>)
// type Id<A> = A

// Id is a type alias turning atomic type into a single param type constructor, casting any value of any type to a Id
val name: Id<String> = Id("Dave")
val num: Id<Int> = Id(234)
val list: Id<List<Int>> = Id(listOf(1))

fun usingId() {
    val a = Id.monad().just(3)
    val b = Id.monad().binding { a.bind() + 1 }
    val c = b.fix()
    // idea is to abstract over monadic and non code(extremely powerful) we can run code async in production using future
    // and sync in test using id(case study ch8)
}

// 4.3.1 Exercise: Monadicc Secret Identities - implement pure, map, flatmap for Id
fun monadicId() {
    fun <A> pure(value: A): Id<A> = Id<A>(value)
    fun <A, B> Id<A>.mapp(f: (A) -> B): Id<B> = pure<B>(f(this.value))
    fun <A, B> Id<A>.flatmap(f: (A) -> Id<B>): Id<B> = f(this.value)

    val v = pure(1)
    v.p()
    v.map { it.plus(1).toString() }.p()
    v.mapp { it.plus(2).toString() }.p()
    v.flatmap { i: Int -> Id((i + 3).toString()) }.p()
    v.flatMap { i: Int -> Id((i + 4).toString()) }.p()
}

fun main(args: Array<String>) {
    monadicId()
}