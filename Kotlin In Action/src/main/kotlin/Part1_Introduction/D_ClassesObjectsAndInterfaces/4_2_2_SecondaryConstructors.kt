package Part1_Introduction.D_ClassesObjectsAndInterfaces

// majority of where you need overloaded constructors in java are covered by default values
// but its common to extend a framework class that has multiple constructors to initialize

open class SecondaryConstructors {
    constructor(i:Int)
    constructor(s:String)
}

class SecondaryExtended:SecondaryConstructors {
    constructor(i:Int):super(i)
    constructor(s:String):super(s)
    constructor():super(if (true) 1 else 0)
    //    constructor(intOrString:Boolean):super( if(intOrString) 3 else "hey")
    // conditional branch result of int/string is implicitly cast to Any
    // error - none of the following functions can be called with the arguments supplied

}

fun a() {
    //SecondaryExtended(if(true)3 else "hey") // same problem
}
// @see https://stackoverflow.com/questions/45665485/kotlin-secondary-constructor-of-extended-class-conditional-branch-super-value

//  implementing properties declared in interfaces
// interface can contain abstract property declarations
interface U {
    val name:String // does not specify if obtained through getter or backing field
}

class PU(override val name:String):U // primary constructor property
class SU(val email:String):U { // custom getter
    override val name:String get() = email.substringBefore('@')
    // calculated for every call
}

class FU(val id:Int):U {
    override val name = getName(id) // property initializer
    fun getName(id:Int) = "test" // evoked once during initialization, connection in costly
}

interface UGetSet {
    // both allowed as long as they don't reference a backing field
    val email:String // must be overriden
    val name:String get() = email.substringBefore('@') // can be inherited
    // result is computed on each access, email, name are abstract properties
}

// accessing a backing field from a getter setter
// two kinds of properties: store values and custom accessors calculate values on access

class Us(val name:String) {
    var addr:String = "pre"
        set(inVal) { // can both read and modify the value
            // what's the difference between $addr and $field
            println("""
            Address was changed for user $name: $addr
            "$field" -> "$inVal".""".trimIndent())
            // addr is not changed except in this scope
            field = inVal

        }
        get() {
            return "test"
        }
    // *outdated?*you can only redefine one of the accessors for a mutable property
    // example shows both being redefined, try val

    val ad2:String = "test"
//    get() = "test2" // if get, then initialization is not allowed
//    set(value) {
//        field = value // cannot have a setter
//    }
}

fun main(args:Array<String>) {
    val user = Us("tom")
    user.addr = "new add" // set is called each =
    println(user.addr)

    val lc = LenConuter()
    println("length ccounter init ${lc.counter}")
//    lc.counter = 4 // setter is private
    lc.addWord("hey")
    println(lc.counter)
}

// changing accessor visibility

class LenConuter {
    var counter:Int = 0 // because of private set, you can't change the property
            // outside of the class
        private set

    fun addWord(word:String) {
        counter += word.length
    }
    // counter property is public, but it's value can only be changed within the class
}

// more about properties - lateinit modifier on a non null property specifies
// a later init after constructor is called
// - lazy initialized properties are part of 'delegated properties'
// - annotations can emulate java features in kotlin, @JvmField on property exposes
// public field without accessors
// const modifier makes working with annotations convenient, letting you use a property of
// of a primitive type or string as an annotation