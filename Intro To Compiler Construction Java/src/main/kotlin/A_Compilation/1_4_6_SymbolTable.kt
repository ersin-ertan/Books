package A_Compilation

/*
During semantic analysis, compiler must construct and maintain a symbol table in which it declares names, has nested
scope for declared names, thus the symbol table must behave like a pushdown stack.

Symbol table is represented as a single linked list of objects extending Context, each object in the list is an area of
scope, containing mappings from names to definitions. Context objects maintain three pointers: to object representing the
surrounding context, one for compilation unit context(at root), one for the enclosing class context.

CompilationUInitContext object for scope comprising the program, the entire compilation unit.
ClassContext for scope of class declaration, has a reference to the defining class type to determine where we are(for
compiler) for accessibility
MethodContext(subclass of LocalContext) for scopes of methods and constructors, also of a block. Local variables names
are declared and mapped to LocalVariableDefn class.
*/