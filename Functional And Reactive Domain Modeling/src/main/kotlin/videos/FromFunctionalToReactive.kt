package videos

/*

From functional to Reactive - Patterns in Domain Modeling

Functional Domain Modeling

Reactive { Responsive, Elastic, Resilient }

Make apis reactive mostly using algebraic techniques

How do we define the domain model.

Implementing a domain model, trying to replicate the behaviour of the problem domain into the solution space using
specific technology.

Pure functions, immutable data, algebra of types

Using algebra, we can ensure that those business rules are validated/verified. Then we enrich the algebra to add effects
to handle error handling etc. compose functions to richer domain functionality. Cascading of effects, with reactive as
yet another effect.

Agenda: formalize domain model, domain model algebra, ffrom functional to algebraically reactive, beyond algebra,
actors and domain models, reactive streams.

Your server as a function: Futures, services, and filters
Not just composable, but substrate for reactive servers

Can your domain model thought of as a function? Or collection of?

Models within the model
Bounded Context A[ Domain model A[ ubiquitous language, entities, value objects, services] code, schemas, other artifacts]
Bounded Context B[ Domain model B[ ubiquitous language, entities, value objects, services] code, schemas, other artifacts]
Bounded Context C[ Domain model C[ ubiquitous language, entities, value objects, services] code, schemas, other artifacts]

Across contexts algebras break, thus we need a looser protocol

Bounded Context: consistent vocabulary, set of domain behaviours modeled as functions on domain objects implemented as types,
related behaviours grouped as modules

Domain model = Union(i) Bounded Contexts(i)

Bounded Context = { f(x) | p(x) E Domain Rules }
collection of functions subject to a set of predicates(domain rules)

f(x) domain function, on an object of type x, composes with other functions, closed under composition
p(x) business rules

Our algebra is {
Functions/Morphisms
Types/Sets
Composition
Rules/Laws
}

Domain model algebra(algebra of types, functions, laws)

explicit: types, type constraints, expression in terms of other generic algebra
Verifiable: type constraints, more constraints if you have Dependent type, algebraic property based testing

Domain Algebra{
Domain behaviour, domain rules, domain types, generic algebraic structures
}

Domain Model = Union(i) Domain Algebra(i)
Domain Algebra = { f(x) | p(x) E Domain Rules }


Algebras don't unify across bounded contexts, decoupled across space and time, separate vocabulary, types break down

We need protocols to establish communication across bounded contexts and be reactive

Resilience(responsive in failure), elastic(under load), message-driven(loose coupling, isolation through async message passing)
all equal responsive(through bounded latency)

We explicitly incorperate failure handling as part of the algebra,

see example...


Being reactive: without foregoing the benefits of algebraic reasoning with types
effect of elasticity as yet another effect

Future: async non blocking computation

Coroutines: async non blocking
do coroutines compose, do they have their own algebra
augment our domain algebra with future based apis, just like either, kleisli, asynchrony is yet another stackable effect
within our computation

richer abstractions lead to less lines of code


actors and domain models: powerful un algebraically powerful, gain power at semantic level lose power of reasoning

Future are algebraic, use the least powerful abstraction that does the job.
For domain model resilience, choese futures over actors when you can

When actors: shared mutable state, centralized failure management

Centralized failure management: supervisor hierarchies that manage failures, kill restart suspend/resum, no messy handling
code scattered throughout, requires careful upfront design though

Low level untyped non composition un algebraic, separate fedinition from execution

Reactive streams offer the composition type safe way to compose the workflows: concepts, source sink, flow, runnable graph
*/