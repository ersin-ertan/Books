package Ch02

// transform data with expressions(ideally without side effects), mathematical function

// Immutable data, but state management is handled explicitly

val lambda:(String) -> String = { str: String -> str.capitalize() }
// is the same as
val capitalize:(String)-> String = object: Function1<String, String> {
    override fun invoke(p1: String): String  = p1.capitalize()
}


// transform which is fun <T> transform(t:T, fn:(T)->T):T{ return fn(t) parameterizes both input and transform function
// now we can pass function refrences transform("mystring", MyUtils::doSomething)
// ex is of object method but normal function references, both to objects and companion object functions, we can even pass
// in lambdas of functionality

// now we can make dsls
fun unless(condition:Boolean, block:()->Unit) = if(!condition) block() else Unit

val test = unless(true){ false }
val test1 = unless(true, { false })

// type alias can be mixed with functions and used to replace simple interfaces


typealias Machine<T> = (T)->Unit
fun <T> useMachine(t:T, machine:Machine<T>) = machine(t) // instead of interface Machine<T> fun process(product:T)... machine.process(t)

// since the machine is lambda, we just now override the invoke(p1:T) when subclassing it

class SpecialMachine<T>:Machine<T>{
    override fun invoke(p1: T) = print(p1.toString()) // special operation
}

val test2 = useMachine(2){println(it)}

// why pure functions refernetial transparency, caching(memoization)

// recursive functionsn mantain a stack and can be optimize with tailrec modifier

fun functionalFactorial(n: Long): Long {
    fun go(n: Long, acc: Long): Long {
        return if (n <= 0) acc
        else go(n - 1, n * acc)
    }
    return go(n, 1)
}

fun tailrecFactorial(n: Long): Long { // because we use an internal recursive function, go calling itself until a condiion
    // is reached. Tailrec is faster than imperative version(but not always)
    tailrec fun go(n: Long, acc: Long): Long {
        return if (n <= 0) acc
        else go(n - 1, n * acc)
    }
    return go(n, 1)
}

// tail recursion is where the recursive call is the very last operation in the function, thus we can just pass the result
// instead of waiting for it... thus no stack space is consumed
//https://www.quora.com/What-is-tail-recursion-Why-is-it-so-bad
// recursive with O(n) could lead to runtime problems with stack space

// lazy eval via delegate properties

// functional collections and operations ex.
// fold - iterates over collection keeping an accumulator value(inital value)
// reduce- like fold but without an initial value
// using the Right postfix, causes the above two operations to start backwards in the list