package videos

// https://www.infoq.com/interviews/ghosh-functional
// Debasish Ghosh on Functional Programming, NoSQL

/*
 Different way of thinking than object oriented
 Model behaviours as pure functions
 Pure behaviours, side effects - big if the language helps decouple side effects from pure
 Working with pure behaviours we can look at input and output types and understand how functions will sequences without
 so much concern for the internals.
 Helps keep the model pure. Domain model is the pure system behaviour.
 Unsafe(file system, database, user interaction)
 Glue behaviour by means of combinators.
 If you can implement the abstraction using the functional types, you get their mathamatical properties as well which
 need not be implemented within the application logic.
 The application logic becomes pure business rules(domain logic)
 Surface area of api is reduced, understadability and readability increases for others.

 Validation, monoid and semi group which does the opending of errors.

 Monads offer the convineience of binding seperate computation.
 E: Take the content transform it and put it back into it's container type. flatMap or bind
 E: Containers and what are their use cases, capabilities.

 Isolation is provided by the type system, thus the compiler will not allow you to do IO related computation unless specified.

 E: Strict enforcement, and strict usage of the type system to declare what kind of computation will occur.
 E: Important to build error handling into the type system of the domain.

 Forces you to have IO as part of the function signature. IO is the generic use case.

 Monads help you compose larger abstractions from smaller ones.

 Hierarchy Functor -> Applicative -> Monad(strongest)

 Monad specialized on applicative, we can use either of them but we must apply the most specific abstraction to match
 the model that we define.

 Applicative gives you sequencing effect, in the validation example you need to sequence through the validations.

 But if you need to fail fast, on the first failure, then one should try a monad.

 When functions are pure, computation can be distributed across cores since they only depend on input. Parallel.

 E: When we have asociative types that can be rearranged, computations that rely only on input, collection would benifit
 from having .parallel operation call.

 What's next larger projects, after seperating the domain model which abstracts the business, we can provide a linguistic
 abstraction by creating a dsl appealing to the business user.



*/
