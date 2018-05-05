package ch03

/*
3 Functors - sequences of operations within a context(list, option, ...) special cases like monads and applicative functors
are most common

Anything with a map method - rather than traversing the list, we are transforing all of the values inside in one go
Specify the function to apply and map applies it to every item, values change but list structrue remains same

listOf(2,3,4).map{ it+1 }

transform contents but leave some or none unchanged

list<circle> to list<star>
option<halfCircle> to option<halfStar>
either<pent,circle> to either<pent,star>

Because structure of context is unchanged, it can be called repeatedly to sequence multiple computations on contexts of
initial data structure
list().map.map.map

map sequenses computations on values ignoring complication of data type

future<circle> to future<star>

*/

