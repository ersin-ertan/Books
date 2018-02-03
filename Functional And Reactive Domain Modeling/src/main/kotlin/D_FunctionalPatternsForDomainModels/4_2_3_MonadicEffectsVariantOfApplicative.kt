package D_FunctionalPatternsForDomainModels

import arrow.data.State
import arrow.data.StateApi.modify
import arrow.typeclasses.Monoid
import java.util.*

// 4.2.3 Monadic effects—a variant on the applicative pattern

// monad extends applicative
/* applicative effect preserves the shape of the computation.
Both help core domain model to remain clean boilerplate free.

If you have f:A->F<B> and g:B->F<C> and F is a monad, you can compose them as A->F<C>, composition of monadic functions.
Kleisli composition. Ex stat monad, compose stateful functions and manages threading of the state through them auto.


The State Monad - managing stateful computations in your model

Manage state not passed to functions as mutable objects. Ex random number generator, maintain global state and updates
each time a new number is generated. Invoking functions never passes the state explicitly, invoke calls which generates
the number for you.

State monad threads state as part of  its computational structure, via sequencing power(flatMap) of the monad.One must only
provide how to modify state, and how to fetch the value from the computation via pure functions. Ex. Update balances with
transactions for several accounts.
*/
class Balance(val amount: Int = 0) : Monoid<Balance> {
    override fun combine(a: Balance, b: Balance): Balance = Balance(a.amount + b.amount)

    override fun empty(): Balance = Balance()
}
typealias AccountNo = String
typealias Balances = Map<AccountNo, Balance> // the map is the state you need to manage. Thus query and update the map
// on each transaction

class Money

class Transaction(val accountNo: AccountNo, val amount: Int, val date: Date)

val txns: List<Transaction> = listOf()

fun updateBalance(txns: List<Transaction>): State<Balances, Unit> =
    modify { b: Balances ->
        txns.fold(b) { a, txn ->
            /*Monoid<Balances>().op(a, mapOf(txn.accountNo to Balance(txn.amount)))*/
            a
        }
    }


//val balances:Balance