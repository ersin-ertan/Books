package A_Compilation

/*
analyze also does a little storage allocation, positions in the methods current stack frame for formal parameters and
other local variables.  JVM is a stack machine: computations are carried out atop the runtime stack.

On each method invocation, JVM allocates a stack frame, contiguous block of memory locations on top of the run time stack.
Actual arguments substituted for formal params, values of local variables, and temp results are given positions within
this stack frame. Runtime stack frames for:

Static Method [0:formal parameter1, 1:formal parameter2, ..., n-1:formal parameterN, n:local variable1, n+1:local var2
..., n+m-1:local varM, <-computations->]

Instance Method [0:this, 1:formal parameter1, 2:formal parameter2, ..., n:formal parameterN, n+1:local variable1,
n+2:local var2..., n+m:local varM, <-computations->]

Locations are set aside for n formal parameters and m local variables, n,m, or both may be 0. For Static Methods,
locations are allocated at offesets beginning at 0. But in the invocation of an instance method, the instance (this)
must be passed as an argument, an instance method's stack frame, location 0 is set for this, parameters and local variables
are allocated offset postions at 1. Computations are memory locations for run time stack computations within method
invocation.

Compiler cannot predict how many stack frames will be pushed onto the stack, but it can compute the offsets of all formal
parameters and local variables, and compute how much space the method will need for its computation, in each invocation.


*/