package A_GettingStartedWithParsing.A_LanguageApplicationsCrackedOpen

// Learn to recognize computer language(valid sentences), every language app has a parser(recognizer) lest it be code gen

// Multiple components in a multistage pipline analyzing/manimpulating an input stream.
// Valid input sequence to internal data structure, or sentence of another language.
// Reader recognizes input and builds an intermediate representation to feed the rest of the app.
// Generator emits output based on the IR, and info from intermediate stages(semantic analyzer)
// Kind of app dictates the pipline stages and hookup.

// Reader - builds data structure from one or more input streams, text or binary. Ex. config file readers, program analysis
// tools, class file loaders

// Generator - walks internal data struct, emits output. Ex. object-to-relational database mappers, object serializers,
// source code generators, web page generators.

// Translator/Rewriter - Reads text/binary input, emits output conforming to same or different language. A combined reader
// generator. Ex. old programming lang translator to modern, wiki to HTML, refactorers, profilers instrumenting code, log
// file report generaors, pretty printers, macro preprocessors. Assemblers/compilers get their own category.

// Interpreter - read, decode, execute instructions. Ex. Calculator, POP protocol servers, programing language implementations


// Parsing Input Sentences
// Stronger parsing patterns are more complicated and slower. Grammars((formal language specs)

// Keyphrases: Mapping grammars to recursive descent recognizers, recursive descent parsers, Backtracking parser,
// memoizing parsers, predicated parser


//Constructing Trees
// IR - processed version of input text, easily traversed. High or low level trees. Parse Tree(noisy) includes rules to
// recognize input, and the input itself. Used for building syntax highlighting editors.
// Abstract Syntax Tree AST for source code analyzers, transaltors because they're easier to work with.
// Ast has a node for every important token, uses operators as subtree roots.
//
// Ex. this.x = y
//
// [=]->[y]
//   \_>[.]->[this]
//        \_>[x]

// Ast implementation pattern depends on how you plan on traversing(Homogeneous AST), single object type per node and use
// normalized child list(nodes represent specific children by position)

// Multi node types Pattern, different nodes for addition and variable reference nodes. Common to track children with fields
// rather than lists,(Irregular Heterogeneous AST)


// Walking Trees
// Extract information/perform transformationsby traversing the IR/AST. Embed methods within each node class(Embedded
// Heterogeneous Tree Walker) or encapsulate those methods in an external visitor(External Tree Visitor). We can automate
// visitor construction like we do parser construction. Recognize with Tree Grammar, or Tree Pattern Matcher. A grammar
// describes the entire structure of all valid trees. A Pattern matchers focus on just the subtrees.


// Figuring out what the input means
// Analyze input to extract info relevant to generation(semantic analysis). For a given symbol ref x, what is it. A var,
// a method, its type, defined? Track all input symbols via symbol tables(dictionary mapping symbols to definitions)

// Semantic rules of language dictate which symbol table pattern to use. Four scoping rules: languages with single scope,
// nested scopes, C-style struct scopes, class scopes. Java has lots of semantic compile time rules for type compatibility
// between operators or assignment statements. A reader like a config file or java .class, file reader pipeline is done.
// For interpreter/translator, needs more stages.


// Interpreting Input Sentences
// Interpreters execute instructions stored in the IR but need other data struccts too like symbol table. Interpreter patterns
// are as equivalently capable, differences being the instruction set, execution effeciency, interactivity, ease of use,
// ease of implementation.


// Translating one language to another
// Compilers to machine code. Finally after ranslation, a generator emits structured text or binary. Output is a function
// of the input/semantic analysis. Reader and generator can be combined to a single pass if simple, else decouple the order
// to compute output phrases from emittion.Generators conform to a language, thus use a formal language tool to emit strucuterd
// text. An "unparser" template engine.