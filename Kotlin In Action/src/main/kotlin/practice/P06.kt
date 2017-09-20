package practice

class P6_A
class P6_B()
class P6_C(val v:Int)
class P6_D constructor(val v:Int)

sealed class P6_S(val v:Int) // open by default
open class P6_SC:P6_S(4) // hard value set
class P6_SCC:P6_SC() // hard value set
class P6_SC1(e:Int):P6_S(e) // flexible

private class P06 {

    val v = P6_SC() // no params for v, already set
    val v1 = P6_SC1(2) // e is populating parents v

    infix fun P6_SC1.print(s:String) = "$v $s" // must have one input

    infix fun String.upper(s:String) = "$this ${s.toUpperCase()}"

    fun main(args:Array<String>) {
        println(v1.v)
        println(v1 print "hey" upper "yo")

        println(v.v) // should be 4
        val p6 = P6_SC1(/*needs e*/ 6)
        println(p6.v) // e is v
        // an extended class that defines an var to populate the parents constructor
        // will have a name that is only class scope, but just populates the parents value

        // -----

//    NoExternalAccess(3) cannot create
//    NoExternalAccess.create how do we call this?
        //NoExternalAccess.HasAccess() // constructor of inner class HasAccess can be called
        // only with receiver of containing class
        NoExternalAccess.NoAccess()
    }


    class NoExternalAccess private constructor(i:Int) {
        fun create() = NoExternalAccess(2)

        inner class HasAccess { // but how is the parent created to give it access in the first place
            fun callCreate() = this@NoExternalAccess.create()
        }

        class NoAccess {
//        fun callCreate() = this // cannot ref NoExternalAccess
        }
    }

    // alternative private
    class NoExternal {
        private constructor()
    }

// companion objects can access the private constructor
}