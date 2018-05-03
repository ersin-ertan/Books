package ch02

/*
2.2 Semigroup - just the combine part of monoid, some are monoids but some data types cannot define an empty element
Restrict Ints to non empty sequences and only positive, we are no longer able to define empty.
NonEmptyList data type has an implementation of Semigroup but no implementation of monoid

interface Semigroup<A> { fun combine(x:A,y:A):A }
interface Monoid<A>:Semigroup<A> { fun empty:A }

because of this inheritance, when defining a monoid for type A we get a semigroup for free thus we can pass a monoid in
all cases a semigroup is needed
*/
