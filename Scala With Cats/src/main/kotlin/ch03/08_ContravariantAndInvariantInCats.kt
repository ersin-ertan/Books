package ch03

import arrow.instances.StringShowInstance

/*
Contravariant and Invariant in Cats

there exists contravariant<F<*>> fun contramap ...
and invariant<F<*>> fun imap ...
*/

val showString = object : StringShowInstance {}
//val showSymbol = showString.
// arrow doesn't have contravariant and invariant typeclasses, yet