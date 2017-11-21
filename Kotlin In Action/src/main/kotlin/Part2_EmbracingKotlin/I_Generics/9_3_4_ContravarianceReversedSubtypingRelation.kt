package Part2_EmbracingKotlin.I_Generics

// 9.3.4 Contravariance: reversed subtying relation
// is dual to covariance: subtyping relation is the opposite of the subtying relations used as its type argument

/*
interface Comparator<in T>{ // T is in 'in' position
    fun compare(e1:T, e2:T):Int{}
}

val anyC = Comparator<Any>{
...
}
listOf("st","ring").stortedWith(anyC)  // use the comparator for any objects to compare specific objects, such as strings
*/

// sortedWith expects a Comparator<String>, but it's safe to pass one that compares more general types, thus
// the comparator handles the intended class and all supertypes
// Now Comparator<Any> is a subtype of Comparator<String>, where Any is a supertype of String
// The relation between comparators with different types goes the opposite direction of the relation between type

// A class is contravariant on the type parameter is a generic class where Consumer<A> is a subtype of Consumer<B>
// if A is a super type of B. The type argumens A and B changed places, so subtyping is reversed.
// 'in' means values of the corresponding type are passed in to the methods of this class and consumed by those methods.
// Similar to the covariant case, constraining use of the type parameter leads to the specific subtyping relation.
// in on the type param T means subtyping is reversed, T can be used only in 'in positions

// covariant - Producer<out T> - subtyping for the class is preserved, T only in out pos
// contravariant - Consumer<in T> - subtyping is reversed: Consumer<Animal> is a subtype of Consumer<Cat>, T only in 'in' pos
// Invariant - MutableList<T> - no subtyping, T in any position

// class or interface can be covariant on one type and contra on other
interface FuctionAgain<in P, out R> { // aka (P) -> R
    operator fun invoke(p: P): R
}

// or

fun enumerateCats(f: (Cat) -> Number) {}
fun Animal.getIndex(): Int = 0

fun enum() {
    enumerateCats(Animal::getIndex) // legal, animal is a supertype of cat, and Int is a subtype of number
}

// cat -> animal
// (cat) -> number   ->   (animal) -> int
// int -> number
