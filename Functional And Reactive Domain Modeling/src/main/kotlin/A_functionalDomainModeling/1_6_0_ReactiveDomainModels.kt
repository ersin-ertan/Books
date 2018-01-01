package A_functionalDomainModeling

// 1.6.0 Reactive domain models

// Reactive: responsive to ui, resilient(to failures, by restarting parts or give feedback for next step), elastic
//(responsive to varying load), message driven(loosly coupled non blocking async message passing

// 1.6.1 The 3+1 view of the reactive model is bound by responsive

// 16.2 My model can't fail myth: disks fail, memory fails, network components fail, infrastructure fails, beyond your control
// design around failures and increase the overall resiliency of the model. Duplicating same code for exception handling
// polutes the code
// Separate modules should handle failures, keep domain model clean and decouple failure handling from business logic

// Centralize handlers, have one module for each function of the application, the number of handlers depends on the number
// of modules

// Failures: micromanage within model using exception handling logic: convoluted code, coupling with domain logic, non scaleable
// Failures: handling as a separate component: better separation, failures can be delegated to components, handling can be pluggable, scales

// 1.6.3 Being elastic and message driven
// Important to scale back when load decreases to ensure economy of resources and operations
// Reduce coupling between components of model, loose architectures using async message boundaries
// Nonblocking communication components interact using immutable messages without any sharing of mutable state
// Actor model of computation provides a fairly high level of concurrency construct that helps organize your model
// and can be bounded contexts. Message driven, events can encapsulate a domain concept

