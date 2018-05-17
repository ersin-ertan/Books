package Ch05

import p

// Creating A DSL

// ex registry of bikes
/*xml
<bicycle desc="Fast carbon commuter">
  <bar material="ALUMINUM" type="FLAT">
  </bar>
  <frame material="CARBON">
    <wheel brake="DISK" material="ALUMINUM">
    </wheel>
    ...
</bicycle>
*/

// create a type safe builder

/*
val comuter = bicycle {
  description("Fast carbon commuter")
  bar {
    barype = FLAT
    materrial = ALUMINUM
  }
  frame {
    material = CARBON
    backWheel{
      material = ALUMINUM
      brake = DISK
    }
  } // ...
}*/

// regular kotlin code, compiled fast, idea will autocomplete, type check

interface Element {
  fun render(builder: StringBuilder, indent: String)
}

@DslMarker
annotation class ElementMarker

@ElementMarker
abstract class Part(private val name: String) : Element {
  private val children = arrayListOf<Element>()
  protected val attributes = hashMapOf<String, String>()

  protected fun <T : Element> initElement(element: T, init: T.() -> Unit): T {
    element.init()
    children.add(element)
    return element
  }

  override fun render(builder: StringBuilder, indent: String) {
    builder.append("$indent<$name${renderAttributes()}>\n")
    children.forEach { c -> c.render(builder, indent + "\t") }
    builder.append("$indent</$name>\n")
  }

  private fun renderAttributes(): String = buildString {
    attributes.forEach { attr, value -> append(" $attr=\"$value\"") }
  }

  override fun toString(): String = buildString { render(this, "") }
}

//  Part is the base class, has children and atributes properties, inheriting Element interface thus
// changing to json, yaml is easy. initELement receives two parameters, element T and init function with receiver T.() -> Unitt
// internally init is executed and the element is added as children

// Part is annotated wih @ElementMarker annotation, which is annotated with @Dslmarker, preventing inner elements from reaching
// outer elements
// using frame in bar is compile error, but using this@bicycle.frame{} allows
// use enums to describe materials, bar types, brakes
enum class Material { CARBON, STEEL, TITANIUM, ALUMINIUM }

enum class BarType { DROP, FLAT, TT, BULLHORN }

enum class Brake { RIM, DISK }


abstract class PartWithMaterial(name: String) : Part(name) {
  var material: Material
    get() = Material.valueOf(attributes["material"]!!)
    set(value) {
      attributes["material"] = value.name
    }
}

//use a material property of type material enum, stored inside the attributes map transforming the value back and forth

class Bicycle : Part("bicycle") {
  fun description(description: String) {
    attributes["description"] = description
  }

  fun frame(init: Frame.() -> Unit) = initElement(Frame(), init)
  fun fork(init: Fork.() -> Unit) = initElement(Fork(), init)
  fun bar(init: Bar.() -> Unit) = initElement(Bar(), init)
}

class Frame : PartWithMaterial("frame") {
  fun backWheel(init: Wheel.() -> Unit) = initElement(Wheel(), init)
}

class Wheel : PartWithMaterial("wheel") {
  var brake: Brake
    get() = Brake.valueOf(attributes["brake"]!!)
    set(value) {
      attributes["brake"] = value.name
    }
}

class Bar : PartWithMaterial("bar") {
  var barType: BarType
    get() = BarType.valueOf(attributes["bar"]!!)
    set(value) {
      attributes["bar"] = value.name
    }
}

class Fork : PartWithMaterial("fork") {
  fun frontWheel(init: Wheel.() -> Unit) = initElement(Wheel(), init)
}

fun bicycle(init: Bicycle.() -> Unit): Bicycle = Bicycle().apply(init)

fun main(args: Array<String>) {
  val bike = bicycle {
    description("Cool bike")
    bar {
      barType = BarType.BULLHORN
      material = Material.ALUMINIUM
    }
    fork {
      frontWheel {
        brake = Ch05.Brake.DISK
        material = Ch05.Material.CARBON
      }
    }
    frame {
      backWheel { brake = Ch05.Brake.RIM }
    }
  }

  bike.toString().p()
}

// dsls in kotlin with infix functions, operator overloading and type safe builders are powerful9