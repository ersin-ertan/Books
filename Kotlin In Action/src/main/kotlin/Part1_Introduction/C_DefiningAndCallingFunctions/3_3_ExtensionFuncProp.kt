package Part1_Introduction.C_DefiningAndCallingFunctions

// Extension function can be called as a member of a class but is defined outside of it.

fun String.lastChar():Char = this.get(this.length - 1)
// class name goes before function name

// String is the receiver type, this.get and this.length is the receiver object
// this are implicit and can be omitted

fun String.lastChar1():Char = get(length - 1)

// don't have access to private or protected members of the class

// import filename.functionname or for function extensions, you can use short name
// using as keyword
// import filename.functionname as fn
// "hi".fn()

fun test() {
    val c = "kotlin".lastChar()
    val cc = "kotlin".lastChar1()
    // call from java with char c = StringUtilKt.lastChar("java");
}

fun <T> Collection<T>.joinToString(sep:String = ", ", pre:String = "", post:String = ""):String {
    val result = StringBuilder(pre)

    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(sep)
        result.append(element)
    }
    result.append(post)
    return result.toString()
}

val list = arrayListOf(2, 5, 6)
fun pr() {
    println(list.joinToString("--")) // extended from collections
}

// if specifically typed Collection<String>.join
// can't use it with non strings Error: Type mismatch:

// static nature of extensions means it cant be overridden in subclasses

// no overriding for extension functions
// overriding view allows for the button to be clicked, but it doesn't work for ext
//  functions because they are declared outside of the class because the function
// that's called depends on the static type of the variable being declared not of the
// runtime type stored in the variable

// extending a class and adding a new func will override any extension functions
// from the parent class that a client programmer might have implemented(if same name)

// Extension properties
val String.lastChar:Char get() = get(length - 1) // no state, getter must always be defined

// because there is no backing field, nowhere to store the value, same for initializers
fun t() {
    println("test".lastChar)
}