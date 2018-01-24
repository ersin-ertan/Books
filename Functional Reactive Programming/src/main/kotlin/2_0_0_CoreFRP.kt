// Stream and cell types, map, merge, hold, snapshot, filter, lift, never, constant, primitives
// forward references with StreamLoop and CellLoop, making an accumulator with hold and snapshot

// Stream: of events
// Cells: value that changes over time

// arrows are streams, boxes are transformations on stream, modules(black boxes) are clouds(export import streams/cells)

// button click, event generated, fed into stream sClicked, sclicked contains nothing value of Unit, event propagates to
// map, transforms Unit into "", map produces new stream called sClearIt, event sClearIt propagates to text field, changes
// its text contents to contained value "".

// Stream<String> sClearIt = clear.sClicked.map(unit->"")

// event: propagation of asynch message from one part of a program to another
// stream: discrete events, event stream, observable, signal(clicks,touches, creation destroy, damage, coneccion establish/lost
// bookmarking site, adding, moving, deleting, zeroing a trip distance)

// 2.3.0 Components of FRP system
// operation: function converting streams or cells to other streams or cells
// primitive: fundamental operation can't be expressd in terms of other operations. Implemented via functions.

// map , merge , hold , snapshot , filter , lift , never , constant , sample , and switch

// Combining primitives, separating io from logic, type safety, referential transparency(no io, no exceptions thrown unless
// caught, no external variable modification or read unless they are constants, no external state modification, not state
// kept between invocations, no external effects other than returned value)