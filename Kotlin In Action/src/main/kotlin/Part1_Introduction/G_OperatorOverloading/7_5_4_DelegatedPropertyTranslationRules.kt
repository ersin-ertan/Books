package Part1_Introduction.G_OperatorOverloading

// Delegated property translation rules

// instance of MyDelegate will be stroed in ahidden property of type KProperty, thus
// private vale <delegate> = MyDelegate()
// var c:Type
// set(value:Type) = <delegate>.setValue(c,<property), value)
// get() = <delegate>.getValue(c, <property>)

// inside every access to the property the corresponding get and set values methods are called

// thus instead of a property access, get and set value function on the <delegate> are called
// allowing you to coustomize where the value fothe property is stored/retrieved (map, db, cookies)
// and for access - validation, change notifications, etc