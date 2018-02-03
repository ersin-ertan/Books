package practice

import p

fun main(args: Array<String>) {

    data class DC(val int: Int)

    val v = DC(1).copy(3)
    v.p()

    val p = Pair<DC, DC>(v, v)
    Triple(p.first, p.second, v).copy(third = v.copy(v.int * 4)).p()

    for (n in 1..4) println(n)

    "ass".p()
    IntRange(1, 3).associate { it to it * 10 }.p() // gives range values and returns pair
    "assBy".p()
    IntRange(1, 3).associateBy { it * 10 }.p() // gives range values and returns keys

    val m = mutableMapOf(1 to 2, 3 to 4, 5 to 6)
    "assTo".p()
    IntRange(7, 10).associateTo(m, { a -> a to a * 10 }).p() // appends to map, returns pair


    val mm = mutableMapOf(1 to 2, 3 to 4, 5 to 6)
    "assToBy".p()
    IntRange(7, 10).associateByTo(mm, { a -> a * 10 }).p() // appends to map, returns keys for ranges values

    val mmm = mutableMapOf(1 to 2, 3 to 4, 5 to 6)
    "assToBy with key selector".p()
    IntRange(7, 10).associateByTo(mmm, { a -> a * 10 }, { b -> b * 100 }).p() // appends to map, returns keys, and ranges values transformation for key

    "chunked".p()
    IntRange(0, 9).chunked(4).p()
    IntRange(0, 9).chunked(2).p()
    IntRange(0, 9).chunked(5, { a -> a.plus(100) }).p() // transformed list is return

    for (n in 0 until 10) println(n) // 0-9

    "and".p()
    (1 and 0).p()
    (1 and 1).p()


    data class T(val a: Int, val b: Int)

    infix fun T.copyTest(i: Int) = this.copy(b = i)

    (T(1, 2) copyTest 4).p()

}
