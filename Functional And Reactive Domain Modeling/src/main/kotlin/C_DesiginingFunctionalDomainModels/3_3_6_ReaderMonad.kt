package C_DesiginingFunctionalDomainModels

import arrow.core.Option
import arrow.data.Try
import java.time.Instant
import java.util.*

// 3.3.6 The reader monad

// Computation needs additional inputs from environment besides explicitly passed arguments. Instead of changing the functions
// scope to global, reader monad allows you to access an environment where it reads the required info. Common to build
// computational pipelines differed till the end. Prev ex may need to be composed with List or option, you need monad
// transformers. Wrap function1(no ability to transform) into another abstraction, Reader, which can have a transformer
// ReaderT to compose other monads with the Reader.

// case class Reader<R,A>(run:R->A)
// ADT for application of an environment, R to generate a value A
// now you implement map and flatMap to enable transformation and sequencing of reads in a pipeline.

class Ex08 {

    interface AccountRepository
    class AccountRepo : AccountRepository

    class Account
    class Balance

    interface AccountService<Account, Amount, Balance> {
        fun open(num: String, name: String, openingDate: Option<Date>): MyReader<AccountRepository, Try<Account>>
        // all other functions return Reader
    }

    class MyReader<R, A>(val run: (R) -> A) {
        fun <B> map(f: (A) -> B): MyReader<R, B> = MyReader({ r -> f(run(r)) })
        fun <B> flatMap(f: (A) -> MyReader<R, B>): MyReader<R, B> = MyReader({ r -> f(run(r)).run(r) })
    }

    object App : AccountService<Account, Amount, Balance> {
        override fun open(num: String, name: String, openingDate: Option<Date>): MyReader<AccountRepository, Try<Account>> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        // op { credit, credit, debit, balance yields b which is then returned in a Reader }
    }

    fun usage() {
        App.open("234234NTHE", "Tom", Option.pure(Date.from(Instant.now()))).run(AccountRepo())
    }

    // compose upfront, evaluate later makes testing easy. Defer injection of the repo until just before the evaluation,
    // and supply alternate implementations for unit testing.

    // Reader monad: de complexifies the implementation, implementation more domain friendly by reducing boiler plate,
    // defers injection of concrete repo implementation at the use site of api instead of definition, design is modular.
    // functional1 as the reader is practical of the reader monad. Reader decouples computation from the environment, unit
    // testing is easy.

    // How do we do multiple injections, pass them into a single dependency.

    // Factories:creat objects, abstract creation(smart constructors
    // Repos: Store aggregates, support queries, support lazy fetch
    // Aggregates: in memory representations, immutable, modeled using algebraic data types, functionally updated lenses,
    // smartly crerated, honours laws

    // F get created from(as new insteances) as aggregates, aggregates get updated functionally, and get stored into repos
    // repos get created from queries via aggregates


}