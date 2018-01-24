package Part1_Introduction.E_ProgrammingWithLambdas

// functional apis for collections
// use library functions for the majority of tasks, main is filter and map. Similar to any functional language that supports
// lambdas

// essentials: filter map

data class Person2(val name:String, val age:Int)

val list = listOf(1, 2, 3, 4, 5)
val people = listOf(Person2("tom", 29), Person2("jill", 34))

fun filter() { // filter removes elements but doesn't change it
    println(list.filter { it % 2 == 0 }) // only those that satisfy the filter will go through

    println(people.filter { it.age > 30 })
}

fun map() {
    println(list.map { it * it })
    println(people.map { it.name }) // or
    println(people.map(Person2::name))
    println(people.filter { it.age > 30 }.map(Person2::name))
    println(people.filter { it.age == people.maxBy(Person2::age)?.age })  // need null check
    // this code rpeats the find max age for every person, thus calculate age once
    val maxAge:Int? = people.maxBy(Person2::age)?.age
    println(people.filter { it.age == maxAge }) // don't repeat the calculation
}

fun filterMaps() {
    val numbers = mapOf(0 to 'z', 1 to 'o')
    println(numbers.mapValues { it.value.toUpperCase() })
    // filterKeys and mapKeys and filterValues and mapValues when needed
}

// 5.2.2 all any count find: predicate to a collection - matching a condition via all/any
// count checks how many elements satisfy the predicate, and find returns the first matching element

val over30 = { p:Person2 -> p.age > 30 }

fun predicates() {
    println("Is tom over 30 ${over30(people[0])}")
    println("Is jill over 30 ${over30(people[1])}")
    println("Is everyone ${people.all(over30)}")
    println("Is anyone ${people.any(over30)}")

    println(!list.all { it == 3 }) // not all are equal to three, true
    println(list.any { it != 3 }) // any is not equal to three, true

    println(people.count(over30)) // count only tracks the number of matching elements
    println(people.filter(over30).size) // filter creates a collection of all elements that are 'over30'

    println(people.find(over30)) /// returns first element to match, or null
}

fun groupBy() { // converting a list to a map of groups
    println(list.groupBy { it % 2 == 0 }) // {false=[1, 3, 5], true=[2, 4]} as a Map<Boolean, List<Int>>
    val listA = listOf("a", "ab", "b")
    println(listA.groupBy(String::first)) // {a=[a, ab], b=[b]} as Map<Char, List<String>>
}

fun flatMapAndFlatten() {
    // processing elements in nested collections
    class Book(val title:String, val authors:List<String>)

    val books = listOf<Book>(
            Book("abc", listOf("Tom", "Bill", "John")),
            Book("cde", listOf("Bill", "Tom", "Cher")),
            Book("efg", listOf("Bill", "Cher", "Jill", "Jack")))

    println(books.flatMap { it.authors }.toSet()) // flatMap both transforms/maps each element to a collection according
    // to the function given as an argument, and combines(flattens) several lists into one

    val strings = listOf("abcd", "def")
    println(strings.flatMap { it.toList() }) // not double d
    println(strings.flatMap { it.toList() }.toSet()) // note single d, removes duplicates
    // maps each string to a list of characters, then flattens it to a single list of characters

    val listOfLists = listOf(list, list, list)
    println(listOfLists)
    println(listOfLists.flatten()) // flattens collection of collections into a single collection
    println(listOfLists.flatten().toSet()) // flattens collection of collections into a single set

}

fun main(args:Array<String>) {

//    filter()
//    map()
//    filterMaps()
//    predicates()
//    groupBy()
//    flatMapAndFlatten()
}