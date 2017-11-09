package Part2_EmbracingKotlin.I_Generics

// Generics
// Declaring generic functions and classes
// type erasure and reified type parameters
// Declaration site and use site variance

// Type arguments for genirc types only exist at compilet time
// can't use types with type arguments together with the 'is' operator, because type arguments are erased at runtime
// Type parameters of inline functions can be marked as reified to allow you to use them at runtime to perform 'is'
// checks and obtain java.lang.Class instances
// Variance is a way to specify whether a type parameter of a type can be substituted for its sub or super class
// can declare a class as covariant on a type parameter if the parameter used only in 'out' position
// oposite is true for contravariant cases, for in positions
// read only interface List in kotlin is covariant so List<String> is a subtype of List<Any)
// function interface is declared as contravariant on its first type param and covariant on second, so (Animal) -> Int
// is a subtype of (Cat) -> Number
// Kotlin lets yoau specify variance both for a generic class as a whole(declaration-site variance and generic type(use syte variance)
// stare propection syntax can be used when the exact type args are unkonown