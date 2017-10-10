package A_Compilation

/*
Front end and back end

source language > front end > intermediate representation > backend > machine code

Front End: takes high level language program, produced intermediate language between source and target IR
Back end: takes IR produces target machine language

Front End:
- part of compiler analyzing input determining its semantic
- source language dependent(targ8et michine/language independent)
- decomposed into sequence of analysis phases
source language program > scanner > tokens > parser > AST > semantics > IR

Scanner: breaks input stream of chars into stream of tokens: identifiers, literals, reserved words, operators, separators
Parser: takes sequence of lexical tokens, parses against a grammar producing AST, making syntax implicit to source program
explicit.
Semantics Phase: semantic analysis: declaring names in a symbol table, looking up names as they are referenced determining
type, assigining types to expressions, assigining addresses or offsets to variables. Requires two passes over program.

Back End:
- part of compiler that takes IR producing a target machine program with same meaning
- is target language dependent(source independent)
- decomposed into sequence of synthesis phases
IR > codegen > target machine code > peephole > better target machine code > object > target machine program

Codegen Phase: chooses what target machine instruction to generate, using info from earlier phases
Peephole Phase: implements peephole optimizer, scans through generated instructions looking locally for wastefull
instruction sequences(branches to branches), unnecessary load/store pairs(to stack/register) then back to original location
Object Phase: linkes modules produced in code generation, constructs a single machine code executable

Middle End: might have an optimizer
jvm code > front end > IR > optimizer > back end > target machine program
Improve IR and collect info that back end may use:
- organize program into basic blocks, no branches out
- from basic block, compute next use info for variable lifetime, loop identification
- next use info can eliminate common sub expressions and constant folding(x+5 is now 9 because x is 4), register allocation,
which to keep which to free
- loop information, pulling loop invariants out, strength reduction ex. replacing multiplication ops by less expensive
addition ops

Advantages to Decomposition:
- reduces complexity (small modules)
- several individuals to work concurrently on separate parts, faster implementation time
- permits re-use

Jvm is an interpereter, compiles hotspots "critical methods" to native code(with inlining)
JIT compilation - compile each method to native code and cache that native code when method is first invoked.

Jvm is stack based(not register based)





*/