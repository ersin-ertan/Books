package ch04

import arrow.core.Tuple2
import arrow.data.State
import arrow.data.run
import arrow.data.runA
import arrow.data.runS
import p

// 4.9 The State Monad
// allows us to pass additional state around as part of computation. Define state instances representing atomic
// state operations and thread them togeher9 using map and flatmap, we can model mutable state in a pure functional
// way without mutation

// Creating and Unpacking State
// Boiled down to their simplest form, instances of State<S,A> respresent functions of S->(S,A>
// S is state type, A is result type

val s = State<Int, String> { state -> Tuple2(state, "res") }

// instance of state is a function that: transforms an input state to an output state, computes a result
// run monad by supplying initial state, State provides three methods, run, runS, runA, returning different
// cominations of state and result. Each returns an instance of Eval, which state uses to maintain stack
// safety. Call value method to extract the actual result

fun stateUsage() {
  s.p()
  val (state, res) = s.run(10)
  state.p()
  res.p()
  "".p()

  val state1 = s.runS(20)
  state1.p()
  "".p()

  val res1 = s.runA(30)
  res1.p()
}

// 4.9.2 Composing and Transforming State
// Reader and Writer, power of state monad comes with combining instances. map and flatmap thread the state
//from one instance to another,, each instance represents atomic state transformation, combination respresnts
// a complete sequence of changes

fun stateComposition() {
  val s1 = State<Int, String> { Tuple2(it + 1, "Result of s1: ${it + 1}") }
  s1.p()

  val s2 = State<Int, String> { Tuple2(it * 2, "Result of s1: ${it * 2}") }
  s2.p()

//  state monad/flatmap is unknown TODO("how to use?")
  // functions: get gets state as result, set updates the state returns unit as result
  // pure ignores state, returns supplied result, inspect extracts state via transform func
  // modify updates the state using an update function

  val getDemo = State().get<Int>()
  getDemo.run(10).p()

  val setDemo = State().set(30)
  setDemo.run(10).p()

//  val justDemo = State.just<Int, String>( "Result")

  val inspectDemo = State().inspect<Int, String> { it.toString().plus("!") }
  inspectDemo.run(10).p()

  val modifyDemo = State().modify<Int> { it.plus(1) }
  modifyDemo.run(10).p()

  // Todo("assemble building blocks using for comprehension")
  // Todo("exercise post order calculator")
}

fun main(args: Array<String>) {
//  stateUsage()
  stateComposition()
}
