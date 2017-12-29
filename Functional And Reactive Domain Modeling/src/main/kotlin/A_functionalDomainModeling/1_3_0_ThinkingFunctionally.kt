package A_functionalDomainModeling

import java.math.BigDecimal

// 1.3.0 Thinking Functionally

/*
OO completely dominated complex domain models, when functions can suffice.
*/

typealias Amount = BigDecimal

fun main(args: Array<String>) {
//    Ex04().runExample()
    Ex05().runExample()
}

fun Any.p() = println(this)
private class Ex04() {


    // mutable state example
    class Date

    class Balance(val amount: Amount = BigDecimal.valueOf(0))

    class Account(val no: String, val name: String, val dateOfOpening: Date) {

        var balance: Balance = Balance()

        fun debit(a: Amount) {
            if (balance.amount < a) throw Exception("insufficient")
            balance = Balance(balance.amount - a)
        }

        fun credit(a: Amount) {
            balance = Balance(balance.amount + a)
        }

    }

    fun runExample() {

        val a = Account("a1", "John", Date())
        (a.balance.amount == Balance(BigDecimal.valueOf(0)).amount).p()
        a.balance.amount.p()

        a.credit(BigDecimal.valueOf(100))
        (a.balance.amount == Balance(BigDecimal.valueOf(100)).amount).p()
        a.balance.amount.p()

        a.debit(BigDecimal.valueOf(20))
        (a.balance.amount == Balance(BigDecimal.valueOf(80)).amount).p()
        a.balance.amount.p()
    }


}
// The mutability is hard to use in a concurrent setting, difficult to reason about code

private class Ex05() {

    class Date

    class Balance(val amount: Amount = BigDecimal.valueOf(0))

    class Account(val no: String, val name: String, val dateOfOpening: Date, val balance: Balance = Balance()) {

        fun debit(a: Amount): Account {
            if (balance.amount < a) throw Exception("insufficient")
            return Account(no, name, dateOfOpening, Balance(balance.amount - a))
        }

        fun credit(a: Amount): Account {
            return Account(no, name, dateOfOpening, Balance(balance.amount + a))
        }

    }

    fun runExample() {

        val a = Account("a1", "John", Date() )
        (a.balance.amount == Balance(BigDecimal.valueOf(0)).amount).p()
        a.balance.amount.p()

        val b = a.credit(BigDecimal.valueOf(100))
        (b.balance.amount == Balance(BigDecimal.valueOf(100)).amount).p()
        b.balance.amount.p()

        val c = b.debit(BigDecimal.valueOf(20))
        (c.balance.amount == Balance(BigDecimal.valueOf(80)).amount).p()
        c.balance.amount.p()
    }
}

// mutable state is gone, a new object with the modified state is created. But account is still an abstraction that holds
// both state and behaviour, we must decouple the two for better modularity and better compositionality.