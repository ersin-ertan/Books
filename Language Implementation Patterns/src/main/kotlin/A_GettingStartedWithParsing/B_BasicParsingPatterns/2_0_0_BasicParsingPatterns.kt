package A_GettingStartedWithParsing.B_BasicParsingPatterns

/*
Language Recognition: recognizing what kind of phrase, assignment or function call and act on it
Parsing: distinguish phrase from other constructs in language, and identify elements of any substructures of phrase

Basic parser design patterns:
- Mapping grammars to recursive descent recognizers - convert grammar(formal language spec) to a hand built parser(used
in next three patterns)
- LL(1) recursive descent lexer - breaks up character streams into tokens(use by parsers in subsequent patterns)
- LL(1) recursive descent parser - well known rdp pattern - look at current input symbol to make parsing decision(for each
rule in grammar, there is a parsing method in the parser)
- LL(k) recursive descent parser - augments the LL(1) parser to look multiple symbols ahead(up to k) to make decisions

Grammars: functional specifications or design documents for parsers; we need to precisely define the language to parse,
execuable programs written in DSL, designed for expressing language structures.

Parser generators like ANTLR can automatically convert grammars to parsers.
*/