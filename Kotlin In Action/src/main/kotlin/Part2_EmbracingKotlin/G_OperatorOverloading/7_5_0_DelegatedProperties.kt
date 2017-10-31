package Part2_EmbracingKotlin.G_OperatorOverloading

import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

// Delegated properties: basics

class Foo {
//    var p: Type by Delegate()
}

// new instance of the delegate via by, compiler creates hidden helper property initialized with instance of delegate
// object, to which p delegates(generated get and set)

// example delegate class

class Delegate {
//    operator fun getValue(){}
//    operator fun setValue(){}
}

class Fooo {
    // var p: type by Delegate()
}

// using delegated properties lazy initialization and by lazy()
// lazy init is on demand object creation on first access, less resources if the object isn't always used

class Email {}
class Person1 {
    var name = ""
    private var _emails: List<Email>? = null
    val emails: List<Email>
        get() {
            if (_emails == null) {
                _emails = loadEmails(this)
            }
            return _emails!!
        } // uses backing property
}


fun loadEmails(person: Person1): List<Email> {
    print("load emails for ${person.name}")
    return person.emails
}

class Person2 {
    val emails by lazy { loadEmails(this) }
    fun toPerson1() = Person1()
}

fun loadEmails(person: Person2): List<Email> {
    return loadEmails(person.toPerson1())
}

// lazy function is thread safe by default but can bypass sync if newer used in multi threaded environment


// Implementing delegated properties - notifying listeners

open class PropertyChangeAware {

    protected val changeSupport = PropertyChangeSupport(this)
    fun addProprtyChangeListene(listener: PropertyChangeListener) = changeSupport.addPropertyChangeListener(listener)
    fun removePropertyChangeListener(listener: PropertyChangeListener) = changeSupport.removePropertyChangeListener(listener)
}

class Person3(val name: String, age: Int, salary: Int) : PropertyChangeAware() {

    var age: Int = age
        set(newValue) {
            val oldValue = field
            field = newValue
            changeSupport.firePropertyChange("age", oldValue, newValue)
        }

    var salary: Int = salary
        set(newValue) {
            val oldValue = field
            field = newValue
            changeSupport.firePropertyChange("salary", oldValue, newValue)
        }
}

fun makeChanges() {
    val p = Person3("dimitry", 23, 2333)

    p.addProprtyChangeListene(PropertyChangeListener { event ->
        println("property ${event.propertyName} changed" +
                "from ${event.oldValue} to ${event.newValue}")
    })

    p.age = 35
    // will print
}

// this requires field identifiers, notifiers, attaching change listeners
// we can extract out an ObservableProperty, and use the fields in the class, and make getValue, setValue as operators
// and you need KProperty<*> ... or delegated properties

class Person4(val name: String, age: Int, salary: Int) : PropertyChangeAware() {

    private val observer = { prop: KProperty<*>, oldValue: Int, newValue: Int ->
        changeSupport.firePropertyChange(prop.name, oldValue, newValue)
    }

    var age: Int by Delegates.observable(age, observer) // right of by need not be new instance creation, can be function
    // call or another property/expression, so long as the value can hetValue and setValue for the compiler
    var salary: Int by Delegates.observable(age, observer)

}

