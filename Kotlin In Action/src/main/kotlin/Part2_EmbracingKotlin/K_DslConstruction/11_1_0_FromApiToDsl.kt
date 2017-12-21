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
*/