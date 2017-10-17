package A_GettingStartedWithParsing.B_BasicParsingPatterns.classicParsingPatterns.practice

internal class RDL1 {

    class Token(val type:Int, val text:String) {
        override fun toString():String = "<'$text',${ListLexer.TOKEN_NAMES[type]}>"
    }

    abstract class Lexer(val input:String) {

        var p = 0
        var c = input[p]

        companion object {
            const val EOF = (-1).toChar()
            const val EOF_TYPE = 1
        }

        fun advance() {
            c = if (++p >= input.length) EOF else input[p]
        }

        fun consume() {
            advance()
            if (isWhiteSpace()) consume()
        }

        abstract fun isWhiteSpace():Boolean
        abstract fun nextToken():Token
        abstract fun getTokenName(type:Int):String
    }

    class ListLexer(input:String):Lexer(input) {

        companion object {
            const val LBRACKET = 2
            const val RBRACKET = 3
            const val COMMA = 4
            const val NAME = 5
            val TOKEN_NAMES = arrayOf<String>("n/a", "<EOF>", "LBRACKET", "RBRACKET", "COMMA", "NAME")
            val WHITE_SPACE = arrayOf<Char>(' ', '\t', '\n', '\r')
        }

        override fun getTokenName(type:Int):String = TOKEN_NAMES[type]
        override fun isWhiteSpace():Boolean = c in WHITE_SPACE
        override fun nextToken():Token {
            while (c != EOF) {
                if (isWhiteSpace()) consume() // self looping
                return when (c) {
                    '[' -> {
                        consume()
                        Token(LBRACKET, "[")
                    }
                    ']' -> {
                        consume()
                        Token(RBRACKET, "]")
                    }
                    ',' -> {
                        consume()
                        Token(COMMA, ",")
                    }
                    else -> if (isLetter()) NAME() else throw Exception("Invalid char: $c")
                }
            }
            return Token(EOF_TYPE, "<EOF>")
        }

        fun isLetter() = c in 'a'..'z' || c in 'A'..'Z'
        fun NAME():Token = Token(NAME, buildString {
            do {
                append(c)
                consume()
            } while (isLetter())
        })
    }

    fun test() {
        val input = "[a   , bname]"
        val lexer = ListLexer(input)
        var token = lexer.nextToken()
        while (token.type != Lexer.EOF_TYPE) {
            println(token)
            token = lexer.nextToken()
        }
        println(token) // EOF
    }
}

fun main(args:Array<String>) {
    RDL1().test()
}