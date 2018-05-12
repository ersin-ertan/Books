package ch03

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import p
import pp

fun main(args: Array<String>) {
//  changeData()
//  changeDataImmutable()
  lowCoupling()

}

// Advantages of immutability
// thread safety, low coupling, referential transparency, failure atomicity, compiler optimizations, pure functions

// Thread Safety

fun changeData() {
  class MyData(var data: Int = 0)

  val data = MyData()

  async(CommonPool) {
    for (i in 11..20) {
      data.data += i
      "First async: ".pp(); data.data.p()
      delay(200)
    }
  }
  async(CommonPool) {
    for (i in 1..10) {
      data.data++
      "Second async: ".pp(); data.data.p()
      delay(300)
    }
  }
  runBlocking { delay(3000) }
}

// data consistency is not ensured
// we could use locks with fear of deadlock

fun changeDataImmutable() {
  class MyData(val data: Int = 0)

  val data = MyData()

  async(CommonPool) {
    var copy = data.data
    for (i in 11..20) {
      copy += i
      "First async: ".pp(); copy.p()
      delay(200)
    }
  }
  async(CommonPool) {
    var copy = data.data
    for (i in 1..10) {
      copy++
      "Second async: ".pp(); copy.p()
      delay(300)
    }
  }
  runBlocking { delay(3000) }
}

// use of local variables within async, and a immutable val

// Low Coupling - (thread dependency between threads is coupling) avoid complexity
fun lowCoupling() {

  // this is coupling!!

  class MyData(var data: Int = 0)

  val data = MyData()

  async(CommonPool) {
    for (i in 11..20) {
      data.data += i
      "First async: ${data.data}".p()
      delay(50)
    }
  }
  async(CommonPool) {
    for (i in 1..10) {
      data.data++
      "Second async: ${data.data}".p()
      delay(30)
    }
  }

  runBlocking { delay(200) }
}

// referential transparency - expression always evaluates to the same value irrespective of context and other
// can replace a function with its return value

// Failure atomicity - failure in one thread cannot affect the state of another thread

// Caching - immutable objects can be easily cached to improve performance, thus avoid making repeated calls
// by caching the value(saves processing power) advantages:
// reduced overhead from server resources, increases perf of app via cached output, decreases cpu round trips
// fetching data from database by persisting data in memory, increases reliability

// compiler optimization - todo("Find out more")

// Pure Functions - idempotent

// Disadvantages of immutability - small datasets recreation doesn't matter