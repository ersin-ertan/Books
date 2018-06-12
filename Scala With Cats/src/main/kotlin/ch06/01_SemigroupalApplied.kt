package ch06

// 6.3 Semigroupal Applied to Different Types
// doesn't always provide what we expect, esp. for types that have Monoid instances.

// future - semantics of future provide parallel instead of sequential execution
// val futurePair = Semigroupal<Future>.product(Future("Hello"), Future(123))
// Await.result(futurePair, 1.sec)
// res1: (String, Int) = (Hello, 123)

// use apply syntax to zip fixed numbers to futures
// class Cat(name:String, age:Int, foodList:List<String>)
// val futureCat = (Future("Gard"), Future(23), Future(listOf("a", "b")).mapN(Cat.apply)
// Await.result(futureCat, 1.sec)

// combining lists produces potentially unexpected results, we expect a zip but get a cartesian product of elements
// Semigroupal<List>.product(List(1,2), List(3,4))
// List<Pair<Int,Int>> = List(Pair(1,3), Pair(1,4), Pair(2,3), Pair(2,4))
// ziping lists tend to be more common

// Either - fail fast vs accum error handling. product applied to either to acc errors instead of fail fast
// product implements the same fail fast as flatMap
// type ErrorOr<A> = Either<Vector<String>,A>
// Semigroupal<ErrorOr>.product(Left(Vector("e1")), Left(Vector("E2"))
// ErrorOr<Pair<Nothing,Nothing>> = Left(Vector(E1))
// stops on first failure

// 6.3.1 Semigroupal Applied to Monads

// Since list and either are bothe monads, consistent semantics with Cat's monad which extends Semigroupal provides std
// definition of product in terms of map and flatMap, thus is enexpected. But consistency of semantic is important for
// higher level abstractions.
// flatmap provides sequential ordering, so product is the same

// Why Semigroupal? We can create data types that have instances of Semigroupal (and Applicative) but not monad.

// Exercise: The Product of Monads - implement product in terms of flatmap TODO()