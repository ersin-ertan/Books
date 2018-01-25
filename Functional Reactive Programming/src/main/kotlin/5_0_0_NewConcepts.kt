/*
 5.0.0 New Concepts

 Von Neumann, compositionality, imutable values and data structures, clarity of intent, cheap abstraction

 Future proof code from hardware inovation we need to write a program haat fits the problem and gives dependency
 information to the compiler so it is optimized for the target. FRP tracks data dependencies and removes in place state
 mutation. FRP in inherently parallelizable.

 Tames complexity by enforcing compositionality. FRP imposes mathematical rusels that force interaction of elements
 to be simple and predictable. Complexity stays closer to proportional to program size. Denotational semantic is the
 math proof of compositionality.

 Reductionisim assumes compositionality. What happens when we can't. Event propogation guarantees compositionality.

 Using FRP with end up thinking about pure logic, and structure that limits scope.

 Refactoring easy so long as types match.

 Actor is a process that handles incoming messages from a single async input queue. Each actor with a public adress
 that other actors send messages to. Has a reply mechanism. Can span new actors send address of actor to another actor.

 Advantage of actor is it allows implicit state machines. But duplicate logic, long methods with local variables.



*/