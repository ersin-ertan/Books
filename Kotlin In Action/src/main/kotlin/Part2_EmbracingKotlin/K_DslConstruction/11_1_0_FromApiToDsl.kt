package Part2_EmbracingKotlin.K_DslConstruction

// DSL, lambdas with receiver, invoke convention, existing dsls

// Lambdas with receiver allow us to create DSL structure by changing name resolution rules in code blocks
// invoke convention enables more flexibility in combining lambdas and property assignments in DSL code

// 11.1.0 From Api to Dsl
// To achieve best code readability and maintainability, not only with individual classes but with inter-
// actions, thus what is the interface(the api) using extension functions, infix calls, lambda syntax,
// operator overloading.

// What is a clean api
// clear for reader to understand code with good names, and concepts
// look clean with minimum ceremony and no unnecessary syntax

/* Examples:
 Extension function: myString.capitalize()
 Infix function: 1 to "one
 Operator overloading: set += 2
 Convention for get method: map["key"]
 Lambda outside of paren: file.use{ it.read() }
 Lambda with receiver: with(sb){ append("yes") append("no) }

 DSLs are statically typed

 ex. val yesterday = 1.days.ago

 11.1.1 concept of domain specific languages

 general purpose vs domain specific(sql, reg ex)

 Using sql you use need operations, regex needs matches. DSLS tend to be declarative, but can be hard to
 combine with the host/general purpose language.



 // 11.1.2 Internal DSLs

 External dsl have own syntax, internal are part of the program, using the same syntax. A particular way of using the lang
 External DSL may be SQL, internal one may be via the exposed framework(for db access)

 // 11.1.3 Structure of DSLs

 Often DSLs have structure/grammar
 Library has calls, but no structure of sequence of calls, the context is not maintained between calls(command-query API)

 DSLs tend to be defined by grammar of domain specific language. Commonly created through the nesting of lambdas, or chained
 method calls.

 Sql executes a query requiring a combination of method calls, describing the different aspects of required result set and
 the combined query is easier to read than single method call taking all params.

 DSL single operation can be composed of several smaller ones, type checker will ensure that callers are combined meaningfully.

 DSL allows context reuse across multiple method calls
 dependencies{
    compile ("junit:junit:4")
    compile ("eunit:eunit:4")
}

else you would have to

project.depedencies.add("compile", "junit:junit")
project.depedencies.add("compile", "eunit:eunit")


or
str should startWith("kot") vs assertTrue(str.startsWith("kot"))
*/