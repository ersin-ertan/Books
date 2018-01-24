package Part2_EmbracingKotlin.H_HigherOrderFunctions

import p

// Default and null value for parameters with function types

// in the previous joinToString example, the toString method is flexible, relying on toString to do the object to string
// conversion, but this is not always needed. You can pass a lambda to specify how values are converted into strings.
// But all callers cannot pass in the lambda, as must are ok with default value, so define a parameter of a function
// type and specify a default value for its lambda

fun <T> defaultFuncParam(input: T, operation: (T) -> String = { it.toString() }): String {
    return operation(input) // wil default it.toString
}

fun testDefault() {
    defaultFuncParam(1).p()
    defaultFuncParam(2, { (it * it).toString() }).p()
}

// use generics to enforce the type parameter for input and lambda

// or via a nullable function parameter

fun nullable(inp: Int, op: ((Int) -> String)?): String {
    return op?.let {
        // if op is not null
        op(inp)
    } ?: "default"
}

// or a variable of a function type is an instance of a class implementing the corresponding FunctionN interface, with
// invoke method which can be called via callback?.invoke(), thus

fun invoke(inp: Int, op: ((Int) -> String)?): String {
    return op?.invoke(inp) ?: "default"
}


// returning functions from function
enum class ShippingMethod {
    LAND, AIR
}

fun shippingCalculator(sm: ShippingMethod): (Int, Int) -> Double = when (sm) {
    ShippingMethod.AIR -> { length, width -> length * width * 2.3 }
    ShippingMethod.LAND -> { length, width -> length * width * 1.4 }
}

fun calcShippingCost() {
    val calc = shippingCalculator(ShippingMethod.LAND)
    // calc is the function
    println("cost by land is: $${calc(3, 5)}")
}

// useful as a filter for contacts list, holding state of what you typed, the filters entries based on the predicate state
// the get predicate function would be a function taking in a person and returning a boolean

data class Person(val name: String, val number: Int?)
class Filter(val personList: List<Person>) {
    var prefix = ""
    var hasNumber = true

    fun getPeopleWithPrefix(): (Person) -> Boolean {
        // if() return {}
        // else return {}
        return { p -> true } // not so sure about this one
    }
}

// Removing duplication through lambdas -
enum class OS { W, L, M, I, A }

data class SiteVisit(val path: String, val duration: Double, val os: OS)

val log = listOf(
        SiteVisit("/", 34.3, OS.W),
        SiteVisit("/", 24.3, OS.W),
        SiteVisit("/login", 3.7, OS.I),
        SiteVisit("/login", 13.7, OS.I),
        SiteVisit("/signup", 4.4, OS.A),
        SiteVisit("/signup", 54.4, OS.A),
        SiteVisit("/signup", 74.4, OS.A)
)

val avgWDur = log.filter { it.os == OS.W }.map(SiteVisit::duration).average()
// the only thing changing is the os, thus we extract that part out

fun List<SiteVisit>.averageDurationFor(os: OS) = filter { it.os == os }.map(SiteVisit::duration).average()

fun calcDur() {
    println(avgWDur)
    println()
    OS.values().forEach {
        println("${it.name}: ${log.averageDurationFor(it)} secs")
    }
}

val mac = log.averageDurationFor(OS.M)

// but for mobile platforms like I and A you need to filter{it.os in setOf(OS.I, OS.A)}
// so we

fun List<SiteVisit>.avgDurFor(predicate: (SiteVisit) -> Boolean) = filter(predicate).map(SiteVisit::duration).average()

val mobile = log.avgDurFor { it.os in setOf(OS.M, OS.A) }

fun Any.p(): Unit = println(this)

fun main(args: Array<String>) {
//    testDefault()
//    calcShippingCost()
    calcDur()
    println()
    mac.p()
    mobile.p()
}
