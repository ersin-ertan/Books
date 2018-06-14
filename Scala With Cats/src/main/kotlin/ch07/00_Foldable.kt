package ch07

import arrow.Kind
import arrow.core.Eval
import arrow.core.ForOption
import arrow.core.Option
import arrow.data.ListK
import arrow.typeclasses.Foldable
import p

/*
two type classes that capture iteration over collections
Foldable abstracts the foldLeft and foldRight
Travers is a higher level abstarction using applicatives to iterate easier than folding

7.1 Foldable
Foldable typeclass captures foldLeft and foldRight used in sequences like list, stream.
Can write generic folds that work with a variety of sequence types, or invent new sequences
to plug them into our code. Gives us uses cases for Monoids and Eval

7.1.1 Folds and Folding - supply an accum and binary function to combine each item

*/

fun main(args: Array<String>) {
//  foldUsage()
//  foldExercises()
  foldableInCats()
}


fun foldUsage() {
  ListK(listOf(1, 2, 3, 4)).foldLeft("Elements are:", { s, i -> s.plus(" " + i.toString()) }).p()
  ListK(listOf(1, 2, 3, 4)).foldRight("Elements are:", { i, s -> s.plus(" " + i.toString()) }).p()
  // note that i and s are swapped for left and right

  // order of operation is important for non associative operations
}

// 7.1.2 Exercise: Reflecting on Folds - using foldleft right with an empty list as the eccum and
// :: as the binary operator? :: prepends a single item

fun foldExercises() { // should be foldRight but i used fold
  val l = listOf(1, 2, 3)

  ListK(l).foldLeft(emptyList<Int>(), { a, i -> a.plus(i) }).p()
  // in scala using the :: operator reverses the list

  // implement substitutes for List's map, flatMap, filter and sum via foldRight
  l.map { it.plus(1) }.p()

  fun <A, B> List<A>.foldLeftMap(f: (A) -> B): List<B> =
    this.fold(emptyList(), { acc, a -> acc.plus(f(a)) })

  l.foldLeftMap { it.plus(1) }.p()


  fun <A, B> List<A>.foldLeftFlatMap(f: (A) -> List<B>): List<B> =
    fold(emptyList(), { acc, a -> acc.plus(f(a)) })

  "flatmap".p()
  l.flatMap { setOf(it) }.p()
  l.foldLeftFlatMap { listOf(it) }.p()

  fun <A, B> List<A>.foldLeftFilter(f: (A) -> Boolean): List<A> =
    fold(emptyList(), { acc, a -> if (f(a)) acc.plus(a) else acc })

  fun List<Int>.foldLeftSum(): Int =
    this.fold(0, { acc, t -> acc + t })
}

// 7.1.4 Foldable in Cats
fun foldableInCats() {
  Option(2).foldLeft(10, { i, ii -> i + ii }).p()
  ListK(listOf(1, 3, 4)).foldLeft(10, { i, ii -> i + ii }).p()
  object : Foldable<ForOption> {
    override fun <A, B> Kind<ForOption, A>.foldLeft(b: B, f: (B, A) -> B): B {
      TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <A, B> Kind<ForOption, A>.foldRight(lb: Eval<B>, f: (A, Eval<B>) -> Eval<B>): Eval<B> {
      TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
  }

  // folding left is eager, folding right is lazy thus uses Evals for stack safety
}