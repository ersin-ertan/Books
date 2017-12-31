package A_functionalDomainModeling

import kategory.None
import kategory.Some
import kategory.Try
import kategory.getOrElse
import java.sql.Date
import java.time.Instant

// 1.3.2 Pure functions Compose

/*
You create one function from the combination of others.

map is a higher order function taking in another function as input. This is a combinator.
Looping is abstracted from the user. Function first class. What not how. Free from side effects.
*/

class Ex07 {

    class Balance(val amount: Int)
    class Account(val balance: Balance, val name: String) {
        fun copy(balance: Balance = this.balance, name: String) = Account(balance, name)

    }

    class Amount(val amount: Int) {}

    val generateAuditLog = { a: Account, am: Amount -> Try.pure<String>("123") }
    val write = { s: String -> Unit }

    fun debit(a: Account, amount: Amount): Try<Account> =
            if (a.balance.amount < amount.amount) Try.Failure(Exception("Insufficiant balance"))
            else Try.Success(a.copy(Balance(a.balance.amount - amount.amount), "tom"))


    val myAccount = Account(Balance(10), "Joe")
    val myAmount = Amount(5)


    // not exactly as shown in book
    val result = debit(myAccount, myAmount)
            .flatMap { acc -> generateAuditLog(acc, myAmount) }
            .map(write) // should be foreach a combinator used for side effecting operations
}

/*
debit can generate a Failure with exception or successful generation of modified account
If failure, the sequence is broken
*/

// 1.4.0 Managing side effects

/*
What happens when you have a io exception writing to a file, disk is full, no write permissions in folder.
Needed with non trivial domain models. External systems can fail and has nothing to do with the core banking system.
No way to avoid the situation, decouple the side effect as far as possible from the pure domain logic.

Why side effects and pure domain logic are bad. No separation of concerns, difficult to unit test, need to mock leads,
diffecult to reason about domain logic and side effects, lose composition thus aren't modular.
*/


fun decouplingSideEffects() {

    class Cust(val name: String, val address: String)
    class Account(val no: Int, date: java.util.Date, name: String, address: String)

    open class Verification {
        fun verifyRecord(cust: Cust): Boolean = true
    }

    fun verify(cust: Cust) = if (Verification().verifyRecord(cust)) Some(cust) else None

    fun openCheckingAccount(cust: Cust, date: Date): Some<Account> {
        return Some(Account(234, Date.from(Instant.now()), cust.name, cust.address))
    }

    fun getCust() = Cust("tom", "234 nthoeu")

    val cust = getCust()

    val verfCust = verify(cust)
            .map { cust1 -> openCheckingAccount(cust1, Date.from(Instant.now()) as Date) }
            .getOrElse { Exception("verification failed") }

    // composing identity verification and opening an account

}