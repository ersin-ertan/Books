package A_functionalDomainModeling

// DDD, FP, Reactive principles manage complexity of:

// Domain: mine/understand semantic/characteristic with stakeholder language and real world mapping
// Solution: FP for reasonability and composibility great for time, concurrency, abstraction
// System: Reactive for multicore processor for arch and design


/*
Criteria for success: responsiveness via non blocking and async messaging
Learn: functional design patterns based on algebraic principles
Benefits: Design domain model to be ref transparent decoupling side effects from pure logic and reactive model

Algebraic api design without committing to implementation, adhering to algebraic laws.
Lifecycle patterns of domain objects from factory, to domain behaviour as aggregates, then persisted into repositories
Functors, applicatives, monads as primary reusable patterns and evolving domain model
Modularizing domain model, each made up of smaller models(bounded context) design bounded context and separate artifacts
communication between multiple bounded context are decoupled between space and time. Async messaging backbone. Free monads
and other modularization techniques.
Reactive domain models, non blocking. Future, promise, actor, reactive stream.
Domain model persistence. Critique of CRUD, pros of reactive persistence via event driven like CQRS and event sourcing.
Algebraic testing


Domain is the area of interest. Ui, behaviour, design, abstraction, arch is part of the model.

Domain model is the blueprint between relationships of entities in the problem domain. Objects of domain, their behaviours,
language of domain, context which model operates.

Essential complexities: inherent in the system

Incidental complexities: introduced by solution.

Understanding the domain is primary, after that only then you implement the solution.

Bounded context: smaller model within whole. Domain model is a collection of bounded contexts. Communicated via specific
services/interfaces.

Entities: similar elements with respect to creation, processed through business pipeline, and eviction from system.

Value objects: identity is through value. Like you address, it can change. Are immutable, can't change change value without
changing the object itself.

Entity and value distinction is needed before moving on.

You can choose to have one instance of address shared across holders at the same address.

Value object is semantically immutable, entity is semantically mutable.

Domain service differs from entity/value object via granularity.

Entity: has identity, passes through multiple states in lifecycle, has definite lifecycle in business
Value object: semantically immutable, freely shared across entities
Service: macro level abstraction than entity or value object, involves multiple entities and value objects, usually
models a use case of the business

Ex.
BankingService:Service
fun debit
fun credit
fun transfer
fun balanceInquiry

Bank:Entity

Account:Entity
accountNumber:Identifier
accountHolder:Identifier
address:ValueObject
AccountType:ValueObject


Address is a value object only within the scope of the bounded context in which it's defined. Ex. Personal banking vs
geocoding service with address; value in pb, but identity in geo.
Or, Pb account is entity, but in portfolio it is value object.

Type of the domain element reflects the bounded context it's defined.


*/