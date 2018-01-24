package A_Compilation

/*
j-- names and values have types, indicating how something can behave, is statically typed, compiler must determine the
types of all names and expressions, thus we need representation for types, java uses objects of type Class

j-- encapsulates these via Type, method, constructor, field member, and the TypeName/ArrayTypeName for the parser to know
about the name before the type. During analysis phase of compilation, type denotations are resolved: looked up in the
symbol table and replaced by the actual Types they denote
*/