package practice

class F
open class G()

interface Printable{
    val a:Int?
//    var b:Int = 9 // property initializers not allowed in interfaces
    fun p(){ println(a)}
}

open class H(override val a:Int):Printable
open class H1(override  var a:Int):Printable
open class H2(override val a:Int? = 99):Printable
open class H3(override var a:Int? = 98):Printable
data class I(val a:Int, var b:Int, val c:Int = 3, var d:Int = 4) // must have at least one primary constructor parameter
//open data class I1(val a:Int) // modifier open is incompatible with data, because data means the class is final
private val I.pl:Unit get() = println(this)

//class J:H() // type is final cannot be inherited from
class J:H(3)

interface WithB{
    var b:Int
    fun p(){println("B:$b ")}
}

// overriding interface b
class J1(override var b: Int) : H2(),WithB{
    override fun p() { // must override p because both h2 and withb have the same name
    // because of the implicit Unit will return nothing
//        super<Printable>.p() // not an immediate supertype
        super<H2>.p() // can still be called but is not the returned value 99
        super<WithB>.p() // implicit return 2
    }
}
class J2:H3(),WithB { // overriding b as a member variable
    override var b: Int get() {
//        println("getting b:$b, which is now 12") // recursive property accessor, cannot get b like this
        return 12
    }
        set(value) {
            println("setting value:$value, to b which was $b")
            // b = value // recursive property accessor, because the set occurs at the end of the block, thus you
            // can see before(b) and after(value)
        }
    override fun p() {
        super<WithB>.p() // 12
        super<H3>.p() // 98
    }
}

fun main(args: Array<String>) {
    println(F().toString())
    println(G())
    H(1).p()
    H1(1).p()
    H2(null).p()
    H3(null).p()
    H2().p()
    H3().p()
    I(1,2,d=4).pl
    J().p()
    J1(2).p()
    println("creating j2")
    val j2 = J2()
    j2.p()
    j2.b = 13
    j2.p()
}