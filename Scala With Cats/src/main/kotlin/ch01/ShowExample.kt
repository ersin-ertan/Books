package ch01

class Person1(val firstName: String, val lastName: String)

val personShow = Show<Person1> { "Hello $firstName $lastName" }
val personS = personShow.run { this.toString() }

val intEq = Eq<Int> { x, y -> x == y }