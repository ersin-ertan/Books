package ch03

import arrow.core.*
import arrow.data.k
import arrow.instances.Function1FunctorInstance
import arrow.instances.OptionFunctorInstance
import ch01.p

/*
Examine type class, instances, and syntax
*/
fun usingLists() {

    val list = listOf(1, 3, 5)
//val list2 = object: Functor<List<Int>> {
//    override fun <A, B> Kind<List<Int>, A>.map(f: (A) -> B): Kind<List<Int>, B> {
//    } }

    val list2 = list.k()
    val list3 = list2.map { it * 2 }

    list.p()
    list2.p()
    list3.p()
    list3.list.p()

}

fun usingOs() {

    val o = 1.some()

//val ooo = object: Functor<ForOption>{
//    override fun <A, B> Kind<ForOption, A>.map(f: (A) -> B): Kind<ForOption, B> {
//    }
//}
//val ooo = object: Functor<Option<Int>>{
//    override fun <A, B> Kind<Option<Int>, A>.map(f: (A) -> B): Kind<Option<Int>, B> {
//    }
//}


// both of these are almost equal
    val oo = Option.functor()
    val ooo = object : OptionFunctorInstance {}



    (oo == ooo).p()

    "o is".p()
    o.p()
    o.map { it + 1 }.p()

}

// functor also has lift, converting a function of type A -> B to one that operates over a function that has type
// F<A> -> F<B>

fun usingLift() {

    val func = { i: Int -> i + 1 }

    val liftedFunc = Option.functor().lift(func)
    val liftedFunc1 = Option.functor().lift<Int, Int>(func)

    liftedFunc(1.some()).p()

    val toS = { i: Int -> i.toString() + '!' }
    val liftedToS = Option.functor().lift<Int, String>(toS)

    liftedToS(1.some()).p()
    liftedToS(1.some()).fix().getOrElse { "Didn't work" }.p()
}

fun functorSyntax() {
    val f1 = { i: Int -> i + 1 }
    val f2 = { i: Int -> i * 2 }
    val f3 = { i: Int -> i.toString() + '!' }
//    val f4 = f1.andThen(f2)
    // mapping over functions, function1 doesn't have map, but instead andThen
//    val f4 = object:Function1FunctorInstance<Int>{}.run { f1. }
    Function1.functor<Int>().run { } // I can't find the map method

    object : Function1FunctorInstance<Int> {}.run { }

    // TODO find out how to work with functor
//    fun doMath(start:Kind<Int,Int>, functor:Functor<Kind<Int,Int>>):Kind<Int, Int> = start.map

    //    doMath(Option(20)) // Some(22)
    fun doMath(int: Int) = int.plus(2)
    Option.functor().lift(::doMath)(Option(20)).p()

    fun <T> doMath1(o: Option<T>) = Option.functor().lift({ t: T -> t.toString() + '!' })(o)
    doMath1(1.some()).p()

//    fun <T, K<T>> doMath2(k:K) = K.functor().lift({t:T -> t.toString() + '!'})(k)
//    fun <T, K<T>> doMath2(k:K, f:Functor<K>) = f.lift({t:T -> t.toString() + '!'})(k)

    // an option is Option<out A> = OptionOf<A> = Kind<ForOption, A>
//    fun <A> arrow.core.OptionOf<A> .fix(): arrow.core.Option<A> { /* compiled code */ }

//    override fun <A, B> Kind<DataType, A>.map(f: (A) -> B): Kind<DataType, B> = fix().map(f)
//    fun <DataType, ContentType> Kind<DataType, ContentType>.fix(): DataType = ...

//    fun <DataType> functorForDataType() = object : Functor<DataType> {
//        override fun <A, B> Kind<DataType, A>.map(f: (A) -> B): Kind<DataType, B> = ...
//    }

//    fun <DataType, ContentType> doMath3(dataType: DataType, f: Functor<DataType>) {
//        f.lift<ContentType, String> { t: ContentType -> t.toString() + '!' }(object: Kind<DataType, ContentType>{})
//    }

//    doMath3<Option<Int>, Int>( 1.some(), functorForDataType<Kind<Option<Int>, Int>())

    // the idea is that your data type, be it an option or list may apply the map function to its contents
}

fun mainIdea() {
    // Wallet<CAD> -> Wallet<USD>
//    Wallet.functor().lift { cad: CAD -> USD(cad.amount * 2) }(Wallet<CAD>(CAD(1)))
}


fun main(args: Array<String>) {

//    usingLists()
//    usingOs()
//    usingLift()
    functorSyntax()
}



