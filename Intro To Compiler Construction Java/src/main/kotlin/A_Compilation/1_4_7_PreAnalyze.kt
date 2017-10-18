package A_Compilation

/*
preAnalyze is a first pass at type checking, builds part of the symbol table at the top of AST, declare imported types
and types introduced by class declarations, and to declare members declared in those classes. First pass is for declaring
names that may be referenced before they are defined. J-- has doesn't do nesting classes, thus no need for descending into
methods.

analyze after pre, to build symbol table, decorating AST with type info enforcing type rules and:
- type checking: computes teh type for every expression, check its type when a type is required
- accessibility: enforces accessibiblity rusel(expressed by modifiers, public, potected, private) for types/members
- member finding: finds members(messages in message experssions, based on signature, and fields in field selections)
in types, only compile tyme members name is located, polymorphic messages are determined at run time
- tree rewriting: does a certain amount of AST sub tree rewriting, implicit field selections(denoted by identifiers that
are fields in the current class) are made explict and field and variable initializations are rewritten as assignment
statements after names have been declared
*/