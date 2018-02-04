package practise

import A_functionalDomainModeling.p
import arrow.core.*
import arrow.data.Try
import arrow.syntax.either.left
import arrow.syntax.either.right

sealed class SC(val a: Int, val b: String) {

    class SC1(a: Int, b: String, val c: Boolean) : SC(a, b) {
        override fun toString() = "SC1(a:$a,b:$b)"
    }

    class SC2(a: Int, b: String, val c: Boolean) : SC(a, b) {
        override fun toString() = "SC1(a:$a,b:$b)"
    }
}

const val maxPartsDefault = 5

sealed class Robot(val hp: Int, val maxParts: Int = maxPartsD) {

    companion object {
        const val maxPartsD = 6
    }

    class T1 : Robot(3) {}
    class T2 : Robot(4, Robot.maxPartsD) {}
}

interface RobotUpgrade{

    fun upgrade(r:Robot):Robot
}


fun main(args: Array<String>) {

    1.right().map { it.p() }


    fun sc(b: Boolean): SC = if (b) SC.SC1(1, "a", true) else SC.SC2(1, "a", true)

    fun scO(b: Boolean): Option<SC> = if (b) Some(sc(b)) else None

    fun scT(b: Boolean): Try<SC> = Try { if (b) sc(b) else throw Exception("Hi") }

    // cast is just to enforce using two different types for the either
    fun scE(b: Boolean): Either<SC.SC1, SC> = if (b) (sc(b) as SC.SC1).left() else sc(b).right()


    scO(true).fold({ "empty".p() }, { it.p() })
    scO(false).fold({ "empty".p() }, { it.p() })
    "".p()
    with(scO(false)) {
        when (this) {
            is Some -> {
                "some".p()
                this.p()
                this.t.p()
                this.map { it.p() } // shouldn't run because of None value
                this.getOrElse { 1.p() }
                this.orNull()?.p()
            }
            None -> {
                "None".p()
                this.p()
                this.map { it.p() }
                this.getOrElse { 1.p() }
                this.orNull()?.p()
            }
        }
    }
    "".p()
    with(scO(true)) {
        when (this) {
            is Some -> {
                "some".p()
                this.p()
                this.t.p()
                this.map { it.p() }
                this.getOrElse { 1.p() }
                this.orNull()?.p()
            }
            None -> { // its a singleton, thus no type checking is required
                "None".p()
                this.p()
                this.map { it.p() }
                this.getOrElse { 1.p() }
                this.orNull()?.p()
            }
        }
    }
    "".p()
    scO(true).map { it.p() }
    scO(true).flatMap { Option(Pair(it, it)) }.p()

    "".p()

    scT(false).p()
    scT(true).isFailure().p()
    scT(false).fold({ ("Failed".p()) }, { "success".p() })
    scT(true).fold({ ("failed".p()) }, { "Success".p() })

    //    fun <S : SC> add(sc: SC): S = SC(sc.a + 1, "b", true) // problem is that sealed classes cant be constructed
//    fun <S : SC> add(sc: SC): S = SC.SC1(sc.a + 1, "b", true) // we can't use copy because the type constructor is still unknown


}