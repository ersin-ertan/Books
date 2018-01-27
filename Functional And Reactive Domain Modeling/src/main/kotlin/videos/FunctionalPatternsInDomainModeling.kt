package videos

/*

Functional Patterns in Domain Modeling
https://www.youtube.com/watch?v=U0Rk9Knq8Vk

Topic: Domain api evolution through algebraic composition

Domain model refers to problem domain, abstractions it has and how they interact.
Domain modelling is implementing the behaviours of the problem domain into a solution model, which exhibit the same behaviour.
Correct, extensible, managable, testability

OO nouns first and related behaviours as part of the same abstraction.

Pure functional techniquest to implement domain models, evolve domain level apis from the problem use case.
(first identify the behaviour, map behaviour to functions and types(no implementation details) just by using the types,
functions, and the contracts between them(the algebra) we will evolve larger behaviours out of smaller ones)

Irrespective of paradigm, we start from the problem domain, not the solution domain.

Rich domain models: state and related behaivour are bundled together. Unit of abstraction is the class itself. What if the
behiavour belongs to many objects?
Deeply nested inheritance.

Lean domain model: pure function domain model, focus is on compostionality. ADT models the domain abstraction, only
defining state as immutable values, no need to make thinngs private since immutable values, only essential defintinios
go in the ADT, all behaviours are outside the ADT in modules as functions that define the domain behaviour.

If you change an object in the functional world you get back a new object wih the updated state. ADT are passed around
as the function input/output.

Lean starts with functions(verbs), define algebras using types that don't have implementation yet, primary focus is on
compositionality enabling larger functions from smaller, fonctions reside in modules which also compose, entities are built
with algebraic data types that implement types used in defining functions.

Domain model algebra: composed of an algebra of types, functions and laws
Is explicit(types, constraints, expression in terms of other generic algebra)
Encode domain logic as part of the type system itself. Some of the behaviour of the domain can be expressed through existing
algebra.

Verifiable(type constraints, more constraints with DT, algebraic property based testing(exhaustive data set))

ALgebra consists of: morphisms, sets, laws and is compositional

Monoid is an algebraic structure that hase an identity element and a binary associative operation.
If you can define the two functions then any type can be the type parameter for monoid
Itself, monoid is generic domain independent, and context unaware.
This is the algebra reusable across contexts.

When you define the abstraction within the context of the domain ex Monoid<Money> with defined functions, this is the
interpretation, varies with context

The algebra of the monoid is the design pattern

Domain Algebra { domain behaviour, domain rules, domain types, generic algebraic structures }

Functional modeling encourages algebraic api design which leads to organic evolution of the domain models.

A successful domain model honours the domain vocabulary.

fun clientOrders: ClientOrderSheet -> List<Order>
fun execute: Market -> Account -> Order -> List<Execution>
fun allocate: List<Account> -> Execution -> List<Trade>

one step further

fun clientOrders: ClientOrderSheet -> List<Order>
fun <Account:BrokerAccount> execute: Market -> Account -> Order -> List<Execution>
fun <Account:TradingAccount> allocate: List<Account> -> Execution -> List<Trade>

Algebra of the API { Types(domain entities), functions operating on types(domain behaviours), laws(business rules) }

this module is parameterized
interface Trading<Account, Trade, ClientOrderSheet, Order, Execution, Market> {
 fun clieentOrders:ClientOrderSheet -> List<Order>
 fun execute: Market -> Account -> Order -> List<Execution>
 fun allocate: List<Account> -> Execution -> List<Trade>

 fun tradeGeneration(market:Market, broker:Account, clientAccounts:List<Account>) = ...
}

on implementation we give concrete instances, now for the algebra we don't need it.

Algebraic Design: algebra is the binding contract of the api, implementation is not part of the algebra, algebra can have
multiple interpreters(implementations), one of the core principles of functional programing is ecoupling the algebra from
the interpreter.

we refactor the algebra

fun clientOrders:ClientOrderSheet -> List<Order>
fun execute(m:Market, broker:Account): Order -> List<Execution>
fun allocate(accounts:List<Account>): Execution -> List<Trade>

a problem of composition
fun f:A -> List<B>
fun g:B -> List<C>
fun h:C -> List<D>

list is the effect so its a composition with effects, this isn't a straight match of input to output
Solving the more general problem is easier, thus generalize the effects

fun <M:Monad>f:A -> M<B>
fun <M:Monad>g:B -> M<C>
fun <M:Monad>h:C -> M<D>

one option is to define mapping from M<B> -> B then B -> M<C>
but we cannot get rid of M which is the effect part  - no good

since M is a monad, which is also a functor we must lift g into the context of the monad the functor M

m.map(f(a))(g) -> M<M<C>>

monad has join
m.join(m.map(f(a)(g)) -> M<C>
we explore the algebra of the monad and functore and impose the algebra into our algebra
we name this combinator andThen

fun andThen<M<_>, A, B, C>(f: A->M<B>, g:B->M<C>)(implicit m:Monad<M>): A->M<C> = { (a:A) -> m.join(m.map(f(a))(g)) }

glue combinator its a Kleisli

class Kleisli<M<_>, A,B>( run:A -> M<B>){
 fun <C>andThen(f:B -> M<C>)(implicit M:Monad<M>):Kleisli<M,A,C> = Kleisli((a:A) -> M.flatMap(run(a)(f)) }

 Algebra/abstraction to do what we want.

reusing verified and existing algebras

 fun clientOrders: Kleisli<List, ClientOrderSheet, Order> // takes ClientOrderSheet, generates a List<Order>
 fun execute(m:Market, b:Account):Kleisli<List, Order, Execution>
 fun allocate(acts:List<Account>):Kleisli<List, Execution, Trade>

 new we can compose along with the effects
 The domain algebra is composed with the categorical algebra of a kleisli arrow
 So we have written our trade generation algebra without writing implementation code.

 Irrespective of the implementation types, implementation follows the specification

 fun tradeGeneration( market:Market, brocker:Account, clientAccounts: List<Account>) =
 clientOrders andThen execute(market, broker) andThen allocate(clientAccounts)

 this also handles error cases, if the first function returns empty list the chain breaks early, that code comes from
 the algebra of the combinator (monadic bind)

The output is yet another Kleisli arrow
 Functional programming is not just smashing of side effects, always program to the lowest level of abstraction

 Algebraic and functional:
 just pure functions: lower cognitive load, don't think about classes and data members where behaviour resides
 Compositional: algebras compose, we defined the algebras of our domain apis in terms of existing, time tested algebras of
 kleislis and Monads.


Along with breaking the chain, our algebra still doesn't handle errors that may occur within our domain behaviours.

How do we enrich our algebra in a pure and ref transparent way without throwing exceptions.
With more types.

What happens if List fails, we implement error handling as an effects:
pure functional, explicit published algebra, stackable with existing effects

Thus, M<List<*>>  where m is a monad, thus monadic effects, general effects of stacking monadic effects is monadic transformers


with a typealias Response<A> = String or Option<A>

to reach the a we must do a double for
val count:Response<Int> = some(10).right{for maybeCount <- count{ yield{ for{ c <- maybeCount } yield c}

with multiple effects requires more

Use a monad transformer
typealias Error<A> = String or A
typealias Response<A> = OptionT<Error, A>

val count:Response<Int> = 10.point<Response> for { c <- count } yield (())

gives you the ability to stack an option along with an error

we have enriched our algebra along with reducing the client code
The richer the algebra, more client code you can put into it

MonadTransformers: colapses the stack and gives use a single monad to deal with, order of stacking is important

now our algebra has
Kleisli<Valid, ClientOrderSheet, Order> // take in clientOrdersheet return valid order
change in the algebra adds significant functionality in the contract

Small change in algebra, huge step in domain model

implementation doesn't change much

We can think of domain rules as algebraic properties

Now when we implement rules like this, we can do property based testing to generate data and test the properties

Domain rules as algebraic properties: part of the abstraction, equally important as the actual abstraction, verifiable
 as properties

domain rule verifaction
property("check client order laws") =
for all ((cos:Set<clinteORder>) -> {
val orders = for{ as <- clientOrders.run(cos.toList) } yield os

size law(cos.toSequ)(orders) == true

lineItemLaw(cos.toSeq)(orders)0 == true
})


Useful pattern for decoupling algebra from implementation
declaring or using a free monad, its like an AST, no semantics, just structure of action to execute
each action can be defined as part of the interpreter, its like a data type so when you interrpret, you know the subset of the
algebra to do the checking

First step define the algebra:
Repository: store domain objects, query domain objects, single point of interface of the domain model

Algebra of the repository: pure, compositional, implementation independendant

sealed class AccountRepo<A>
class Query<A>( no : String, onResult:Account -> A) : AccountRepo<A>
class Store<A>(account:Account, next:A) : AccountRepo<A>
class Delete<A>(no:String, nextA) : AccountRepo<A>

object AccountRepo{
implicit val functor: Functor<AccountRepoF> Functor<AccountRepoF{
fun map<A,B>(action:AccountRepoF<A>(f:A->B):AccountRepoF<B> =
action match{
case Store ...
cas Query ...
case Delete ...

type AccountRepo<A> = Free<AccountRepoF, A>

then you define help functions to lift your algebra into the context of the free monad

interface AccountRepository{
fun store(ac:Account):AccountRepo<Unit> = liftF(Store(account,()))
fun query(no:String): AccountRepo<Account> = lift(Query(no, identity))
fun delete(no:String):AccountRepo<Unit> = liftF(Delete(no, ()))

now we define larger functions from smaller functions

fun update(no:STring, f:Account -> Account): AccountRepo<Unit> = for a <- query(no) _ <-store(f(a))} yield() }

we are just defining the algebra of the free monad, no interpreter

duf open(...) store(account...) a<-query(no) yield a}
get back a free monad
with 2 operations chained in sequence

free monad defines the algebra, multiple interpretations

 just algebra

 essence of the pattern: we have built the entire model of computation without any semantics, just the algebra, just a
 description of what we intend to do, not suprising that it's a pure abstraction
 Now we can provide as many interpreters as we wish depending on the usage context, interpreter for testing, that test
 the repo actions against an in memory data structure, one for production using a RDBMS

 duf interpret<A>(script:AccountRepo<A>, list:List<String>):List<String =
 script.fold(_ -> ls, [
 case is Query(no, onResult) -> interpet(...)
 case is Store(account, next) -> interpet(...)
 case is Delet(no, next) -> interpet(...)

interpret the whole abstraction and provide the implementation in context

Intuition: large algebra formed from each individual algebra element is memarly a collection without anny interpretation,
like an AST
the interpreter provides the context and the implementation of each of the algebra elements under that specific context

algebraic design: evolution based on contracts/types/interfaces without any dependency on implementation

evolves straight from the domain use cases using domain vocabulary(ubiquitous language falls in placce because of this direct
correspondence)

modular and pluggable, each of the apis that we disccussed can be pulgged off the specific use cacse and independently used
in other use cases
pure ref trans and testable in isolation

compositional, with domain algebra and with the other categorical algebras inheriting their semantics seamlessly into the
domain model ex. effectful composition with kleislis and fail fast error handing with monads

look at paper Theorems for Free

when using functional modeling, always try to express domain spetific abstractions and behaviours in terms of more generric
lawful abstractions. doing this you make your functions more genic and more usable in a broader context and yet simpler to comprehend.

paramatricity is fundemantal to functional programming
make functions parametric restricted to types instead of implementations


*/


