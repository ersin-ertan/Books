package A_functionalDomainModeling

import kategory.*
import java.math.BigDecimal
import java.time.Instant
import java.util.*

// 1.3.1 The Joys Of Purity

// function is relation between a set of inputs and set of permissible outputs, where each input is related to exactly
// one output

// No dependency of a function on shared mutable states.

/*
Which function should be a part of which class, difficult to compose functions implemented as methods of different classes.
debit and credit are operations on a single account, they were behaviours of Account class. Operation like transfer
has two accounts. Should it be a part of Account or in a domain service. How do you deal with services on an account like
daily balance statement or interest calculation? A single bloated abstraction? Behaviours within specific aggregates also
hampers modularity and compositionality.

General Principles when designing functional domain models:
model immutable state in an algebraic data type
model behaviours as functions in modules, module represents a coarse unit of business functionality. Seperates state from
behaviours. Behaviours compose better than state, related behaviours in a module enables compositionality.
Behaviours in modules operate on types that the ADTs represent.

Params in ADT should be immutable.
Definition of ADT doesn't contain behaviour. Debit and credit are in AccountService, defined as a domain service.
Services are defined in modules, implemented as traits acting as mixins for easy composition.
Concrete instance of module(service) use object keyword.
Decouple state from behaviour, state is within the ADT, behaviours modeled as standalone functions within modules.

debit and credit are pure functions, not tied to any specific object. Take args, perform functionality, generate output.

Try, Success, aFailure are more functional compositional than throwing exceptions
*/

// Purifying the model

fun main(args: Array<String>) {
//    Ex06().runningExample()
    Ex06().multiTransaction()
}

val today = java.sql.Time.from(Instant.now())


class Ex06 {


    //    should be ADTs
    class Balance(val amount: Amount = BigDecimal.valueOf(0))

    class Account(val no: String, val name: String, val dateOfOpening: Date, val balance: Balance = Balance()) {
        fun copy(no: String = this.no,
                 name: String = this.name,
                 dateOfOpening: Date = this.dateOfOpening,
                 balance: Balance = this.balance): Account = Account(no, name, dateOfOpening, balance)
    }

    interface AccountService {

        fun debit(a: Account, amount: Amount): Try<Account> =
                if (a.balance.amount < amount) Try.Failure(Exception("Insufficiant balance"))
                else Try.Success(a.copy(balance = Balance(a.balance.amount - amount)))

        fun credit(a: Account, amount: Amount): Try<Account> =
                Success(a.copy(balance = Balance(a.balance.amount + amount)))
    }

    object AccountServiceO : AccountService

    fun runningExample() {

        val a = Account("a1", "john", today)
        (a.balance.amount == Balance(BigDecimal.valueOf(0)).amount).p()

        val b = AccountServiceO.credit(a, BigDecimal.valueOf(1000))
        when (b) {
            is Success -> b.value.balance.amount.p()
            is Failure -> "Fail".p()
        }
    }

    // Try to build compositional components, using try you can compose multiple debits and credits as follows

    fun multiTransaction() {
        val a = Account("a1", "john", today)

        Try.monad().binding {
            // is it with monad? just a guess
            val b = AccountServiceO.credit(a, BigDecimal.valueOf(1000)).bind()
            val c = AccountServiceO.debit(b, BigDecimal.valueOf(200)).bind()
            val d = AccountServiceO.debit(c, BigDecimal.valueOf(190)).bind()
            yields(d)
        }.ev().map { it.balance.amount }.p()

        Try.monad().binding {
            // is it with monad? just a guess
            val b = AccountServiceO.credit(a, BigDecimal.valueOf(100)).bind()
            val c = AccountServiceO.debit(b, BigDecimal.valueOf(200)).bind()
            yields(c)
        }.ev().map { it.balance.amount }.p()
    }
}