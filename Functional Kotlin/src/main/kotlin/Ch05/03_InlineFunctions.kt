package Ch05

// Inline functions - higher order functions coe with caveat performance penalties, on copileation time, lambda gets translated
// to an object that is allocated, calling invoke operator

// for performance critical locations mark fun with inline and the function execution is replaced by the higher order functions
// body and the lambdas body, faster but at the cost of more byte code, execution optimizations compound

// Recursion and corecursion - used tailrecFib and tailrecFactorial because we started with a number and reduced it to reach
// a base condition. forEach and fold are recursive too, recursion takes a complex value and reduces it to the desired
// answer, corecursion takes a value and builds on top of it o produce a compound value(potentially infinite data struct
// such as Sequence<T>

// As we use fold, we can also use an unfold function which takes in tow params, initial s for start, and f lambda which
// takes the s and produces a Pair<T,S>? nullable pair of the T value to add to the sequence and the next s step.
// If the result of f(s) is null, we return an empty sequence, else we create a single value sequence and add the result
// of unfold with the new step. Thus we can create a function that repeats a single element many times.

fun <T, S> unfold(s: S, f: (S) -> Pair<T, S>?): Sequence<T> {
  val res = f(s)
  return if (res != null) sequenceOf(res.first) + unfold(res.second, f) else sequenceOf()
}

fun <T> elements(element: T, numOfValues: Int): Sequence<T> {
  return unfold(1) { i -> if (numOfValues > i) element to i + 1 else null }
}

// elements function takes the element to repeat any number of times. internally uses unfold passing 1 as the initial
// step and a lambda that takes the current step and compares it with numOfValue, returning Pair<T,Int> with the same
//element and the current step +1 or null

// same for factorial sequence - difference is initial step is Pair<Long, INt> nthe first element to carry the calculation
// and the second to evaluate against size, thus our lambda returns Pair<Long, Pair<Long, Int>>
fun factorial(size: Int): Sequence<Long> = sequenceOf(1L) + unfold(1L to 1) { (acc, n) ->
  if (size > n) {
    val x = n * acc
    (x) to (x to n + 1)
  } else null
}

// same with fibonacci except we use Triple<Long, Long, Int>

// corecursive implementations to generate factorial uand fib seq are mirror of recursive