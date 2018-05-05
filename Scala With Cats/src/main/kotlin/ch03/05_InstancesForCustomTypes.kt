package ch03

import arrow.Kind
import arrow.core.Option
import arrow.typeclasses.Functor

// 3.5.3 Instances for custom types

// although the type sig is ok, it doesn't work because the way arrow handles kind types is by using
// Kind<ContainerType, ContentType> instead of ContainerType<ContentTYpe> thus the functor is already using Kind in the
// map signature.

val optionFunctor: Functor<Option<*>> = object : Functor<Option<*>> {
    override fun <A, B> Kind<Option<*>, A>.map(f: (A) -> B): Kind<Option<*>, B> = this.map(f)
}

// sometimes we need to inject dependencies into our instances, custom Functor for Future
// account for implicit executionContext parameter on future.map, we can't add extra parameters to functor.map
// so we have to account for the dependency when we create the instance
// because in scala the dependency would be resolved via implicits, we would be required to pass one in using kotlin

// 3.5.4 Exercise branching out with functors - write a functor for the binary tree data type

sealed class Tree<out> {
    class Branch<A>(val left: Tree<A>, val right: Tree<A>) : Tree<A>()
    class Leaf<A>(val value: A) : Tree<A>()

}

object TreeInstance {
    fun <A> branch(left: Tree<A>, right: Tree<A>): Tree<A> = Tree.Branch(left, right)
    fun <A> leaf(value: A): Tree<A> = Tree.Leaf(value)
}

// TODO reevaluate the docs for making your own functor, might not be important now

// using type projections for compileability
//val ft = object : Functor<Tree<*>> {
//    override fun <A, B> Kind<Tree<*>, A>.map(f: (A) -> B): Kind<Tree<*>, B> = when (this) {
//        is Tree.Branch<*> -> Tree.Branch( map(this.left)(f),  map(this.right)(f))
//        is Tree.Leaf<*> -> Tree.Leaf(f(this.value))
//        else -> { }
//    }
//}

// Branch(Leaf(10), Leaf(30)).map { it*2 }

//val t = TreeInstance.leaf(100).map{ it*2 }