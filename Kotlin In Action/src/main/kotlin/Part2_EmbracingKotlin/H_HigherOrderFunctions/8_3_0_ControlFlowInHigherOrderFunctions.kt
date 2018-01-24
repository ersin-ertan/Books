package Part2_EmbracingKotlin.H_HigherOrderFunctions

// control flow in higher order functions

// returning from enclosing function

// using the return keyword in a lambda returns from the function in which you called the lambda, not from the lambda
// thus is called a non local return, returning to the larger block than the block containing the return statement

// The return from the outer function is possible only if the function that takes the lambda as an argument is inlined


// Returning from lambdas: return with a label - write a loccal return f rom a lambda using a label

fun ret(people: List<Person>) {

    people.forEach peopleForEach@ {
        if (it.name == "Bill") return@peopleForEach
    }

    people.forEach {
        if (it.name == "Bill") return@forEach // implicit label
    }
}

// lambda expression can't have more than one label

// this expression

fun thiss() {
    StringBuilder().apply sb@ {

        listOf(1, 3).apply {
            this@sb.append(this/*@apply*/.toString()) // this refers to closes immplicit receiver in scope
        }
    }
}

// if syntax is verbose with multiple return expressions, pass around code via anonymous functions


// Anonymous functions: local returns by default

fun myFun(people: List<Person>) {

    people.forEach(fun(person) { // the anonymous function
        if (person.name == "Bill") return // refers to the closest function, thus the anonymous one
    })

    people.filter(fun(person): Boolean { return person.number!! > 2 })
    // if you use an expression body omit the return type
    people.filter(fun(person) = person.number!! > 2)
    people.filter { it.number!! > 2}
}

// inside an anonymous func, return expression without a label returns from the anony func, not the enclosing one
// return returns from closest function declared using fun keyword, since lambda expressions don't use fun, their return
// is to the outer function