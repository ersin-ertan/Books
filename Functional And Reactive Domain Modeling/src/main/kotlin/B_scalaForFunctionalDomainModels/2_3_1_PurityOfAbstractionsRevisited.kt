package B_scalaForFunctionalDomainModels

import arrow.core.andThen

// 2.3.1 Purity of abstractions, revisited

// Pure functions have no side effects


class Ex04 {

// traits are interfaces

    interface Account {
        val no: String
        val name: String
        val balance: Balance

    }

    class SavingsAccount(override val no: String, override val name: String, override val balance: Balance, val rateOfInterest: Double) : Account

    class Balance(val amount: Int = 0)

    val calcInterestRate: (SavingsAccount) -> Double = { sa -> sa.balance.amount * sa.rateOfInterest }
    val deductTax: (Double) -> Double = { interest -> if (interest < 1000) interest else interest - 0.1 * interest }

    fun calcInterestRate(sa: SavingsAccount): Double = sa.balance.amount * sa.rateOfInterest
    fun deductTax(interest: Double): Double = if (interest < 1000) interest else interest - 0.1 * interest

    // can use real functions no need to make them values

    val a1 = SavingsAccount("234", "tom", Balance(), 3.4)
    val a2 = SavingsAccount("235", "tommy", Balance(), 3.1)
    val a3 = SavingsAccount("236", "tim", Balance(), 3.0)

    val accounts = listOf(a1, a2, a3)

    val interestDeductTax = accounts.map(calcInterestRate).map(deductTax) // using function variable
    val interestDeductTax2 = accounts.map { calcInterestRate(it) }.map { deductTax(it) } // using function

    val interestDeductTaxFusion = accounts.map(calcInterestRate andThen deductTax) // only works with function variables
}

// fusion to map two map combinators via function composition

/*
maping twice over the collection is a problem as there is an intermediate collection created if the collection is large
Apply the composition itself. When a function loses its purity it loses its ability to compose.

2.3.2 Other benefits of being referentially transparent

Testibility: no external state, can supply these properties as expressions to do property basted testing via generating
random data to do testing

Parallel Execution: use parallel data structures more effectively without external state.
Scalas collection.map turns into collection.par.map
*/

fun testParallelExecutionOnCollection() {


    fun addOne(i: Int) = i + 1
    val deferred = listOf(1, 2, 3).map { n ->
        //        async { // requires coroutines
        addOne(n)
//        }
    }
}