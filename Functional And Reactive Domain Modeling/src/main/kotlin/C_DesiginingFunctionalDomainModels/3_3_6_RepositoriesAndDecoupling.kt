package C_DesiginingFunctionalDomainModels

import arrow.core.Option
import arrow.data.Try
import java.util.*

// 3.3.6 Repositories and the timeless art of decoupling

// Can constructor aggregate out of repository, repository can query an aggegat from a repo.
// Design and implement a repo, organize repos in modules via traits, manage injecting repos into domain
// services in a compositional way.

// non trivial domain models need separate repositories for every aggregate
// repos offer generic functionalities allowing you to: fetch an aggregate based on an ID(domain entity
// can be identified with an ID), store a complete aggregate

class Ex07 {

    class Account
    class Balance

    interface Repository<A, IdType> {
        fun query(id: IdType): Try<Option<A>> // valid A or exception
        fun store(a: A): Try<A>
    }

    // extend the generic module to one specialized to handle Account aggregates

    interface AccountRepository : Repository<Account, String> {
        override fun query(accNum: String): Try<Option<Account>>
        override fun store(a: Account): Try<Account>
        fun balance(accNum: String): Try<Balance>
        fun openedOn(date: Date): Try<Sequence<Account>>
    }

    // with several aggregates, repository organazition into separate modules provides clear separation of algebra
    // and subsequent implementations for units that model different entities of your problem/solution domain.
    // trait PFRepos extends AccountRepos with CustRepos with BankRepos

    class AccountRepositoryMyDb : AccountRepository {
        override fun query(accNum: String): Try<Option<Account>> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun store(a: Account): Try<Account> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun balance(accNum: String): Try<Balance> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun openedOn(date: Date): Try<Sequence<Account>> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    // injecting a repository into a service
    // implement multiple back ends with injecting repos into a domain service. Services are coarse level abstractions.
    // domain service access api of repository, modeled by passing teh repository as an argument

    interface AccountService<Account, Amount, Balance> {
        fun open(num: String, name: String, openingDate: Option<Date>, rep: AccountRepository): Try<Account>
        fun close(num: String, name: String, openingDate: Option<Date>, rep: AccountRepository): Try<Account>
        fun debit(num: String, amout: Amount, r: AccountRepository): Try<Account>
        fun credit(num: String, amount: Amount, r: AccountRepository): Try<Account>
        fun balance(num: String, r: AccountRepository): Try<Balance>
    }

    // simplest and most naive, what are the problems with this

    object App : AccountService<Account, Amount, Balance> {

        fun op(num: String, rep: AccountRepository) {
            // monad{ credit, credit, debit, balance, yields(b) }
        }

        override fun open(num: String, name: String, openingDate: Option<Date>, rep: AccountRepository): Try<Account> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun close(num: String, name: String, openingDate: Option<Date>, rep: AccountRepository): Try<Account> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun debit(num: String, amout: Amount, r: AccountRepository): Try<Account> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun credit(num: String, amount: Amount, r: AccountRepository): Try<Account> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun balance(num: String, r: AccountRepository): Try<Balance> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    // verbose: user of api must pass repo as an argument, good for single invocation but not required for larger abstractions
    // with lots of sequenced calls
    // coupling with the context of the api: every invocation of a service method passes the instance of the repo, thus
    // strongly coupled with context of the api, but repo forms the context of the environment(store thats there for the service
    // method to access and do required operations)
    // lack of compositionality: inject repo as value, api can be more compositional by injecting in the context of a computation
    // Lift the argument to a curried form.

    // Want compositionality? curry it
    // make the repository a curried argument to the service methods. Sequence the invocations through the for comprehension
    // thread the repository through the computation and defer the injection until the final evaluation of the composed function.
    // Doesn't lose anything on the injection and gain compositionality of your api. Change the algebra of methods to lift
    // repository argument to a curried form

    // function1 as the reader
    interface AccountService1<Account, Amount, Balance> {
        // function return type or Function1??
//        fun open(num: String, name: String, openingDate: Option<Date>): (AccountRepository)->Try<Account>
//        fun open(no: String, name: String, openingDate: Option<Date>):Function1<AccountRepository, Try<Account>>
    }

    // now you write code like this
    object App1
//        :AccountService<Account,Amount,Balance>{
    // scala code shows import AccountService._ what is this, flatMap defined on Function1 is more powerful
    /* credit, crerdit, debit, balance, yields*/


// can make Function1 a monad and give it a flatMap, when you have flatMap defined in function1 and call op
// the complete expressions in not evaluated, just the composed function is returned. pass a repo explicitly to the returned
// value to evaluate the entire composition, or compose it with any other composition returning a Function1<AccountRepo, _>
// and defer the evaluation until you've build the whole compuatation pipeline(building abstractions through incremental composition)

// If you compose op with other computation that's not a Function1 like List or Option, requires the reader monad.
}