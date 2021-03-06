package C_DesiginingFunctionalDomainModels

import A_functionalDomainModeling.today
import arrow.core.Option
import arrow.core.Some
import arrow.core.getOrElse
import arrow.data.Failure
import arrow.data.Success
import arrow.data.Try
import java.util.*

// 3.3.3 Get smarter with more expressive types

// This example has a more expressive return of Try

typealias Amount = Float

class Ex03 {

    fun today() = Calendar.getInstance().time

    class Balance(amount: Amount = 0F)

    sealed class Account {
        abstract val no: String
        abstract val name: String
        abstract val dateOpen: Option<Date>
        abstract val dateClose: Option<Date>
        abstract val balance: Balance


        class CheckingAccount internal constructor(
                override val no: String,
                override val name: String,
                override val dateOpen: Option<Date>,
                override val dateClose: Option<Date>,
                override val balance: Balance) : Account()

        class SavingsAccount internal constructor( // would have been private constructor but the companion object can't get
                // at it
                override val no: String,
                override val name: String,
                override val dateOpen: Option<Date>,
                override val dateClose: Option<Date>,
                override val balance: Balance,
                val rateOfInterest: Amount) : Account()

        companion object {

            private fun closeDateCheck(openDate: Option<Date>, closeDate: Option<Date>): Try<Pair<Date, Option<Date>>> =
                    with(openDate.getOrElse { today }) {
                        closeDate.map { cd ->
                            if (cd.after(this)) Success(Pair(this, Some(cd)))
                            else Failure<Pair<Date, Option<Date>>>(Exception())
                        }.getOrElse { Success(Pair(this, closeDate)) }
                    }
//                }.fold({ Success(Pair(od, closeDate)) }, { it })
        }

        // generic example to understand compilers type checking
/*
Paco's response to why I was having a problem with the types
ye okay so this is usual typechecker problems, nothing to worry
so, first thing, kotlin's typechecker in branches biases towards the first branch
can't remember if it only applies if the branching is in the return of a block because I don't use explicit returns that much anyway
second thing, it chooses the first branch and sees a Success, must be a success then, doublechecks with fold and nothing says otherwise
so the other branch asks for its generic to be inferred
and the checker goes pffff I don't know you're supposed to be a Success<Pair....>
so you have to hint it, algorithm then sees the common parent and the generics match and goes aaaaaaah so you want a Try


            fun tryEx(opA: Option<Int>, opB: Option<Int>): Try<Pair<Int, Option<Int>>> =
                    with(opA.getOrElse { 1 }) {
                        opB.map { intB ->
                            if (this > intB) Success(Pair(this, Some(intB)))
                            else Failure<Pair<Int, Option<Int>>>(Exception(""))
                        }.getOrElse { Success(Pair(this, opB)) }
                    }
*/

        fun checkingAccount(no: String,
                            name: String,
                            openDate: Option<Date>,
                            closeDate: Option<Date>,
                            balance: Balance): Try<Account> =
                closeDateCheck(openDate, closeDate)
                        .map { d -> CheckingAccount(no, name, Some(d.first), d.second, balance) }


        fun savingsAccount(
                no: String,
                name: String,
                openDate: Option<Date>,
                closeDate: Option<Date>,
                balance: Balance,
                rate: Float): Try<Account> =
                closeDateCheck(openDate, closeDate)
                        .map { d ->
                            if (rate <= 0F) throw Exception("Interest rate $rate must be > 0")
                            else SavingsAccount(no, name, Some(d.first), d.second, balance, rate)
                        }

    }
}


// smart constructors idiom useful for instantiation of domain objcets that honour set of constraints
// constructor of class needs to be protected from users(unsave creation) via private and final (closed for kotlin)
// ppublish api needs to be explicit about failure, returned data type must be expressive eneugh to teh user
// simple objects that need no explicit validation, directly use class constructor. named arguments make constructor invocation expressive

/*

Raul I think the use of Try and Exception there are not needed and I'd consider it abuse.
Try is not needed because that is not computing something that it may fail.
Exception is not needed because it doesn't bring additional information and it suffers from getting underneath called
Throwable.fillInStackTrace which is expensive and platform dependent.
Here is an alternative encoding that just uses regular values to accomplish the same thing:
sealed class DateStatus
object OutOfRangeDate : DateStatus()
object InRangeDate: DateStatus()

fun closeDateCheck2(openDate: Option<Date>, closeDate: Option<Date>): DateStatus =
        Option.applicative()
                .map2(openDate.orElse { Some(Date()) }, closeDate, { (open, close) ->
                    if (close.after(open)) InRangeDate
                    else OutOfRangeDate
                }).ev().getOrElse { InRangeDate }
Try is an anemic version of Either. IMO is only useful when you are computing over something that may crash with an
exception, but creating your own ADT or using your own types to represent known errors is always superior to using Try.
*/
