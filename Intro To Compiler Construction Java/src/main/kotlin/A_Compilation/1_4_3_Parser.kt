package A_Compilation

/*
J-- program and construction of AST is driven by language's syntax(syntax directed)
Grammatical rule describing syntax for compilation unit
compilationUnit ::= [package qualfiedIdentifier ;]
                    {import qualifiedIdentifier ;}
                    {typeDeclaration} EOF

Compilation unit consists of:
- optional package clause []
- zero or more import statements {}
- zero or more type declarations (j-- only class declarations)
- end of file

Tokens PACKAGE, SEMI, IMPORT, and EOF are returned by the scanner. Write a method compilationUnit() to parse via recursive
descent:
- if next(first) incoming token were PACKAGE, scan it(then next token), invoke separate method called qualifiedIdentifier()
for parsing a quilified identifier, then scan SEMI(syntax error if next is not)
-While next is IMPORT, scan invoke qualifiedIdentifier() for parsing, scan for SEMI. save imported QI in list
- While next is not EOF, invoke typeDeclaration(), for parsing type declaration, scan for SEMI. Save all of the ASTs for
the type declarations in a list
- Scan the EOF
*/

private class ScanExample {

    class JCompilationUnit(fileName:Any, line:String?, packageName:TypeName?, imports:List<TypeName>, typeDeclarations:List<JAST>)
    class TypeName

    fun have(s:String):Boolean = peekNextToken() == s
    fun see(s:String):Boolean = peekNextToken() == s

    private fun nextToken():String {
        return "a"
    }

    fun qualifiedIdentifier() = TypeName()
    fun peekNextToken() = "a"
    fun mustBe(c:String) {
        val nt = peekNextToken()
        if (nt != c) throw Exception("nextToken:$nt should = $c")
    }

    fun typeDeclaration() = JAST()

    class Scanner {
        class Token {
            fun line():String = "line"
        }

        fun token() = Token()
        fun fileName() = "ABC"
    }

    class JAST

    val PACKAGE = "package"
    val SEMI = ";"
    val IMPORT = "import"
    val EOF = "eof"

    fun compilationUnit():JCompilationUnit {

        val scanner = Scanner()

        val line = scanner.token().line()
        var packageName:TypeName? = null

        if (have(PACKAGE)) {
            packageName = qualifiedIdentifier()
            mustBe(SEMI)
        }

        val imports = mutableListOf<TypeName>()
        while (have(IMPORT)) {
            imports.add(qualifiedIdentifier())
            mustBe(SEMI)
        }

        val typeDeclarations = mutableListOf<JAST>()
        while (!see(EOF)) {
            val typeDeclaration = typeDeclaration()
            typeDeclaration.let { typeDeclarations.add(typeDeclaration) }
        }
        mustBe(EOF)
        return JCompilationUnit(scanner.fileName(), line, packageName, imports, typeDeclarations)
    }
}

/*
have() is same as see, but hase side effect of scanning past incocming token when argument matches.
typeDeclaration() recursively invokes additional methods for parsing HelloWorld class declaration, recursive descent.
Each parsing method produces an AST constructed from type of node, compilationUnit() produces a JCompilationUNit node,
encapsulating any package name, the single import, single class declaration.
*/