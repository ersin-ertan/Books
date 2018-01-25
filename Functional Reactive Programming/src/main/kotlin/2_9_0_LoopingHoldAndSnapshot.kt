/*

 2.9 Looping hold and snapshot to create an accumulator

 Accumulated value is kept in the cell. Forward references, accumulate deltas. Value loop where value is defined directly
 through other variables in terms of itself.
 CellLoop is an immutable variable that you assign once through its loop method, but can reference it before it's assigned.
 Only purpose is to make forward references possible, and allow cycles in variable references.

 2.9.2 Constructing FRP in an explicit transaction

 Construct FRP logic under a single explicit transacion, pass a lambda to a function that opens and closes a resource
 for you.

 2.9.3 Accumulator code
 via hold, snapshot, and loops for forward refrence, or use accum and collect

 2.9.4 Does snapshot see the new value or the old value?
 State changes are made atomically. Snapshot always sees the old value, you can view this as ss sees value as it was
 at beginning of transaction, hold updates are performed atomically at the end of the transaction.

 Hold has an implicit delay, instead of non delaying hold use Operational.updates() and merge primitive to capture the update of
 a cell in the current transaction. or use Operational.defer() create new transaction.


Lift in functional programming: make a function that operates on values into one that operates on type of container.
Lifting the function from values to types
*/