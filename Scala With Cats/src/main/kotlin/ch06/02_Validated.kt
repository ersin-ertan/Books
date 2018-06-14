package ch06

import arrow.core.*
import arrow.data.*
import arrow.typeclasses.Semigroup
import p

// TODO("encapsulate errors into sealed types which may contain error messages")

// 6.4 Validated
// It is imposible to design a monadic data type that implements error accumulating semantics without
// breaking consistency of product and flatMap, thus Validated, an instance of Semigroupal with no
// instance of Monad, thus product can accum errors
// type AllErrorsOr<A> = Validated<List<String>, A>
// Semigroupal<AllErrorsOr).product(Validated.invalid(List("e1")), Validated.invalid(List("e2")))
// AllErrorsOr<Pair<Nothing,Nothing>> = Invalid(List(Error1,Error2))

// between Either and Validated we can failfast and accumulate errors

// 6.4.1 Creating Instances of Validated
fun validatedInstances() {
  Valid<Int>(1).p()
  Invalid<List<String>>(listOf("Bad")).p()

  // or with smart constructors which widen the return type to Validated
  // or extension methods
  34.valid<List<String>, Int>()
  listOf("abc").invalid<List<String>, Int>()

  // or use pure and raiseErorr from applicative and applicativeError
  Validated.applicative(object : Semigroup<Int> {
    override fun Int.combine(b: Int): Int {
      TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
  }).just(1)

  // or using helper methods to create instances of Validated from different sources, created from
  // Exceptions and instances of try either option
  Validated.fromOption(1.some(), { "error" }).p()
  // have not seen Validated.catchOnly<NumberFormatExption>("foo".toInt)
  Validated.fromTry(Try { "a".toInt() })
}

// 6.4.2 Combining Instances of Validated
typealias AllErrorsOr<A> = Validated<String, A>
// validated accums errors using a Semigroup, we need in scope to summon semigroupal.
// Semigroupal<AllErrorsOr>.instance()???
// compiler must have all implicits in scope to summon a semigroupal of the correct type

fun validatedAccumUsage() {
  Validated.applicative<Nel<String>>(Nel.semigroup()).map(1.valid(), 2.valid(), 3.valid()) {
    Tuple3(it.a, it.b, it.c) // it is already a tuple3 but just for clarity
  }.fix().p()

  fun anInvalid(): Validated<Nel<String>, Boolean> =
    Nel<String>("head", listOf("tail")).invalid()

  Validated.applicative<Nel<String>>(Nel.semigroup()).map(anInvalid(), 2.valid(), 3.valid()) {
    Tuple3(it.a, it.b, it.c) // it is already a tuple3 but just for clarity
  }.fix().p()
  // no nonemptyvector so use Nel the typealias for NonEmptyList
}

// 6.4.3 Methods of Validated
// methods like Either to map leftMap bymap to transform values inside valid/invalid
fun validationFunctions() {

  val validTransform = 1.valid<String, Int>().map { it.toString().p() }
  val validTransform1 = 1.valid<String, Int>().leftMap { it.p() }
  val validTransform2 = 1.valid<String, Int>().bimap({ it }, { it })

  "s".invalid<String, Int>().toEither().p()
  //  "s".invalid<String, Int>().toEither().toValidated??? // can only do valid() or invalid()
  fun v(): Validated<Int, Int> = 1.valid()

  val validated = v().toEither() // .toValidated doesn't exist so
  val validated1 = v().withEither { it }
  val validated2 = v().withEither { it.flatMap { n -> Right(n.plus(1)) } }
  // there is no ensure to fail with specified error if predicate does not hold, maybe it is another method
  // TODO("what is the ensure equiv")

  // getOrElse or fold to extract values from the valid and invalid cases

}

// Excercise: Form Validation - implement simple HTML registration form via Map<String,String> and
// parse it to create a user object
fun validationExercise() {
  data class User(val name: String, val age: Int)
  // enforce that incoming data's name and age must be specified
  // name must not be blank, age must be a valid non negative int

  // if all pass return a user else return a list of the error messages
  // need to combine rules in sequence and in parallel
  // use either to combine in sequence failfast
  // use validated to combine them in parallel accum errors

  fun getValue(fieldName: String, m: Map<String, String>): Either<Nel<String>, String> =
    Either.cond(m.containsKey(fieldName), { m[fieldName]!! }, { "fieldName:$fieldName not present".nel() })

  val getName = { m: Map<String, String> -> getValue("name", m) }
  getName(mapOf("name" to "dave M")).p()
  getName(mapOf()).p()

  fun parseInt(s: String, fieldName: String = ""): Either<Nel<String>, Int> =
    Try { s.toInt() }.toEither().mapLeft {
      with(StringBuilder()) {
        if (!fieldName.isEmpty()) append("fieldName:$fieldName with ")
        append("value:$s is not an Int")
        toString()
      }.nel()
    }
  parseInt("1").p()
  parseInt("a").p()
  parseInt("a", "age").p()

  fun nonBlank(s: String): Either<Nel<String>, String> =
    if (s.isBlank()) "s:$s is blank".nel().left()
    else s.right()


  fun nonNegative(i: Int): Either<Nel<String>, Int> =
    if (i < 0) "i:$i cannot be less than 0".nel().left()
    else i.right()


  fun readName(m: Map<String, String>): Either<Nel<String>, String> =
    getValue("name", m)
      .flatMap(::nonBlank)

  fun readAge(m: Map<String, String>): Either<Nel<String>, Int> =
    getValue("age", m)
      .flatMap(::nonBlank)
      .flatMap { parseInt(it) }
      .flatMap(::nonNegative)

  fun <A, B> Either<A, B>.toValidated(): Validated<A, B> = fold({ it.invalid() }, { it.valid() })

  // fromEither or toValidated
  fun readUser(m: Map<String, String>) = Validated.applicative<Nel<String>>(Nel.semigroup())
    .map(Validated.fromEither(readName(m)), readAge(m).toValidated(), { (name, age) -> User(name, age) })

  "Final readUser".p()

  readUser(mapOf("name" to "tom", "age" to "34")).fix().map { it.p() }
  readUser(mapOf("age" to "-3")).p()

}

fun main(args: Array<String>) {
//  validatedInstances()
//  validatedAccumUsage()
//  validationExercise()
}
