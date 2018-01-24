package Part2_EmbracingKotlin.I_Generics

// Replacing class references with reified type parameters

// common use for reified type params is building adapters for APIs that take parameters of type Class.
// Reified type parameters make those apis simpler to call.

object ServiceLoader {
    fun load(c: Class<*>) {}
    // vs
    inline fun <reified A> loadService() = load(A::class.java)
}

sealed class Service {
    class A : Service()
    class B : Service()
    class C : Service()
}

val serviceImpl = ServiceLoader.load(Service::class.java)
// vs
val serviceImpl1 = ServiceLoader.loadService<Service>()

// Consider starting activities in android
/*
inline fun <reified T:Activity> Context.startActivity(){
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}
or even smaller
inline fun <reified T:Activity> Context.startActivity() = startActivity(Intent(this, T::class.java))

for a simple
startActivity<DetailActivity>()

*/

// Restrictions on reified type parameters

// possible use cases: type checks and casts: is !is as as?
// kotlin reflection ::class
// for java class ::class.java
// type parameter to call other functions

// Can't do
// create new instances of the class specified as a type parameter
// call methods on the companion object of the type parameter class
// use a non reified type parameter as a type argument when calling a function with a reified type parameter
// mark type parms of class, properties or non inline functions as reified(function along with all lambdas passed to it
// are inlined, if the lambdas cant be inlined , or you don't want them for performance reasons, use noinline)