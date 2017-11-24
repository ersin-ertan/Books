package Part2_EmbracingKotlin.J_AnnotationsReflection

// 10.0.0 Annotations and Reflection

@Deprecated("use newMethod() instead", ReplaceWith("newMeth()"))
fun oldMeth() {
}

fun useOldMeth(){
    oldMeth() // shows newMeth()
}

// annotations can only have parameters of type: primitive, strings, enums, class refrences, other annotation classes
// and arrays (of annotated classes)??

//@NewAnnotation(NewClass::class) // class as an annotation argument


//@RequestMapping(path = arrayOf("/foo", "/bar")) // array as an arrgument

// annotation arguments need to be known at compile time,
// to use a property as an annotation argument you must mark it as const, so it is a compile time constant

// 10.1.2 Annotation targets