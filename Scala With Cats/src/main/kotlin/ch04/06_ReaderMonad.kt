package ch04

import arrow.core.Id
import arrow.core.Option
import arrow.core.getOrElse
import arrow.data.Reader
import arrow.data.fix
import arrow.data.flatMap
import arrow.data.map
import arrow.instances.monad
import arrow.typeclasses.binding
import p


// 4.8 The Reader Monad
// sequence operations that depend on input. Instances of reader wrap up functions of one argument, providing methods to
// compose them

// Ex dependency injection - a number of operations athat depend on external config, chained together using a reader to produc
// one large operation accepts the config as a param and runs our porgram in the order specified
data class Cat(val name: String, val food: String)

fun creatingUnpackingReaders() {
//   Reader<A,B> from function A->B


  // not the id wrapped value, see:
  // https://gitter.im/arrow-kt/Lobby?at=5af2dd7e1eddba3d04e402c8
  val catName: Reader<Cat, String> = Reader { cat -> Id(cat.name) }
  val catNameRun = catName.run { Cat("name", "food") }

  catName.p() // Kleisli
  catNameRun.p() // Cat(name=name,food=food)
}

fun composingReaders() {
  // why readers, because of map and flatmap methods respersent different kinds of function composition
  // create a set of readers that accept the same type of config, combine them with map and fm then call
  // run to injec the config at the end
  // map extends the computation in the reader by passing its result through a function

  val theCat = Cat("ScoobyDoo", "Food")

  val catName: Reader<Cat, String> = Reader { cat -> Id(cat.name) }
  val exclaim: Reader<Cat, String> = catName.map { s -> s.plus("!") }

  exclaim.run(theCat).p() // id(value=A)

  // flatmap is more interesting - we can combine readers that depend on same input type

  val feed: Reader<Cat, String> = Reader { cat -> Id("Feeding ${cat.food}") }

  // not right
//  val ef= exclaim.andThen { ex -> feed.map{ fe -> fe.plus(ex)} }

  val eff = exclaim.flatMap { ex -> feed.map { fe -> fe.plus(" to ").plus(ex) } }
  eff.run(theCat).p()

  val m = Reader().monad<Cat>().binding {
    feed.bind().plus(" to ").plus(exclaim.bind())
  }

  m.fix().run(theCat.copy("Felix")).p()
}

// classic use of readers to build a program that accepts a config as a param like login system
// two db, a list of valid users and list of password

// create a type alias DbReader for a reader that consumes a db as input
class Db(val usernames: Map<Int, String>, val passwords: Map<String, String>)
typealias DbReader<A> = Reader<Db, A>

fun hackingReaders() {

  fun findUsername(userId: Int): DbReader<Option<String>> =
    Reader { db -> Id(Option.fromNullable(db.usernames[userId])) }

  fun checkPassword(username: String, password: String): DbReader<Boolean> =
    Reader { Id(it.passwords[username].equals(password)) }

  fun checkLogin(userId: Int, password: String): DbReader<Boolean> =
    findUsername(userId).flatMap {
      it.map { username -> checkPassword(username, password) }
        .getOrElse { DbReader { Id(false) } }
    }

  // could not get syntax version to work
//  fun checkLogin1(userId: Int, password: String)/*: DbReader<Boolean>*/ =
//    Reader().applicative<Boolean>().just(false)
//  Reader { Id(it.passwords[it.usernames[userId]].equals(password)) }

  val users = mapOf(1 to "a", 2 to "b", 3 to "c")
  val passwords = mapOf("a" to "aa", "b" to "bb", "c" to "cc")
  val db = Db(users, passwords)

  checkLogin(1, "aa").run(db).p()
  checkLogin(4, "aa").run(db).p()


}

fun main(args: Array<String>) {
//  creatingUnpackingReaders()
//  composingReaders()
  hackingReaders()
}

// 4.8.4 when to use readers - tool for dependency injection, write steps of porgram as instances of reader, chain them
// together with map and flatmap and build a function that accepts the dependency as input

// usefule in sutuations where - we are constructing a batch program that can be represented by a function
// need to defer injection of a known param or set of params
// want to be able to test parts of the program in isolation

// for more advanced problems with lots of dependencies, or not easly reprresneted as a pure function other dep injctions better

// Kleisli Arrows - provide a more general form of reader generalises over type constructor of the result type