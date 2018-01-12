package B_scalaForFunctionalDomainModels

import kategory.*

// 2.6.0 Making modes reactive with scala

// Functional thinking and implementing with pure functions for domain models is the engineering
// Need language support to build models that are: responsive to failures, scales well with increasing load, fast ux
// manage failures(design for failure), minimize latency delegate long running processes to background threads

// manage latency and exceptions as effects, composed along with other pure abstraction of your domain model
// effect adds capabilities to your computation so you don't need to use side effects to model them. Effectful computation

// Effectful computation - give you power via the type system, effects is modeled in form of type constructor constructing
// constructing types with additional capabilities. Ex type A to add aggregation, construct type List<A>, or Option<A>
// adds optionallity for the type A. Ex Try, Future to model effects of exceptions and latency, respectfully. More advance
// is applicatives and monads.

// 2.6.1 Managing Effects

// How to adapt concepts to functional programming, treat them as part of the model so that they can be composed in a
// referentially transparent way, along with other domain elements. Effect in the sense that you abstract them within containers
// that expose functional interfaces to the world.

// Monad comes from category theory, but here it is an abstract computation helps mimic effects of impure actions like
// exceptions, IO, continuations, while providing a functional interface that composes with other components.

// 2.6.2 Managing Failures
// Failures will happen: hardware, network, third party software, own components.
// What models do we use, littering code with error checks is not a solution. Make code that raises exceptions explicit
// via type system, and and use abstractions taht don't leak exception management details within your domain logic leaving
// core logic functionally compositional.

// Encoding explicit failure within the type, thus they never escape  from the try as a side effect.

class Ex08 {

    class SavingsAccount(val rate: Int)

    fun <A : SavingsAccount> calculateInterest(acc: A, balance: Int): Try<Int> =
            if (acc.rate == 0) Failure(Exception("Interest rate not found"))
            else Success(10000)

    fun <A : SavingsAccount> getCurrencyBalance(acc: A): Try<Int> = Success(1000)
    fun <A : SavingsAccount> calculateNetAssetValue(acc: A, ccyBal: Int, interest: Int): Try<Int> =
            Success(ccyBal + interest + 200)

    // try gives you compositionality by being a monad and offering a lot of higher order functions
    // like flatmap that helps compose with other functions that may fail

    fun composingWithTry() {

        Try.monad().binding {

            val a = SavingsAccount(1)

            val b = getCurrencyBalance(a).bind()
            val i = calculateInterest(a, b).bind()
            val v = calculateNetAssetValue(a, b, i).bind()

            yields(Pair(a, v))
        }

        // is equal to


        val a = SavingsAccount(1)

        getCurrencyBalance(a)
                .flatMap { b ->
                    calculateInterest(a, b)
                            .flatMap { i -> calculateNetAssetValue(a, b, i) }
                }
                .map { value -> /*my int value used here*/ }
    }

    // 2.6.3 Managing latency

    // gUarantee some ebounds on latency, wrap long running computations in a future. Computation is delegated to
    // background thread. Future is also a monad.

//    fun <A : SavingsAccount> calculateInterestF(acc: A, bal: Int): Future<Int>
    // fun getCur
    // fun calcNet

    // compose functions sequentially to yield another Future. Entire computation is delegated to background thread.

    // onComplete{
    // when(v) is Success ... is Failure ...
}