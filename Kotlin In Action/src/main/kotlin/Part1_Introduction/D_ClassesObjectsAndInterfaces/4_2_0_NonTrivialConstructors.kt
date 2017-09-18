package Part1_Introduction.D_ClassesObjectsAndInterfaces

// declaring a class with non trivial constructors or properties

// primary constructor usually main way, declared outside of class body
// secondary constructor, declared in class body
// allows for initialization logic in initializer blocks

class User(val nickname:String) // this is the primary constructor
// equal to
// class User constructor(_nickname:String){

// val nickname:String
// init{ nickname = _nickname }} // initializer block, executed when class is created
// through the primary constructor, can declare several initializer blocks in one class


// if there are no annotations or visibility modifiers on the primary const
// you can, note there is no var/val
// class User(_nickname:String){ val nickname = _nickname}

// but if you use val, the corresponding property is generated for the constructor param
// class User(val nickname:String) //

// can use default values too, instances are created without the new keyword
val u = User("bob")

// if all constructor params have default vals, compiler generates an additional
// constructor without params using all default values

// if your class has a superclass the primary constructor also needs to initialize
// the superclass, provide the superclass constructor parameters after the superclass ref
// in the base class list
open class SuperA(val nickname: String)
class ChildA(nickname: String):SuperA(nickname)
// else if you don't a default no op constructor will be generated
open class MyClass
open class SuperNoParam
open class ChildB:SuperNoParam() // this constructor is explicitly required
interface MyInterf // but not with interfaces
open class ChildC:MyInterf

// no instantiations require private constructors

class Secretive private constructor()

//val a = Secretive() // error cannot access init, it is private in Secretive

// java uses private class as static utility classes, kotlin has it built in with top-
// level functions, thus singletons use object declarations