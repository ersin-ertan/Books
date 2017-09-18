package practice

import java.time.Instant
import java.util.*

open class Ab(a:Int)

class Ac(b:Int, private val bb:Int):Ab(b) // cannot have private on a value param

data class Ad(val b:Int, var c:Int, private val d:Int):Ab(b) // extended an open class

class Ae() { // empty primary constructor defined
    constructor(a:Int):this() {}
}

class Af {
    constructor(a:Int) //no need to define, if only constructor can be primary constructor warning
}

class Ag {

    private val b:Boolean
    protected val c:Int
    public val d:Float = 9F // public by default
    val e:Double = d.toDouble()
    var f:String

    constructor(a:Int):this(true)// all vars must be defined or init with a value
    constructor(b:Boolean) {
        this.b = b
        c = 3
        f = "second"
    }

    init { // on all constructors
        f = "first"
    }
}

sealed class Ah // auto create empty constructor
class Ai:Ah()

fun main(args:Array<String>) {
    Ab(1) // a
    Ac(2, 22) // and b are lost in the constructor
    val ad = Ad(3, 33, 333) // must have a constructor val/var, b is retained
    ad.b
    ad.c = 32
    Ag(2).f.p()
    Aj()
    Ak()//.int doesn't exist
    val al = Al()
    println("val ${al.dateVal}")
    println("var ${al.dateVar}")
    Thread.sleep(1000)
    println("val ${al.dateVal}")
    println("var ${al.dateVar}")

    al.a.p()
    al.b.p()
    al.a
//    al.b = "new" // setter protected
    var am = Am()
    println("but the instance of am to string is")
    am.toString().p()
    Am.Companion.setAmA1("setting a") // these are like static calls, accessing the object class not the instance
    Am.Companion.settingAmB("'new Amb'")
    Am.Companion.settingAmB("'test amB'")
    println("but the instance of am to string is still")
    am.toString().p()
}

class Aj(int:Int = 3) // doesn't exist past the construction
class Ak {
    constructor(int:Int = 3)
}

class Al() {
    // difference is when setting
    val dateVal:String get() = Instant.now().toString()
    //        set(){} val cannot have a setter, it's suppose to be immutable, but calculated on each call...?
    var dateVar:String
        get() = Instant.now().toString()
        set(inputValue) {
            inputValue.p()
        }

    var a:String = "abc"
        private set // change the visibility with no body
    var b:String = "cbd" // need default value, unless you have a get()
        protected set(value) {
            field = value.plus(value)
        } // change the visibility with no body

    fun secretSetA(input:String) {
        a = input
    }

    var aSetter = { input:String -> secretSetA(input) }

    companion object {
        fun setB(input:String) {
            b = input
        }

        fun setA(input:String) {
//            a = input // whil/e the variable may be public, the set is marked private, thus call a internal method
//            secretSetA(input)// cant call function, only access public properties?
        }
    }
}

// but we sub class the parent class
open class Am { // auto created constructor
    private var amA = "a"
    protected var amB = "b"
        set(value) {
            println("setting amb to $value")
            field = value
        }

    protected fun setAmA(input:String){ amA = input}

    companion object:Am() { // because of the subclassing, we can now use private properties
        //        var amB = "b" // can't, is final in Am
        fun settingAmB(input:String) {
//            amA = input // cant, is invisible
            println("AmB was $amB")
            amB = input
            println("AmB is now $amB")
        }

//        override fun setAmA(input:String){} can't is final in AM
        fun setAmA1(input:String){setAmA(input)}
    }

    override fun toString():String {
        return amA + amB
    }
}






































