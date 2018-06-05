package ch05

import arrow.data.ForListK
import arrow.data.ListK
import arrow.data.OptionT
import arrow.data.applicative

// 5 Monad Transformers
// monads can bloat code base through nested for comprehensions

// look up user record in db, user may or not be present, return Option<User> and communication with database could fail
// like network, auth, ...) so result is wrapped in Either, so Either<Error, Option<User>>
// requires nested flatmaps

//fun lookupUserName(id: Long): Either<Error, Option<String>> {
//  data class User(val name)
//
//  fun lookupUser(id: Long) = Option(User("Tom"))
//  val name = lookupUser(id).flatMap { user -> user.name }
//  return name
//}

// given two monads, can we combine them to a single monad, do monads compose

//fun compose(m1:Monad<*>, m2: Monad<*>) = object :ComposedMonad ... m1(m2) define pure and flatMap
// it is impossible to write a general definition of flatMap without knowing m1 m2, but if we know about one,
// ex m2 is option, we can
//fun <A,B>flatMap(fa:Composed<A>, f:A->Composed<B>):Composed<B> = fa.flatMap(it.fold(None.pure(m))(f))

// this uses None, an option specific concept but not in monad, we need this to combine Options, other monads have other
// things to help combine

// transformers are available for differen monads, providing knowledge to compose monad with other

// 5.2 A Transformative Example - EitherT, OptionT...
// Use OptionT<List, A> aliased to ListOption<A> to transform a List<Option<A>> to a single monad
// build it from inside out, pass list the type of outer monad as param to optionT the transformer of inner monad
// create instances of ListOption using OptionT constructor or pure

val r1: OptionT<ForListK, Int> = OptionT.just(ListK.applicative(), 10)
//val r1 : OptionT<ForListK, Int> = ListK.just(32.some()) // something like this, then
//var r3 = r1.flatMap{ i: Int -> r2.map{ y:Int -> i+y} }

// monad transformers combine map and flatMap methods to use both components of monads without recursively unpacking
// and repacking values at each stage

//val r = OptionT.just(Option.applicative(), 12).doSomething // Not sure what to do

// 5.3 Monad Transformers in Cats

// each monad transformer is a data type so we can wrap stacks of monads to produc new monads, use monads built via monad
// type class, to understand know:
// available transformer classes, build stacks of monads using transformers, construct instances of monad stacks, pull
// apart stack to access wrapped monads

// classes OptionT, EitherT, ReaderT, WriterT, StateT, IdT

// Reader monad is a specialized kleisli arrow, Kleisli and ReaderT are the same just a type alias