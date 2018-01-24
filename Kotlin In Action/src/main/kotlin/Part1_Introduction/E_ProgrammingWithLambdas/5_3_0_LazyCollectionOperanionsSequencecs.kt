package Part1_Introduction.E_ProgrammingWithLambdas

import java.io.File
import kotlin.system.measureNanoTime

// performance of chained collection operations
// map and filter create eager intermediate collections.
// Sequences give you an alternative way to perform computations, avoiding intermediate temp objects

// both map and filter return a list, creating two lists, is inefficient with a million entries
// use sequences instead

fun seq() {
    println(
            people.asSequence()
                    .map(Person2::name).filter { it.startsWith("t") }
                    .toList())
}
// no intermediate collections store the elements - lazy collections are with the Sequence interface
// a sequence of elements that can be enumerated one by one, Sequence provides only one method, iterator to
// obtain values

//elements are evaluated lazily, thus you can use it to perform chains of operations on elements without creating temp collections

// sequences are good for interating, while collections are good for other api functions

// executing sequence operations: intermediate and terminal operations

// intermediate operation - returns another sequence, knowing how to transform the elements of the original sequence
// terminal operation - returns a result, which may be a collection, element, number, or other object obtained by
// the sequence of transformations of the initial collection

// intermediate operations are always lazy
fun interm() {
    println(
            listOf(1, 2, 3, 4, 5, 6).asSequence()
                    .map {
                        print("map($it) ")
                        it * it
                    } // intermediate operations
                    .filter {
                        print("filter($it) ")
                        it % 2 == 0
                    }
                    .toList())// terminal operation
}
// intermediate transformations are postponed and only applied when the result is obtained, when the terminal operation is called
// naive would me to call map on each element, then filter on each element of resulting for collection
// but not for sequences, all operations are applied to each element sequentially: first element processed(mapped and filtered)
// then second, third. Some elements aren't transformed at all if the result is obtained earlier

fun seqInterm() {
    println(listOf(1, 2, 3, 4).asSequence()
            .map { it * it }.find { it > 3 }) // if collection all would be evaluated by map
    // sequences are lazy skipping some, the find begins processing the elements one by one, since 1^2 is less than three,
    // it is not further processed.

    // order of operations can affect performance
    // map a person to name then filter age less than 18
    // but consider
    // filter age less than 18, then map a person to name

    // streams vs sequences - java 8 streams are the same concept -but j8 gives you to run stream operations in parallel
    // on multiple cpus, until coroutines are implemented
}

fun orderOfOperations() { // this is a contrived example
    println(measureNanoTime {
        (1..100_000).map { it.toString() }.filter { it.toInt() > 50_000 }.toList()
    }.toString() + "\tMap-Filter Collection")
    println(measureNanoTime {
        (1..100_000).asSequence().map { it.toString() }.filter { it.toInt() > 50_000 }
    }.toString() + "\tMap-Filter Sequence")
    println(measureNanoTime {
        (1..100_000).filter { it > 50_000 }.map { it.toString() }
    }.toString() + "\tFilter-Map Collection")
    println(measureNanoTime {
        (1..100_000).asSequence().filter { it > 50_000 }.map { it.toString() }.toList()
    }.toString() + "\tFilter-Map Sequence") // don't understand why filter map longer than map filter
}

fun creatingSequences() {
    val naturalNumbers = generateSequence(0) { it + 1 } // postponed computation
    val numbersTo100 = naturalNumbers.takeWhile { it <= 100 } // postponed computation
    println(numbersTo100.sum()) // all the delayed operations are performed when the result sum is obtained
} // values wont be evaluated until you call the terminal operation sum()

// in a sequence, each element has parents of its own type.
fun File.isInsideHiddenDirectory() = generateSequence(this) { it.parentFile }.any { it.isHidden }

// replating any with find, youll get the desired directory? using sequences allows you to stop traversing the parents as
// soon as you find the required directory
fun isHidden() {
    println(File("/Users/tom/.HiddenDir/a.txt").isInsideHiddenDirectory())
}


fun main(args:Array<String>) {
//    seq()
//    interm()
//    orderOfOperations()
    isHidden()
}