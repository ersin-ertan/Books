/*
 Stream and cell types, map, merge, hold, snapshot, filter, lift, never, constant, primitives
 forward references with StreamLoop and CellLoop, making an accumulator with hold and snapshot

 Stream: of events
 Cells: value that changes over time

 arrows are streams, boxes are transformations on stream, modules(black boxes) are clouds(export import streams/cells)

 button click, event generated, fed into stream sClicked, sclicked contains nothing value of Unit, event propagates to
 map, transforms Unit into "", map produces new stream called sClearIt, event sClearIt propagates to text field, changes
 its text contents to contained value "".

 Stream<String> sClearIt = clear.sClicked.map(unit->"")

 event: propagation of async message from one part of a program to another
 stream: discrete events, event stream, observable, signal(clicks,touches, creation destroy, damage, coneccion establish/lost
 bookmarking site, adding, moving, deleting, zeroing a trip distance)

 2.3.0 Components of FRP system
 operation: function converting streams or cells to other streams or cells
 primitive: fundamental operation can't be expressd in terms of other operations. Implemented via functions.

 map , merge , hold , snapshot , filter , lift , never , constant , sample , and switch

 Combining primitives, separating io from logic, type safety, referential transparency(no io, no exceptions thrown unless
 caught, no external variable modification or read unless they are constants, no external state modification, not state
 kept between invocations, no external effects other than returned value)

 2.5.0 The Cell type: a value that changes over time
 gui text field, cells are passive, streams are active
 stream contains events that fire at discrete times, only has value on fire, cell always has value sampled at any time
 Cells model state, streams model state changes

 model these as a cell: mouse cursor position, object in game, state of polygon editing, vehicle speed, gps pos, time
 wifi network state, signal strength, temperature.

 label is completely controlled by the program, thus receives cell. Textfield receives a stream, because both program
 and user can modify it.
 Can construct cells with immutable values

 reverse textfield to label by reversing the cell value in the text field

 may merge streams, must all have same type. if either stream fires, event will apear on the output stream.

 2.6.1 Simultaneous Events
 FRP processing takes place in a transactional context(sSame for dbs) when a input value is pushed into a stream or cell
 State changes occur as a result of that input are performed within the same transaction. Sodium has no real sim,
 they are relative and processed one after each other. Ok for external events to run in a new transaction.
 Sim events are almost always caused by two streams that are modifications on a single input stream.

 Ex. Click object, it's selected. Click else, it deselects, and selects new.
 two events on two input streams merge combines with left and right
 combining function is not used in the case where input are not sim
 invoke merge via s1.merge(s2, func)

 s1.orELse(s2) variant of merge doesn't take a combining function. Left s1 event takes precedence and right s2 event is
  droped.

  Only ever be one event per transaction in a given steram, no event processing order, all occur in different streams within
  the same transaction.

  No concept of simultaneous events thus merge can't be guaranteed to act consistently, breaking compositionality.

  merge stores events in temp mem when input is done, then outputs an event if received more than one uses supplied func
  to combine them, else outputs single event


  2.7 The hold primitive: keeping state in a cell

  click a or b, will hold the last value in cell from stream(converts stream into a cell so cells value is the most recent)
  thus hold require an initial value to use as the cells value until first is received


  2.8 The snapshot primitive: capturing the value of a cell
  allow free flow of events, and only capture on trigger(button press)
  Snapshot primitive captures the value of a cell at the time when a stream event fires, can combine the stream and cell
  events with supplied function(withLatest, attach, tag)
 */
