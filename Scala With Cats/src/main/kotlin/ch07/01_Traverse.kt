package ch07

import arrow.core.*
import arrow.data.ListK
import arrow.data.applicative
import arrow.data.k
import arrow.data.sequence
import p

// 7.2 Traverse
// foldLeft and right are flexible iteration but require to define accums and combinator funcs. Traverse type class
// is higgher level that uses Applicatives to provide a more lawful pattern for iteration

// Traversing with futures - a list of server hostnames and a method to poll a host for its uptime

fun traversing() {
  val hostnames = listOf("www.a.com", "www.b.com")

//  fun getUptime(hostname:String):Future<Int> = Future(hostname.length * 60)

  // if we wan to poll all the hosts and collect all uptimes we cant map over host names because result is
  // List<Future<Int>> which is more than one Future. Need to reduce results to single Future to block on

  /*val uptimes: Future<List<Int>> = hostnames.k().foldLeft(Future(emptyList<Int>()), {acc,host ->
    with(getUptime(host)){
      yield acc + uptime
    }
  })*/

//  Await.result(uptimes, 1.second) // List<Int> = List(123,34,213)
  // iterate over hostnames, call func for each item, combine result into list
  // not simple because must create and combine Futures at every iteration
  // thus the travers

//  val allUp:Future<List<Int>> = Future.traverse(hostnames)(getUptime)
//  Await.result(allUptimes,1.sec)

//  fun <A,B>traverse(values:List<A>, func:(A)->Future<B>):Future<List<B>> =
//    values.fold(Future(emptyList<B>()), {acc, host-> with(func(host){yield acc + item})})

  // abstr0acts away fold and defining acums and combination functions so we can
  // start with List<A>, provide a function A->Future<B>, end up with Future<List<B>>
//  Future.sequence assumes we start with List<Future<B>> thus we don't need to provide identity function
  // thus we start with List<Future<A>> and end with Future<List<A>>

  // solve specific problem allowing us to iterate over a sequence of futures and accum a result
  listOf(1.some(), 3.some()).k().traverse(Option.applicative(), { it }).p()
  // Some(ListK(list = [1,3]))

  listOf(Option(1), Option(2)).k().sequence(Option.applicative()).p()
  // Some(ListK(list = [1,2]))

  // traverse type class generalises patterns to work on any type of applicative: option, validated...
  // we generalise over applicative, then generalize over sequence for tools that travialise
  // operations with sequences and other data types
}

fun traversingWithApplicatives() {
  // we cane rewrite tarverse in terms of applicative
//  Future(emptyList<>())
//  is the same as
  ListK.applicative().just(emptyList<Int>())
  Option.applicative().just(None) // why are Type working instead of None()

  // our old combine is equal to Semigroupal.combine
//  fun newCombine uses mapN
  // TODO("understand this better")

  // substitute these back into traverse def and we can generalize it to work with any applicative
}

fun questions() {
//  List<Vector<Int>> is the type of arg, using applicative for vector and return type will then me
  // Vector<List<Int>> vector is a monad, semigroupal combine function is based on flatMap,
  // thus we end up with a vector of lists of all possible combos of List 1 2 and list 3 4


  // List<Vector<Int>> will be Vector<List<Int>> 136 135 146 145 246 245 236 235

  // example that uses Option
  fun process(inputs: List<Int>) =
    ListK(inputs).traverse(Option.applicative(),
      { if (it % 2 == 0) Some(it) else None }
    )

  process(listOf(2, 4, 6)).p() // Some lk 246
  process(listOf(1, 3, 5)).p()// None
  process(listOf(1, 2, 3)).p() // None // option is a monad so semigroupal combine func follows flatmap, fail fast error
  // handling

  // traversing with validated - which uses applicative will  have Valid(lk 2,4,6)
  // Invalid(List(1 not even, 3 not even)) aggregating the error
}

fun traversInCats() {
  // listTravers and listSeq work with any Applicative, but only one sequence type:List
  // we can generalize over different esquence types using a type class, Cat's Travers
  // interface Travers<F<_>>{ fun traverse<G<_>: Applicative, A, B>(input:F<A>, func:A-G<B>):G<F<B>>
//  fun sequence<G<_>:Applicative,B>(inputs:F<G<B>>>):G<F<B>>> = traverse(inputs)(identity)

  // cats has instance of travers for list,vec, stream, option...

  // TODO("find out how to do this")
}

fun main(args: Array<String>) {
//  traversing()
  questions()
}

// summary - foldable and travers type classes for interating over sequences
// foldable for foldLeft right adding stack safe implementations
// traverse - abstracts generalises travers sequence methods turning F<G<A> into G<F<A>>
// for any F with an instance of travers and G with an instance of applicative, traverse is the most powerful
// lines of code reducer