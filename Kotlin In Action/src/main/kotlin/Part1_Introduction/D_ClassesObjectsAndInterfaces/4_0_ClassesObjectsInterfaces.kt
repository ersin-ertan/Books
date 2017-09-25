package Part1_Introduction.D_ClassesObjectsAndInterfaces

/*  - interfaces can contain property declarations, final and public by default
    - nested classes aren't inner by default: no implicit reference to outer class
    - ability to fully declare constructors with internal logic
    - properties redefinition of accessors
    - declaring class as data class allows the compiler to generate standard methods
    - delegate pattern supported natively
    - object keyword to declare a class and create an instance for singleton objects,
    companion objects, object expressions
*/

// sealed modifier restricts subclasses of a class

// interfaces in kotiln, methods with default implementation
interface Clickable {
    // single abstract method click
    fun click()

    fun doubleClick() = println("double clicked")
}

class Button:Clickable { // : replaces extends and implements keyword
    // manditory override modifier
    override fun click() = println("clicked")

    // may omit this since the default implementation is above
    override fun doubleClick() {
        println("button double click")
    }
}

interface Focusable {
    fun setFocus(b:Boolean) = println("Is ${if (!b) "not" else ""} focused")
    fun doubleClick() = println("double clicked a focus")
    fun doubleClick1() = println("double clicked a focus")
}

class B2:Clickable, Focusable {

    // override fun doubleClick() { } // which implementation is picked, neither
    // must implement own, or explicitly choose

    override fun doubleClick() = super<Focusable>.doubleClick()
    // equivalent to Clickable.super.doubleClick()

    // may be interesting to have an operator work with the two results
    override fun doubleClick1() {
        if (super<Focusable>.doubleClick().equals(super<Clickable>.doubleClick())) {
        } // get position of the focusable image, some how use the clickable object
        // position for something...
        else {
        }

    }

// *experiment* passing the class of the one you want
//    override fun doubleClick() = super<Focusable>.doubleClick()
//    fun <G:Clickable,Focusable> c(g:G) {
//        val cl:Class<Clickable> = g.javaClass
//        super<cl>.doubleClick()
//    }

    override fun click() {}
}