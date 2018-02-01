package practise

import A_functionalDomainModeling.p
import arrow.syntax.either.right

fun main(args: Array<String>) {
    1.right().map { it.p() }
}