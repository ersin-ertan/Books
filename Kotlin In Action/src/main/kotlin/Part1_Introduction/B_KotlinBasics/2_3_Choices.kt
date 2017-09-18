package Part1_Introduction.B_KotlinBasics

import java.util.*

// enums and when

// enum is a soft keyword, only meaningful if it precedes class keyword

enum class Colour{ RED, ORANGE, GREEN, BLACK, FLASHING }

enum class Colour1(val r:Int, val g:Int, val b:Int){
    RED     (255, 0,0),
    ORANGE  (255, 165, 0),
    GREEN   (0, 255, 0); // semicolon is required

    fun averageColour() = (r + g + b) * 0.333
}

fun enumFunction(){
    println(Colour1.ORANGE.averageColour())
    println(Colour1.ORANGE.g)
    println(Arrays.toString(Colour1.values())) // will [print, the, array]
    for (colourEnumVal in Colour1.values()) println(colourEnumVal) // will list out one by one
}

fun getMapper(col:Colour) = when(col){
    Colour.GREEN -> "GOGOGO"
    Colour.RED -> "STOP"
    Colour.ORANGE -> "SLOW"
    Colour.BLACK, Colour.FLASHING -> "Maintenence"
}

fun mixedMapper(c1:Colour, c2:Colour) = when(setOf(c1, c2)){ // can check any object
    // for equality, pairs can be mixed(sets - order doesn't matter), has else case
    setOf(Colour.RED, Colour.ORANGE)    -> "Slow to stop"
    setOf(Colour.GREEN, Colour.ORANGE)  -> "Continue if safe"
    else -> throw Exception("Not Working")
}

// harder to read but intermediary no object creation
fun whenWithoutArgs(c1:Colour, c2:Colour) = when {
    (c1 == Colour.RED && c2 == Colour.ORANGE) ||
    (c1 == Colour.ORANGE && c2 == Colour.GREEN) -> "Legal"
    c1 == Colour.BLACK && c2 == Colour.FLASHING -> "Illegal"
    else -> throw Exception("Not Working")
}

// smart casts - combining type checks and casts

interface Expr

class Num(val value:Int):Expr // implements an interface

class Sum(val left:Expr, val right:Expr):Expr

fun creation() = Sum(Sum(Num(1), Num(2)), Num(4))
    // creates a tree of sum => num 4 and sum => num 1 and num 2

fun javaEval(e:Expr):Int{
    if(e is Num){
        val n = e as Num // explicit cast
        return n.value }
    if(e is Sum) return javaEval(e.right) + javaEval(e.left)
    throw IllegalArgumentException("Unknown expressions")
}

// smart casts
fun smartCasts(e:Expr):Int{
    if(e is Sum){ // only works if a variable couldn't have changed after the is check
        return smartCasts(e.right) + smartCasts(e.left) // e is smart cast to sum
    }else return (e as Num).value

}

fun kotlinEvalRewrite(e:Expr):Int = when(e){ // both block and branches can be used
    // within the when is expressions ex. is Num -> { a;b; c}
// with the last expression being c is returned
    is Num -> e.value // when branches
    is Sum -> kotlinEvalRewrite(e.left) + kotlinEvalRewrite(e.right) // smart casts
    else    -> throw IllegalArgumentException("unknown expression")
}

// however functions can have eithre an expression body that is not a block,
// or a block body with explicit return statements inside