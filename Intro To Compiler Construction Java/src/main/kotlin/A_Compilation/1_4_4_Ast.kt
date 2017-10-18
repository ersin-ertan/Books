package A_Compilation

// Another representation of source program, makes syntactic structures explicit which was once implicit.

// All classes in the e-- compiler that are used to represent nodes in the AST extend the abstract JAST and have names
// beginning with the letter J, each implement three methods for compilation

// preAnalyze() for declaring types and class members in the symbol table
// analyze() for declaring local variables and typing all expressions
// codegen() for generating code for each sub tree


/*


                 /imports> [  ] -> TypeName -> java.lang.System
JCompilationUnit -packageName> TypeName -name> pass
                 \typeDeclarations> JEClassDeclaration -name> String -> HelloWorld
                                                       \mods>[ ] -> public
                                                       \classBlock> [ ] -> JMethodDeclaration -name> String -> main
                                                                                              \mods> [] -> public/static
                                                                                              \returnType> Type.VOID
                                                                                              \body>
\body> JBlock -> [ ]-> JStatmentExpression -expr> JMessageExpression -messageName> println
                                                                     \target> JFieldSelection -target> JVariable -> System
                                                                      \                        \field> out
                                                                       \arguments> [ ] -> STRING_LITERAL -> Hello, world
*/