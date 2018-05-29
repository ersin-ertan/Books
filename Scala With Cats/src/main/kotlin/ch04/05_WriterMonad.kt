package ch04

import arrow.Kind
import arrow.core.Tuple2
import arrow.data.WriterT

// 4.7 The Writer Monad - carry a log along with the computation, to record messages, errors or additional data about
// the computation and extract the log alongside the final result

// common to record sequences of steps in multi threaded computations where standard logging can result
//from interleived messages frorm different contexts. With writer the log for the computation is tied to the results.

// 4.7.1 Creating and Unpacking writers
// Writer<Log, Res)> carries long and result

fun usage() {
  // Writer(Vector("a", "b"), 34) is actually a WriterT
  val wt = WriterT<String, Int, Boolean>(object : Kind<String, Tuple2<Int, Boolean>> {})
  // WriterT is a monad transformer - WriterT<Id, Log, Res> = Writer<Log, Res>
//  wt.pure(1) empty log, only result
//  wt.tell(Monad<String>, Semigroup<Int>, 1) // log and no results use tell
//  wt.apply scala syntax for log and result
  // val aResult = wt.value
  // val aLog = wt.written
//  wt.run to get both // scala syntax
}

// 4.7.2 Composing and Transforming Writers - log in writer is preserved when we map/flatmap, flatmap appends the logs from
// source writer and the results of the users sequencing function. Thus use a log with efficient append and concatenate
// like Vector<E>
// we can also transform the log in a rier with the mapWritten method
// writer2 = wrier1.mapWritten(it.map(::toUpperCase)) // WriterT(Vector(A,B,C), 34)
// or transform both via bimap({it.toUpperCase()},{it + 1})
// clear log with reset, and swap log and result with swap

// TODO("Exercise")

fun main(args: Array<String>) {
//  usage()


}