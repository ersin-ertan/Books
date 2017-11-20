package Part2_EmbracingKotlin.I_Generics

// 9.3.0 Variance: generics and subtyping

// variance describes how types with the same base type and different type arguments relate to each other
// List<String> vs List<Any>, essential to understand when writing your own generic classes or functions, creating
// Apis that don't restrict users in inconvenient ways and don't break their type safety expectations


// 9.3.1 Why variance exists: passing an argument to a function
// a function that takes a List<Any> as an argument

fun printContents(list: List<Any>) {}

// strings work fine here, any is safe

fun addAnswer(list: MutableList<Any>) {
    list.add(42)
}

// but compile time error (book said runtime error), the idea is determining
fun runtimeError() {
    val strings = MutableList<Any>(0) {}
    strings.add("a")
    // nor does calling addAnswer when the list values are known
    addAnswer(strings)
//    val newAns = strings.maxBy { it.length } // doesn't work

}

// 9.3.2 Classes, types and subtyping
// Type and class are not equivalent. For a non generic class, the name can be used as the type.
// var x:String creates a variable holding an instance of the string class, but use String? then the class can be used
// to construct two types. This becomes more complicated with generic classes. To get a valid type you have to substitute
// a specific type for the class's type parameter. List isn't a type, it's a class with all of List<Int>, List<String>
// being potential subtypes, each generic type produces infinite classes

// Subtype is the sub in the super-sub relationship. A sub is always a super, but a super is never a sub.
// Subtypes are not the same as subclasses, see nullable types. A non null type is a subtype of it's nullable version,
// both corresponding to one class.

// The generic class like MutableList is an "Invariant" on the type parameter for any two different types like A,B.
// thus MutableList<A> isn't a subtype or super of MutableList<B>. Java all classes are invariant.

// If B is a subtype of A, then List<A> is a subtype of List<B>, the interface/class is covariant.


// 9.3.3 Covariance: preserved subtyping relation
// a generic class like P<T>, where P<A> is a supertype of P<B>, subtyping is preserved. To declare a covariant class
// of a type parameter put "out" keyword

interface P<out T> { // T is covariant
    fun p(): T
}

// you may now pass values of that class as function parameters and return values

open class Animal {
    fun feed() {}
}

class Herd<T : Animal> {
    val size: Int
        get() {
            return 1
        }

    operator fun get(i: Int): T {
        return size as T
    }
}

fun feedAll(animals: Herd<Animal>) {
    for (i in 0 until animals.size) animals[i].feed()
}

class Cat : Animal() { // cat is an animal, but is not covariant
    fun cleanLitter() {}
}

fun takeCareOfCats(cats: Herd<Cat>) {
    for (i in 0 until cats.size) {
        cats[i].cleanLitter()
    }
//    feedAll(cats) // but Error: inferred type is Herd<Cat> but Herd<Animal> was expected
}

// we must make Herd covariant using out
class Herd2<out T : Animal> {
    val size: Int
        get() {
            return 1
        }

    operator fun get(i: Int): T {
        return size as T
    }
}

fun feedAll2(animals: Herd2<Animal>) {
    for (i in 0 until animals.size) animals[i].feed()
}

fun takeCareOfCats2(cats: Herd2<Cat>) {
    for (i in 0 until cats.size) {
        cats[i].cleanLitter()
    }
    feedAll2(cats)
}

// making any class covariant would be unsafe, use type parameter constraints. using 'out' means it returns type T
// but can not take them in

// if T is used as the return type of a function, then its out, because function produces T
// if T is used as the type. of the function parameter, it's in, consuming

interface T<T> {
    fun t(t: T/*this T is in*/): T // last T is out
}

// out preserves subtyping where P<Cat> is subtype of P<Animal>

// in List, you can only get the element of type T, no methods store the value of type T in the list, thus covariance

// the out can even be a type param for another generic :List<T> instead of simply returning T

// you can't declare MutabelList<T> as covariant on its type parameter because it contains methods that take values of
// T as parameters and return values of T, thus it is both out and in
// Type parameter T is declared as 'out' but occurs in the 'in' position

// constructor params are neither out nor in, even if it is declared out, you can use it in 'in' too


// variance protects from class misuse when working with more generic types, since constructor can't be called later
// there is no danger
// But if you use val or var with a constructor parameter, you declare a getter and setter(if mutable) so the type
// parameter is usind in both in/out position for read only properties and both for mutable properties

class Herdd<T : Animal>(var leadAnimal: T, vararg animals: T) {}