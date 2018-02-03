package practice

private class P02 {


    fun a() {
        val a = try {
            val aa = 1
            aa // auto returns aa
        } catch (e:Exception) {
            2
        } finally {
        }

        for (i in 1..10) println(i)
        for (i in 1..10 step 3) println(i)
        for (i in 1..10) if (i > 10) {
        } else {
        }

        val n:Int = 3
        if (n is Number) n.compareTo(5)

        val b = when (a) {
            1 -> "1"
            2 -> "2"
            else -> "3"
        }

        val c = if (true) 1 else 2
    }

    fun b(void:Unit):Unit {
        return void
    }

    fun c(void:Unit):Unit {
        return Unit
    }

    fun d(void:Unit):Unit {
        return
    }

    fun e(void:Unit):Unit {
    }

    val a:Int = 3
    var b:String = Integer.toString(a)
    fun f() {
        b = "another + $a"
    }

    interface Interface

    class A:Interface

    class C(val a:Number = 4, var b:Int? = null) {
        fun smartCastToInt() {
            if (a is Int) a.and(3)
        }

        fun equate() = 3
        fun a() {
            val a = C(2)
            val b = a.a
            val c = a.b
        }

        fun b() {
            for (i in 1..10 step 2) println(i)
            var size = 10
            for (i in 0 until size) println(--size)

            for (i in 100 downTo 40 step 3) println(i)
            val sise = 10
            for (i in 0 until sise step 1) println(i)

        }
    }

    enum class Enum(i:Int) {
        A(1), B(2), C(3), D(4);

        fun stringify(e:Enum) = e.toString() + A.toString()
    }

    fun d() {
        var enum:Enum = Enum.B
        enum.stringify(enum)

        val i = when (enum) {
            Enum.A -> 1
            Enum.B, Enum.C -> 2
            else -> 3
        }
    }

    fun e() {
        var b:Int = 0
        val a = try {
            ++b
        } catch (e:Exception) {
            b * 10
        } finally {
            b += 2
        }

        if (b != 0) throw IllegalStateException("bs not 0, you're full of poop")

        class A {
            var variable = 1
            val c:Double get() = Math.random() + variable
            // will lazy calculate value on first call, and using property
        }
    }
}
