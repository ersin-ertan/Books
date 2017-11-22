package Part2_EmbracingKotlin.I_Generics

// 9.3.5 Use-site Variance: specifying variance for type occurrences

// Declaration site variance - variance annotations on class declarations applies to everywhere the class is used
// Java uses ? extends and ? super. Every time you use a type with a type parameter, you can also specify if it can be
// replaced with its sub or super types, this is use site variance

// Use site variance is supported in kotlin even when it can't be declared as covariant or contravariant in the class
// declaration

// MutableList is neither covariant or contravariant, but can both produce and consume values specified by type params

fun <T> copyData(source: MutableList<T>, dest: MutableList<T>) {
    for (item in source) dest.add(item)
}

// copies elements from one collection to another while both are invariant typed, source for reading, dest for writing
// element types need not match correctly. To work with types, introduce the second generic parameter

fun <T : R, R> copyData1(source: MutableList<T>, dest: MutableList<R>) {
    for (item in source) dest.add(item)
}

fun cd() {
    val ints = mutableListOf(1, 2, 3)
    val anyItems = mutableListOf<Any>()
//    copyData(ints, anyItems) // type inference failed, cannot infer type T
    copyData1(ints, anyItems)
}
// declare two generic parameters representing the element types in source and destination list
// kotlin allows for greater elegance

fun <T> copyData2(source: MutableList<out T>, dest: MutableList<T>) {
    for (item in source) dest.add(item)
} // out keyword to type usage, no methods with T in 'in' position are used


// you can specify variance annotation on any usage of a type parameter in a type declaration: parameter type,
// local variable type , function return type, ...
// Type projection - source is not a MutableList but a projected(restricted) one, can only call methods that return the
// generic type parameter or, use it in the "out" position only
fun a() {
    val list: MutableList<out Number> = mutableListOf(1)
//    list.add(34) // out projected MutableList prohibits the use of "add()" defined in MutableList
}

// we should be using List<T> as the source type, since we only use List's methods(reading)
// if the type already has a out variance like List<out T> there is no need to get an out projection of the type param

// in is similar

fun <T> copyData3(source: MutableList<T>, dest: MutableList<in T>) {
    for (item in source) dest.add(item)
}

// use site variance declarations of out and in equals java's MutableList<? extends T> and MutableList<? super T>