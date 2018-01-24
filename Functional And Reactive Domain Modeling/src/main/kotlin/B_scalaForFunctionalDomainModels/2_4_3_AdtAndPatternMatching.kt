package B_scalaForFunctionalDomainModels

// 2.4.3 ADTs and Pattern Matching

// Pattern matching helps keep functionality local to respective variant of the ADT, readability to model, and robust code

class Ex06 {

    fun getMarketValue(e: Equity, a: Int): Amount = Amount(a, Currency.USD)
    fun getAccruedInterest(i: String): Amount = Amount(1, Currency.USD)

    class Balance(val amount: Int = 0, val ins: Instrument, val asOf: Int)

    class Account(val no: String, val name: String, val dateOfOpening: Int, val balance: Balance)

    interface Instrument

    class Equity(isin: String, name: String, dateOfIssue: Int, issueCurrency: Currency, nominal: Int) : Instrument
    class FixedIncome(val isin: String, val name: String, val dateOfIssue: Int, val issueCurrency: Currency, val nominal: Int) : Instrument

    sealed class Currency : Instrument {
        object USD : Currency()
        object CAD : Currency()
    }

    class Amount(val amount: Int, val currency: Currency) {

        operator fun plus(that: Amount): Amount {
            require(that.currency == currency)
            return Amount(amount + that.amount, currency)
        }
    }

    val getMarketValue: (e: Equity) -> Amount = { Amount(1, Currency.USD) }
    val getAccruedInterest: (i: String) -> Amount = { Amount(1, Currency.USD) }

    val getHolding: (account: Account) -> Amount = { account ->
        with(account.balance) {
            when (ins) { // equivalent to pattern matching
                is Currency -> Amount(amount, ins)
                is Equity -> getMarketValue(ins, amount)
                is FixedIncome -> Amount(ins.nominal * amount, ins.issueCurrency).plus(getAccruedInterest(ins.isin))
                else -> {
                    Amount(1, Currency.USD)
                } // were these sealed? placeholder return
            }
        }
    }

    // pattern matching in getHolding computing the accounts net holding value based on the type of balance that it holds
    // Instrument is defined as a sum of product type, pattern matching enables localization of logic with sum type and
    // pattern matching.  Using Visitor pattern(hard to extend) algebraic data types solve the problem elegantly. Compiler
    // checks for all enumerations of balance
}

// 2.4.4 ADTs encourage immutability
/*
Referential transparency: immutable thus always have a value be understood as our structure shows.
How do we modify the value of any of its attributes? Create a new instance with the modified value.

Share values without mutable state, keep domain behaviours pure.
*/

fun immutableClasses() {
    class SavingsAccount(val number: String, val name: String, val dateOfOpening: Int, val rateOfInterest: Int) {
        fun copy(number: String = this.number,
                 name: String = this.name,
                 dateOfOpening: Int = this.dateOfOpening,
                 rateOfInterest: Int = this.rateOfInterest) = SavingsAccount(number, name, dateOfOpening, rateOfInterest)

    }

    val today = 1

    val a1 = SavingsAccount("a-234", "tom", today, 3)
    val a2 = a1.copy(rateOfInterest = 5)

    class B(val valB: Int) {
        fun copy(b: Int = this.valB) = B(b)
    }

    class A(val valA: Int, val classB: B) {
        fun copy(valA: Int = this.valA, classB: B = this.classB) = A(valA, classB)
    }
    // what about nested copy? Inner double copy

    val a = A(1, B(2))
    val aa = a.copy(classB = a.classB.copy(3))
}