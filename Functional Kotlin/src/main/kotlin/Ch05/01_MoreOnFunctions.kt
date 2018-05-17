package Ch05

import p

// 5 More on Functions- extension functions, operator overloading, type safe builders, inline functions
// recursion and corecursion

fun <T> varargdLambdas(vararg t: (T) -> Unit) {
  t.forEach { it.p() }
}

fun namedParamsHigherOrderFunctions(f: (int: Int, s: String) -> Unit) = f(1, "s")

fun main(args: Array<String>) {
//  varargdLambdas<Int>({ 1 }, { i: Int -> i + i }, { i -> i }, {})
//  namedParamsHigherOrderFunctions { int, s -> 1 }
//  namedParamsHigherOrderFunctions({ i, s -> Unit })
}

// instance of a class with extension functions declared is called a Dispatch receiver
class I

fun I.d() = Unit
open class K {
  open fun I.d() = Unit
  open fun test() = Unit // may be re implemented via override
  final fun cant() {}
}

class M : K() {

  //  fun K.test() = 11 // shadowed thus will never be used
//  fun test() = 11 // hides member of supertype
  override fun test() = Unit

  //  override fun cant(){} // cant in K is final and cant be overriden
//  fun cant(){} // cant hides a member of supertype K
  // this keyword in an extension function means the instance of the receiver type
  override fun I.d() {
    this is I // always true
    this@M is M // always true
  }
}

// extension functions can override private, and take precedence but will not if private is changed to public
// can extend existing public, but will be shadowed and never be inovoked.

// Extension functions for objects
object O

class P {
  companion object Q {
    object R
  }
}

fun O.extend() {}
fun P.extend() {}
fun P.Q.extend() {}
fun P.Q.R.extend() {}

// can even use backticks in function names

fun `testing*(&)(^`() {}
fun t() {
  `testing*(&)(^`()
}

//fun ` `(){}
fun ` `(` `: () -> Unit) {}

class ` ` {
  companion object ` ` {

  }

  fun ` `.` `(` `: (` `) -> ` `): (` `) -> ` ` = ` `
}

fun testSpacebacktick() {
  ` `() // either function or constructor
  ` ` { -> }
  ` ` { -> }
  1.let { i -> }
//  ` `.` `
  ` `()
  ` `;

  { -> }
}

// possible overrides for binary operators: plus, minus, times, div, rem, rangeTo, contains, plussAssign, ...Assign
// ?.equals(y)?:(y===null), !(x?.equals(y) ?:(y===null)), compareTo ... <0 >0 <=0 ...

// Invoke - the definition of Function2 has the invoke function, which can be called without a name
// thus we can call it on our instance like a constructor val my = My(); my() or my.invoke()
// which is wy we can call the lambda function directly with parenthesis, due to the invoke function
//  you can have invoke with parms too

// Indexed Access - the array read-write with square brackets [], in kotlin using get-set
// these are equiv to myArray[input]!! and myArray[index] = "new"

// Unary operators - don't have parms and act directly in the dispatcher: unaryPlus-Minus, not, inc, dec both pre and postfix

// Type-safe builders - define data in semi declarative way, very useful to define guis, markup, xml
// class FxView:View(){ override val root = vbox{ label("function kotlin); button("press me") } }
// languages have with keyword but in kotlin it is a function
// public inline fun <T,R> with(receiver:T, block:T.()->R):R = receiver.block()

// ex fun buildString(builderAction:StringBuilder.() -> Unit):String = StringBuilder().apply(builderAction).toString()
//apply is similar to with but returns the recceiver instance instead of R, thus apply is used for initializing and instance
// public inline fun <T> T.apply(block:T.()->Unit):T{ block(); return this }
// or better :T = this.also{ block() } clearer semantic, but two lambdas

