package ch05

import arrow.Kind
import arrow.core.Either
import arrow.core.ForEither
import arrow.core.Right
import arrow.core.Some
import arrow.data.OptionT

// 5.3.2 Building Monad Stacks
// all follow same convention, transformer is the inner monad in the stack, first type param is the outer monad, remaining
//type params are types used to form the corresponding monads

typealias ListOption<A> = OptionT<List<A>, A> // but in kotlin we cant put a type in a type constructor place
// thus it is included for example purposes

typealias ErrorOr<A> = Either<String, A>
typealias ErrorOrOption<A> = OptionT<ErrorOr<A>, A> // a monad, can use pure, map, flatmap to create & transform instances:

fun transformerUsage() {

  val a: OptionT<Kind<ForEither, String>, Int> = OptionT(Right(Some(10)))
  val b: OptionT<Kind<ForEither, String>, Int> = OptionT(Right(Some(43)))

//  val c = a.flatMap { x -> b.map{ y -> x + y } } // not working
  // TODO("Figure out how this works")
}

// to stack three or more monads, Future Either Option:  class EitherT<F<_>, E, A)(stack:F<Either<E,A>>){...}
// F<_> is outermonad in the stack, either is inner, E is error type for Either, A is result type for Either
// create alias for EitherT fixing Future and Error and allow A to vary ...

fun threeMonadStack() {
  // Todo("Find out")
}

// 5.3.3 Constructing and Unpacking Instances
// unpack using errorStack1.value.map{ it.getOrElse{-1} }
// each call to value unpacks a single monad transformer

// 5.3.4 Default Instances
// Many monads are defined using corresponding transformer and Id monad,
// type Reader<E,A> = ReaderT<Id,E,A> // also = Kleisli<Id,E,A>
// other cases monad transformers are defined seperately

// 5.3.5 Usage Patterns - wide spread usage is difficult because they fuse monads in predefined ways, thus forced
// un and repackage in different configs to operate on them in different contexts

// Cope with this by creating super stack, ex web app, decides all request handlers are async and can fail with same HTTP
// error cods, thus design custom ADT to represent all errors and use a fusion Future and Either everywhere in code

sealed class HttpError {
  class NotFound(item: String) : HttpError()
  class BadRequest(msg: String) : HttpError()
  // ...
}
// typealias FutureEither<A> = EitherT<Future,HttpError,A>

// but super stack fails in larger, heterogenious code bases where different stacks make sense in different contexts

// We could also use monad transformers as glue code, by exposing untransformed stacks at module boundaries, transforming them
// to operato on them locally, and untransform before passing them on, thus each module decides which to use

//typealias Logged<A> = Writer<List<String>,A>

/*fun parseNumber(s:String):Logged<Option<Int>> = Try(str.toInt).toOption when {
  Some(num) -> Writer(list("Read $s"), Some(num))
  None -> Writer(list("Fail $s"), None)
}

fun addAll(a:String, b:String, c:String):Logged<Option<Int>> {
val result =
OptionT(parseNum(a) + OptionT(parseNum(b) + OptionT(parseNum(c)
return result.value
}

thus val r1 = addAll("1", "2","3") // returns full type WriterT((List(Read1, Read2, Read3), Some(6))
thus val r2 = addAll("1", "a","3") // returns WriterT((List(Read1, Fail, Read3), Some(6))
OptionT is not forced on others

no one size fits all when using monad transformers, best approach depends on: size and experience of team, complexity of
code base, ...
*/
