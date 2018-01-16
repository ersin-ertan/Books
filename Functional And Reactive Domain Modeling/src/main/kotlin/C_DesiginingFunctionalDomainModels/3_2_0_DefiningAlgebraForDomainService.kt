package C_DesiginingFunctionalDomainModels

import A_functionalDomainModeling.today
import kategory.*

// 3.2.0 Defining an algebra for a domain service

class Ex01 {

//    fun open(no: String, name: String, date: Option<Int>): ???

    // what should the return type of the function be, Account? No because validation may fail, but you want to inform why
// Either, or Try basic difference of returning a value like account from open opposed to something like an option, either,
// try providing an abstraction over evaluation

    // 3.2.1 Abstracting over evaluation
    // instead of returning a data type of Account, or a failure value of null(both evaluated values)
    // Return Try or option(type constructors) which model effectful computation.
    // Option the abstraction ofer optionality, try abstracts over failures
    // The caller of your function can decide whether to evaluate the abstraction by extracting the value out of it
    // or compose it with other abstraction.

    // 3.2.2 Composing Abstractions
    // openning, performing a series of debits and credits on an account in functional paradigm has an impreative feel
    // but is expression oriented programming under the hood.
    // Usually use a for comprehension, abstraction needs to support chaining of sccess and abort of composition in failure
    // this is monadic model of computation

    // The return type of each of the service functions need to support this monadic model.
    // THIS IS THE STRATEGY FOR COMPOSING ALGEBRAS!

    // If success, it continues, else failure early return. These are effectful computation.
    // Try violates some laws of abstraction design(not lawful monad)

    // Why return Try<Account> instead of account, account is an instance(already evaluated) and it doesn't compose.
    // Result is Failure or Success, abstraction gives you the power to algebraically compose the returned value with
    // other abstractions.

    // 3.2.3 The final algebra of types
    // Compose a coarser service method out of smaller ones by only using the algebra of the composing methods.


    // because try is a monad, you can sequence, and pass along failure info to the user
    // you can use debit and credit together to compose a new function, transfer:

    interface AccountService<Account, Amount, Balance> {

        fun open(no: String, name: String, openDate: Option<Int>): Try<Account>
        fun close(account: Account, closeDate: Option<Int>): Try<Account>
        fun debit(account: Account, amount: Amount): Try<Account>
        fun credit(account: Account, amount: Amount): Try<Account>
        fun balance(account: Account): Try<Balance>

        fun transfer(from: Account, to: Account, amount: Amount):
                Try<Tuple3<Account, Account, Amount>> = Try.monad().binding {
            val a = debit(from, amount).bind()
            val b = credit(to, amount).bind()
            yields(Tuple3(a, b, amount))
        }.ev()
    }


// what is a monad, functional programing states computations as expressions. Expression can be a primitive copmutation or
// complex one that evolves as a result of combining simpler ones. Building domain behaviours as part of your domain model
// requires implementing combinators that evolve in a similar way. Simpler functions, then using higher order functions
/// compose them to design larger abstractions. Monad abstracts one style of computation for building such combinator libraries.
// Consists of three elements: type constructor M<A> as interface M<A> or class M<A>
// unit method lifts computation into the monad, invocation of the class constructor.
// bind method sequences computations, flatmap is the bind
// Monad is an algebraic structure, any monad that has these three elements also needs to honor the following laws:
// identity: monad m, m flatMap unit => m, unit: m, unit(v) flatMap f => f(v), associativity m, m flatMap g flatMap h =>
// m flatMap { x => g(x) flatMap h }

// 3.2.4 Laws of the algebra

// Algebra based api design requires formasilation of laws of the algebra. Explicitly record invariants that your apis
// have to honour. Generic constraints or driven by rules from the domain. Given balance B, succesfull credit, debit of
// same amount will return the same B. Using Api definitions, formalize the law as a property of your model and record
// it as part of your test suite. Spek and kluent. Using such invariants and encoding them as verifiable properties
// documents domain model constraints and can be executed on each build.

// Laws make aggregates consistent
// Important property of aggregate, defines consistency boundary of the abstraction. Operations you do on an aggregate never
// becomes inconsistent from the model point of view. Laws of algebra must ensure this. Many laws are verified by type system,
// others can be checked using property based testing. Ex close date must be greater than open date of account.

// 3.2.5 The interpreter for the algebra
// That was the contract for the api, what about the implementation. Decouple the implementation from the algebra so you
// can allow multiple implementations for a single algebra. Each implementation is known as the interpreter of the algebra
// and will consist of concrete classes and functions that implement the api definitions.
// Our made up types for Account service may now be made concrete, as an interpreter.

    class Amount(val amount: Int)

    val todayy = 3

    class Balance(val amount: Amount = Amount(0))
    class Account(val no: String, val name: String, val dateOfOpen: Int, val dateOfClose: Option<Int> = None, val balance: Balance = Balance(Amount(0)))

    object AccountServiceImpl : AccountService<Account, Amount, Balance> {
        override fun open(no: String, name: String, openDate: Option<Int>): Try<Account> =
                if (no.isEmpty() || name.isEmpty()) Failure(Exception("Account no or name cannot be empty"))
                else if (openDate.getOrElse { 4/*should be today*/ }.lt(b = 4/*today*/)) Failure(Exception("Cannot open account in the past"))
                else Success(Account(no, name, openDate.getOrElse { 4/*today*/ }))


        override fun close(account: Account, closeDate: Option<Int>): Try<Account> {
            val cd = closeDate.getOrElse { 4 }
            return if (cd.lt(b = account.dateOfOpen)) Failure(Exception("close date $cd cannot be before opening date ${account.dateOfOpen}"))
            else Success(Account(account.no, account.name, account.dateOfOpen, Some(cd)))
        }

        override fun debit(account: Account, amount: Amount): Try<Account> =
                if (account.balance.amount.amount < amount.amount) Failure(Exception("insufficient balance"))
                else Success(Account(account.no, account.name, account.dateOfOpen, account.dateOfClose, Balance(Amount(account.balance.amount.amount - amount.amount))))

        override fun credit(account: Account, amount: Amount): Try<Account> = Success(Account(account.no, account.name, account.dateOfOpen, account.dateOfClose, Balance(Amount(account.balance.amount.amount + amount.amount))))
        override fun balance(account: Account): Try<Balance> = Success(account.balance)
    }
}

// Algebraic api design { module definitions along with apis, algebraic laws(modeled as properties) } ==> interpreter of algebra