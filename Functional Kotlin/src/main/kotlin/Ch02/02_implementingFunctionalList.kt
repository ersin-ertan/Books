package Ch02

import Ch02.FunList.Cons
import Ch02.FunList.Nil
import p

sealed class FunList<out T> {
  object Nil : FunList<Nothing>()
  data class Cons<out T>(val head: T, val tail: FunList<T>) : FunList<T>()

  fun <R> fold(init: R, f: (R, T) -> R): R {
    tailrec fun go(list: FunList<T>, init: R, f: (R, T) -> R): R = when (list) {
      is Cons -> go(list.tail, f(init, list.head), f)
      is Nil -> init
    }
    return go(this, init, f)
  }

  fun forEach(f: (T) -> Unit) {
    tailrec fun go(list: FunList<T>, f: (T) -> Unit) {
      when (list) {
        is Cons -> {
          f(list.head)
          go(list.tail, f)
        }
        is Nil -> Unit//Do  nothing
      }
    }
    go(this, f)
  }

  fun reverse(): FunList<T> = fold(Nil as FunList<T>, { acc, i -> Cons(i, acc) })

  fun <R> foldRight(init: R, f: (R, T) -> R): R = reverse().fold(init, f)

  fun <R> map(f: (T) -> R): FunList<R> = foldRight(Nil as FunList<R>, { tail, head -> Cons(f(head), tail) })
}

fun main(args: Array<String>) {
  val nums = Cons(1, Cons(2, Cons(3, Nil)))
  val nums1 = intListOf(1, 2, 3)
  nums1.forEach { it.p() }

  nums.fold(10, { acc, i -> acc + i }).p()
}

// better initializer
fun intListOf(vararg nums: Int): FunList<Int> =
  if (nums.isEmpty()) Nil
  else Cons(nums.first(), intListOf(*nums.drop(1).toTypedArray().toIntArray()))

// fun list as an ADT - either a Nil or Cons

// comparing speed, funList is 10x slower than fold on list