package C_DesiginingFunctionalDomainModels

// 3.0.0 Designing Functional Domain Models

// functional and algebraic way
// decoupling the algebra of the domain from its implementation
// enforcing laws of algebra in designing apis
// implementing lifecycle patterns of domain objects

// ADT, sum types, product types, combine them to form abstractions that model your domain elements

// First with specs of the model, using algebraic composition of types, build apis for our domain model
// apis are contracts that objects laws of the domain, use algebra of types to ensure that they are correctly constructed
// learn how to develop apis as algebraic specifications and write properties that verify the laws that form the business rules

// three basic life cycle patterns of domain objects: aggregates, factories and repositories, implement them in domain model
// using functional programming idioms

// 3.1 algebra of api design: how it differs from oo design
// 3.2 defining the algebra of domain services: designing a specific algebra, composing algebras, laws of algebra
// 3.3 lifecycle patterns of domain objects: factories, aggregates, repositories, fp abstractions(lenses, monads), dep inj in FP

// 3.1.0 Algebra of api design

// java starts api design with an interface, publishes the contract of the model to the user, then classes and objects
// of concrete. Identify the class then group operations as methods of class.

// functional programing is opposite, operations correspond with basic domain behaviours, groups related into modules that
// form larger use case. Behaviour is modeled using functions operating on types, types represent data, class or object
// of domain.

// A module is a collection of functions that operate on a set of types honouring invariantns(domain rules). The algebra
// of the module. Algebra:
// one or more sets: sets are datatypes that form part of the model
// one or more functions that operate on the objects of the sets:functions that you define and publish as the api to the user
// few axiums or laws that are assumed to be true and can be used to derive other theorems: define operations in api, laws
// will specify the relationships between these operations

// Implementation, not part of algebra(is the contract the behaviours publish that forms the algebra of the api)

// 3.1.1 Why an algebraic approach?

// Advantages: first user understands behaviours of the domain, algebraic approach models with pure functions, not classes
// or objects. Apis need to compose, which algebraic does well. Comes with set of laws which can be vareifyied.

// Loud clear: focus on behaviour of model at start, it is what user sees, functions implement behaviours. Simple mental
// model, grouped together as modules that implement larger behaviours

// compositionality: function has an algebra. functions compose when types align. Api as a function, build larger functions
// composing from he algebra itself. No need for knowledge about the implementation of composting functions. Classes/objects
// in OO aren't well defined operations
// verifiablity: define laws of algebra, implement verifiablity of your model(robust). Set of properties that you include
// with core model implementation enusres that at no stage do you violate any of them as a result of some changes in the
// specification.

// Algebra is set of types, set of functions defined with them, set of laws that interrelate the functions.
// Potentially have many interpreters defining individual concrete implementations