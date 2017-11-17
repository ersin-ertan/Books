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
    val strings = MutableList<Any>(0){}
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