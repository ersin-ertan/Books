package Part1_Introduction.G_OperatorOverloading

// Delegated property translation rules

// instance of MyDelegate will be stroed in ahidden property of type KProperty, thus
// private vale <delegate> = MyDelegate()
// var c:Type
// set(value:Type) = <delegate>.setValue(c,<property), value)
// get() = <delegate>.getValue(c, <property>)

// inside every access to the property the corresponding get and set values methods are called

// thus instead of a property access, get and set value function on the <delegate> are called
// allowing you to customize where the value fothe property is stored/retrieved (map, db, cookies)
// and for access - validation, change notifications, etc


// Storing property values in a map

// dynamically defined set of attributes assciated with them
class Person5 {
    private val _attributes = hashMapOf<String, String>()

    fun setAttributes(attrName: String, value: String) {
        _attributes[attrName] = value
    }

    val name: String
        get() = _attributes["name"]!! // manual retrieval
}

fun use() {
    val p = Person5()
    val data = mapOf<String, String>("name" to "tom", "company" to "cloud")

    for ((attrName, value) in data) p.setAttributes(attrName, value)
}

// via delegates

class Person6 {
    private val _attributes = hashMapOf<String, String>()

    fun setAttributes(attrName: String, value: String) {
        _attributes[attrName] = value
    }

    val name: String by _attributes // uses the map as a delegated property and will find key named "name"
}

// standard library defines getValue and set extension function on standard Map and mutableMap interfaces.
// the name of the property is used as the key to store the value in the map


// Delegated properties in frameworks

/*

operator fun <T> Column<T>.getValue(o: Entity, desc: KProperty<*>): T {
// retrieve the value from the database
}
operator fun <T> Column<T>.setValue(o: Entity, desc: KProperty<*>, value: T) {
// update the value in the database
}

object Users : IdTable() {
    val name = varCChar("name", length = 40).index()
    val age = integer("age")
}

class User(id: EntityID) : Entity(id) {
    var name: String by Users.name
    var age: Int by Users.age
}*/

// object corresponds to a table in the database, properties correspond to columns in the table, each insteace of user
// corresponds to specific entity in the table, value of name is the value stored in the database for that user

