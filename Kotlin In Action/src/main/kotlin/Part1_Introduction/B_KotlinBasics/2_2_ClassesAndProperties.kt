package Part1_Introduction.B_KotlinBasics


// instead of imports use package, but no need if within package scope
import java.io.File.listRoots // importing a method
import java.io.BufferedReader // or a class
import java.math.*// for all classes and top level functions within them


fun importMethodAndClass(){
    listRoots()
    val br:BufferedReader = BufferedReader(null)
    BigInteger("3")
    }

class Person(val name:String) // value objects, set is when you create via constructor

fun usingPerson(){
    val p:Person = Person("has constructor")
    p.name // has getter, public by default
    // p.name = "can't chane vals"
}

// properties
class P2(var name:String, var someVar:Boolean = false){ // default false
    fun flipBoolean(inp:Boolean) = !inp
}

fun usingVar(){
    val p:P2 = P2("first")
    p.name = "mutable"
    p.name = "variable"
    p.flipBoolean(p.someVar)
}

class P3(var isTrue:Boolean)
fun isPrefixForBooleans(){
    println(P3(false).isTrue)
    // calling from java, no need to call getIsTrue, just call isTrue, but still
    // you must call setIsTrue for setting
}

// custom accessors
class Rect(val height:Int, val width:Int){
    val isSquare:Boolean get(){ return height == width} // property declaration
    //or
    val isSquar:Boolean get() = height == width
    // is computed on each access
    // if you describe the characteristic (the property) of a class,
    // you should declare it as a property
}

fun usingClassesFromAFile(){
    geometry.A(1) // multiple classes in this file, file name is irrelevant
    geometry.B(2) // but to keep standard java conventions create a shapes package
    // then put the shapes within a single file if needed
    // to get geometry.shapes.A
    geometry.C(3)

}
