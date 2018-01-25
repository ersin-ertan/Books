package D_FunctionalPatternsForDomainModels

import arrow.syntax.order.gt
import arrow.typeclasses.Monoid
import java.math.BigDecimal
import java.util.*

// 4.0.0 Functional patterns for domain models

// how functional design patterns differ from oo design patterns, algebra as patterns, monoids design pattern, programming
// with effects(functors, applicatives, monads)

// Generic, parametric can be reused across your domain models regardless of nature of domain, not best practices to
// reimplement every time.

// Pattern terminalogy: pattern concepts, pattern mining, monoids
// Three fundamental patterns: fuctors, applicatives, monads
// Patterns shape domain model: patterns and genericity, parametricity, qualities that patterns add to model
// Evolution of api using types and patterns: effectful composition, kleisli composition
// Types and patterns to enforce domain invariants: api composition, phantom types

// 4.1.0 Patterns confluence of algebra, functions, types

// Functional patterns offer more reusability: completely generic and reusable(algegbra) invariant across all contexts
// context specific implementation varies across all instances(interpreter)

// interface <T>Monoid{ fun zero:T fun op(t1:T,t2:T):T }

// op named zero, as identity element to next func op. Invoke op with zero as argument, will return other argument.
// op is binary and associative
// identity part of zero and associativity of op are enforced by monoid laws: left identity, right, associativity

// reusable across all contexts, parametricity

// 4.1.1 Mining patterns in a domain model

// Explore domain behaviours, identify commonalities to be extracted as reusable patterns
// Discover use cases implementing app specific code, common structure and behaviour of specific computation, existing
// generic abstractions to be specialized to model preceding app specific computation, replace app specific code with
// instances of generic patterns

// 4.1.2 Using functional patterns to make domain models parametric
// implement following behaviours as part of our model: given list of transactions, identify highest value debit transaction
// during day. Given list of client balances, compute sum of all credit balances.

// Simple from domain logic, but identify the FP pattern

// Identify the commonality
// Real banking associates a currency with an amount, new Money model has amount and currency. Handle multiple denominations
// in multiple currencies.
val zero = BigDecimal(0L) // added for convenience

class Ex01 {

    sealed class TransactionType {
        object DR : TransactionType()
        object CR : TransactionType()
    }

    sealed class Currency {
        object USD : Currency()
        object JPY : Currency()
        object AUD : Currency()
        object INR : Currency()
    }

    class Money(val m: Map<Currency, BigDecimal>) {
//        fun toBaseCurrency:BigDecimal =

//
//        operator fun plus(bd: BigDecimal): Money {
//        }
    }

    class Transaction(val transId: String, val accNum: String, val date: Date, val amount: Money, val transType: TransactionType, val status: Boolean)

    class Balance(val bal: Money)


    interface Analytics<Transaction, Balance, Money> { // algebra of the module
        fun maxDebitOnDay(transList: List<Transaction>): Money
        fun sumBalances(balList: List<Balance>): Money
    }

//    val zeroMoney: Money = Money(Monoid<Map<Currency, BigDecimal>>.zero)

/*    object AnalyticsO : Analytics<Transaction, Balance, Money> {
        override fun maxDebitOnDay(transList: List<Transaction>): Money =
                transList.filter { it.transType == TransactionType.DR }
                        .fold(Money(mapOf(Currency.USD to zero)),
                                { a, trans -> if (gt(trans.amount, a)) valueOf(trans) else a) })

        override fun sumBalances(balList: List<Balance>): Money =
                balList.fold((zeroMoney, { a, b -> a + creditBalance(b) }))

        private fun valueOf(trans: Transaction): Money = Money(mapOf(Currency.USD to zero))
        private fun creditBalance(bal: Balance): Money = Money(mapOf(Currency.USD to zero))
    }*/


// both implementations fold over the collection to compute the core domain logic, fold takes unit object Money as init,
// performs binary op on money in loop(comparision and addition respectively) both different but both associative binary

// Look at the pattern, identify the algebra that it fits into(may require a tweak of implementation)

// Abstract over the operations

// Define instance of Monoid for Money(was defined in terms of Map) thus Monoid<Map<K,V>> use that to define Monoid<Money>
// define two instances, one for each operation for maxDebitOnDay and sumBalances ex.

/*    final val zeroMoney: Money = Money(Monoid<Map<Currency, BigDecimal>>.zero)

    fun MoneyAdditionMonoid() = object:Monoid<Money>{
        val m = Monoid<Map<Currency,BigDecimal>>
        val zero = zeroMoney
        fun op(m1:Money, m2:Money) = Money(m.op(m1.m, m2.m))
    }*/

    // operation within fold is no an operation on a monoid instead of hardcoded operations on domain specific types

}