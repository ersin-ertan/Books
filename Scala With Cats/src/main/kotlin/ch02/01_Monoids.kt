package ch02

import arrow.typeclasses.Monoid

// Monoids and Semigroups - first type classes to add or combine groups
// instances for ints, strings, lists, options...

// Integer addition - binary operation that is closed(two ints produce one int)
val add = 2 + 3

// identity element 0 with the property that a+0 == 0+a == a for any Int
// 3+0, 0+3

// Associativity - regardless of order the result is the same for addition, multiplication

// String and sequence concatenation - can also be a binary operation with empty string "" as identity, concat is associative

// Define Monoid - addition scenarios with associative binary addition and identity element is a monoid for type A
// that has an operation to combine with type (A,A) -> A
// an element empty of a type A

// interface Monoid<A> { fun combine(x:A, y:A):A  fun empty:A }
// monoids must obey laws - all values x,y,z in A combine must be associative and empty must be an identity element

fun <A> associativeLaw(x: A, y: A, z: A, m: Monoid<A>): Boolean = m.combineAll(x, y, z) == m.combineAll(z, y, x)
fun <A> identityLaw(x: A, m: Monoid<A>): Boolean = (m.combineAll(x, m.empty()) == x) && (m.combineAll(m.empty(), x) == x)

// Int subtraction is not a monoid because subtraction is not associative
