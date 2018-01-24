package Part2_EmbracingKotlin.J_AnnotationsReflection

import org.junit.Rule

// 10.0.0 Annotations and Reflection

@Deprecated("use newMethod() instead", ReplaceWith("newMeth()"))
fun oldMeth() {
}

fun useOldMeth() {
    oldMeth() // shows newMeth()
}

// annotations can only have parameters of type: primitive, strings, enums, class refrences, other annotation classes
// and arrays (of annotated classes)??

//@NewAnnotation(NewClass::class) // class as an annotation argument


//@RequestMapping(path = arrayOf("/foo", "/bar")) // array as an arrgument

// annotation arguments need to be known at compile time,
// to use a property as an annotation argument you must mark it as const, so it is a compile time constant

// 10.1.2 Annotation targets

// Single Kotlin declarations often correspond to multiple Java implementations
// To specify the element to be annotated use a "use-site target" with annotation name

// @get:Rule

@get:Rule // Example of JUnit annotation, will only annotate the getter and not the property
val folder = TempFolder()

fun TempFolder() {}

// full list of supported use-site targets are: property, field, get, set, receiver, param, setparam, delegate, file
// ** annotations with file must be placed at the tope level of the file, before package directive

// you can apply annotations to arbitrary expressions, not only class and method declarations or types

fun casting(list: List<*>) {
    @Suppress("UNCHECKED_CAST")
    val strings = list as List<String>
}

// some annotations control how the declarations in kotlin are complied in Java bytecode/Java callers
// like keywords. Others change kotlins declarations visibility to Java callers like: JvmName, JvmStatic
// JvmOverloads, JvmField

// 10.1.3 Annotations to customize JSON serialization
// see http://github.com/yole/jkid

// TODO - Chapter 10.3 Is quite advanced and will be continued later