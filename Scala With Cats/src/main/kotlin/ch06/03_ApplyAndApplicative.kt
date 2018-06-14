package ch06

// 6.5 Apply and Applicative
/*
 Semiproupals - functionality related to typeclass applicative functore

 semigroupal and applicative provide alternative encoding of joining contexts

 Cats models applicatives using two type classes Apply extends Semigroupal and functor and
 adds the ap method that applies a parameter to a function within a context
 Applicative extends Apply adds the pure method

 interface Apply<F<_>> extends Semigroupal<F> with Functor<F> {
 fun <A,B>ap(ff:F<A->B>)(fa:F<A>):F<B>
 fun <A,B>product(fa:F<A>, fb:F<B>):F<Pair<A,B>> = ap(map(fa)(a->(b:B) -> (a,b)))(fb)
 }

 interface Applicative<F<_>> extends Apply<F>{ fun <A>pure(a:A):F<A> }

 ap applies a param fa to a function ff within context F<_>.
 product ofc Semigroupal is defined in terms of ap and map

 pure is same as Monad - constructs a new applicative instance from an unwrapped value
 Applicative to Apply, as Monoid to Semigroup

 6.5.1 The Hierarchy of sequencing type classes
 - every monad is an applicative, every applicative is a semigroupal, and so on
 Monad type class hierarchy
 Cartesian(product) -> Apply(ap) -> Applicative(pure) --> Monad
 Functor(map) -----/              \->FlatMap(flatMap) -/


 Lawful nature of relationships between type classes, inheritancence are constant across all instances
 Apply defines product in terms of ap and map, Monad defines product, ap, map, in terms of pure and flatMap

 Monad foo has instance fo Monad type implements pure and flatMap and inherits std def of product map ap
 Applicative functor bar has instance of Applicative, implements pure and ap and inherits std def of product map

 We know more about Foo than Bar, as monad is subtype of applicative, thus we can guarantee properties of Foo(flatMap)
 that we cannot with Bar, but Bar may have wider range of behaviour, due to fewer laws to obey.

 Tradeoff of power vs constraint

 Monads impose strick sequencing on computations they model
 Applicatives and semigroupals impose no restriction to represent parallel and independent computions

 Choose data structures based on semantics

 6.6 Summary

 Monads and functors are most used sequencing, semigroupals applicatives are most general like combining indiependent values
 like validation rules, thus Validated.

*/