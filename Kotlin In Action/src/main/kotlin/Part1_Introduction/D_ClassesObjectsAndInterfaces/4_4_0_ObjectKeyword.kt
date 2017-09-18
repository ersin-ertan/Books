package Part1_Introduction.D_ClassesObjectsAndInterfaces

// declaring a class and creating an instance, combined with the object keyword
// which defines a class and creates an instance at the same time

// object declaration to define singletons

// companion objects can contain factory methods and other methods related to class
// but don't require a class instance to be called, members are accessed via class name

// object expression is used instead of java's anon inner class

// singletons - combine class declaration and declaration of single instance
data class Person1(val name:String, val id:Int)
object Payroll{ // can inherit from classes and interfaces
    val allEmployees = arrayListOf<Person1>()
    fun calcSalary() {for(person in allEmployees){}}
}
// only thing not allowed are constructors(both primary and secondary), because it is
// created at point of definition
fun s(){
    Payroll.allEmployees
    Payroll.calcSalary()
}

// a good example of the need for a singleton would be a comparator object, which
// doesn't hold state, only one is needed. Can be passed to functions like any other object.
// and can be useful nested into the class which uses it

// object declarations aren't always ideal for large software systems, but rather good
// for small software systems with low to no dependencies, because you don't have control
// for instantiation, thus no control of params for constructors. You cant replace
// implementations of the object, or it's dependencies in unit tests, configurations etc.

// special case objects nested inside a class: companion objects
// a place for factory methods and static members
// static is not part of kotlin, relying on package-level functions and object declarations
// package level functs cant access private members of a class, thus you need a function
// called without a class instance but with access to internals of a class
// use an companion object nested in the class, which can call class private functions

class Outer{
    companion object {
        fun s(){}
    }
}

fun main(args: Array<String>) {
    Outer.s()
    println(Animal.africanAnimal("Cheeta"))
    println(Animal.idAnimal(342))
}

class Animal(val name:String){
    companion object{
        fun getName(id:Int)= "name" // can also return subclasses
        fun africanAnimal(name:String) = Animal("AFR_$name")
        fun idAnimal(id:Int) = Animal("ID_${getName(id)}")
    }
}

// companion object members can't be overridden in subclasses