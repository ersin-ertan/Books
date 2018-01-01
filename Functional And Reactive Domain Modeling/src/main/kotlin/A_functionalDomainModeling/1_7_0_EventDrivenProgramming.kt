package A_functionalDomainModeling

import java.util.concurrent.Future
import java.util.concurrent.FutureTask

// 1.7.0 Event driven programming

// Ex portfolio statement of all holdings from the bank: general currency holdings, equity holdings, debt holdings, loan info,
// retirement fund valuation.

// Sequential calls means the latency of computation is the sum of the latencies of all individual functions

fun r1(){}
fun r2(){}
fun r3(){}
fun r4(){}
fun r5(){}
fun r6(){}

// distribute the processing across multiple parallel units of computation, keep main thread in the role of coordinator

class Ex09(){

    class Balance

// val curr: Future<Balance> = getCurrencyBalance()
// val equ: Future<Balance> = getEquityBalance()
// val debt: Future<Balance> = getDebtBalance()

    private fun getCurrencyBalance(){} // : Future<Balance> {}
    private fun getEquityBalance(){}  // : Future<Balance> {}
    private fun getDebtBalance(){}   // : Future<Balance> {}

    class Portfolio

//    val portfolio:Future<Portfolio> =
//            for{ // for comprehension
//                val c = curr()
//                val e = equ()
//                val d = debt()
//                yields generatePortfolio(c,e,d)
//            }

    // call generatePortfolio leaves main thread completes tasks in separate threads, when done returns to main thread
    // as a single return object

    // fetch the result from the callback that registered it earlier, one form of event driven programming, event implicitly
    // sent to calling thread by the Future abstraction

//    1.7.1 Events and commands
//    small messages enable non blocking programming models

    // there are two different events, one where the db save triggers its completion sending a message to the client with
    // the update. Then the client event which allows the in memory data has been changed and to trigger a ui/memory update.

    /*
    Debit: effects global state of system, equivalent to a write operation on the aggregate, changes balance of account
    A message that an object in the system sends prior to effect taking place in system.
    Mutating message, processed by a single handler of system, can fail if some of the constraints are violated

    DebitOccurred: Is just a notification sent to interested subscribers(account holders), a message sent by the system after
     the effect has occurred, can be handldled by multiple parties, each responding differently to the message, can't fail
     because the associated effect has already taken place within the system.

     debit is a command, debitOccurred is an event, both messages are generated and processed by the model, differ in semantics
     subtly.

     1.7.2 Domain Events

     Domain events speak the language of the domain and events are immutable.

     Time
     t0 Bob opens an account with address recorded as A.    Trigger event AddressChanged(Bob, _, A, T0)
     t1 Bob's address changes to B.                         Trigger event AddressChanged(Bob, A, B, T1)

     Events can't be changed, applied on domain model to arrive at current snapshot. Apply the last event on Bob's record
     in bank to get his current address. If he doesn't have the change, then you haven't gotten the last event with the
     state from the bank.

     Events help you build models that retain the entire history since evolution, called Self tracing models because domain
     event logs make our event traceable at any point in time. Domain events are important in reactive domain models.

     Uniquely identifiable as a type - for each event, you have a type in your model
     Self contained as a behaviour - every domain event contais all information relevant to the change that just occurred in the system.

     We end up with snapshots but not the history.

     Or

     All actions record event in the event log, then update the model. We have
     a snapshot as well as history of how we are here. Useful for system recreation from time 0.

     First accepts all actions and applies them as updates to get current snapshot. Lose history of evolution.

     Second has all events stored in a log, history as well as ability to recreate entire model from any point in time.

     Observable by consumers - evens are meant to be consumed for further action by downstream components of model
     Time relevant - possibly the most important characteristic of a domain event. Monotonicity of time is built into the stream of events.

     Entire model is this equation: M(tN) = E(all events from t0 till tN)
     M(tN) is the state of the model expressed as a sum of all events starting from time t0
    */

    // 1.8.0 Functional meets reactive

    // one way to keep model responsive even in the face of varying load is event driven, main thread of execution is never blocked
    // Pur logic scales and sideefects don't, you gain when combining functional and reactive model thinking
}

