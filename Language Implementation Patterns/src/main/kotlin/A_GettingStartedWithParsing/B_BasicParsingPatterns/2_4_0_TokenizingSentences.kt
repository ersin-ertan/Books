package A_GettingStartedWithParsing.B_BasicParsingPatterns

/*
We unconsciously combine letters into words recognizing grammatical structure. Morse code forces us to go letter by letter.
Recognizers that feed off character streams are called tokenizers or lexers(like LL(1) recursive descent lexer)
Overall sentence has structure, the individual tokens have structure. At the character level, we refer to syntax as the
lexical structure.

Grammars describe language structures, so we can also use them for lexical specifications.

Number  : '0'..'9'+ ;           // 1 or more digits 0 to 9
ID      : ('a'..'z'|'A'..'Z'+ ; // 1 or more upper or lower case letters

now we make expr better using generic variable names/numbers

expr    : ID '+' Number // used by returnstat
        | ID '<' Number // used by if conditional
        | Number        // used in assign
        ;

Still not general enough but shows lexical and syntactic rules interacting

Ex. Grammar for simple language to recognize [a,b,c] and nested lists [a,[b,c],d]
Use ANTLR grammar with three syntactic rules and one lexical rule(start with uppercase)

grammar NestedNameList;
list        : '[' elements ']' ;        // match bracketed list
elements    : element(',' element)* ;   // match comma-separated list
element     : NAME | list ;             // element is name or nested list
NAME        : ('a'..'z'|'A'..'Z')+ ;    // NAME is sequence of >=1 letter


When reading lexer/parser design patterns, they are near identical. Both look for structure in input sequences. The diff
is the type of input symbols - characters or tokens. Further, Tree Grammar recognizes structure in tree node sequences.
Grammars drive our parser generators, we need to understand the underlying mechanism.


*/