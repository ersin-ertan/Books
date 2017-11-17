package Part2_EmbracingKotlin.I_Generics

// 9.3.0 Variance: generics and subtyping

// variance describes how types with the same base type and different type arguments relate to each other
// List<String> vs List<Any>, essential to understand when writing your own generic classes or functions, creating
// Apis that don't restrict users in inconvenient ways and don't break their type safety expectations


// 9.3.1 Why variance exists: passing an argument to a function
// a function that takes a List<Any> as an argument

fun printContents(list: List<Any>) {}

// strings work fine here, any is safe

fun addAnswer(list: MutableList<Any>) {
    list.add(42)
}

// but compile time error (book said runtime error), the idea is determining
fun runtimeError() {
    val strings = MutableList<Any>(0){}
    strings.add("a")
    // nor does calling addAnswer when the list values are known
    addAnswer(strings)
//    val newAns = strings.maxBy { it.length } // doesn't work

}

//