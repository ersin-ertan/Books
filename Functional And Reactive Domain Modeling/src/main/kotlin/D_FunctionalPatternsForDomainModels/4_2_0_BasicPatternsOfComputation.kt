package D_FunctionalPatternsForDomainModels

import arrow.core.Option
import arrow.data.Validated
import java.math.BigDecimal
import java.util.*

//4.2 Basic patterns of computation in typed functional programming
/*
In typed functional programing language most important aspect of domain modeling is organization of computation
structures using type system to achieve maximum compositionality within model.

Abstractions compose when types aligin, else non reusable/composable artifacts. Building abstractions through compostion
of typed functions can be mapped closely to composing morphisms in the mathematical world of category theory and have
close counter part in the world of typed functional progrraming.

Basis of effecctful computing in functional programming(abstractions to handle effects within domain model) Few operations
are intrinsically pure. Handle optional values, exception tracking, io, you must design pure and ref transparent ui on top
of effectful computation. Can generalize effects algebraically.

4.2.1 Functors - the pattern to build on.
Try: reficitaion of exceptions, list repetition of elements of type A, uncertainty of A existing. Abstraction out
and you are left with the map function. lift function f into an effect F<_>, modeled using a type constructor.

Extract the computation into a type of its own, the Functor. Implement an interpreter for the functor algebra for each of
the above ex. val Listfunctor:Functor<List> = Functor<List> { fun <A,B>map(a: List<A>)(f:A -> B):List<B> = a map f

Functor abstracts the capability of mapping over a data structure with an ordinary function(generalizes your model and
makes some parts of your behaviour resuable across components) functors allow you to map over pure functions.

4.2.2 The Applicative Functor Pattern
Where functore lifts pure function of one argument into a specific context, the context F is a type constructor, where
A-> is lifted into context of F thus F<A> -> F<B>.
Applicative Functor lifts a function with any arity.

What happens when you want to do more validations, thus multiple validations must be invoked before you return a valid
aggregate to the user. It should be expressive and all validations should be composed together as an expression.


Algebraic model to come up with appropirate API: every validation function needs to return a context that will contain the
validated object or an error indicating why fail, contexts will be processed either to construct instance or report error.

Introduce context that abstracts result of validation, enure it conforms to rules o domain. Context Validation<E,A> as
the type constructor. E error, A object thus Validation<String, A> alagebra and type alias of validations.
*/
typealias V<A> = Validated<String, A>

class Ex02 {

    interface Va {
        fun validateAccountNo(no: String): V<String>
        fun validateOpenCloseDate(openDate: Option<Date>, closeDate: Option<Date>): V<Pair<Date, Option<Date>>>
        fun validateRateOfInterest(rate: BigDecimal): V<BigDecimal>
    }
}
/*
Know not of the implementation, just how it fits into the algebra of validation functions. Three invokations yields
three instances of V<_> and if all success extract the validated args and pass them to a function f constructing the
final validated object, else report errors.

fun apply3<V<V>, A,B,C,D> (va:V<A>, vb:V[B], vc:V<C>) input contexts (f:(A,B,C) -> D) processing function :V<D> validated
output object in same context

or

fun lift3<V<_>, A,B,C,D>(f:(A,B,C) -> D):(V<A>, V<B>, V<C>) -> V<D> = apply3(_,_,_)(f)

lift pure function f into context V.


*/