package practice

import Part1_Introduction.C_DefiningAndCallingFunctions.joinToString
import java.time.Instant
import java.util.*
import kotlin.collections.HashMap

const val topLevelProperty = "HELLO"

private class P07 {

    enum class RoboModel(val mark:String) {
        OPTRON("OPT-345-A"), EMPRON("EMP-199-A"), GLADIOR("GLD-366-S");

        fun getClassSet():Set<Char> {
            val classSet = HashSet<Char>()
            RoboModel.values().forEach { classSet.add(it.mark.last()) }
            return classSet
            // what I wanted to do was check to see if S is in the set, and the set is larger than 1, then move S to the first position
            // also override HashSet toString
        }
    }

    open interface Classifiable {
        fun getClassification():Char = 'X' // default classification value
        val date:String
            get() {
                return Date.from(Instant.now()).toString()
            }
    }

    interface Classifiable1:Classifiable {
        override fun getClassification():Char {
            return 'Y'
        }
    }

    class IntList(var num:List<Int>):List<Int> by num { // only interfaces can be delegated to
        override fun toString():String {
            // calculated on each call
            return this.num.joinToString("_", "Your list: ")
        }

        fun doS() {}
    }


    open class Robo(val rm:RoboModel):Classifiable { // open for extension
        val S:Char = 'S' // const only allowed for top level properties
        lateinit var il:IntList
        fun getil():IntList {
            return il
        }

        var i:Int = 0


        constructor(i:Int?):this(RoboModel.OPTRON) {// val and var on secondary constructor is not allowed
            // remember that this is the secondary constructor, thus the primary constructor must be 'inherited' from this()
            this.i = checkI(i)
        }

        fun setup() {
            il = IntList(listOf(1, 4, 5))
        }

        private fun checkI(i:Int?):Int = when {
            i == null -> 0
            i > 0 -> 1
            i < 0 -> when (i) { // both whens for with and without pram
                is Int -> -10 // checks type
                else -> -1
            }
            else -> {
                println("i is already 0")
                0 // returns 0
            }
        }

        override fun getClassification():Char = rm.mark.last()

        object DieRoll {
            fun roll(min:Int = 1, max:Int = 6):Int = (min..max).rand()
            fun ClosedRange<Int>.rand():Int = Random().nextInt(endInclusive - start + 1) + start
            // want the 6 to be included too
        }
    }

// Can the default value of a named variable of a function be accessed from a function extension
//fun Robo.DieRoll.cheatRoll():Int = roll(min = max -1)

    fun Robo.DieRoll.cheatRoll():Int = roll(min = 5)

    fun String.p() {
        println(this)
    }

    fun main(args:Array<String>) {
        val r = Robo(RoboModel.OPTRON)
//    println(r.getClassification())
//    println(r.getClassification())
//    println(r.rm.getClassSet()) // accessing the property
//    (1..10).forEach { println(Robo.DieRoll.roll(max = 7)) }
//    (1..10).forEach { println(Robo.DieRoll.cheatRoll()) } // extension function used
//    println(IntList((1..10).toList()).toString())
//    println(r.date)
//    Thread.sleep(1000)
//    println(r.date)
//    r.setup()
//    val list:List<Int> = r.il // lateinit property il has not been initialized
//    list.doS() // unresolved reference
//    (list as IntList).doS()
//    list.doS()
//    println(IntList((0 until 10).toList())) // excluding the 10
        var hm = HashMap<Int, Char>()
        var count:Int = 0
        ('a' until 'j' step 3).forEach { hm[count++] = it }
        println(hm.toString())
        for ((key, value) in hm) {
            println("$key+$value=${key + (value - 'a')}")
        }
        for ((index, elem) in listOf(1, 3, 8, 0).withIndex()) println("$index and $elem")
        hm = hashMapOf(1 to 'a', 2.to('b'), Pair(3, 'c')) // infix and function notation
        "String".substringAfter('t') + """${'$'}this is a dollarsign
                                               |-
                                               |--
                                               |----
                                               |-------
                                               |---------------""".trimMargin().p()
    }

    fun funn() {
        val X:Char = '*'
        val O:Char = 'O'
        var xPos = 0
        val min = 1
        val max = 50

        while (true) {
            (1..max).forEach { it ->
                if (it == xPos)
                    print(X)
                else if (it == max) {
                    println();Thread.sleep(20)
                    if (++xPos == max) xPos = 0
                } else print(O)
            }
        }
    }
}