package A_GettingStartedWithParsing.A_LanguageApplicationsCrackedOpen

// Choosing patterns and assembling applications
// Chose important and often used ones, ANTLR. Either implement DSLs, or process and translate general purpose programming
// languages. Implement graphics and mathematics languages, few build compilers and interpreters for full programming languages.
// Build tools to refactor, format, compute software metrics, find bugs, instrument, or translate to another high level.

// Patterns to build compilers are also critical to implementing DSLs, or even processing general purpose languages.
// Symbol table management is the bedrock of most language applications. Parsers are the key to analyzing syntax, symbol
// table is the key to understanding semantics of input. Syntax what, semantics how.

// Patterns for reading input, analyzing input, interpreting input, and generating output. Basic architecture combines
// lexer and parser patterns, heart of Syntax directed interpreter, and syntax directed translator. Recognize input sentences,
// then call a method which executes or translates them. For an interpreter, calling some implementation like assign() or
// drawLine(). For translator, means printing an output statement based on symbols from the input sentence.

// Common architecture create an AST from input (tree construction actions in the parser) instead of trying to process
// the input on fly. Use AST to access input multiple times without rephrasing it. Tree based interpreter revisits AST
// nodes all the time as it executes while loops. AST can store information that we compute at various stages of app
// pipeline. Annotate the AST with pointers into the symbol table, to tell what kind of symbols the AST node represents,
// and if it is an expression, what the result type is.

// With that, generate a report via a final pass over the AST to collect and print information. Translator via Translating
// Computer languages, and generating DSLs with Templates. A simple generator walks the AST directly print output staments only
// if output and input statement order are equal. To be flexible, construct an output model of strings, templates, specialized
// output objects. Build an AST for flexibility.