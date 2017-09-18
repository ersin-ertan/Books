package Part1_Introduction.B_KotlinBasics


fun max(a:Int, b:Int):Int{
    return if (a > b) a else b
}
// similar to ternary
// because if is an expression not a statement, where the expression has a value.
// statements are top level control structures

// functions can be writen from expression bodies to block bodies, wow
fun max1(a:Int, b:Int):Int = if(a > b) a else b

// omit the return type, type inference will evaluate the return type
fun max2(a:Int, b:Int) = if(a > b) a else b

// variables
val question = "what time is it?"
val answer = 34
val answer1:Int = 35
val floatingPoint = 4.5e3 // 4.5*10^3 = 4500 will be typed as a double
// if no initializer, type must be explicit if inside function local scope
// but cant do this in class scope
//val answer3:Int // property must be initialized
fun initializer(){
    val answer2:Int
    answer2 = 4
}

val immutableReference = "declaration corresponds with javas final"
var mutableRefernce = "declaration corresponds with nonfinal"

fun vals(){
    val m:String // only one initialization
    if(true) m = "true" else m = "false"
    // m = "hello" // val cannot be reassigned
}

fun mutateObjectPointingtoImmutable(){
    val myArrayList = arrayListOf("test")
    myArrayList.add("new")
}

fun variablesAreTypeFixed(){
    var t = "string"
    // t = 34 // the integer literal does not conform to the expected type string
}

fun stringTemplates(){
    val name = if (true) "bob" else "bobby"
    println("hey $name you can escape with \$name")
    class S{val name = "myName"} // interesting, need for new instance
    println("hey ${S().name}")

    println("logic within string template ${if (true) S().name else "text"}")
    // note the double quotes for text at the end
}