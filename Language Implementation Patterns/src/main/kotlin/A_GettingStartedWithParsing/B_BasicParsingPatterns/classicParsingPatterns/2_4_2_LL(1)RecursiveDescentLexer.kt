package A_GettingStartedWithParsing.B_BasicParsingPatterns.classicParsingPatterns

/*
Lexers derive a stream of tokens from a character stream by recognizing lexical patterns. Called scanners, lexical
analyzers, or tokenizers this pattern can recognize nested lexical structures like nested comments.

Lexer emits a sequence o tokens each with a token type and the text associated with it.
Ex. NAME is the identifier category, then token types for fixed string vocabulary symbols: COMMA, LBRACK, RBRACK.
Lexer throws out white space/comments, parser doesn't need them.

We write a method for each token definition, token T's definition becomes fun T().
To make the lexer look like an enumeration of tokens, define a fun called nextToken(), which uses the lookahead character
to route control flow to the next appropriate recognition method. Upon seing a letter, nextToken() calls a function to
recognize the identifier pattern.
*/
/*
private class Token {


    var lookaheadChar = 'a'
    val EOF = -1
    val EofToken = Token()
    val commentStartSequenceSequence = true
    fun COMMENT() {}
    fun T1() = Token()
    fun T2() = Token()
    val charsPredictingT1 = 'b'
    val charsPredictingT2 = 'c'
    fun consume() {
        lookaheadChar++ // some kind of increment
    }

    fun nextToken():Token {
        nextLookaheadChar@ while (lookaheadChar.toInt() != EOF) {

            if (commentStartSequenceSequence) {
                COMMENT()
                continue
            }

            return when (lookaheadChar) {
                ' ' -> {
                    consume()
                    continue@nextLookaheadChar
                }
                charsPredictingT1 -> T1()
                charsPredictingT2 -> T2()
            // ... charsPredictingTn return Tn()
                else -> throw IllegalStateException("No recognized lookahead char")
            }
        }
        return EofToken
    }
}

class MyLexer(val input:String)
class MyParser(val lexer:MyLexer) {
    fun startRule() {}
}

fun main(args:Array<String>) {

    val lexer = MyLexer("this is the input sentence")
    val parser = MyParser(lexer)
    parser.startRule()
}*/

class Token(val type:Int, val text:String) {
    override fun toString():String = "<'$text',${ListLexer.tokenNames[type]}>"
}

abstract class Lexer(val input:String) {
    var p = 0
    var curChar = input[p]

    companion object {
        val EOF:Char = (-1).toChar()
        val EOF_TYPE = 1
    }

    fun consume() {
        p++
        curChar = if (p >= input.length) EOF else input[p]
    }

    abstract fun nextToken():Token
    abstract fun getTokenName(tokenType:Int):String

}

class ListLexer(input:String):Lexer(input) {


    companion object {
        val NAME = 2
        val COMMA = 3
        val LBRACK = 4
        val RBRACK = 5
        val tokenNames = arrayOf<String>("n/a", "<EOF>", "NAME", "COMMA", "LBRACK", "RBRACK")
        val whiteSpace = listOf<Char>(' ', '\t', '\n', '\r')
    }

    fun isLetter() = curChar in 'a'..'z' || curChar in 'A'..'Z'

    override fun getTokenName(tokenType:Int):String = tokenNames[tokenType]

    override fun nextToken():Token {
        nextT@ while (curChar != EOF) {
            when (curChar) {
                in whiteSpace -> {
                    WS()
                    continue@nextT
                }
                ',' -> {
                    consume()
                    return Token(COMMA, ",")
                }
                '[' -> {
                    consume()
                    return Token(LBRACK, "[")
                }
                ']' -> {
                    consume()
                    return Token(RBRACK, "]")
                }
                else -> return if (isLetter()) NAME() else throw Error("invalid character: $curChar")
            }
        }
        return Token(EOF_TYPE, "<EOF>")
    }

    fun NAME():Token {
        return Token(NAME, buildString {
            do {
                append(curChar)
                consume()
            } while (isLetter())
        })
    }

    fun WS() {
        while (curChar in whiteSpace) consume()
    }
}

fun main(args:Array<String>) {

    var arg = arrayOf("[a, b]")

    val lexer = ListLexer(arg[0])
    var t:Token = lexer.nextToken()
    while (t.type != Lexer.EOF_TYPE) {
        println(t)
        t = lexer.nextToken()
    }
    println(t) // EOF
}