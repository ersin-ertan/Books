package Part1_Introduction.C_DefiningAndCallingFunctions

// defining and calling functions

class A{
    val set = setOf(1,3,5) // java.util.HashSet
    val list = listOf(1,3,5) // java.util.ArrayList
    val map = mapOf(1 to "one", 7 to "seven") // java.util.HashMap
    // to is a normal function

    fun javaClass() = println(set.javaClass) // equivalent of getClass()

    var last = set.last()
    var first = set.first()
    var max = set.max()

    fun print()=println(list) // [1,3,5] java invokes toString()
    // but we need (1;3;5), thus
    fun <T> joinToString(col:Collection<T>, separator:String, prefix:String, postfix:String):String{

        val result = StringBuilder(prefix)

        for ((index, element) in col.withIndex()) {
            if(index > 0) result.append(separator)
            result.append(element)
        }
        result.append(postfix)
        return result.toString()
    } // how can you change the declaration to make the function call less verbose

    fun customPrint()=joinToString(list, ";", "(", ")" )

    // named arguments
    fun named()=joinToString(list, separator = ";", prefix = "(", postfix = ")" )

    // default prameter values - overabundance of overloaded methods

    fun <T> joinToString1(col:Collection<T>, separator:String = ", ", prefix:String = "", postfix:String = ""):String{
        return joinToString(col, separator, prefix, postfix)
    }

    // now on call you can omit some
    fun call(){
        joinToString1(list, "--") // limited to omitting the trailing args
        // thus named arguments
        joinToString1(list, postfix = "!") // and the default values are used
    }
    // look at @JvmOverloads to auto generate overloads because you have te specify all param values from java

    // getting rid of static utility classes: top level functions and properties
    // kotlin doesn't use util class but puts functions at the top level of source file
    // outside of any class, still members of the package, but without extra nesting

    // @see TopLevelFunction.kt
    // @see TopLevelProperties.kt


}