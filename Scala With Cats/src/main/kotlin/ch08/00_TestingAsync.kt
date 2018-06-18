package ch08

import arrow.core.Option
import arrow.core.Some
import arrow.syntax.collections.optionTraverse

// Case Study: Testing Asynchronous Code
// how to simplify unit tests for async code by making them sync
// Ill try using Option instead of future

interface UptimeClient {

  fun getUptime(hostname: String): Option<Int>
}

class UptimeService(val client: UptimeClient) {

  fun getTotalUptime(hostnames: List<String>): Option<Int> =
    hostnames.optionTraverse { client.getUptime(it) }.map { it.sum() }
}

// modeled as a interface because we want to stub it out in a unit test, wrie test allowing dumy data

class TestUptimeClient(val hosts: Map<String, Int>) : UptimeClient {

  override fun getUptime(hostname: String): Option<Int> =
    Some(hosts.getOrElse(hostname, { 0 }))
}

// want to test ability to sum values, regardless of source
fun testTotalUptime() = {
  val hosts = mapOf("host1" to 10, "host2" to 6)
  val client = TestUptimeClient(hosts)
  val service = UptimeService(client)
  val actual = service.getTotalUptime(hosts.keys.toList())
  val expected = hosts.values.sum()
//  assert(actual == expected)
} // code doesn't compile because actual results are in type Option thus cant compare directly
// could alter test to async, but make service code sync so test works without mod

// 8.1 Abstracting over Type Constructors
// implement two versions of UptimeClient: async for production and synchronous for unit tests
interface ProductionUptimeClient : UptimeClient {
  override fun getUptime(hostname: String): Option<Int>
}

//interface UptimeClient{ fun getUptime(hostname:String):???}

interface Test1UptimeClient : UptimeClient {
//  override fun getUptime(hostname: String): Int
  // want to retain int from each type but throw away Future, thus Identity type Id
  // can wrap types in a type constructor without changing meaning

  // write a trait defyinition for uptimeclient that accepts a type constructor F<_> as param
  // extend it wih two traits production and test that bind F to Option and Id
  // write out the method signatures for getUptime in each case to verify compile
}

interface UptimeClient1<F> {
  fun getUptime(hostname: String): F//<Int>
}
/*

interface Produ:UptimeClient1<ForOption>{
  override fun getUptime(hostname: String):Option<Int>
}

interface testUpt:UptC<Id>{
  ...Id<Int> // since id is int we can write Int, but is that so in kotlin
}*/

//class TestUptimeClient2(val hosts:Map<String,Int>):UptimeClient1<Id>{
//  override fun getUptime(hostname: String): ForId = hosts.getOrDefault(hostname, 0)
//}

// 8.2 Abstracting Over Monads
// rewrite UptimeService to abstract over two types of UptimeClient

//class UptimeService<F<_>>(client:UptimeClient<F>)){
//  fun getTotaluptime(hostname: String:List<String>):F<Int> = ???
//}

//class UptimeService<F<_>>(client:UptimeClient<F>))(implicit a:Applicative<F>{
//  fun getTotaluptime(hostname: String:List<String>):F<Int> =
//  hostnames.traverse(client.getUptime).map(it.sum())
//}

// TODO("Understand")

// Travers only w orks on seq of values that have applicative, we were with List<Future<Int>>
// but with List<F<Int>> we nee to show the compiler that F has an applicative
// now F binds to Id to allow sync

// Summary - Applicative type class to abstract over async and sync, functional abstractions
// to ignore impl details

// Stack of computational type classes like functor, applicative, monad, traverse, provide abstract
// implementations of patterns like mapping, zipping, sequencing, itteration
// and math laws on types ensure they work together consistent to our semantic

// use least powerful type class you need. if you need flatMap, swap for monad, or abstract over sequence types
// use travers, or Applicative Error, MonadError for failure models

// next is map-reduce-parallel processing