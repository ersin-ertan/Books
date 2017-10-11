package A_Compilation

/*
                                                    v/----compilationUnit()---<\
Source language program > scanner <-nextToken()-Parser-> AST <_ preAnalyze()<|------|
                                                              \_ analyze()<--| main |
                                                              \_ codegen()<--|______|

Each node in the ast is a object reflecting the linguistic component or operation. JCompilationUnit is root as the program
being compiled, has subtrees for package names, list of imported types list of type(class) declarations. Object type
JMultiplyOp in the AST, represents multiplication operations. Two subtrees, two operands. Leaves of tree, JVariable, and
objects of constant literals. Each node in the AST defines three methods, performing a task on the node, and recursively
it's sub trees.
- preAnalyze(Context context) defined only for types of nodes near the top of AST(j-- doesn't implement nested classes).
Declares imported types, defined class names and class member headers(method headers and fields) Required because method
bodies may make forward refrences to names declared later in input. Context argument is string of Context(or subtype)
objects representing compile time symbole table of declared names/definitions.
- analyze(Context context) defined over all types of AST nodes. When invoked on a node, method declares names in symbol
table, and converts local variables to offsets in a method's run-time local stack frame where local variables reside.
- codgen(CLEmitter output) for generating JVM code for that node, applied recursively generating code for any sub trees.
Output arg is an abstraction of the output .class file

Once Main created the scanner and parser:
- Main sends a compilationUnit() message to the parser, which parses the program via recursive descent to produce AST
- Main sends preAnalyze() message to root node(object JCompilationUnit) of the AST. preAnalyze() recusively descends
the tree to the class member headers for declaring types and teh class member in the symbol table context.
- Main sends analyze() message to root JCompilationUnit node, analyze() recursively descends the tree all the way down
to leaves, declaring names and checking types.
- Main sends codegen() message to root JcompilationUnit node, codgen() recurzively descends the tree to leaves, generating
JVM code. Start of each class declaration, codegen() creates a new CLEmitter object for representing target .class file
for that class; end of each class declaration codegen() writes out the code to .class file on file system.
- Compiler is done with work, errors in any phase will accumulate until the phase is done halting entire process.

*/