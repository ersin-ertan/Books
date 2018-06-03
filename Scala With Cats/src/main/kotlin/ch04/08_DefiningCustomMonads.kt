package ch04

import arrow.Kind
import arrow.core.Either
import arrow.core.ForOption
import arrow.core.Some
import arrow.typeclasses.Monad

// 4.10 Defining custom monads
// by providing implementations of three methods, flatmap, pure, tailRecM

val optionMonad = object : Monad<ForOption> {
  override fun <A> just(a: A): Kind<ForOption, A> = Some(a)

  override fun <A, B> tailRecM(a: A, f: (A) -> Kind<ForOption, Either<A, B>>): Kind<ForOption, B> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun <A, B> Kind<ForOption, A>.flatMap(f: (A) -> Kind<ForOption, B>): Kind<ForOption, B> =
    TODO("imp")
}

// tailRecM is an optimisation to limit amount stack space by nested calls to flatMap, recursively call
// unstill result of fn returns a Right, if tail recursive we guarantee stack safety, but is challenging

// Exercise - write monad for threed ta type

sealed class Tree<in A>
data class Branch<A>(val left: Tree<A>, val right: Tree<A>) : Tree<A>()
data class Leaf<A>(val value: A) : Tree<A>()

fun <A> branch(left: Tree<A>, right: Tree<A>): Tree<A> = Branch(left, right)
fun <A> leaf(value: A): Tree<A> = Leaf(value)

val treeMonad = object : Monad<Tree<*>> {
  override fun <A> just(a: A): Kind<Tree<*>, A> = TODO("Leaf(a)")

  override fun <A, B> tailRecM(a: A, f: (A) -> Kind<Tree<*>, Either<A, B>>): Kind<Tree<*>, B> {
    TODO("see https://stackoverflow.com/questions/44504790/cats-non-tail-recursive-tailrecm-method-for-monads")
  }

  override fun <A, B> Kind<Tree<*>, A>.flatMap(f: (A) -> Kind<Tree<*>, B>): Kind<Tree<*>, B> =
    TODO("""when{
//      this is Branch<*> -> branch( f(flatMap { left }), f(flatMap { right })) // is this correct?
//      this is Leaf<*> -> f(value)
    }""")
}

// then you can
//val treeMonadUsage = branch(leaf(100), leaf(200)).flatMap{x-> branch(leaf(x-1), leaf(x+1))}
// for comprehension a, b, c

//TODO("Understand the exercise")

// monad for option provides fail fast semantics
// monad for list provides concatenation semantics
// monad for tree provides list concatenation along two axes

// Summary - flatmap is viewed as an operator for sequencing, dictating order of operation.
// option represents commutation that can fail without error message, either with message, list for
// multiple results, future for computation that may produce a value in future

// custom types and data structures: id, reader, writer, state

// custom monad and tailRecM