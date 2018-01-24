package A_GettingStartedWithParsing.B_BasicParsingPatterns.classicParsingPatterns.practice

internal class RDP1k {

    abstract class Parser(val lexer:RDL1.Lexer, val k:Int) {

        var p = 0
        var lookahead = Array<RDL1.Token>(k) { lexer.nextToken().also { p = (p + 1) % k } }

        fun consume() = run { lookahead[p] = lexer.nextToken().also { p = (p + 1) % k } }

        fun LT(i:Int):RDL1.Token = lookahead[(p + i - 1) % k]
        fun LA(i:Int):Int = LT(i).type
        fun match(type:Int) = if (LA(1) == type) consume() else throw Error("Expecting: ${lexer.getTokenName(type)} Found: ${LT(1)}")
    }

    class LookaheadLexer(input:String):RDL1.Lexer(input) {

        companion object {
            const val NAME = 2
            const val COMMA = 3
            const val LBRACKET = 4
            const val RBRACKET = 5
            const val EQUALS = 6
            val TOKEN_NAMES = arrayOf("n/a", "<EOF>", "NAME", ",", "[", "]", "=")
            val WHITE_SPACE = arrayOf<Char>(' ', '\t', '\r', '\n')
        }

        fun isLetter() = c in 'a'..'z' || c in 'A'..'Z'
        override fun isWhiteSpace():Boolean = c in WHITE_SPACE

        override fun nextToken():RDL1.Token {
            while (c != RDL1.Lexer.EOF) {
                if (isWhiteSpace()) advance()
                return when (c) {
                    ',' -> {
                        consume()
                        RDL1.Token(COMMA, TOKEN_NAMES[COMMA])
                    }
                    '[' -> {
                        consume()
                        RDL1.Token(LBRACKET, TOKEN_NAMES[LBRACKET])
                    }
                    ']' -> {
                        consume()
                        RDL1.Token(RBRACKET, TOKEN_NAMES[RBRACKET])
                    }
                    '=' -> {
                        consume()
                        RDL1.Token(EQUALS, TOKEN_NAMES[EQUALS])
                    }
                    else -> if (isLetter()) name() else throw Error("Invalid char: $c")
                }

            }
            return RDL1.Token(EOF_TYPE, TOKEN_NAMES[EOF_TYPE])
        }

        fun name():RDL1.Token = RDL1.Token(NAME, buildString {
            do {
                append(c)
                LETTER()
            } while (isLetter())
        })

        fun LETTER() = if (isLetter()) consume() else throw Error("Expecting letter Found: $c")

        override fun getTokenName(type:Int):String = LookaheadLexer.TOKEN_NAMES[type]
    }


    class LookaheadParser(input:RDL1.Lexer, k:Int):Parser(input, k) {

        fun list() {
            match(LookaheadLexer.LBRACKET)
            elements()
            match(LookaheadLexer.RBRACKET)
        }

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
            LookaheadLexer.LBRACKET -> list()
            else -> throw Error("Expecting name or list Found ${LT(1)}")
        }
    }

}