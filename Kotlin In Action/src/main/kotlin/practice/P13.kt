package practice

interface MyInterface {
    fun func()
}

fun getDelegate():MyInterface {
    return object:MyInterface {
        override fun func() {

        }

    }
}

private class P13 private constructor(delegate:MyInterface):MyInterface by object:MyInterface {
    override fun func() {
    }

    val delegate = delegate
} {

//    (val int:Int):this(){ // if the constructor keyword is usually implicit in the class declaration,
//     why wouldn't it be implicit for secondary constructors
//
//    }
//
//    (var b:Boolean):this(){ }

//    construtor(val d:MyInterface):this(d))
//    {
//
//    }

    private constructor (int:Int):this(true)
    constructor (b:Boolean):this(getDelegate()){
    }



    val a = 1
    var b = 1
    val c:Int? = 1
    val d = if (true) true else false
    val e = try {
        1
    } catch (e:Exception) {
        2
    } finally {
        println("assigned")
    }
    var f = when (a) {
        1 -> 1
        else -> 2
    }
    private val g = listOf(1, 3, 4, 5)
    val h = 1 in g


    fun a() {}
    fun b() = 1
    fun c(int:Int? = 1) {}

    class Another

    inner class Inner {
        val myA = this@P13.a
    }

    companion object { // anon inner, can have name, but will default to Companion

        fun likeStaticMethod(int1:Int) = int1

        fun createP13() = P13(1)
    }


}

fun main(args:Array<String>) {
    P13.likeStaticMethod(1)
//    P13() // private
    P13.createP13()
    P13.Companion.createP13()
}