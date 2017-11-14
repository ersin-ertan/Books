package Part2_EmbracingKotlin.I_Generics

// generics at runtime: erased and reified type parameters - Jvm uses type erasure, thus type arugements of an instace of
// a generic class aren't preserved at runtime. Get around limitations by declaring a function inline.


// Generics at runtime: type checks and casts
// at runtime a List<String> is only a List

// its not possible to use type with type parameters in is checks, because types aren't stored
// if (value is List<String>)

// Benefits of erasing generic type information - overall amount of memory used by application is smaller, less info

// Use star projection syntax
// if(value is List<*>)

// include a * fore every type parameter the type has, like javas List<?>

// Warning won't fail with as casts, if the class has the correct base type but wrong type argument because the type
// is not known at runtime. You will se an unchecked cast warning.