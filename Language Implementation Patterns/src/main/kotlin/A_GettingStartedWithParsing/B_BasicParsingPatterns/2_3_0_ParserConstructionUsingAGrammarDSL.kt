package A_GettingStartedWithParsing.B_BasicParsingPatterns

/*
Use dsl for describing languages else use of templates and bugs in repetitive implementation. Programs in the DSL are
called grammars. Tools that translate grammars to parsers are called parser generators. Grammars act like functional
specifications for languages.

Substrctures in the parse tree and functions in the parser correspond to rules in the grammar. Children of a substrucutre
become references to rules and tokens on teh right side of a rule definition. This is ANTLR grammar

stat        : returnstat   // "return x+0" or
            | assign        // "x=0;" or
            | ifstat        // "if x<0 then x=0;"
            ;
returnstat  : 'return' expr ';' ; // single-quoted strings are tokens
assign      : 'x' '=' expr ;
ifstat      : 'if' expr 'then' stat ;
expr        : 'x' '+' '0'       // used by returnstat
            | 'x' '<' '0'       // used by if conditional
            | '0'               // used in assign
            ;


stat is "a stat can be either a returnstat, an assign, or an ifstate", we can use a syntax diagram to visualize the control
flow within that rul
stat -->[returnstat]---->
     |_>[assign]-----|^
     |_>[ifstat]-----|^

if there is only on alternative, like in returnstat     ->['return']->[expr]->[';']->
the syntax diagram is just a sequence of elements.

When rules get more complicated, syntax diagrams come in handy
expr --->['x']->['+']->['0']--->
      |->['x']->['<']->['0']-|^
      |->['0']---------------|^

Rule expr might look a little because expressions can only be one of three alternative token sequences. Variables can
only be x, and integers can only be 0. This brings us to the last piece of the language recognition puzzle: combining
input characters into vocabulary symbols(tokens)
*/