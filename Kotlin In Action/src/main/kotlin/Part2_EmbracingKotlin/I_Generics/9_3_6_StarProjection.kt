package Part2_EmbracingKotlin.I_Generics

import java.util.*
import kotlin.reflect.KClass

// 9.3.6 Star projection: Using * instead of a type parameter

// no information about generic argument

// MutableList<Any?> is not the same as MutableList<*>
// the former may contain elements, but the later will contain non null elements

// can't put anything in, it might violate type rules but can get because it at least will be Any?

fun star() {
    val list: MutableList<Any?> = mutableListOf("a", 3, "eu")
    val chars = mutableListOf('f', 'o', 'p')

    val unknownelements: MutableList<*> = if (Random().nextBoolean()) list else chars

//    unknownelements.add(2) // out projected MutableList<*> prohibits the add() call
    println(unknownelements.first())
}
// in this context the list acts like a MutableList<out Any?>, MyType<*> is the same as javas MyType<?>

// for contravariant type parameters like Consumer<in T> a star projection is equal to <in Nothing>
// you can't call methods that have T in the signature on that star projection, contravariance is only a consumer
// but since you don't know of what, you can't

// when the info about type args are not important(you don't use any methods that refer to the type param in the signature)
// or you only read the data and don't care about specific type like a printFirst method

fun printFirst(list: List<*>) {
    if (list.isNotEmpty()) println(list.first())
}


// interface fieldValidator, contains type parameter in position, can be declared contravariant

interface FieldValidator<in T> {
    fun validate(input: T): Boolean
}

object DefaultStringValidator : FieldValidator<String> {
    override fun validate(input: String): Boolean = input.isNotEmpty()
}

object DefaultIntValidator : FieldValidator<Int> {
    override fun validate(input: Int): Boolean = input >= Int.MIN_VALUE
}

// to store all validators and get the rig8ght one at input type you could use a map
fun validators() {
    val validators = mutableMapOf<KClass<*>, FieldValidator<*>>()
    validators[String::class] = DefaultStringValidator
    validators[Int::class] = DefaultIntValidator

    // but you may have difficulties trying to use the validators, can't validate a string with a validator of type
    // FieldValidator<*>, it's unsafe because compiler doesn't know what type it is

// validators[String::class]!!.validate("")
    // out projected type FieldValidator<*> prohibits use of fun validate(input T):Boolean
    // because the value stored in the map has the type FieldValidator<*>, it's unsafe to give a value of specific type
    // to a validator for an unknown type you can cast with as

    val stringValidator = validators[String::class] as FieldValidator<String> // warning unchecked cast
    println(stringValidator.validate("eou"))
// but error is hidden until validator use, not type safe is error prone

    val stringValidatorNoCast = validators[String::class]
//    println(stringValidatorNoCast.validate("eou")) // not allowed
}

object Validators {

    private val validators = mutableMapOf<KClass<*>, FieldValidator<*>>()

    fun <T : Any> registerValidator(kClass: KClass<T>, fieldValidator: FieldValidator<T>) {
        validators[kClass] = fieldValidator
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any> get(kClass: KClass<T>): FieldValidator<T> = validators[kClass] as? FieldValidator<T>
            ?: throw IllegalArgumentException("No validator for ${kClass.simpleName}")
}

fun useValidators() {
    Validators.registerValidator(String::class, DefaultStringValidator)
    Validators.registerValidator(Int::class, DefaultIntValidator)

    println(Validators[String::class].validate("Kotlin"))
    println(Validators[Int::class].validate(43))
}

// better because map is only internally accessible, puts the correct key-value pairs, when a validator corresponds to
// a class, and suppresses the warning about the unchecked cast to FieldValidator<T>

// Type safe api, unsafe loggic is hidden in the body of the class, will get a compiler error if you validate with wrong class
// can be extended to store any custom generic classes