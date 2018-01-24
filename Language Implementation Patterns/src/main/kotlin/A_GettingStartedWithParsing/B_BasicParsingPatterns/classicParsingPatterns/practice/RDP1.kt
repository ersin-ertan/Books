package A_GettingStartedWithParsing.B_BasicParsingPatterns.classicParsingPatterns.practice

internal class RDP1 {

    abstract class Parser(val lexer:RDL1.Lexer) {

        lateinit var lookahead:RDL1.Token

        init {
            consume()
        }

        fun consume() = run { lookahead = lexer.nextToken() }

        fun match(type:Int) = if (lookahead.type == type) consume()
        else throw Exception("Expecting: ${lexer.getTokenName(type)} Found: $type")
    }

    class ListParser(lexer:RDL1.Lexer):Parser(lexer) {

        fun list() {
            match(RDL1.ListLexer.LBRACKET)
            elements()
            match(RDL1.ListLexer.RBRACKET)
        }

        fun elements() {
            element()
            while (lookahead.type == RDL1.ListLexer.COMMA) {
                match(RDL1.ListLexer.COMMA)
                element()
            }
        }

        fun element() = when (lookahead.type) {
            RDL1.ListLexer.NAME -> match(RDL1.ListLexer.NAME)
            RDL1.ListLexer.LBRACKET -> list()
            else -> throw Exception("Expecting name or list Found: $lookahead")
        }
    }

    fun test() {
        ListParser(RDL1.ListLexer("[a, b, c, [aa, bb, [ccc]], d]"))
    }

}

fun main(args:Array<String>) {

    RDP1().test()

}
