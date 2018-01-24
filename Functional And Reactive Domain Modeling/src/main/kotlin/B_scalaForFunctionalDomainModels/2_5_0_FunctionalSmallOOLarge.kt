package B_scalaForFunctionalDomainModels

import arrow.core.Option
import arrow.core.Some

// 2.5.0 Functional in the small, OO in the large

/*
Non trivial domain model has many abstractions, entities, value objects, services, and associated behaviours.
Monolithic app, bundled in same namespace has entanglement. No context bound separation. Need modules, they must be
highly cohesive but loosely coupled, abstractions small and tight.

Scala uses traits(mixin based composition) and objects. Not function composition, but unit of functionality, with few methods
delivering functionality
*/


class Ex07 {

    interface IBalance
    interface IInterestCalc
    interface ITaxCalc // defined below
    interface ILogging

    interface PortfolioGeneration : IBalance, IInterestCalc, ITaxCalc, ILogging // {... implementations}

    // all orthogonal to each other, can be reused in other contexts

    object PortfolioGenerator : PortfolioGeneration // but why have interface PortfolioGenereration? why not directly in Generator
    // have final module in form of trait before committing to concrete implementation

    // then when you need a bigger abstraction, you can define it composed with the PortfolioGeneration trait

    // Module implements specific business functionality, one module is usually part of one bounded context where a
    // bounded context may contain many modules.

    // Modules can be parameratized to incorporate variations in business rules
    // For specific date range: Calc interest, calc tax deducted from interest, tax needs specific tax tables of item and rate
    // depending on transaction

    // Module computing net interest referes to another module for computation of tax deduction(which is parameterized on
    // text tables that depend on the transaction type being performed

    sealed class TaxType {

        object Tax : TaxType()
        object Fee : TaxType()
        object Commission : TaxType()

    }

    sealed class TransactionType {
        object InteresteComputation : TransactionType()
        object Dividend : TransactionType()
    }

    class Balance(val amount: Int = 0)

    interface ITaxCalculationTable<TT : TransactionType> {
        val transactionType: TT

        fun getTaxRates(): Map<TaxType, Int> = mapOf(TaxType.Tax to 1)
    }

    interface ITaxCalculation<TCT : ITaxCalculationTable<TransactionType>> {
        val table: TCT
        fun calculate(taxOn: Int): Int = table.getTaxRates().map { doCompute(taxOn, it.value) }.sum()
        private fun doCompute(taxOn: Int, rate: Int) = taxOn * rate
    }

    interface ISingaporeTaxCalc : ITaxCalculation<ITaxCalculationTable<TransactionType>> {
        fun calculateGST(tax: Int, gstRate: Int) = tax * gstRate
    }

    interface IInterestCalculation<TC : ITaxCalculation<ITaxCalculationTable<TransactionType>>> {
        val taxCalculation: TC

        fun interest(b: Balance): Option<Int> = Some(b.amount * 1)
        fun calculate(b: Balance): Option<Int> = interest(b).map { it - taxCalculation.calculate(it) }
    }

    // no contrete instances of modules, just module definitions and dependencies reflecting variability in domain model
    // Implement variablities using abstract types and vals

    // concrete module for interest calculation, don't need to commit to the exact type or value until you declare the object

    object InterestTaxCalculationTable : ITaxCalculationTable<TransactionType> {
        override val transactionType: TransactionType
            get() = TransactionType.InteresteComputation

    }

    object TaxCalculation : ITaxCalculation<ITaxCalculationTable<TransactionType>> {
        override val table: ITaxCalculationTable<TransactionType>
            get() = InterestTaxCalculationTable
    }

    object InterestCalculation : IInterestCalculation<ITaxCalculation<ITaxCalculationTable<TransactionType>>> {
        override val taxCalculation: ITaxCalculation<ITaxCalculationTable<TransactionType>>
            get() = TaxCalculation
    }
}

// InterestCalc, TaxCalcu, TaxCalcTable all have parameterized module contracts with TaxCalc, TaxCalcTable, and transactionType
// respectfully to form a dependency chain. Each of the contrete modules then have their respective contrete implmentation
// dependency chains and parameterized module contracts(InterestComp, InterestTaxCalcTable, TaxCalc)


// Successful domain modeling requires: static type systems(rich types system, type inference, higher order(kinds)type support)
// Functional programming: algebraic data types, pattern matching, pure functions, higher order functions, compositionality, adv. functional design patterns
// Powerful module System: first class module support, composable modules, parameterized modules