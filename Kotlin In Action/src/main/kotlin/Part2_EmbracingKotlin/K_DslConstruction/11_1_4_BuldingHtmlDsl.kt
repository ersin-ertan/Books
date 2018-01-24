package Part2_EmbracingKotlin.K_DslConstruction

/*
// 11.1.4 Building Html with an internal DSL

*/

//fun createSimpleTable() = createHTML()
//        .table{
//            tr {
//                td { +"cell"}
//            }
//}

//better than
/*

<table>
    <tr>
        <td>cell</td>
    </tr>
<table>

Kotlin version is type safe, use td tag only inside tr, else won't compile
Can generate table cells dynamically.

fun createAnotherTable() = creatHTML().table{
    val numbers = mapOf(1 to "one", 2 to "two)
    for ((num,string) in numbers){
        tr{
            td{ +"$num" }
            td{ +"string" }
        }
    }
}

Markup languages perfectly illustrate this concept, like xml, html
*/