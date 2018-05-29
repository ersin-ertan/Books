package ch04

import arrow.core.*
import arrow.typeclasses.binding
import p

// 4.4 Either - useful monad
val e = Either.monad<Boolean>().binding {
    //    true.left().bind()
    true.right().bind()
}

//fun countPositive(nums: List<Int>) =
//        nums.fold(Right(0)) { acc /*:Either<Nothing, Int>*/, num/*: Int*/ ->
//            if (num > 0) acc.map { it + 1 }
//            else Left("Negative. Stopping")
//        }
// compiler infers type of accum as right instead of either
// didn't specify type params for right.apply so compiler infers the left as Nothing

// scala version requires 0.right() but kotlin must be explicit in the acc
fun countPositive1(nums: List<Int>) =
        nums.fold(Right(0)) { acc: Either<String, Int>, num: Int ->
            if (num > 0) acc.map { it + 1 }
            else Left("Negative. Stopping")
        }

fun testCount() {
    countPositive1(listOf(1, 3, 4)).p()
    countPositive1(listOf(1, -3, -4)).p()
}

fun extensionMethods() {
    Try.just(2).toEither().p()
    Option(2).toEither { 1 /*if empty then 1*/ }.p()
    None.toEither { 1 /*if empty then 1*/ }.p()
    // Transforming Eithers
    "test".left().getOrElse { 0 }.p()
    "test".left().swap().p()
    "test".left().swap().p()
    Try.just(3).recover { 1 }.p()
    Try.just(3).recoverWith { Try.just(4) }.p()
    Try.just(3).orElse { Try.just(5) }.p()
    Right(12).mapLeft { "flower" }.p() // Result: Right(12)
    Left(12).mapLeft { "flower" }.p()  // Result: Left("flower)
    1.right().bimap({ fa -> "L" }, { fb -> "R" }).p()
    1.left().bimap({ fa -> "L" }, { fb -> "R" }).p()
}

// 4.4.4 Error Handling
fun errorHandling() {
    Right(1).flatMap { it.left() }
    Right(1).flatMap { it.right() }
    Left(1).flatMap { /*it = Nothing*/ 1.right() }
    Either.monad<Int>().binding { 1.right().bind() }

    // could use Either for error handling needing to determine what type we want to use to represent errors
    // or use throwable, but throwable is too broad
    // Thus we define algebraic data types to represent errors

}

sealed class LoginError
class UserNotFound(val username: String) : LoginError()
class PasswordIncorrect(val username: String) : LoginError()
object UnexpectedError : LoginError()
data class User(val username: String, val password: String)

typealias LoginResult = Either<LoginError, User>

fun modelingErrors() {
    // fixed set of expeted error types and a catch all of anything else - safety of exhaustivity checking on pattern matching
    fun handleError(error: LoginError) {

        when (error) {
            is UserNotFound -> "User not found".p()
            is PasswordIncorrect -> "password incorrect".p()
            is UnexpectedError -> "unexpected".p()
        }
    }

    val r1: LoginResult = User("dave", "pass").right()
    val r2: LoginResult = UserNotFound("dave").left()

    r1.fold(::handleError, Any::p)
    r2.fold(::handleError, Any::p)

}

// error recovery is important for processing large jobs - ex. fail on last element
// error reporting is important too
// number of cases we want to collect all the errors, not just the first one we encountered - validating web forms

// 4.5 Aside: Error Handling and Monad Error

// MonadError abstract over either-like data types used for error handling; providing extra operations for raising and
// handling errors like raiseError, handleError, ensure
// MonadError<F[_], E> extends Monad<F> Fe is the type of the monad, E is the type of error contained within F
// typealias ErrorOr<A> = Either<String, A>
// val monadError = MonadErrror<ErrorOr, String>

// ApplicativeError - MonadError extends AppErr

fun raisingAndHandlingErrors() {

  val success = Either.monadError<String>().just(34)
  val fail = Either.monadError<String>().raiseError<Int>("Wrong")
  success.p()
  fail.p()
//    val fail1 = Option.monadError().handleError // not implemented
//    val ensure = Either.monadError<String>().ensure // not implemented
  Try.monadError().raiseError<String>(Throwable("Error")).p()
}

// 4.5.4 Exercise not yet completed in book

fun main(args: Array<String>) {
//    testCount()
//    extensionMethods()
//    modelingErrors()
  raisingAndHandlingErrors()
}
