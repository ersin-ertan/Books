package B_scalaForFunctionalDomainModels

// 2.4.0 Algebraic Data Types and Immutability

/*
Modeling entities, value objects and other types of abstraction, an algebraic data type is the main concept

// 2.4.1 Basics: sum type and product type

Types of currencies are: USD, AUD, EUR, INR, but are all types of currency
*/

class Ex05 {

    // was sealed trait
    interface Currency {
        object USD : Currency
        object AUD : Currency
        object EUR : Currency
        object INR : Currency
    }
// can only have a single currency, an OR is represented via +
// Currency = USD + AUD + EUR + INR

// Via type theory the number of inhabitants of data type Currency is found by summing the number of distinct values that
// the currency data type can have. Currency is a Sum type

    sealed class Either<A, B> {
        class Left<A, B>(a: A) : Either<A, B>()
        class Right<A, B>(b: B) : Either<A, B>()
    }

    // what are the number of inhabitants for Either, the number of inhabitants in a and the number in B, thus
    // Either<Boolean, Unit> is 3, 2 for boolean, 1 for unit
    // not for currency because all are individual types due to them being singletons.

// constructing an instance of either you can inject a value of type A using Left constructor or B with Right thus Left Or Right

    // was sealed trait
    interface Account {
        val number: String
        val name: String
    }

    class CheckingAccount(override val number: String, override val name: String, val dateOfOpening: Int) : Account
    class SavingsAccount(override val number: String, override val name: String, val dateOfOpening: Int, val rateOfInterest: Float) : Account

    // sum types, generally known as type CheckingAccount = string x string x date, thus a collection of all valid combinations of
    // tuple(string, string, da te), cartesian product of these three data types, thus a Product type

    // Account is a sum tpe and each type of account is a product type
}

// 2.4.2 ADTs structure data in the model

/*
Sum and product types are neccessary abstractions for structuring the various data of our domain model.

Sum types model variations within a particular data type

Product types help cluster related data into a larger abstraction

Proper application of ADTs makes data types compositional.

Checking Account is just combining (string string, date) and tagging it as a new type, why not use a tuple, the tag
can associate a named identity that maps to the domain that you are modelling.

The compiler automatically validates various tags containing valid combinations of data types, each instantiation must be
a valid encoding from the list of enumerations that your data type has, the component data types must also match 1 to 1.

ADT forces you to build abstraction stricly according to the rules that you've defined for it.
*/