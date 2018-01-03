package B_scalaForFunctionalDomainModels

// 2.4.3 ADTs and Pattern Matching

// Pattern matching helps keep functionality local to respective variant of the ADT, readability to model, and robust code

class Ex06 {

    class Balance(val amount: Int = 0)

    class Account(val no: String, val name: String, val dateOfOpening: Int, val balance: Balance)
}