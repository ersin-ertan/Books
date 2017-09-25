package Part1_Introduction.B_KotlinBasics

import java.io.BufferedReader

// function caller can catch this exception and process it, or it's rethrown up the stack

fun except(num:Int) {
    if (num !in 0..9) throw IllegalArgumentException("Input $num, is not within 0-9")
    // no new needed, can use the input as part of the argument
    // or
    val nonNeg = if (num > 0) num else throw IllegalArgumentException("Input $num, cannot be negative")
}

fun readNumber(reader:BufferedReader):Int? { // exceptions need not be specified
    try {
        return Integer.parseInt(reader.readLine())
    } catch (e:NumberFormatException) {
        return null
    } finally {
        reader.close()
    }
    // java must declare all checked exceptions that you can throw
}

fun tryAsExpression(r:BufferedReader) {
    val num = try {
        Integer.parseInt(r.readLine())
//    }catch (e:NumberFormatException) {return} // nothing is printed if not a number
    } catch (e:NumberFormatException) {
        null
    } // may return nothing or null

    println(num) // the value of the try expression, always need to enclose the statement
    // will print out the number or null thrown and caught
}
