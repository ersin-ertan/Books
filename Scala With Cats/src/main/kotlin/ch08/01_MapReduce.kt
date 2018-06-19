package ch08

// Chapter 9

// implement parallel processing framework using Monoids, Functors, and others
// Hadoop, bigdata, MapReduce, a programming model for parallel data processing acr0oss clusters of
// machines(nodes)  model is build around map phase, and reduce phase, or fold

// parallelizing map and fold
// map is apply func A->B to F<A> returning F<B>
// no dependencies between transformatiotns of elements thus is parallelizable
// implement fold with Foldable instance, which not every functor has but we can map-reduce any that does
// distributed reduce loses control over traversal order overall may not be left to right
// but sub sequences can and then combine result, reductio must be associative

// fold requires to seed computation with type B, (seed should not affect result) thus identity element

// parallel fold with yield correct results if: reducer function is associative, seed computation with id function
// this is a Monoid

// Simple single machine map-reduce implementing method called FoldMap to model the data flow

// 9.2 Implementing foldMap - re implement foldMap here
// accept paarams
// fun <A, B:Monoid> foldMap(seq:Vector<A>, f:(A)->B)
// ad implicit parm or context bounds to complete type sig

// book uses Vector, should i use list or Sequence?

// body = initial data seq, map step, fold/reduce step, result

//fun <A,B: Monoid<*>>foldMap(values:Sequence<A>, func:(A)->B): Id<B> =
//  values.fold(Monoid<B>.empty, {it.combine(it)}) // uses scalas |+|
// thus foldMap(Vector(1,2,3))(identity) is Int = 6

// or slight change
/*
fun <A,B:Monoid<*>> foldMap(values:ListK<A>, func:(A)->B):B =
  values.foldLeft(object:Monoid<B>{
    override fun empty(): B {
      TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun B.combine(b: B): B {
      TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
  }.empty(), { it.combine(func(it)) })
*/

// 9.3 Parallelising foldMap
// single threaded implementation of foldmap
// distributed is:
// start with initial list of all data to process, divide data to baches, send one patch per cpu
// cpu run a batch level map phase in parallel, cpu run batch level reduce in parallel producing
// a local result for each batch, reduce the results for each batch to a single final result

// could use parallel collections library, or try ourselves using future

// 9.3.1 Futures, Thread Pools, and Execution Contexts
// Scala futures run on a thread pool determined by implicit ExecutionContext param in scope

// List<Computations> -> List<CpuList<Computation> -> mapping -> value reduction -> final reduction

// val ftuture1 = Future { (1 to 100).toList.foldLeft(0)(_ + _)

// default context allocates a thread pool with one thread per cpu
// map and flatMap schedule computation as soon as input are computed and cpu is available

// future4 = for { a <- future1 b<-future2 } yield a+b
// We can convert List<Future<A>> to Future<List<A>> using .sequence via traverse instance

// then Await.result

// also monad and monoid implementatiotns for Future available

// 9.3.3 Dividing Work
// Runtime.getRuntime.availableProcessors
// (1 to 10).toList.grouped(3).toList = List<List<Int>

//9.3.3 Implementing parallelFoldMap
/*
* fun <A,B:Monoid>parallelFoldMap(values:Vector<A>, func:A->B):Future<B> =
*
* val numCores = 4
* val grouSize = (1*values.size/numcores).ceil.toInt
*
* val groups:iterator<Vector<A>> = values.grouped(groupSize)
*
* val futures:Iterator<future<b>> = groups map { group -> Future{ group.foldLeft(Monoid<B>.empty)
* (_ |+| func(_))
* }
*
* // this could be foldMap(group)(func)
* Future.sequence(fuures) map { iterable -> iterable.foldLeft(Monoid<B>.empty)(_|+|_)
* }}
*
* val result:Future<Int> = parallelFoldMap((1 to 100_000).toVector)(identity)
*
* Await.result(result, 1.second)
* */

// can reuse def of foldMap for more consise solution - local maps and reduces in step 3 and 4 are a single flodMap

// or use Foldable type class

fun main(args: Array<String>) {
//  listOf(1,2,3).k().traverse(Option.applicative(), {it})
}
