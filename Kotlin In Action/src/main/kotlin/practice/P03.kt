package practice

import java.util.*

// top level functions and properties

fun t():Boolean = true
val t:Boolean = true
val tt:Boolean get() = !t

enum class Robot(hp:Int){
    TANK(9), SCOUT(2), AIR(5);

    fun attack(roll:Double, defence:Double){}
}

class AB{
    val bb:Int = 3
    fun a(a:Boolean = true, b:Int = 3, c:Double = 3.9){}

    fun call(){
        a(true, 3) // last param not needed
        a(c = 54.9) // takes default values
    }

    fun fors(){
        for(i in 10 downTo 6){}
        for(i in 10 until 15){}
        val vv:Boolean? = try {
            val v = when (true) {
                true -> if (t) true else throw Exception("Not good")
                false -> false
                else -> false
            }
            return
        }catch (e:Exception) {null}
    }

    fun en(){
        val rob:Robot = Robot.TANK
        rob.attack(Math.random(), 5.2) // not the best example of enums fuc

        if(AB().bb is Number) // using the property and is example
            return
    }

}