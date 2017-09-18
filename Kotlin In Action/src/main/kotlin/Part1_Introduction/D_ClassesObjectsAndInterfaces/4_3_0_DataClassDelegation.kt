package Part1_Introduction.D_ClassesObjectsAndInterfaces

// compiler generated methods: data classes and class delegation

// universal object methods
open class Client(var name:String, val code:Int){
    override fun toString()= "${this.javaClass.simpleName}(name=$name, code=$code)"
    override fun equals(other:Any?):Boolean{
        if(other==null || other !is Client) return false // is equals instanceof
        return name == other.name && code == other.code
    }
}

fun main(args: Array<String>) {
    val c = Client("Tom", 1)
    val cc = Client("Tom", 1)
    val ccc = c
    c.name = "Bob"
    println(c.toString())
    println(ccc.toString())
    println(c==cc) // false
    println(c==ccc) // true

    val setOfHash = hashSetOf(Client("Bob", 1))
    println(setOfHash.contains(Client("Bob", 1))) // not equal; false

    val setOfHash1 = hashSetOf(Client1("Bob", 1))
    println(setOfHash1.contains(Client1("Bob", 1))) // equal; true

    val c1 = Client1("Tom", 1)
    println("copy original ${c1}" )
    println(c1.copy(code = 2).toString())
 }

// java uses == to compare primitive and reference types
// kotlin == is default to compare two objects, comparing their values by
// calling equals under the hood, if equals is overridden you can compare instances using
// ==, which is the same for reference comparisons too

// at first their hash codes are compared, and then, only if they’re equal, the
// actual values are compared

class Client1(name: String, code: Int) : Client(name, code){
    override fun hashCode(): Int = name.hashCode() * 31 + code
    fun copy(name:String = this.name, code:Int = this.code) = Client(name, code)
}

// adding data before class generates toString, equals, hashCode

data class Client2(val name:String, val code:Int)
// properties that aren't declared in the primary constructor are not part of equality
// and hashcode computation

// copy()
// strongly recommended to use val properties for data classes, else containers like
// HashMap can get into an invalid state due to value modification

// class delegation by keyword
// implementation inheritance causes system fragility in oop systems
// an evolving system will change it's implementation causing dependent classes which
// overrode methods to behave incorrectly
// Kotlin treats all classes as final by default, only classes designed for extension
// can be inherited from, often you need to add behaviour even if it wasn't open
// you can use the decorator pattern, a new class created, implementing the same
// interface as original but storing the instance of the original class as a field,
// thus non modified behaviour would be forwarded to the class, but requires large boilerplate code
// so, whenever you implement an interface, you can delegate the implementation to another
// object using by

class DelegatingCollection<T>(innerList:Collection<T> = ArrayList<T>())
    :Collection<T> by innerList{
// compiler will generate implementation
}

class CountingSet<T>(val innerSet:MutableCollection<T> = HashSet<T>()):MutableCollection<T> by innerSet{
    var objectsAdded=0
    override fun add(element:T):Boolean{
        if(innerSet.add(element)) {
            ++objectsAdded
            return true
        }
        return false
    }

    override fun addAll(elements: Collection<T>): Boolean {
        if(innerSet.addAll(elements)) {
            objectsAdded += elements.size
            return true
        }
        return false
    }
    // delegates all methods to innerSet, except for above two
}
