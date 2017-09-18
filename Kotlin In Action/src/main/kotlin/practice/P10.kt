package practice

enum class Col(val r:Int, var g:Int, val b:Int, a:Boolean) {
    RED(255, 0, 0, true), // these types are a specific combination of the inputs
    BLUE(0, 0, 255, true),
    GREEN(0, 255, 0, false);

    init {
        println("when do I get called?")
    }

    fun averageColours():Int { // this function is like a static function, a utility to the enum Col class types
//        g = 3 // can if var
//        r = 3 // cant
        return r + g + b / 3
    }
}

fun main(args:Array<String>) {
    Col.BLUE // on first reference, it get called three times, one per entry
    Col.RED // but not on additional references, lazy
//    colour.b // you can see the preset values
//    colour.averageColours() // you can operate on the pre set values too

    val a = Animal.BEAR
    val i:Int = when {
//        is Animal ->
        a.equals(Animal.BEAR) -> 1
        a.equals(Animal.TURTLE) -> 2
        a.equals(Animal.HAWK) -> 3
        else -> 0
    }

    println((1..10).step(2).toList()) // will be a range of 1..9, because 10 is not included with the step
    println((1..10).step(1).toList())
    ('a'..'z')
}

fun typing() {

    open class A(val i:Int)
    abstract class B(d:Double):A(3) {
        abstract fun doBfun():Double
    }

    // anonymous inner
    val clas = object:B(7.0) {
        override fun doBfun():Double {
            return 3.3
        }

        fun doAnonfun() {}
    }

    val cla = A(4)

    if (clas is A) clas.doBfun()
    else if (clas is B) clas.doBfun()
    else clas.doAnonfun()

//    if (cla is A) cla.doBfun() // cant do be
    if (cla is B) cla.doBfun() // smart cast to b

    // like when and sealed classes, enums need not use else if using the variable directly
    val aClass = A(0)
    when (aClass) {
        is A -> 1
        !is A -> 2 // not showcase, no need for else
    }

}

enum class Animal(val atk:Int, var def:Int, var hp:Int) {
    BEAR(10, 6, 14),
    TURTLE(4, 10, 10),
    HAWK(7, 3, 5);

    fun rest(hr:Int) {
        val modifier = hr / 2
        def += 3 * modifier
        hp += 5 * modifier
    }
}

