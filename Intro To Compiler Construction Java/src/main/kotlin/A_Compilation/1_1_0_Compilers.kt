package A_Compilation

/*
 Compiler: program translates source program of a high level language, to a equivalent target program of lower level
 Source language program > compiler > machine code program

 Semantic preserving: translation has the same behaviour as original(translation is the compilation)
 Programming Language: programmer writes to control behaviour of machine specified in three steps:
 - tokens or lexemes are described. Ex. if, +, 4, 'c', foo, this is the natural language
 - Syntax of language and constructs. Ex. classes, methods, statements, expressions. Less flexible than natural language
 - Specify meaning, semantic of constructs, described in English

 Extra, java specifies static type rules.

 Computers machine language(instruction set) designed for easy computer interpretation. Consists of instructions and
 operands which each operand occupies on or more bytes for easy access. Instruction set and behaviour are referred as
 its architecture. MIPS is reduced instruction set computer (RISC) small instructions, at least 32 registers. Registers
 (cpu)are faster than memory locations, ram/hdd. JVM

 Traditional compiler:
 - maps names to memory addresses, stack frame offsets, and registers
 - generates linear sequence of machine code instructions
 - detects errors in program that can be detected in compilation

 Interpretation: where high level language program(loaded in interpreter) is executed directly, ex. unix shell bash.
 Input      ___ Interpreter -> results
 Program    _/
 Performance hit, machine code is faster(must parse/analyse statements every time it executes), better to do all at once
 More secure(obfiscated)
*/