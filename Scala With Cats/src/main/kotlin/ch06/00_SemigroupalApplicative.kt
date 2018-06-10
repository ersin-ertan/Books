package ch06

// 6 Semigroupal and Applicative

// functors/monads sequence via map/flatmap but cannot represent certain flows
// ex form validation, to return all errors, not fail fast.

// semigroupal for composing pairs of contexts
// applicative extends semigroupal and functor, applying functions to parameters within a context
// often formulated in terms of function application, instead of semigroupal formulation

// 6.1 semigroupal - combine contexts. F<A> and F<B>, Semigroupal<F> ccan combine to form
// F<Pair<A,B>>
// interface Semigroupal<F<_>>{ fun product<A,B>(fa:F<A>, fb:F<B>):F<Pair<A,B>>
// since fa and fb are independent, combining order doesn't matter before passing to product
// flatmap is strick order

// 6.1.1 Joining Two Context - Semigroup joins values, Semigroupal joins contexts

//val sg = Semigroupal  doesn't exist in arrow
// Semigroupal<Option>.product(234.some(), "ab".some()) is Option<Pair<Int,String> or Some(Pair(234,"ab))
// if either is None, then both are or both are some

// 6.1.2 Joining three or more contexts - map contramap instances for greater arities

// 6.2 Apply Syntax
// (Option(1), Option(3)).tupled creates the some pair

// or mapN(Cats.apply), mapX methods are type checked and param number checked

// 6.2.1 Fancy Functors and apply syntax - apply syntax also has contramapN and imapN accepting
// contravariant and invariant functors. Ex combine Monoids via Invariant

// class Cat(name:string, age:Int, foodList:List<STrinG>)
// val tupleToCat:(String, INt, List<String>) ->Cat = Cat.apply _
//val catToTuple: Cat ->(String, Int, List<String>) = cat -> (cat.name, cat.age, cat.foodList)
// implicit val catMonoid:monoid<Cat> = ( Monoid<String>, Monoid<Int>, Monoid<List<String>>).imapN(toupleToCat)(catToTuple)

// Monoid allows us to create empty cats, and add cats

// c1 + c2