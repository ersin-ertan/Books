package Part1_Introduction.D_ClassesObjectsAndInterfaces

// fragile base class is when modifications of the base class can cause incorrect behaviour
// in subclasses, Effective java says - design and document for inheritance else prohibit
// all classes and methods not intended to be overridden need to be final

// kotlin has final by default, lest you mark them as open

open class RichButton:Clickable, Focusable {// open class, to inherit fcrom

    fun disable() {} // final can't override

    open fun animate() {} // open may override

    override fun click() {} // overrides an open function and is open
    // open by default because from base class/interface, but if you want
    // to close it, set it to final

    final override fun doubleClick() {}

    class AnotherButton:RichButton() {
        override fun click() = super.click()

        // override fun doubleClick() {} // is marked final and cannot be overridden
    }
}

// a benefit of final classes is smart cast in more scenarios, since they are only
// used with a class property that is a val, without a custom accessor, else
// the subclass clashes with the defined property

abstract class Animated { // class is abstract, no instances

    abstract fun animate() // function is abstract, must be overridden, open by default

    open fun stopAnimating() {} // non abstract funcs arne't open by default

    fun animateTwice() {}
}

/** final       - can't be overridden, default for class members
 *  open        - can be overridden, should be specified explicitly
 *  abstract    - must be overridden, only in abstract classes, abstract members have no implementation
 *  override    - overrides a member in a superclass, open by default, if not final
 */

// same visibility modifiers from java, but public by default, java is package-private
//// which doesn't exist in kotlin, packages only organize code in namespaces, not
// visibility.

// but you can use internal, which is visible throughout a module, which is a set of
// files compile together, this is real encapsulation for implementation details
// which was defeated in javas external class definitions in same packages of your
// code thus accessing your package local declarations

// because you can define functions outside of a class, you may mark them as private
// which limits their scope to within the containing file, or class private for
// within the class

/*
public (default)- visible everywhere
internal        - visible in a module
protected       - visible in subclases
private         - visible in a class, visible in a file
 */

internal open class TButton:Focusable {
    private fun yell() = println("hey")
    protected fun whisper() = println("yo")
}

//fun TButton.speech(){ // error public member exposes its internal receiver type TButton
//    yell() // error cannot access yell it is protected in TButton
//    whisper() // error cannot access whisper it is private in TButton
//}

// outer classes don't see private members of its inner class in kotlin