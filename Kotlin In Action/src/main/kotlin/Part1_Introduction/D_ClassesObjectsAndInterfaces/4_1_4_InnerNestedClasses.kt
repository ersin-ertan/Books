package Part1_Introduction.D_ClassesObjectsAndInterfaces

import java.io.Serializable

class A(){
    // good for helper class, or code localization
    class B(){
        // don't have access to outer class instance

    }
}

interface State: Serializable

interface View {
    fun getCurrentState():State
    fun restoreState(state:State) {}
}

class ButtonB:View {

    override fun restoreState(state:State){}
    override fun getCurrentState():State = ButtonState()

    class ButtonState:State {} // same as static nested class in java, where the
    // implicit reference from that class to its enclosing class is removed

    inner class ButtonStateInner:State { // now has a reference to ButtonB
        fun getOuterRef():ButtonB = this@ButtonB
    }
}

// nesting may be useful when creating hierarchy containing limited number of classes

// Sealed Classes - defining restricted class hierarchies
// *re-visit book and actual implementation are different*

// a default option must be present when evaluating expressions using 'when', else
// throw an exception. Adding a subclass is not detected by the compiler, thus it is
// easy to forget to add branching logic.

open class Super0
class Child0:Super0()
class Child00:Super0() // adding this prone to logical errors

sealed class Super() { // restricts ability to create external direct subclasses, like final private
// sealed is open by default
    class Child1:Super() // must be nested, or within the same file
    class Child2:Super()
//    class Child3:Super() // adding this will be a compiler error with the when
}

fun sealedWhen(s:Super, s0:Super0){
    val a = when(s){
        is Super.Child1 -> 1
        is Super.Child2 -> 2
        // no else required
    }

    val notSealed = when(s0){
        is Child0 -> 1
        else -> 2 // else required
    }
}