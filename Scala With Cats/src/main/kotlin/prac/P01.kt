package prac

import arrow.typeclasses.Monoid
import arrow.typeclasses.Semigroup
import ch01.Show
import p

// Typeclasses - extend without inheritance

interface Mech {
    val durability: Int
}

class Head(override val durability: Int = EYE()) : Mech {
    companion object DEFAULT
}

class Body(override val durability: Int) : Mech {
    companion object DEFAULT
}

class Arm(override val durability: Int) : Mech
class Leg(override val durability: Int) : Mech
object Energy : Mech {
    override val durability: Int
        get() = 0
}

fun Head.DEFAULT.EYE() = 5
fun Body.DEFAULT.BODY() = 10

interface MechBuilder<A> {
    fun build(): Mech
    fun build(upgradeMaterial: A): Mech
}

object MechBuilderInstances { // Type class (concrete) instances

    val headIntMaterialBuilder: MechBuilder<Int> = object : MechBuilder<Int> {
        override fun build(): Mech = Head(Head.EYE())
        override fun build(upgradeMaterial: Int): Mech = Head(Head.EYE() + upgradeMaterial)

    }

    // Body cannot be produced with Any material, ex. it must be use Metal - see below
    val bodyBuilder: MechBuilder<Metal> = object : MechBuilder<Metal> {
        override fun build(): Mech = Body(Body.BODY())
        override fun build(upgradeMaterial: Metal): Mech = Body(when (upgradeMaterial) {
            Metal.GOLD -> Body.BODY() * 20
            Metal.SILVER -> Body.BODY() * 8
            Metal.BRONZE -> Body.BODY() * 3
        })
    }
}

object MechInterfaceObject {
    fun <A> buildMech(upgradeMaterial: A, mb: MechBuilder<A>): Mech = mb.build(upgradeMaterial)
}

enum class Metal {
    GOLD, SILVER, BRONZE
}

fun buildMechHeadWithIntMaterialInstance() =
        MechInterfaceObject.buildMech(8, MechBuilderInstances.headIntMaterialBuilder)

fun buildMechBodyWithCustomMaterialBuilder(): Mech {

    val bodyMetalBuilder = object : MechBuilder<Metal> {
        override fun build(): Body = Body(Body.BODY())

        override fun build(upgradeMaterial: Metal): Body = Body(when (upgradeMaterial) {
            Metal.GOLD -> Body.BODY() * 20
            Metal.SILVER -> Body.BODY() * 8
            Metal.BRONZE -> Body.BODY() * 3
        })
    }

    return MechInterfaceObject.buildMech(Metal.GOLD, bodyMetalBuilder)
}


fun Head.build(upgradeMaterial: Int, mb: MechBuilder<Int> = MechBuilderInstances.headIntMaterialBuilder) = mb.build(upgradeMaterial + this.durability) as Head

fun giveMechBuildSyntax() {

    Head(6).build(3).p() // uses default head int builder instance
    Head(6).build(3, MechBuilderInstances.headIntMaterialBuilder).p()
}

fun showMechDurability() {
    val headShow = Show<Head> { "Head:durability:$durability" }

    val headInstance = MechInterfaceObject.buildMech(66, MechBuilderInstances.headIntMaterialBuilder) as Head
    headShow.run { headInstance.show().p() }
}

fun mechEquality() {
    val mechEq = arrow.typeclasses.Eq<Mech> { a, b -> (a.durability == b.durability) && a.javaClass.typeName == b.javaClass.typeName }
    val h1 = Head(1).build(0)
    val h2 = h1
    mechEq.run { h1.eqv(h2).p() }
    mechEq.run { h1.eqv(Body(1)).p() }
}

fun combiningMechs() {

    // Semigroup is the combine
    // Monoid extends Semigroup and has empty

    val headMonoid = object : Monoid<Head> {
        override fun Head.combine(b: Head): Head = this.build(b.durability)
        override fun empty(): Head = Head(0)
    }

    val energySemigroup = object : Semigroup<Energy> {
        override fun Energy.combine(b: Energy): Energy = Energy
    }

    // TODO("Use monoid and semigroup")
}

fun main(args: Array<String>) {

//    buildMechHeadWithIntMaterialInstance().p()

//    buildMechBodyWithCustomMaterialBuilder().p()

//    giveMechBuildSyntax()

//    showMechDurability()

//    mechEquality()

    combiningMechs()

}