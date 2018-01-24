package C_DesiginingFunctionalDomainModels

// 3.3.7 Lifecycle patterns effectively - major take aways

// Aggregates are primary in memory rep of domain object(of entities anfd value objects) Will be primary point of interaction
// within Api domain

// Abstract creation of aggregates using factories of smart constructors allows: creation details to be abstracted,
// multiple subtypes of the same class, creation may even be a single api

// never mutate aggregates in place, use lenses to do functional updates and also offer compositional apis. Or Zipper.

// Algebraic data types are convenient for pattern matching

// Focus on algebraic laws thta your aggregates honour, explicitly verifiable properties

// Summary
// Initial focus is on operations that model domain behavvours and how you collect them into modules

// Type driven composition first identify algebra of individual apis then compose them to form larger behaviours by aligning typse
// computations bind them together

// Seperation of concerns decouple interpreter from algebra

// Aggregate as unit of consistency, use laws of algebra for module

// functional implemntation of domain object patterns - lifecycle with factories, repositories, aggregates, update with lenses
// design repos, inject with domain services via reader monads