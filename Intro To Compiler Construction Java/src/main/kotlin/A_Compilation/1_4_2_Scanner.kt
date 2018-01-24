package A_Compilation

/*
Scanner supports parser, scans tokens from input stream of characters of source language.
*/

public class HelloWorld {
    fun main(args:Array<String>) {
        println("hello world")
    }
}

/*
breaks text into atomic tokens, like import java, lang, System, ; are all distinct tokens.
java, HelloWorld, main are identifiers, the scanner categorizes tokens as IDENTIFIER tokens. Parser uses category names
to identify the kinds of incoming tokens. IDENTIFIER tokens carry images as attributes, ex. first id is java, used in
semantic analysis.
Some tokens are reserved words, unique name in code. import, public, class are reserved. Operators and separators are
also have distinct names, and token names.
Other literals, string Hello World is a single token called STRING_LITERAL, comments are scanned and ignored.
Token scanning is done on demain, each time parser needs a subsequent token, sends nextToken() to scanner which returns
the token id and image info.
*/