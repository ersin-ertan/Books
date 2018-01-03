package B_scalaForFunctionalDomainModels

import B_scalaForFunctionalDomainModels.Ex03.AccountService.calculateInterest
import kategory.*

// 2.3.0 Pure Functions ofr domain behaviour

class Ex03 {

    interface Account {
        val number: String
        val name: String
    }

    class CheckingAccount(override val number: String, override val name: String) : Account

    interface InterestBearingAccount : Account {
        val rateOfInteresnt: Float
    }

    class SavingsAccount(override val number: String, override val name: String, override val rateOfInteresnt: Float) : InterestBearingAccount
    class MoneyMarketAccount(override val number: String, override val name: String, override val rateOfInteresnt: Float) : InterestBearingAccount

    object AccountService {
        fun <A : InterestBearingAccount> calculateInterest(account: A, period: IntRange): Try<Int> = Success(1)
    }

    fun compositionLeadsToEvolutionOfAbstractions() {


        val s1 = SavingsAccount("12", "tom", 2.7F)
        val s2 = SavingsAccount("12", "jill", 2.2F)
        val s3 = SavingsAccount("12", "tim", 2.4F) // sample list of savings accounts

        val dateRange = IntRange(1, 4)

        // maps over a list of savings accounts, computes interest for each
        listOf(s1, s2, s3).map { acc -> calculateInterest(acc, dateRange) } // maps over a list of savings accounts, computes
        // interest for each of them

        // finds total interest accumulated in a list of savings accounts
        listOf(s1, s2, s3)
                .map { acc -> calculateInterest(acc, dateRange) }
                .fold(0F) { a, calculatedInterestRate ->
                    calculatedInterestRate.map { it + a }.getOrElse { a }
                }

        // gets list of interest calculated using the filter combinator
        listOf(s1, s2, s3).map { acc -> calculateInterest(acc, dateRange) }.filter { it is Success }

        class Amount(val a: Int)

        fun getCurrencyBalance(acc: Account): Try<Amount> = Try { Amount(0) }
        fun getAccountFrom(no: String): Try<Account> = Try { SavingsAccount("3", "eu", 3.4F) }
        fun calculateNetAssetValue(a: Account, balance: Amount): Try<Amount> = Try { Amount(0) }


//        val result:Try<Pair<Account, Amount>> =
//                Try.monad().binding {
//                    val s = getAccountFrom("eu").bind()
//                    val b = getCurrencyBalance(s).bind()
//                    val v = calculateNetAssetValue(s, b).bind()
//
//
//                    if (v.a > 100000) yields(Pair(s, v)) // the if is ruining the compilation, what is the analog to filter
//                    yields()
//                }

        // looks something like this, lists net asset value from an account number if net asset value > 100,000
// for comprehension is syntactic sugar on top of chained flatMaps and maps, if one fails, they all fail

        val result1 = getAccountFrom("eu")
                .flatMap { s ->
                    getCurrencyBalance(s)
                            .flatMap { b ->
                                calculateNetAssetValue(s, b)
                                        .filter { v -> v.a > 100000 }
                                        .map { v -> Pair(s, v) }
                            }
                }


    }

    // if(a) then B else D

    // statement: side effecting each statement is an assignment, doesn't return any value, only side effects,
    // difficult to compose and combine


//    val result = if(A) then B else D

    // expression: referentially transparent if individual expressions are referentially transparent, returns values that can
    // be passed into a function, can be composed easily because expression oriented, easy to read

}