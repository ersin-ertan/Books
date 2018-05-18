package Ch05

// Inline functions - higherder functions coe with caveat performance penalties, on copileation time, lambda gets translated
// to an object that is allocated, calling invoke operator

// for performance critical locations mark fun with inline and the function execution is replaced by the higher order functions
// body and the lambdas body, faster but at the cost of more byte code, execution optimizations compound