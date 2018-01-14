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