package A_GettingStartedWithParsing.B_BasicParsingPatterns.classicParsinPatterns

/*
Mapping Grammars to Recursive-Descent Recognizers: translates a grammar to a recursive descent recognizer that matches
pharses and sentences in the language specified by the grammar. Id's control flow for recursive descent lexer, parser,
or tree parser.

Grammars are excelent documentation that can go into a reference manual and into parsing code as comments. Left recursion
results in an infinite method invocation loop ex. r: r X ;

void r() { r(); match(X); }

Other grammar constructs yield nondeterministic recursive descent recognizers, cannot decide which path to take. Use more
lookahead to increase the strength of the recognizer, thus easier to write grammar.

Grammar G, a set of rulse which we generate class definitions containing a method for each rule

public class G extends Parser {
    token type definitions
    suitable constructor
    rule methods
}

Parser class defines the state of a typical parser needs, like lookahead token or tokens and an input stream.
Rule, r, defined in a grammar, we build a method of the same name

public void r(){}

Each rule looks like a sub-rule, r becomes r()

Converting tokens, token type T becomes calls to match(T), a support method in Parser, that consumes a token if T is the
current lookahead token. If mismatch, throw exception.
Define T, in parser object or in our lexer object, for every token T:

public static final int T = sequential integer
public static final int INVALID_TOKEN_TYPE = 0
public static final int EOF = -1

Could use enum but integer is simple enough.

Converting subrules, switch or if-then-else sequence, depending on the complexity of the lookahead decision. Each alternative
gets an expression predicting whether that alternative would succeed at the current input location.

(alt1|alt2|...|altN)
 subrule ____> alt1 --->
           \_> alt2 _|^
           \_> altN _|^

general implmentation
if(lookahead predicts alt1){ match alt1}
else if(lookahead predicts alt2){ match alt2}
else throw exception

or
switch(looahead token)
    case token1 predicts alt1
    case token2 predicts alt1
        match alt1
        break;
    case token1 predicts alt2
    case token2 predicts alt2
        match alt2
        break;
    default throw exception

optimize by collapsing subrules whoes alternatives are token refrences like (A|B|C) into sets. Testing lookahead against
a set is usuallly much faster and smaller than switch.

All recucrsive descent recognizers make decisions according to this template. To implement, fill lookahead prediction
expressions.

Converting subrule operators, optional subrules are  easy to convert all we do is remove default error clause, thus no
possibility for error. Either there or not. For one or more (...)+ we use do-while loops

optional _> T __>
          \__/^

one or more _> T __>
             ^\__/

do{
    code matching alternative
}while(lookahead predicts an alt of subrule)

Zero or more (...)* are like optional and one or more combined
while(looahead predicts an alt of subrule){
    code matching alternatives
}

Recognizer can skip over the subrule if t he lookahead does not predict an alternative within the subrule

*/