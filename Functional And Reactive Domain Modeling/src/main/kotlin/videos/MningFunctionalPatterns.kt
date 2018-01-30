package videos

/*
Mining Functional Patterns by Debasish Ghosh at FnConf17
https://www.youtube.com/watch?v=tstLx9ilPrk

Very good presentation for intermetidate

Develop an insight to identify good structures, to improve the transformation of an algebraic pattern.

Basic underpinning is algebra, encode functional design pattens using the concepts from algebra.

Patterns part of the language, with mathematical foundations, with an algebra that composes.

Use patterns to raise the level of abstraction, and remove repeated code.

Code is scala, but principles apply equally well to any statically typed functional programming language.

Design Pattern: ocntext dependent, solution to a promlem in context
generic component invariant across context of application

Algebra is the study of  algebraic structures: abstract algebr, structure is a set(underlying set) with oneor more
finitary operations defined on it that satisfies a list of axioms.

The moment we qualify A with a type it becomes an instance of the algebra.
Addition of integers is associative,


Algebra of semigroup is having a set, and an associative binary operation.
When we add the identity operation, it becomes the algebra of monoids.

How do we import this algebra into our code.


class Monoid a where
mempty :: a
mappend :: a -> a -> a

Not specialized for any contrete type

import them in terms of laws
identity laws
x <> mempty = x
mempty <> x = x

associativity
(x <> y) <> z = x <> ( y <> z )

trait Semigroup<A> { def combine(x:A, y:A) :A }

trait Monoid<A> extends Semigroup<A> { def empty:A }

algebra(interface), reusable, polymorphic, standard lib code,

instance of the implementations
specific for a datatype
domain specific instance, specifc for Money, application lib code

val intAdditionMonoid:Monoid<Int> = new Monoid<Int> {
    fun empty: int = 0
    fun combine(x:Int, y:Int):Int = x + y
}

Generic -> Specific

specific implementations use the generic protocol interrface, reusability is enforced by parametricity(no type specific info
in the protocol), genicity implies reusability

Pattern is generic and reusable
The instance is context specific

Functional patterns: generic, reusable algebra, parametirc on types, clear separation between pattern algebra and its instances,
composable through function composition.

Standard vocab, rich ecosystem of support through standard lib, functions defined only terms of these interfaces algebra
can be reused by application level data types that follow the pattern.

Freebies: given a type that has an instance of a Monoid, we have a List of such objects we can combine hem for free using
the combine function of Monoid. Behaviour of combine is completely polymorphic, depending on the instance of the Monid you
pass in.
Given a type V that has an instance of a Monoid, Map<K, V> also gets a Monoid. Standard lib but as an app dev you get for free.

Multiplicative Power: Domain model types with monoid instances A:Monoid, B:Monoid, C:Monoid
Polymorphic behaviour in the lib that expects a monoid.
fun fold<A>(fa:F<A>)(implicit A:Monoid<A>) : A ... foldMap, foldMapM

can combine your application with the types just by the algebra

How can we abstract away the fusion of two maps
dont do tolist tolist.groupBy(k).map{case kv -> k, v.map(second).sum) ...

If the cost of the abstraction excedes the value, don't

Similar problem, different context, reuse of the same algebra, differentt concrete instance of the algebra

Abstractivg over structure and op.eration

Anti patterns: repetition, imperative not expression based, not composeabe, littered with exception handling code
violating referential transparency, not modular

Algebraic composition ex.

fun fromMyConfig(config:Config):ErrorOr<MySettings> = for{
b <- readString("docs.my.b, config)
c <- readString("docs.my.c, config)
d <- readString("docs.my.d, config)
yields MySettings(b, c, d)

we want to compose on the return type
fun fromMyConfig: Config -> ErrorOr<MySettings> = (config:Config) -> for{
b <- readString("docs.my.b, config)
c <- readString("docs.my.c, config)
d <- readString("docs.my.d, config)
yields MySettings(b, c, d)

Config -> Either<Exeption, MySettings>
ReaderT<ErrorOr, Config, MySetings>

now

typealias ConfigReader<A> = ReaderT<ErrorOr, Config, A>

fun fromMyConfig: ConfigReader<MySettings> = for{
b <- readString("docs.my.b)
c <- readString("docs.my.c)
d <- readString("docs.my.d)
yields MySettings(b, c, d)

fun readString(pth:String):ConfigReader<String> = Kleisli { (config:Config) ->
try{Either.right(config.getString(path)) }
catch { case ex:Exception -> Either.left(ex) }
}

Functional patterns: reuse of already existing algebra(Reader, Monad, Either), algebraic composition form larger patterns from
smallers ones, abstractions remains composable, modular

*/