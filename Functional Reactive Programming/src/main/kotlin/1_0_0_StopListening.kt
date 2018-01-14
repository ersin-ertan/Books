import nz.sodium.Cell
import nz.sodium.Lambda2

fun main(args: Array<String>) {
}

// FRP is essentially an embedded logic language, looks same within any language. Minimal complete dsl for stateful logic.
// aside from I/O complex video games can be written in frp

// what frp is, what events are, how they cause trouble, frp is for(what problem), benifits of frp, workings of frp system
// thinking in frp

// Replaces listeners(callbacks/observer pattern), simpler

// complexity wall, common problem: event propagation, get the boolean value through layers of interfaces and abstractions,
// frp solves this

// replacement for the observer pattern, composable, modular way to code event driven logic, different way of thinking,
// reaction to inputs or flow of data, brings order to program state.

// true frp is denotative and temporally continuous
// denotative, simple implementation independent compositional semantic that exactly specifies the meaning of each type
// and building block. Compositional nature of the semantic determines the meaning of all type correct combinations of
// the building block

// denotational semantics math expressions for formal meaning of programming language. FRP system provides formal spec of
// system and proof that the property of compositionality holds for all building blocks in all cases.

// Compositionality math for composability

// Functional programming - based on mathe functions, avoud shared mutable stat using immutable data structures emphasises
// compositionality

// ractive programming - eventbased, acts in responce to input, viewd as a flow of data, instead of flow of control
// loose connectionivity

// Functional reactive - reactive programming enforcing functional programming, esp compositionality
// rxchain event handlers, frp controls more tightly for stronger guarentees

// 1.4.0 events or threads
// threads model state as transations of control flow, good for io, where state transitions as clearly defined(same with
// actors and generators)

// events are discreet, asynch messages that are propogated around the program, sequence is less obvious, interactions
// between components are complex(gui and video games)

// 1.5.0 State machines are hard to reason about
// input to system, program logic decides based on input event and current program state, program logic changes the program state
// the program logic may produce output

// manage complexity

class Ex01 {

    class ListBox {

        interface Listener {
            fun itemSelected(index: Int)
        }

        var listeners: ArrayList<Listener>? = null

        fun addListener(l: Listener) = listeners?.add(l)
        fun removeListener(l: Listener) = listeners?.remove(l)
        fun notifyItemSelected(i: Int) = listeners?.forEach { it.itemSelected(i) }
    } // listeners invert natural dependency, consumer depends on producer, loose coupling
}

// but the problem is: unpredictable order of event registration and process, missed first event if registration late,
// messy state as callbacks push code into traditional state machine stile, threading issues like dead lock and deregister
// timing, leaking callback on forget deregister listener leaking memory, accidental recursion

// frp keeps state in cells, valid if departure date <= return date

// configuration code that is used in different parts of the application is hard to test, and usually resorts to restart


//      val valid :Cell<Boolean> = SDateField().date.lift(SDateField().date, (d,r)-> d.compareTo(r) <= 0)
//      SButton ok = new SButton("ok", valid)

// cells represent values that change overtime
// streams represent streams of events

// instead of a List<Date> it is a Cell<Date> implying the date is var, two dates are being used with lift()

// 1.12.1 Life cycle of an FRP program

// frp statments, directed graph, frp engine(inputs and outputs)
// stage 1: init, startup, code are converted into directed graph in memory
// stage 2: running, program execution feeds values and handle and produce outputs with the engine(graph is static, but can
// be dynamically altered)

// order is critical. Threads for secquence, events fo dependency.
// frp expresses dependencies directly, when you add or remove the sequence is auto updated

// declarative describes the relationship between things, what not how

// conceptual vs operation understanding
// operational is more complex, conceptual frp is simple

// during initialization, push based frp constructs a network of listeners
// objects listening to others are called back on their update method
// dep.date or ret.date notifies listeners of change, update(...) method for valid is called, logic/business rules recalculated
// with latest values, valid notifies listeners, update(..) method for ok is called, cases button to be dis/en-abled

// 1.17.0 Applying functional programming to event based code

// cells and streams follow rules of functional programming, in frp functional programming is the meta language for event
// based logic


// reify: convert abstract representation to code

class Ex02 {

    class Rule(val f: Lambda2<Int, Int, Boolean>) {
        fun reify(dep: Cell<Int>, ret: Cell<Int>) = dep.lift(ret, f)
        fun and(other: Rule) = Rule(Lambda2 { d, r -> this.f.apply(d, r) && other.f.apply(d, r) })
    }


    fun unlucky(day: Int) = day in listOf(4, 14, 24)

    val depart = 4
    val returnn = 9

    val r1 = Rule(Lambda2({ d, r -> d.compareTo(r) <= 0 }))
    val r2 = Rule(Lambda2({ d, r -> !unlucky(d) && !unlucky(r) }))
    val r = r1.and(r2)

    val valid: Cell<Boolean> = r.reify(Cell(depart), Cell(returnn))
    val isValid = valid
}