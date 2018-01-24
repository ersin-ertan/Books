package A_functionalDomainModeling

/*
Every entity/value object in any model must have a definite lifecycle pattern for

Creation: created within system, special abstractions for creating

Participation in Behaviours: represented in memory when it interacts within system. Model an entity or value object.
Complex entity may consist of other entities and other value objects.

Persistence: maintained in persistent form, how you write element to storage, retrieval of details for queries, if
relational db, how you insert, update, delete, query an entity.

Patterns are how we handle three above.

Factories: Centralize creation to keep code in one place, abstract process of creation of any entity from caller
Allows you to create different types of objects using the same api. Abstract the process and type of created objects.
Factory provides you a service and possible initialization. Make the factory part of the module that defines the domain
object, like companion object. Or as part of a set of domain services

*/

// this example is a scala to kotlin translation

private class Ex01() {

    sealed class Account {
        companion object Factory {
            fun create(int: Int): Account = when (int) {
                2 -> SavingsAccount()
                3 -> MoneyMarketAccount()
                else -> CheckingAccount()
            }
        }

        class CheckingAccount : Account()
        class SavingsAccount : Account()
        class MoneyMarketAccount : Account()
    }


    fun createAccounts() {

        val checkingAccount = Account.create(1)
        val checkingAccount1 = Account.create(2)
    }
}

/*
Aggregates: composed of a group of related objects, core account identifying attributes(account num), various non
identifying attributes such as holders' names, date of account creation, date of close, reference to other objects(Bank)

Form a Consistency boundary within itself, instantiation must have all composing objects honoring the rules. Participating
objects within, the graph becomes an aggregate. Aggregate within bounded context is a transaction boundary in model.
One entity in aggregate is the aggregate root, single point of interaction with clients: ensuring consistency boundary of
business rules and transactions within aggregate, prevent implementation of aggregate from leaking out to its clients like
a facade for all operationsn that the aggregate supports
*/

private class Ex02() {

    class Bank
    class Address
    class Date
    class Option<T>
    class Amount

    sealed class Account(
            val no: String,
            val name: String,
            val bank: Bank,
            val address: Address,
            val dateOfOpening: Date,
            val dateOfClose: Option<Date>) {

        // our ADTs
        class CheckingAccount(
                no: String,
                name: String,
                bank: Bank,
                address: Address,
                dateOfOpening: Date,
                dateOfClose: Option<Date>) : Account(no, name, bank, address, dateOfOpening, dateOfClose)

        class SavingsAccount(
                no: String,
                name: String,
                bank: Bank,
                address: Address,
                dateOfOpening: Date,
                dateOfClose: Option<Date>,
                val rateOfInterest: Float) : Account(no, name, bank, address, dateOfOpening, dateOfClose)
    }

    interface AccountService {
        fun transfer(from: Account, to: Account, amount: Amount): Option<Amount>
    }

    // continued from Repositories: aggreg...

    class Criteria<A>

    interface AccountRepository {
        fun query(accountNo: String): Option<Account>
        fun query(criteria: Criteria<Account>): Sequence<Account>
        fun write(accounts: Sequence<Account>): Boolean
        fun delete(account: Account): Boolean
    }
}

/*
Allow updating immutable account but in a functional way. instead of mutating the object, updates produce a new instance
with modified attribute values. Thus continue sharing original abstraction as immutable entity while doing updates that
generate new instance of same entity.

Repositories: aggregates created by factories represent underlying entities in memory during active hpase of objects lifecycle.
But need to persist an aggregate when you need it later. Usually implemented based on persistent storage like RDBMS.
The persistent model of the aggregate may be entirely different from the in memory aggregate driven by storage model.
Repository is responsible for providing the interface for manipulating entities from storage without exposing relation data model.
Interface has no knowledge of the nature of persistent store. User interacts with repo through aggregate.

Aggregate single window to lifecycle of entity: supply args to factory get back aggregate, use aggregate as contract through
all behaviours implemented through services, use aggregate to persist the entity in the repo.

What binds together these aspects in domain driven design: vocabulary of the model

1.2.4 Ubiquitous language

Function body is minimal and doesn't contain irrelevant details, just encapsulate domain logic for fund transfer between
accounts.
Implementations uses terms from banking domain, business people can understand.
Implementation narrates happy path. Exceptional paths are encapsulated within abstraction for implementation.
Scala for comprehension is monadic, taking care of exceptions in execution sequence.
*/

private class Ex03 {

    class Account
    class Amount
    class Try<T>


    interface AccountService {
        fun debit(a: Account, amount: Amount): Try<Account> = TODO()
        fun credit(a: Account, amount: Amount): Try<Account> = TODO()

        // for comprehension is chaining together map/flatMap/filter

//        fun transfer(from: Account, to: Account, amount: Amount) = for {
//            d <- debit(from, amount)
//            c <- credit(to, amount)
//        } yield (d, c)
    }
}

/*
Start with the correct naming of entities and atomic behaviours, then extend the vocabulary to large abstractions that
you compose out of them. Different modules speak different dialects of the language, often using teh same term to mean
different things within its bounded context. But within the context, it should be unambiguous. Related to DSL.
*/