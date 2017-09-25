package Part1_Introduction.D_ClassesObjectsAndInterfaces

interface JsonFactory<T> {
    fun fromJson(jsonText:String):T
}

// companion object implementing an interface
class Obj(val name:String) {

    companion object Loader:JsonFactory<Obj> {
        override fun fromJson(jsonText:String):Obj = Obj("test")
    }
}

class CO {
    // name not needed
    companion object:JsonFactory<CO> {
        override fun fromJson(jsonText:String):CO {
            return CO()
        }
    }
}

// a function that uses an abstract factory to load obj can be passed an Obj

fun main(args:Array<String>) {
    loadFromJson(Obj)
// or without the named companion obj, which is referenced via Person.Companion.func()
    // in java
    loadFromJson(CO)
}

fun <T> loadFromJson(loader:JsonFactory<T>):T {
    return loader.fromJson("testing")
}

// java code requiring a member of your class to be static can use @JvmStatic,
// to declare a static field @JvmField on top level property or property declared object

// companion object extensions
// clean separation of concerns for person class, not coupled with module for specific
// data format, the deserialization function needs to be defined in the module responsible for
// client/server communication

fun CO.Companion.extFromJson(json:String):CO = CO()
fun Obj.Loader.extFromJson(json:String):Obj = Obj("test")

// note that in order to extend companion functions, it had to be defined in class
// even if empty

