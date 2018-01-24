package C_DesiginingFunctionalDomainModels

import A_functionalDomainModeling.p
import arrow.core.None
import arrow.core.Option
import arrow.core.Some

// 3.3.3 Patterns in the lifecycle of a domain model

// Creation: object created from components
// Participation: valid domain object interacts with other abstraction to deliver functionality in the  domain
// Persistence: domain object gets written on to some persistent store

// Simplest technique of direct invocation of class constructor has pitfalls for in non trivial cases.
// Must address validation too, must be minimally valid on creation.

// After domain validation phase, receive a fully created valid domain aggregate(complete domain entity describing central
// concept of domain model. Validations need to be pluggable to objects and resualbe under variaous constraints, must have
// specific failure semantics and domain freindly syntax.Instead of rich domain models, functional uses skinny domain objects
// Use algebraic data types to model skinny elemens. functionality will be distributed across reusable and extensible structures
// called modules that form the algebra of the model. To ensure lawfulness, use the type system to guarantee correctness.

// 3.3.1 Factories - where objects come from
// More complex models require better abstraction managemen, thus specific creational strategies.
// Questions to ask: are factory returned objects valid, where does validation logic go, what happens if validation fails.

// factories must create minimally valid objects

// 3.3.2 The Smart Constructor idiom
// Prohibit basic constructor invocation, and provide a smarter version ensuring the user gets back a valid instance or
// appropriate explanation of the failure.

// instead of
fun main(args: Array<String>) {
    Ex02().dowUsage()
}

class Ex02 {

    class DOW(val day: Int) {
        init {
            if (day < 1 || day > 7) throw IllegalArgumentException("must be between 1 and 7")
        } // but in function we don't throw Execptions

    }

    interface DayOfWeek {

        val value: Int

        // note the two gg's because "An interface may not override a method of Any"
        fun toStringg() = when (value) {
            1 -> "Mon"
            2 -> "Tue"
            3 -> "Wed"
            4 -> "Thu"
            5 -> "Fri"
            6 -> "Sat"
            7 -> "Sun"
            else -> "Mon"

        }

        companion object {

            private fun unsafeDayOfWeek(d: Int) = object : Ex02.DayOfWeek {
                override val value: Int
                    get() = d
            }

            private val isValid: (Int) -> Boolean = { it in 1..7 }

            fun dayOfWeek(d: Int): Option<DayOfWeek> = if (isValid(d)) Some(unsafeDayOfWeek(d)) else None

        }
    }

//    object DayOfWeekO {
//
//        private fun unsafeDayOfWeek(d: Int) = object : DayOfWeek {
//            override val value: Int
//                get() = d
//        }
//
//        private val isValid: (Int) -> Boolean = { it in 1..7 }
//
//        fun dayOfWeek(d: Int): Option<DayOfWeek> = if (isValid(d)) Some(unsafeDayOfWeek(d)) else None
//    }

    fun dowUsage() {

        DayOfWeek.dayOfWeek(1).map { it.value.p() }
        DayOfWeek.dayOfWeek(7).p()
        DayOfWeek.dayOfWeek(8).p()

        DayOfWeek.dayOfWeek(1).toString().p()
        DayOfWeek.dayOfWeek(1).map { it.toStringg().p() }
        DayOfWeek.dayOfWeek(7).map { it.toStringg().p() }
        DayOfWeek.dayOfWeek(8).map { it.toStringg().p() }


        DayOfWeek.dayOfWeek(2).map { schedule(it.value) }
    }

    fun schedule(day: Int): Unit = Unit
}

// features of implementation:
/*
- primary interface for creating DayOfWeek has been named unsafe and marked private, not exposed, preventing out of range
construction
- only way to get an instance of data type representing eith r a valid construted object or absence of it is to use dayOfWeek
the smart constructor from the companion object DayOfWeekO
- return type of constructor is Option<DayOfWeek> or None
- Option is used to represent the optional presence, complex validation could require a reason why via Try or Either
- Domain logic for creation and validation is moved away from the core abstraction to the companion object
- typical invocation of the smart constructor could be         DayOfWeek.dayOfWeek(2).map { schedule(it.value) }


Scala what is a sealed trait/class? Defined in same class. Compiler knows all possible subtypes
*/