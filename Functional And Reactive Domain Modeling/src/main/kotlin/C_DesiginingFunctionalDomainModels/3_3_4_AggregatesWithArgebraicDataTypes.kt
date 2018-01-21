package C_DesiginingFunctionalDomainModels

import A_functionalDomainModeling.p
import arrow.core.Option
import arrow.core.Some
import arrow.core.getOrElse
import java.time.Instant
import java.util.*

// 3.3.4 Aggregate provides a single point of ref to an entity from outside. Account is entity, may conatin other entities
// or value objects, but want to manipulate account without implementation details of elements in Account.

// Ex portfolio, collection of positions indicating curency balances as a date
// Account E34 balance of EUR 3,423
// Account E62 balance of USD 1,654

// Model portfolio as an aggregate so client api doesn't deal with individual elements like position or money.


class Ex04 {

    sealed class Account {
        class MyAccount : Account()
    }

    sealed class Currency {
        object USD : Currency()
    }


    class Money(val amount: Int)
    class Position(val account: Account, val cur: Currency, val bal: Money)
    class Portfolio(val pos: Sequence<Position>, asOf: Date) {

        fun balance(a: Account, cur: Currency): Int =
                pos.find { p -> p.account == a && p.cur == cur }?.let { it.bal.amount } ?: 0

        fun balance(cur: Currency): Int =
                pos.filter { it.cur == cur }.map { it.bal.amount }.fold(0, { i, ii -> i + ii })
    }

    fun test() {
        val date = Date.from(Instant.now())

        Portfolio(sequenceOf(Position(Account.MyAccount(), Currency.USD, Money(1))), date).balance(Currency.USD).p()
        val a = Account.MyAccount()
        Portfolio(sequenceOf(Position(a, Currency.USD, Money(1))), date).balance(a, Currency.USD).p()
    }
}

// portfolio is the aggregate root, being the central point of interaction. Fat aggregate, with multiple entities. this
// model wont scale with a large model. Hand to enforce consistency boundaries with invariants and transactions with multiple
// entities in an aggregate. Can refactor Position to contain the account number instead of the whole account and will
// construct an account instance when needed. One entity portfolio and all others are value objects
//
// Aggregate consists of algebraic data types that form the structure of the entity, and modules that offer the algebra
// of manipulating the aggregates in a compositional manner.

// Modularity - keep this separation in mind when desiging domain models for modular design and clear separation.
// Aggregate struture shouldn't leak to client. Pattern matching can sometimes expose implmentation detail
// All manipulations of aggregate are done through law abiding algebra of modules
// Drawbacks of domail models: expose object representations considered anti modular
// cant define custom patterns for matching - tied to one to one with the types of the case classes(not extensible)
// Many cases might consider extractor pattern,


// experiment, closest to extractor by destructuring within the map

/*
* Raúl Raja Martínez @raulraja 17:10
There is no Extractor in Kotlin because Kotlin does not support unapply synthetically in data classes and their when
expressions do not support most of the pattern matching you get in Scala
Even if Arrow added unapply via the annotation processor to data classes the when grammar would need to be modified in
the compiler. We have a limited impl of case that uses PartialFunction but it's not near as usable as Scala's pattern matching
* */

class Ex05 {


    sealed class User(val name: String, val num: Int) {
        fun unapply(): Option<Pair<String, Int>> = Some(Pair(name, num))
        class FreeUser : User("will", 1)
        class PremiumUser : User("rick", 2)
    }

    fun getGreeting(user: User): String = user.unapply().map { (name, num) ->
        when (user) {
            is User.FreeUser -> "Hello $name"
            is User.PremiumUser -> "Welcome back $name"
        }
    }.getOrElse { "Hello" }
}

//
//fun usage() {
//    val user: User = FreeUser()
//    val greeting = if (!user.unapply().isEmpty) = when (user) {
//        is FreeUser -> {
//            user.unapply().
//        }
//        is PremiumUser -> {
//        }
//    }
//}

// Invariants
// model and associated elements, all functions based on rules of domain. Can never have 2 accounts with same acc number,
// or close earlier than open. Creating domain honoured aggregates is up to the api. Basic validations must pass on newly
// created objects. Smart constructor will help you.