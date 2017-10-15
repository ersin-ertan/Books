package A_GettingStartedWithParsing.B_BasicParsingPatterns.classicParsingPatterns


/*
Analyzes the syntactic structure of the token sequence of a phrase using k>1 lookahead tokens

Strength of a recursive descent parser depends entirely on the strength of its lookahead decisions. Single token lookahead
is weak, we must contort grammars to make them LL(1). Larger(fixed) buffers is strong enough for computer languages.
Including config files, data formats, network protocols, graphics languages and programming languages.

Motivating the need for more lookahead
Lets augment list of names grammar, we want to recognize input like: [a, b=c,[d,e]], to accommodate the change, we add
an alternative to element matching assignments:
list    : '[' elements ']' ;
elements: element (',' element)* ;
element : NAME '=' NAME     // the matched assignment
        | NAME
        | list
        ;

New alternative renders element non-LL(1) since the first two alternatives start with the same NAME token. We need two
lookahead tokens to distinguish the alternatives. Is it an assignment or a name. If NAME =, parsing decision should
predict the first alternative(assignment). Else parser should predict second alternative: Ex.
element for [a,b=c]
   [         a  ,      b = c]       or    [a,       b =     c]
consumed  lookahead                     consumed  lookahead

Using LL(1) we rewrite element to look like
element : NAME ('=' NAME)?
        | list
        ;

This (..)? optional subrule version works but is less clear. Similar situations occur in real grammars.

Building a circular lookahead buffer
To (simply)provide lots of parser lookahead is to buffer up all input tokens. Input cursor can be an integer index.
p into that buffer, execute p++ to consume token , the next k tokens of lookahead would be tokens[p]..tokens[p+k-1] which
works for finite and reasonably small input, not infinite token streams(network sockets)

When we increment p, add a token to the end of the buffer. Fixed buffer size is a complication so we add tokens in a
circular fashion:  p ranges from =0..k-1
A circular buffer is one where indexes that fall off the end wrap to the beginning. If 2 is the accepting state, then
3 would wrap around to 0. Modulo expression p%3 bwraps indexes for a buffer of size 3

Ex. for [a,b=c]
[a,     ba,     b=,     b=c     ]=c ...
012      p        p     p        p
p

for each increment in p, fill its prev pos with the next items in the buffer.

Implementation
The list of names language augmented with assignments from the previous section. First we'll build the lookahead
infrastructure, then we'll implement LL(2) rule element.
*/



abstract class Parser1(val input:Lexer, val k:Int) {
    // k is the lookahead size
    var p = 0 // circular index of next token pos to fill
    val lookahead = Array<Token>(k) { input.nextToken().also { p = (p + 1) % k } }

    fun consume() {
        lookahead[p] = input.nextToken()
        p = (p + 1) % k
    }

    fun LT(i:Int):Token = lookahead[(p + i - 1) % k] // circular fetch
    fun LA(i:Int):Int = LT(i).type
    fun match(x:Int) = if (LA(1) == x) consume() else throw Error("Expecting: ${input.getTokenName(x)} Found: ${LT(1)}")

}

class LookaheadLexer(input:String):Lexer(input) {

    override fun getTokenName(tokenType:Int):String = LookaheadLexer.tokenNames[tokenType]

    override fun nextToken():Token {
        while (curChar != Lexer.EOF) {
            if (isWhiteSpace) advance()
            return when (curChar) {
                ',' -> {
                    consume()
                    Token(COMMA, ",")
                }
                '[' -> {
                    consume()
                    Token(LBRACK, "[")
                }
                ']' -> {
                    consume()
                    Token(RBRACK, "]")
                }
                '=' -> {
                    consume()
                    Token(EQUALS, "=")
                }
                else -> if (isLetter) name() else throw Error("invalid character: $curChar")

            }
        }
        return Token(Lexer.EOF_TYPE, "<EOF>")
    }

    /** name : LETTER+ ; // name is sequence of >=1 letter  */
    fun name():Token = Token(NAME, buildString {
        do {
            append(curChar)
            LETTER()
        } while (isLetter)
    })

    /** LETTER   : 'a'..'z'|'A'..'Z'; // define what a letter is (\w)  */
    fun LETTER() = if (isLetter) consume() else throw Error("expecting LETTER; found $curChar")

    companion object {
        var NAME = 2
        var COMMA = 3
        var LBRACK = 4
        var RBRACK = 5
        var EQUALS = 6
        val tokenNames
            get() = arrayOf("n/a", "<EOF>", "NAME", ",", "[", "]", "=")
    }

    override val isWhiteSpace
        get() = when (curChar) {
            ' ', '\t', '\n', '\r' -> true
            else -> false
        }

    val isLetter
        get() = curChar in 'a'..'z' || curChar in 'A'..'Z'
}

class LookaheadParser(input:Lexer, k:Int):Parser1(input, k) {

    /** list : '[' elements ']' ; // match bracketed list */
    fun list() {
        match(LookaheadLexer.LBRACK)
        elements()
        match(LookaheadLexer.RBRACK)
    }

    /** elements : element (',' element)* ; // match comma-separated list */
    fun elements() {
        element()
        while (LA(1) == LookaheadLexer.COMMA) {
            match(LookaheadLexer.COMMA)
            element()
        }
    }

    fun element() = when (LA(1)) {
        LookaheadLexer.NAME -> {
            if (LA(2) == LookaheadLexer.EQUALS) {
                match(LookaheadLexer.NAME)
                match(LookaheadLexer.EQUALS)
            }
            match(LookaheadLexer.NAME)
        }
        LookaheadLexer.LBRACK -> list()
        else -> throw Error("Expecting name or list Found ${LT(1)}")
    }
}

fun main(args:Array<String>) {
    val arg = "[a,b=c,[d,e]]"   // completes silently
    val argX = "[a,b=c,,[d,e]]" // Exception in thread "main" java.lang.Error: Expecting name or list - Found <',',COMMA>
    val lexer = LookaheadLexer(arg)
    val parser = LookaheadParser(lexer, 2)
    parser.list()
}