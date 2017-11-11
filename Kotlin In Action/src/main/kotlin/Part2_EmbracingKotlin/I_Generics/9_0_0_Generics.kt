package Part2_EmbracingKotlin.I_Generics

// Generics
// Declaring generic functions and classes
// type erasure and reified type parameters
// Declaration site and use site variance

// Type arguments for generic types only exist at compile time
// can't use types with type arguments together with the 'is' operator, because type arguments are erased at runtime
// Type parameters of inline functions can be marked as reified to allow you to use them at runtime to perform 'is'
// checks and obtain java.lang.Class instances
// Variance is a way to specify whether a type parameter of a type can be substituted for its sub or super class
// can declare a class as covariant on a type parameter if the parameter used only in 'out' position
// opposite is true for contravariant cases, for in positions
// read only interface List in kotlin is covariant so List<String> is a subtype of List<Any)
// function interface is declared as contravariant on its first type param and covariant on second, so (Animal) -> Int
// is a subtype of (Cat) -> Number
// Kotlin lets you specify variance both for a generic class as a whole(declaration-site variance and
// generic type(use site variance)
// star projection syntax can be used when the exact type args are unknown or unimportant


// reified type parameters - inlined functions type arguments type at runtime

// Declaration site variance - specify whether a generic type with a type argument is a subtype or supertype of
// another generic type with the same base type and different type of argument. Ex List<Int> to funcs expecting List<Any>

// use site variance - same for specific use of generic type, same as java's wildcard