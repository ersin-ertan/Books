package practice

private class P08 {
    fun sum() = { x:Int, y:Int -> x + y }
    val sum = { x:Int, y:Int -> x + y }
    fun max() = { list:List<Int> -> list.max() }

    fun Int.p() = println(this)

    fun main(args:Array<String>) {

//    sumWhyd(1, 3, sum()).p()
//    sumWhyd(1, 3, sum).p()
//    sumWhyd(1, 3, { x:Int, y:Int -> y + x + x }).p()
//    sumWhyd(1, 3) { x:Int, y:Int -> y + y + x + x }.p()

//    val l = listOf<Int>(1, 3, 5)

        runLambda()
    }

    fun sumWhyd(i:Int, y:Int, function:(Int, Int) -> Int):Int = Math.pow(function(i, y).toDouble(), y.toDouble()).toInt()

    class AA(val a:Int)

    val aa = AA(1)
    val bb = AA(2)

    val ga = AA::a
    val age = { aa:AA -> aa.a }

    val aFun = aa::a
    val bFun = AA::a

    fun runLambda() {
//    println(run(::ga))
//    println(run(::age))
//
//    println(ga(aa))
//    println(age(aa))
//
//    println(aFun)
//    println(bFun)
//    println(aFun.get())
//    println(bFun.get(AA(3)))
//    println(bb::a.getDelegate())
//    println(bb::a.get())

//    listOf<Int>(1, 2, 3, 4, 5, 6).filter { it > 3 }.map { it.toString() }.forEach { it.p() }
//    (1..1000).asSequence().filter { it > 950 }.map { it.toString() }.forEach { it.p() }
        // remember Int.toString will provide '1', not one, unless you map them
        println((1..1000).asSequence().filter { it > 950 }.map { it.toString() }.all { it.endsWith('1') })
        println((1..1000).asSequence().filter { it > 950 }.map { it.toString() }.any { it.endsWith('1') })
        println((1..1000).asSequence().filter { it > 950 }.map { it.toString() }.find { it.endsWith('1') })

        val listOfStringNums = (1..1000).asSequence().filter { it > 950 }.map {
            it.toString().toCharArray()
                    .map { mapToWord(it) }
                    .joinToString("-")
        }.toList()

        listOfStringNums.take(10).groupBy { it.substringAfter("nine-").substringBefore("-") }.forEach { println(it) }

        println("count of ends with e: " + listOfStringNums.count { it.endsWith('e') })

    }

    fun mapToWord(i:Char):String = when (i) {
        '0' -> "zero"
        '1' -> "one"
        '2' -> "two"
        '3' -> "three"
        '4' -> "four"
        '5' -> "five"
        '6' -> "six"
        '7' -> "seven"
        '8' -> "eight"
        '9' -> "nine"
        else -> " "
    }

}