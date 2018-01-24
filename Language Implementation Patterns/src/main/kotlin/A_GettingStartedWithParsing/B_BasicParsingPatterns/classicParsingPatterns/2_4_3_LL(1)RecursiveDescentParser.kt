package A_GettingStartedWithParsing.B_BasicParsingPatterns.classicParsingPatterns

/*
Analyses the syntactic strcuture of the token sequence of a phrase using a single lookahead token

Parsing decisions use a single token of lookahead. Weakest recursive descent parser but easiest to understand/implement
Parser tests current lookahead token against the alternatives in the lookahead sets, which is a set of tokens that can
begin a particular alternative.

Computing lookahead sets
Using two computations: FIRST and FOLLOW, (what tokens can possible start phrases beginning at this alternative?
An alternative that begins with a token refernce, it's lookahead set is just that token.
state: 'if' ...
     | 'while' ...
     | 'for' ...
     ;

If an alternative begins with a rule reference instead of a tokon reference, lookahead set is whatever begins with rule
body_element
    : state     // lookahead for    {if, while, for}
    | LABEL ':' // lookahead is     {LABEL}
    ;

The lookahead set for the first alternative is the union of the lookahead sets from stat. Will get complicated with empty
alternatives, consider:
optional_init
    : '=' expr
    | // empty alternative
    ;
The lookahead for the first alternative is =, but the empty alternative it is the set of tokens following references to
optional_init:

decl: 'int' ID optional_init ';' ;
arg : 'int' ID optional_init ;
func_call:  Id '(' arg ')' ;

In this case, ; follows optional_int in decl so we know that at least ; is in the set. Rule arg also references
optional_init, but there is no token following it. We have to include whatever follows arg.Token ) can follow a reference
to arg, so tokens ';' and ')' can follow a reference to optional_init.

What is going on here is computing the possibilities of a route thus, all chains of ambiguity must be followed back to
their terminal known computations.

Deterministic Parsing Decisions
LL parsing decisions work only when the lookahead sets predicting the alternatives are disjoint(no tokens in common).
A rule whole parsing decision is deterministic because the single lookahead token uniquely predicts which alternative
to chose. Match -3, 4, -2.1 or x, salary, username, and so on
expr: '-'? (INT|FLOAT)
    | ID
    ;
Upon a -, INT, or FLOAT, rule expr knows to predict the first alternative. Upon ID, it knows to predict the second alt.

If the lookahead sets overlap, though, the parser is nondeterministic - it cannot determine which alternative to choose:
expr: ID '++' // matches x++
    | ID '--' // matches x--
    ;

Two alternatives begin with the same token(ID), the token beyond dectates which alternative phrase is approaching.
expr is LL(2), an LL(1) parser can't see past the left common prefix with only on symbol o lookahead, without seeing the
suffix operator after the ID. the parser cannot predict which alternative will succeed. To handle grammatical constructs,
tweak your grammar or use LL(k). by left-factoring out the common ID left prefix, we get an LL(1) grammar that matches
the same language.

expr: ID ('++'|'--') ; // match x++ or x--
If you build parsers by hand, spend time getting good at computing lookahead sets, else let parser generator do it.

Implementation
Build ListParser, to go with the lexer, here is the grammar:
list        : '[' elements ']' ;        // match bracketed list
elements    : element (',' element)* ;  // match comma separated list
element     : NAME | list ;             // element is name or nested list
*/
abstract class Parser(val input:Lexer) {

    init {
        consume()
    }

    lateinit var lookahead:Token

    fun match(x:Int) =
            if (lookahead.type == x) consume()
            else throw Exception("Expecting: ${input.getTokenName(x)}, found: $lookahead")

    fun consume() {
        lookahead = input.nextToken()
    }

}

class ListParser(input:Lexer):Parser(input) {

    fun list() {
        match(ListLexer.LBRACK)
        elements()
        match(ListLexer.RBRACK)
    }

    fun elements() {
        element()
        while (lookahead.type == ListLexer.COMMA) {
            match(ListLexer.COMMA)
            element()
        }
    }

    fun element() = when (lookahead.type) {
        ListLexer.NAME -> match(ListLexer.NAME)
        ListLexer.LBRACK -> list()
        else -> throw Error("Expecting name or list; found: $lookahead")
    }
}
// elements and element use lookahead to make parsing decisions.
// elements, COMMA predicts entering the (...)* subrule.
// element, NAME predicts the first alternative
// LBRACK predicts the second alternative

// to support this concrete class we need to build some support code in abstract Parser class, two state variables for:
// input tken stream and lookahead buffer. We can usa single lookahead variable, token
// We could also use a big token buffer holding all of them, track index into the buffer instead of using a field, but
// we assume for this example that we cannot buffer input.

// Need metcchods to compare expected tokens against the lookahead symbol and consume input
// LL(1) parsers are the easiest way to learn about parsers, but we really need more than a single token of lookahead