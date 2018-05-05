package ch02

import arrow.core.*
import arrow.instances.*
import arrow.typeclasses.Monoid
import arrow.typeclasses.Semigroup
import ch01.p

// 2.3 Exercise - Make Monoids

// turns out there was a bug in option monoid
// https://gitter.im/arrow-kt/Lobby?at=5aebb85bb37eab7d0466e78b
// where none was not defaulting to the monoids empty value

// for boolean
val boolAndMonoid = object : Monoid<Boolean> {
    override fun empty(): Boolean = true
    override fun Boolean.combine(b: Boolean): Boolean = this && b
}
val boolOrMonoid = object : Monoid<Boolean> {
    override fun empty(): Boolean = false
    override fun Boolean.combine(b: Boolean): Boolean = this || b
}

// exclusive Or
val boolEitherMonoid = object : Monoid<Boolean> {
    override fun empty(): Boolean = false
    override fun Boolean.combine(b: Boolean): Boolean = (this && !b) || (!this && b)
}

// exclusive Nor
val boolXnorMonoid = object : Monoid<Boolean> {
    override fun empty(): Boolean = true
    override fun Boolean.combine(b: Boolean): Boolean = (this && !b) && (!this && b)
}

// what monoids and semigroups are there for sets(no repeated values, no order)
// emptySet, combine set union set2 because you cannot have repeats

fun <A> setUnionMonoid() = object : Monoid<Set<A>> {
    override fun empty(): Set<A> = emptySet()
    override fun Set<A>.combine(b: Set<A>): Set<A> = this.union(b)
}

private val intMonoid = object : Monoid<Int> {
    override fun empty(): Int = 0
    override fun Int.combine(b: Int): Int = this + b
}
// what is the point of having the intMonoid defined if the set monoid has precedence in the monodial operations

val intSetMonoid = object : Monoid<Set<Int>> {
    override fun empty(): Set<Int> = emptySet<Int>()
    override fun Set<Int>.combine(b: Set<Int>): Set<Int> = this.union(b)
}


fun testMonoid() {
    setUnionMonoid<Int>().combineAll(setOf(1, 2), setOf(2, 3)).run { println(this) }
    intSetMonoid.combineAll(setOf(1, 2), setOf(2, 3)).run { println(this) }
}

// Monoid extends Semigroup - Cats kernel a subproject of cats of typeclasses for libs that don't require all

fun stringAndIntMonoids() {
    val stringMonoid = object : Monoid<String> {
        override fun empty(): String = ""
        override fun String.combine(b: String): String = this.plus(b)
    }
    stringMonoid.empty() // like this or below
    stringMonoid.run { empty() }
    stringMonoid.combineAll("Hi ", "there")

    val stringMonoidInstance = object : StringMonoidInstance {}
    stringMonoidInstance.run { empty() }
    stringMonoidInstance.run { "Hi ".combine("there") }

    val stringSemigroupInstance = object : StringSemigroupInstance {}
    stringSemigroupInstance.run { println("hello ".combine("how are you")) }

    val intMonoidInstance = object : IntMonoidInstance {}
    intMonoidInstance.run { println(23.combine(22)) }
}

fun optionMonoid() {
    val a = 33.some()
    val b = 23.some()

    object : OptionMonoidInstance<Int> {
        override fun SG(): Semigroup<Int> = object : IntSemigroupInstance {}
    }.run { println(a.combine(b)) }
}

fun monoidSyntax() {
    // scala has special syntax |+| to combine, kotlin simply uses .combine
    object : StringMonoidInstance {}.run { empty().combine("hi ").combine("there") }
}

fun main(args: Array<String>) {
//    testMonoid()
//    stringAndIntMonoids()
//    optionMonoid()
//    monoidSyntax()
//    addingAllTheThings()
//    addingAllTheThings3()
    testOptionAdd()
}

// 2.5.4 Exercise:Adding All the things
fun addingAllTheThings() {
    fun add(items: List<Int>): Int {
        val intMonoid = object : Monoid<Int> {
            override fun empty(): Int = 0
            override fun Int.combine(b: Int): Int = this + b
        }
        // recommended way
//        return items.fold(intMonoid.empty(), {acc: Int, i: Int -> intMonoid.combineAll(acc, i) })
        // my way, but why is the intMonoid working without
        intMonoid.run { empty() }
        intMonoid.empty()
        return intMonoid.combineAll(*items.toTypedArray()) // don't forget the spread operator
    }

    println(add(listOf(1, 2, 3)))
}

// where... what does this do
fun <A, B, C> Int.test(pair: Pair<A, B>): Pair<A, B> where B : Pair<Int, B>, A : C = Pair(pair.first, pair.second)

fun addingAllTheThings2() {
    fun <A> add(items: List<A>, monoid: Monoid<A>): A = items.fold(monoid.empty()) { acc: A, a: A -> monoid.combineAll(acc, a) }
}

inline fun <reified A> add(items: List<A>, monoid: Monoid<A>): A = monoid.combineAll(*items.toTypedArray<A>())
//fun <A> add2(items: List<A>, monoid: Monoid<A>): A = items.fold(monoid.empty()) { acc: A, a: A -> monoid.combineAll(acc, a) }
// so we try
fun <A> add3(items: List<A>, monoid: Monoid<A>): A = items.fold(monoid.run { empty() }) { acc: A, a: A -> monoid.run { acc.combine(a) } }

fun addingAllTheThings3() {
    println(add(listOf(1, 2, 3), object : IntMonoidInstance {}))
//    println(add2(listOf(1,2,3), object :IntMonoidInstance{}))
// this will provide a class cast exception from object to integer in combine all
    println(add3(listOf(1, 2, 3), object : IntMonoidInstance {}))
}

fun testOptionAdd() {

//    val optionMonoidInstance = object : Monoid<Option<Int>> {
//        override fun empty(): Option<Int> = Some(0)
//        override fun Option<Int>.combine(b: Option<Int>): Option<Int> =  when (this) {
//            is Some<Int> -> when (b) {
//                is Some<Int> -> Some( this.t + b.t)
//                None -> empty()
//            }
//            None -> empty()
//        }
//    } // the way I would have expected

    val optionMonoidInstance1 = object : OptionMonoidInstance<Int> {
        override fun SG(): Semigroup<Int> = object : IntMonoidInstance {}
    }

    "Testing the scoped function result".p()
    add(listOf(Some(1), None, Some(2), None, Some(3)), optionMonoidInstance1).run { this.p() } // returning None instead of Some(6)
    add3(listOf(Some(1), None, Some(2), None, Some(3)), optionMonoidInstance1).p() // returning None instead of Some(6)

    "Testing the scoped function result 2".p()
    add3(listOf(Some(1), None, Some(2), None, Some(3)), optionMonoidInstance1).run { this.p() } // returning None instead of Some(6)
    add(listOf(Some(1), None, Some(2), None, Some(3)), optionMonoidInstance1).p() // returning None instead of Some(6)

    add(listOf(1.some(), 2.some(), 3.some()), object : OptionMonoidInstance<Int> {
        override fun SG(): Semigroup<Int> = object : IntMonoidInstance {}
    }).p() //Some(6)

    "Recommended Way".p() // from docs
    Option.monoid(Int.monoid()).run { listOf<Option<Int>>(Some(1), Some(2), None).combineAll() }.p()
    Option.monoid(Int.monoid()).run { listOf<Option<Int>>(Some(1), Some(2), None).combineAll().p() }

    // both yield None instead of some

    data class Order(val quantity: Int, val totalCost: Float)

    add(listOf(Order(1, 1.1F), Order(2, 2.2F)), object : Monoid<Order> {
        override fun empty(): Order = Order(0, 0.0F)
        override fun Order.combine(b: Order): Order = Order(quantity + b.quantity, totalCost.plus(b.totalCost))
    }).p()


}