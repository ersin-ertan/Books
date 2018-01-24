package Part1_Introduction.E_ProgrammingWithLambdas

// using java functional interfaces
// onclick listeners can be passed a lambda instead of creating an anon inner
// when an interface has only one abstract method, its called a functional interface, or SAM interface
// single abstract method - like runnable and callable
// kotlin has proper function types, thus methods that take lambdas as parameters should used function types not
// functional interface types.


// passing lambda as a paramater to java method
// java
// void postponeComputation(int delay, Runnable computation);
// kotlin
// postponeComputation(100){ println(43) }
// or
// postponeComputation(100, r:Runnable { override fun run(){ println(43) } })
// when you pass a lambda an anonymous class is created

// when you explicitly declare an object, the new instance in created on each invocation. Lambdas are different,
// if it doesn't access any variables from the function where it was defined, the same anon class instance is reused

// the equivalent implmentation with explicitly object declaration is
val runnable = Runnable { println(43) }

fun handleComputation() {
    postponeComputation(100, runnable)
}

fun postponeComputation(i:Int, runnable:Runnable) {}
//fun postponeComputation(i:Int):Runnable {return Runnable()}

// else capturing a variable requires a new Runnable instance
fun handleComputation(id:String) {
//    postponeComputation(100){ print(id)} // something like this
}

// kotlin 1.0 every lambda is compiled into an anon class, unless it's an inline lambda, with support for generating java 8 bytecode coming later
// allowing the compiler to avoid generating separate class files for every lambda. If a lambda captures variables,
// the anon class will have a field for each captured variable, and a new instance will be created per invocation.
// Class is created called HandleComputation$1

// lambdas passed into kotlin functions marked as inline don't create anon classes.

// SAM constructors: explicit conversion of lambdas to functional interfaces
// SAM constructor is a compiler generated function to perform explicit conversion of a lambda to a functional interface instance
// used in contexts when the compiler doesn't apply the conversion automatically. A method returning an instance of a
// functional interface, cant return a lambda directly, wrap it into a SAM

fun createAllDoneRunnable():Runnable = Runnable { println("done") }

// SAM constructor's name is the same as functional interface, taking a single argument, a lambda that is used as the body
// of the single abstract method in the functional interface, returning an instance of the class implementing the interface
// SAM constructors can return values and are used to store functional interface instance generated from a lambda in a
// variable. Like reusing a listener for several buttons

/*
val listener = OnClickListener  { view ->

    val text = when(view.id){
        R.id.b1 -> "first"
        R.id.b2 -> "seccond"
        else -> "unknown"
    }
    toast(text)
}
b1.setOnClickListener(listener)
b2.setOnClickListener(listener)*/

// there is no this in a lambda, because it's in an anon object thus no way to refer to the instance which the lambda is converted
// To the compiler, the lambda is a block of code, not an object thus no object reference. The this ref in a lambda refers
// to the surrounding class. If your event listener needs to unsubscribes itself while handling an event, don't use a lambda.
// Use an anonymous object to implement a listener, the this keyword refers to the instance of that object, and can be passed to
// the api that removes the listener.