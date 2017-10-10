package A_GettingStartedWithParsing.B_BasicParsingPatterns

/*
Syntax analysis: identifying vocabulary symbols(tokens) like variable and operator, or the role of token subsequences
(expressions)

return x+1;      x+1 is the expression, the phrase is the entire return statement
      [expr]
[returnstat]
[ statement] when we flip this over we get a parse tree

state __> returnstat __> return
                      \_> expr __> x
                       \_> ;    \_> +
                                 \_> 1

Tokens hang from the parse tree as leaf nodes, interior nodes identify the phrase substrucures

See book page 39 for more detailed parse tree for: if x<0 then x=0 ;

Parse trees tell us everything we need to know about the syntax of the phrase. To parse is to conjure a two dimensional
parse tree from a flat token sequence.
*/