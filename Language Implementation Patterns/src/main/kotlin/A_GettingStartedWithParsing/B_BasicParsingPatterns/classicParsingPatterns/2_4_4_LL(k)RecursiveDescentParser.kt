package A_GettingStartedWithParsing.B_BasicParsingPatterns.classicParsingPatterns

/*
Analyzes the syntactic structure of the token sequence of a phrase using k>1 lookahead tokens

Strength of a recursive descent parser depends entirely on the strength of its lookahead decisions. Single token lookahead
is weak, we must contort grammars to make them LL(1). Larger(fixed) buffers is strong enough for computer languages.
Including config files, data formats, network protocols, graphics languages and programming languages.

Motivating the need for more lookahead
Lets augment list of names grammar, we want to recognize input like: [a, b=c,[d,e]], to accommodate the change, we add
an alternative to element matching assignments:
list    : '[' elements ']' ;
elements: element (',' element)* ;
element : NAME '=' NAME     // the matched assignment
        | NAME
        | list
        ;

New alternative renders element non-LL(1) since the first two alternatives start with the same NAME token. We need two
lookahead tokens to distinguish the alternatives. Is it an assignment or a name. If NAME =, parsing decision should
predict the first alternative(assignment). Else parser should predict second alternative: Ex.
element for [a,b=c]
   [         a  ,      b = c]       or    [a,       b =     c]
consumed  lookahead                     consumed  lookahead

Using LL(1) we rewrite element to look like
element : NAME ('=' NAME)?
        | list
        ;

This (..)? optional subrule version works but is less clear. Similar situations occur in real grammars.

Building a circular lookahead buffer
To (simply)provide lots of parser lookahead is to buffer up all input tokens. Input cursor can be an integer index.
p into that buffer, execute p++ to consume token , the next k tokens of lookahead would be tokens[p]..tokens[p+k-1] which
works for finite and reasonably small input, not infinite token streams(network sockets)

When we increment p, add a token to the end of the buffer. Fixed buffer size is a complication so we add tokens in a
circular fashion:  p ranges from =0..k-1
A circular buffer is one where indexes that fall off the end wrap to the beginning. If 2 is the accepting state, then
3 would wrap around to 0. Modulo expression p%3 bwraps indexes for a buffer of size 3

Ex. for [a,b=c]
[a,     ba,     b=,     b=c     ]=c ...
012      p        p     p        p
p

for each increment in p, fill its prev pos with the next items in the buffer.

Implementation
The list of names language augmented with assignments from the previous section. First we'll build the lookahead
infrastructure, then we'll implement LL(2) rule element.
*/



