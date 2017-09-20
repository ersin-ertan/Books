package Part1_Introduction.F_TheKotlinTypeSystem

import java.io.BufferedReader

// collections and arrays - kotlin builds on javas collections library via extension functions.

// Nullability and collections - collections type param can be nullable, as for the collection itself

private fun <T:Number> add(c:Collection<T?>) {
    val l:MutableList<T> = mutableListOf()
    c.forEach { if (it != null) l.add(it) }
}

fun readNums(reader:BufferedReader):List<Int?> {
    val result = ArrayList<Int?>()

    for (line in reader.lineSequence())
        try {
            val number = line.toInt()
            result.add(number)
        } catch (e:NumberFormatException) {
            result.add(null)// its ok to add nulls
        }

    return result
}

fun readNums1(reader:BufferedReader):List<Int?> {
    val result = ArrayList<Int?>()
    for (line in reader.lineSequence()) result.add(line.toIntOrNull()) // power method
    return result
}

fun wholeListNull(reader:BufferedReader):List<Int?>? {
    return null // the whole list can be null, and both the elements in the list can be null
} // you need a null check for both the list, and the contents of the list

val listWithNulls = listOf<Int?>()

fun workingWithNullValues() {
    val validNums = listWithNulls.filterNotNull()
    // do operations with the validNums list
}

// Read only and mutable collections - kotlin reparates interfaces for accessing the data and for modifying it

// key methods in Collection: size, iterator, contains
// vs MutableCollection: add, remove, clear

// if you have a collection that is part of the internal state of your componnent, you may need to make a copy of the collection
// before passing it to a function(defensive copy)

fun <T> copyElements(source:Collection<T>, target:MutableCollection<T>) {
    for (item in source) {
        target.add(item)
    }
}

fun copy() {

    val mutable = mutableListOf<Int>(1, 2, 3, 4)
    val non = listOf<Int>(5, 6, 7, 8, 9)
    copyElements(non, mutable) // type safe for non mutable lists

    mutable.addAll(non) // why don't we use this?
    // read only collections aren't necessarily immutable, it may be one of many references, thus some can be mutable
    // read only collections aren't always thread safe
}

// kotlin  collections and java - every java collection interfacce has two representations in kotlin, readonly and mutable
// mutable and normal versions of : iterable, colletion, list, set

// if you a passing a collection from kotlin to java, you must use the correcct type for the parameter depending on
// whether the java code will mutate the collection, along with non null elements

// Collections as platform types - platform types kotlin doesn't have the nullability info, so the compiler allows kotlin
// code to treat them as nullable or non null. Variables of collection types declared in java are also seen as platform types
// A collection with platform types is one with unknown mutability.

// is the collection nullable, are the elements nullable, will your method modify the collection
// because of this java to kotlin conversion, a lot will be required to be known upfront when implementing the interface

// prefer collections to arrays

// Arrays of objects and primitive types -

fun main(args:Array<String>) {
    for (i in args.indices) { // use array.indices extension property to iterate over thhe range of indices
        println("arg $i is ${args[i]}")
    }
}
// to create an array in kotlin you have the follwing possibilities - arrayOf, arrayOfNulls, Array constructor

val letters = Array<Char>(26) { i -> 'a' + i }

// a common case for creating array in cokting is to call java method that takes an array, or vararg param

val typedArray = listOf(1, 3, 5).toTypedArray() // can use this to convert

// spread operator * is used to pass an array when vararg parameter is expected
val print = println("%s%s%s".format(*typedArray)) // type arguments of array types always become object types
// thus Array<Int> will have boxed integers = javas Integer[], if you need primitive types without boxing
// use a specialized class.

// IntArray, BtyeArray, CharArray and so on, compiled to regular java primitive types arrays like int[]
// use the constructor, factory method intArrayOf, or constructor with lize and init lambda

val arr1 = IntArray(2)
val arr2 = intArrayOf(0, 0)
val arr3 = IntArray(2, { 0 }) // all three are equal

// boxed values, to array
val arr4 = listOf<Int>(1, 2).toIntArray()

// working with arrays are the same as lists, but the return type of some like filter, map... are lists

fun main1() {
    arr1.forEachIndexed { index, element ->
        println("arg $index is $element")
    }
}