package Part1_Introduction.D_ClassesObjectsAndInterfaces

import java.awt.SystemColor.window
import java.awt.Window
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

// object expressions - anonymous inner classes rephrased
// object can be used to declare anonymous objects too, replacing javas anon inner class

fun ss(){
    Window(null).addMouseListener(
            object:MouseAdapter(){ // anonymous object extending MouseAdapter
                override fun mouseClicked(e: MouseEvent?) {
                    // do whatever, override MouseAdapter method
                    }
            }
    )

    // else if a named assignment is needed, use a variable
    val listener = object:MouseAdapter(){
        override fun mouseClicked(e: MouseEvent?) {}
    }
}

// an anonymous inner may implement multiple interfaces, or none
// an object acting as an anon inner is not a singleton, new instances are created on
// each object expression execution
// can modify value of variables from within an object expression

fun countClicks(window:Window){
    var clickCount = 0 // declare local variables
    window.addMouseListener(object:MouseAdapter(){
        override fun mouseClicked(e: MouseEvent?) {
            ++clickCount // update the variable's value
        }
    })
}

// object expressions mostly useful while overriding multiple methods in anon object
// else if you nee to implement single function interfaces, use kotlins SAM conversion
// which converts a function literal to an implementation of an interface with a single
// abstract function, writing your implementation as a function literal(lambda)