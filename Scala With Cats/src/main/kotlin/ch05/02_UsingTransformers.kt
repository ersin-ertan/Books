package ch05

// exercise - request is made with function and Response<Int> is returned
// but Response<A> = Future<Either<String,A>>
// rewrite this using monad transformer thus EitherT<Future,String,A>

// if db/map doesn't have requested data, return error message and include string info in error message
// fun get(who:String):Response<Int> = db.get(who) when{ Some -> EitherT.right(Future(ans)) None -> EitherT.left(Future("no ans"))

// need to combine responses and do logic with result thus for comprehension yield(r1 + r2) > 5

// need to take two names and print a message saying if they can do logic thus,
// fun report(w1:String, w2:String):String = Await.result( canDoLogic(w1, w2).value, 1.second) when {
// Left -> "Error", Right -> if (Right.value == true) "Ready" else Read.value == false "Not ready"

// Summary - monad transformers eliminate need for nested for comprehensions and pattern matching when working with stacks
// of nested monads. Each transformer provides code to merge its relaetd monad with others, transformer is a data structure
// wraping a monad stack, giving it map and flatmap methods to un and repack the stack

// type sigs of monad transformers are writen inside out, good to type alias when working with deeply nested monads

// great for sequencing computation using flatmap
