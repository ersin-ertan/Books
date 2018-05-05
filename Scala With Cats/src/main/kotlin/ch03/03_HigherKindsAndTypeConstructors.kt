package ch03

/*
Kinds are types for types - describing the number of holes in a type
Regular types have no holes
Type constructors have holes to fill to produce types

List has one hole, and we fill it bby specifying a param to produce a regular type like List<Int>

Type Constructors are not the same as generic types. List is a type constructor, List<A> is a type

close analogy - functions and values

functions are value constructors
math.abs == function that takes one param
math.abs(x) == value produced using a value param

scala type constructor are via underscore
def myMeth[F[_]] = {
val functor = Functor.apply[F]

same as
val f = (i:Int) -> i+1
val f2 = f andThen f

higher kinded types are advanced language feature
*/