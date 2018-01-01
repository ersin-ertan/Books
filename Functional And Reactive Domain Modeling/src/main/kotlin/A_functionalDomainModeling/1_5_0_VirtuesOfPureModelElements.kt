package A_functionalDomainModeling

import kategory.Failure
import kategory.Success
import kategory.Try

// 1.5.0 Virtues of pure model

/*
Can verify correctness of it like math. Equational Reasoning
Using the model, you can write assertions that validate your implementation. These are properties of the model and can be
verified correct of the model.
*/

fun main(args: Array<String>) {
    Ex08().sub()
}

class Ex08 {

    class Account(val no: String, val name: String, val balance: Balance = Balance(0)) {
        fun copy(balance: Balance): Account = Account(this.no, this.name, balance)
    }


    class Balance(val amount: Int)

    private fun credit(a: Account, amount: Int): Try<Account> = Success(a.copy(Balance(a.balance.amount + amount)))
    private fun debit(a: Account, amount: Int): Try<Account> =
            if (a.balance.amount - amount > 0) Success(a.copy(Balance(a.balance.amount - amount)))
            else Failure(Throwable("Insuff balance"))

    fun verify() {

        val a = Account("a1", "John")

        credit(a, 100).flatMap { acc -> debit(acc, 100) }.map { acc1 -> acc1 == a }.p()

        // credit 100 debit 100 balance and account should be 0 and the same as the one defined above.
    }


    // substitution model of evaluation - when your functions are equal to expressions

    private fun f(a: Int) = sumOfSquares(a + 1, a * 2)
    private fun sumOfSquares(a: Int, b: Int): Any = square(a) + square(b)
    private fun square(a: Int) = a * a

    fun sub() {
        (f(5) == 136).p()
        (sumOfSquares(5 + 1, 5 * 2) == 36 + 100).p()
        (square(6) + square(10) == 6 * 6 + 10 * 10).p()
    }

    // Returning the same value for a repeated function call is called referentially transparent
    // Ref Trans expressions are pure, make the substitution model work, helps with equational reasoning

    // three pillars of functional programming: ref trans -> substitution model -> equational reasoning
    // need to be fast (latency) thus reactive

}