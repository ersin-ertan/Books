package ch03

import p

// 3 Immutability - what, advantage, how, in variables, val vs var, val and const val, compiler optimization
// immutable collections, disadvantages

// immutability and thread safety

// val enforces read only, cannot write to a val variable after initialization without a custom getter
// thus we use const val
object MutableVal {
  var count = 0
  val s: String = "Mutable"
    get() = "$field ${++count}"
  const val ss: String = "Immutable" // cannot have a getter because it is a compile time constant
}

fun mutableAndImmutable() {
  repeat(3) { MutableVal.s.p() }
  repeat(3) { MutableVal.ss.p() }
}

fun main(args: Array<String>) {
//  mutableAndImmutable()
}


// while val can be in any scope, const val must be top level member of class/object
// delegates cannot be written for it either
// Const val can only be primitives or String
// Cannot be nullable typed thus no null values

// Types of immutability
// Referential immutability - once reference is assigned it cannot be reassigned, values within can be changed
// but the variable cannot, plus/add to list generates a new one
// Immutable values - Collection and Mutable collection, list is unmodifiable, creates new data structures