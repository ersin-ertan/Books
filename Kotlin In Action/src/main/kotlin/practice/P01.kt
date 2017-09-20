package practice

import java.time.Instant
import java.util.*

private class P01 {


    class DataClass(intData:Int = 1, var isBooleanData:Boolean?) {
        var string:String = "defaultString"
        val d:Double = 3.1 // exposed

        constructor(id:Int, double:Double = 3.3, st:String):this(2, true) {
            // need not include code block where code expression of this will do
            // but I want to set the string
            string = st + " set ${this.string} " + this.isBooleanData
            return Unit // may be omitted, equivalent to void
        }
    }

    fun dc() {
        DataClass(3, true)
        DataClass(1, null) // note the question mark to allow nulls
        val dc:DataClass = DataClass(3, 3.4, "String")
        dc.isBooleanData = true// exposed
        dc.d
        // but dc.intData is not
        // var or val on secondary constructor is not allowed

        val dc1 = DataClass(3, 4.4, "string")
        dc1.string
        dc1.d
    }

    // explicit return and implicit with expression bodies, no code blocks
    fun bool():Boolean = if (true) true else false

    fun b2() = if (3 > 1) true else false

    class DC(var name:String) {
        var countsOfGet = 0
        // custom accessors
        val greeting:String get() = this.name + ", welcome! The time is " + Date.from(Instant.now())
        val greeting2 get() = this.name + ", welcome! The time is " + Date.from(Instant.now())
        val greeting3:String
            get() /*=*/ { // note no equal sign
                val countsOfGet:Int = 0 // will always be 2
                return this.name + ", welcome! The time is " + Date.from(Instant.now()) +
                        " initial call #$countsOfGet and persistent call #${++this.countsOfGet}"
            }

        fun multiGet() {
            // notice how the countsOfGet in get local is always 0, then in persistent call it is
            // referring to the class instance
            for (i in 1..4) {
                println(greeting3); Thread.sleep(1000)
            }
        }

    }
}
