package B_scalaForFunctionalDomainModels

import arrow.data.Failure
import arrow.data.Success
import arrow.data.Try

// 2.0.0 Scala for functional domains

/*
Why scala, benefits of domain modeling using statically typed language, combine oo and fb power of scala for modular pure models

Features in functional languages: algebraic data types(case classes) with built in support for immutability( help modeling
domain objects-entities and value objects)
Pure functions(help model domain behaviour), function composition and higher order functions(compose smaller behaviours
to implement larger ones
Advanced static type sysem with type inteference(helps make model more robust by encapulatincg some of the constratins
and business logic within the type itself compiler can infer types from expression)
Traits and objects that compose(help modularization, organize your model as objects composed of multiple traits that
implement various functionalities)
Can be parameterized with types allowing you to plug in behaviour correpsponding to business rules
Genericts(build abstractions on generic types that can be later instatitonated for specific ones)
Support for concurrency models like actor model(actors and futures for reactive nonblocking elements)


// 2.2.0 Static types and rich domain models

First attempt at: Customer bank account is interest bearing only if it is a savings account, others don't
*/

class Ex01 {

    enum class AccountType {
        SAVINGS, CHECKING
    }

    class Account() {
        val accountType = AccountType.SAVINGS
    }


    fun calculateInterest(account: Account, period: IntRange): Try<Account> =
            if (account.accountType != AccountType.SAVINGS) Failure(Exception("must be savings"))
            else Success(account)


    // variation, instead of passing a concrete type Account, make the function polymorphic, no longer pass concrete data
    // types for Account as you did with the function calculateInterest. Parameterize function on the type of account
}

class Ex02 {

    interface Account {
        val number: String
        val name: String
    }

    class CheckingAccount(override val number: String, override val name: String) : Account

    interface InterestBearingAccount : Account {
        val rateOfInterest: Float
    }

    class SavingsAccount(override val number: String, override val name: String, override val rateOfInterest: Float) : InterestBearingAccount
    class MoneyMarketAccount(override val number: String, override val name: String, override val rateOfInterest: Float) : InterestBearingAccount

    fun <A : InterestBearingAccount> calculateInterest(account: A, period: IntRange) {
        // Fail Succeed
    }

    // Account is now a polymorphic data type, function calculateInterest polymorphic on Account type
    // when the type system encodes domain logic, and encoding in the function definition that interest can be calculated
    // only for certain account types

    /*
    Domain logic is explicit, seperate data type encoding the account type makes the domain model more expressive
    Function calculateInterest has the valid account type as parte of the signature(constrained to generic type A as a
    subtype of InterestBearingAccount) Signature will be a part of the documentation.
    Passing the info that function can only take accounts that are subtypes ensures compiler has more info, to make
    optimizations.
    No longer need to write tests validating the proper account type passed into the function, compiler can. Can never
    invoke the function with an account type of anything else.
    */

}