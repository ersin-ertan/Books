package Part1_Introduction.G_OperatorOverloading

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

// lazy function is thread safe by default but can bypass sync if newerr used in multi threaded environment
