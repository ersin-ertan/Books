package A_GettingStartedWithParsing.B_BasicParsingPatterns

/*
Parser checks if sentence conforms to syntaxt of language(lang is a set of valid sentences) by identifying a sentence's
parse tree. But doesn't need to construct in memory, just to recognize various sub structures and associated tokens.
Do this when you see that.

Avoind building parse trees by tracing them out implicitly via a function call sequences(call tree). Make a function for
each sub structure(interior node) of the parse tree.To match a substrcture(usbtree) function f calls the function
associated with the subtree. To match token children, f calls match() support function. ex. return x+1;
to parse call stat()
*/
private class BuildingRecursiveDescentParsers {

    fun returnXplus1() {
        fun match(string:String) = {} // advances token

        fun expr():Unit {
            match("x")
            match("+")
            match("1")
        }

        fun returnstat():Unit {
            match("return")
            expr()
            match(";")
        }

        fun stat():Unit {
            returnstat()
        } // because of local function, the order of function declaration must be reversed

    }

    // lets do if return and assignment statements
    fun ifReturnAssignment() {

        // top down parser, LL(1) recursive descent parser, descent is the top down nature, recursive is that functions
        // can call themselves. Nesting in a parse tree gives rise to recursion in a recursive descent parser.
        // Formally, using a single lookahead token is the parser LL(1)
        // L(read the input from left to right), L(descend into parse tree children from left to right)

        /*fun state(){
            if(lookaheadTokenIsReturn) returnstat()
            else if (lookahead token is identifier) assign()
            else if( lookahead token is if) ifstate()
            else parseError
        }*/
// Once we find, we must act and do something
    }
}

// Building parsers: predicting which kind of phrase approaches, invoking functions to match substructures, matching
// tokens, executing application specific actions

// Generating parsers is troubled because must languages have infinite number of sentences, we can't delineate them all
// and we cant delineate all possible parse trees. We nee a DSL for specifying languages.