package practice

import java.time.Instant
import java.util.*
import kotlin.collections.ArrayList

enum class EC(var inter:Int?){
    EC1(1), EC2(null);
    fun which(){
        EC1.inter
    }
}

class D(var a:Int = 1, val b:Int = 2, var c:Int?, vararg val d:Int)

//val D.stateException:String = "null arg" // can't do this
val D.stateException:String get() = Date.from(Instant.now()).toString() + " null"
//const var se:String = "test" // cant const on vars
const val se:String = "test" // public static final

fun D.printNonNulls(){

   val nn:String = """V
                       -A
                       -L
                       -I
                       -D
                       -!""".trimMargin("-").substringBeforeLast('!')// .trimEnd()

    // if you remove substring, print(nn) will be on the same line, else
    // its on the line below, does substring have a \n within it, yes, if you
    // trimEnd(), then it goes back to single line with "Dvalue"

    fun printNonNull(any:Any?){
        try{
            checkNotNull(any)
            if(any is IntArray) for (i in any.withIndex()) println(i)
            else{
                var new = nn
                when(nn.startsWith("V")){
                    true -> new = nn.replace("V", "\\/", false)
                    else -> "unused"
                }
                print(new)
                println(any.toString())}
        }catch (e:IllegalStateException){ println(stateException)}
    }

    printNonNull(a)
    val pair:Pair<Int, String> = b.to("B")
    printNonNull(pair)
    printNonNull(c)
    printNonNull(d)
}

fun start(){
    val al:IntArray = IntArray(2)
    al[0] = 3
    al[1] = 4
    D(c=null, d = *al).printNonNulls()
}