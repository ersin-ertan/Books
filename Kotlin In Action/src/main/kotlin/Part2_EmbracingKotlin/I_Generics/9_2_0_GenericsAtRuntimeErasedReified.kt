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

fun print(c: Collection<*>) {
    // warning unchecked cast
    val intList = c as? List<Int> ?: throw IllegalArgumentException("list is expected")
    println(intList)
}

// call with a different container type and get IllegalArgumentException, call with wrong types in container to get
// ClassCastException

// is works with hard type argument on collection
fun print1(c: Collection<Int>) {
    if (c is List<Int>) {
        print(c)
    }
}


// Declaring functions with reified type parameters

// generally this is not allowed
//fun <T> isT(value: Any) = value is T // cannot check for erased types

// inline, compiler will replace every call to the function with the actual code implemnting the function

inline fun <reified T> isT(value: Any) = value is T // works

//ex

val items = listOf("one", 3)

val i = items.filterIsInstance<String>()