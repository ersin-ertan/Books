package Part1_Introduction.C_DefiningAndCallingFunctions

// splitting strings
// java split() doesn't work with . which is because the split takes the regular expression
// as the param, thus . is any character, in kotlin we can use toRegex for string to reg
// kotlin taxes arbitrary num of delimiters as plain text strings
fun a() = println("12.423-3.A".split(".", "-"))
// [12, 423, 3, A] // or use character arguments '.' and ','

fun parsePath(p:String) {
    val dir = p.substringBeforeLast("/")
    val fullName = p.substringAfterLast("/")
    val fileName = fullName.substringBeforeLast(".")
    val extension = fullName.substringAfterLast(".")

    println("dir:$dir, name:$fileName, ext:$extension")

//     >>> parsePath("/Users/yole/kotlin-book/chapter.adoc")
//    Dir: /Users/yole/kotlin-book, name: chapter, ext: adoc
}

// kotlin methods can help by reducing the need for regex, else use them
fun ParsePathRegex(p:String) {
    val regex = """(.+)/(.+)\.(.+)""".toRegex() // triple quoted string no need to escape chars including backslash
    val matchResult = regex.matchEntire(p)
    if (matchResult != null) {
        val (dir, fileName, extension) = matchResult.destructured
        println("dir:$dir, name:$fileName, ext:$extension")
    }
}

// multiline triple quoted strings - not only avoid escaping chars, string literal can contain
// any chars including line breaks, easy way to embedding text with line breaks

val kotlinLogo = """| //
                   .|//
                   .|/ \"""

fun kl() {
// produces the top line to the left, and the other two lines right aligned
    println(kotlinLogo)

    // fixes the alignment, by deleting all text prior to text margin
    println(kotlinLogo.trimMargin("."))
}

val price = """${'$'}88.8""" // literal dollar sign

// very important for tests, compare results with expected output