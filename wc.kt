package ichain.test.wc
import java.io.File
import java.io.InputStream
import kotlin.system.exitProcess

/*
  wc ファイル内の行数、ワード数、バイト数、または文字数を数えます。
  iChainコーディングスキルチェック
  2024.04.03 Gert
*/

fun main(args: Array<String>) {
    if (args.any { it.contains("--help")})
        displayHelp()
    else if (args.any { it.contains("--version")})
        displayVersion()
    else {
        val input = processArguments(args)
        val textfiles = readFiles(input.filenames)
        val output = analyzeFiles(textfiles, input.arguments)
        displayOutput(output)
    }
}

private fun processArguments(args: Array<String>): Input {
    if (args.isEmpty())
        reportError("no filename specified")

    var options = args
        .filter { it.startsWith("-") }
        .flatMap { it.drop(1).toList() }

    val filenames =  args.filter { !it.startsWith("-") }
    if (filenames.isEmpty())
        reportError("no filename specified")

    if (options.any { it == 'a' || it == 'b' || it == 'd' || it == 'e' || it == 'f' || it == 'g' ||
                it == 'h' || it == 'i' || it == 'j' || it == 'k' || it == 'n' || it == 'o' ||
                it == 'p' || it == 'q' || it == 'r' || it == 's' || it == 't' || it == 'u' ||
                it == 'v' || it == 'x' || it == 'y' || it == 'z' || it == 'A' || it == 'B' ||
                it == 'C' || it == 'D' || it == 'E' || it == 'F' || it == 'G' || it == 'H' ||
                it == 'I' || it == 'J' || it == 'K' || it == 'M' || it == 'N' || it == 'O' ||
                it == 'P' || it == 'Q' || it == 'R' || it == 'S' || it == 'T' || it == 'U' ||
                it == 'V' || it == 'W' || it == 'X' || it == 'Y' || it == 'Z' || it == '-' })
        reportError("invalid option")

    if (options.isEmpty())
        options = listOf('l','w','c')

    return Input(filenames,options)
}

private fun readFiles(filenames: List<String>): List<TextFile> {
    val result = mutableListOf<TextFile>()
    for (filepath in filenames) {
        val file = File(filepath)
        if (file.exists()) {
            val inputStream: InputStream = file.inputStream()
            val inputString = inputStream.bufferedReader().use { it.readText() }
            result.add(TextFile(filepath,true,inputString))
        } else {
            result.add(TextFile(filepath,false, ""))
        }
    }
    return result
}

private fun analyzeFiles(textfiles: List<TextFile>, arguments: List<Char>): List<List<String>> {
    val results : MutableList<List<String>> = mutableListOf()
    val totals : MutableMap<Char,Int> = mutableMapOf( 'l' to -1, 'w' to -1, 'c' to -1, 'm' to -1, 'L' to -1)
    for(text in textfiles) {
        val currentresults = mutableListOf<String>()
        if (text.exists) {
            if ('l' in arguments) {
                val l = text.content.count { it == '\n' }
                keepValueAndUpdateTotal(currentresults, l, totals, 'l')
            }
            if ('w' in arguments) {
                val w = text.content.split("\\s+".toRegex()).filter { it.isNotEmpty() }.size //Regex for whitespace
                keepValueAndUpdateTotal(currentresults, w, totals, 'w')
            }
            if ('c' in arguments) {
                val c = text.content.toByteArray().size
                keepValueAndUpdateTotal(currentresults, c, totals, 'c')
            }
            if ('m' in arguments) {
                val m = text.content.length
                keepValueAndUpdateTotal(currentresults, m, totals, 'm')
            }
            if ('L' in arguments) {
                val bigL = text.content.split("\n").maxOfOrNull { it.length } ?: 0
                keepValueAndUpdateTotal(currentresults, bigL, totals, 'L')
            }
        }
        else
            currentresults.add("File not found :")

        currentresults.add(text.filename)
        results.add(currentresults)
    }
    if (textfiles.size > 1) {
        val totalsText = convertTotalsToStrings(totals)
        results.add(totalsText)
    }
    return results
}

private fun convertTotalsToStrings(totals: MutableMap<Char, Int>): MutableList<String> {
    val totalsText = mutableListOf<String>()
    for (pair in totals) {
        if (pair.value > -1)
            totalsText.add(pair.value.toString())
    }
    totalsText.add("total")
    return totalsText
}

private fun keepValueAndUpdateTotal(
    currentresults: MutableList<String>,
    value: Int,
    totals: MutableMap<Char, Int>,
    character: Char
) {
    currentresults.add(value.toString())
    if (character == 'L') { //L is an special case: keep only the highest value
        if (value > totals.getValue(character))
            totals[character] = value
    }
    else {
        if (totals.getValue(character) < 0)
            totals[character] = 0
        totals[character] = totals.getValue(character) + value
    }
}

private fun displayOutput(output: List<List<String>>) {
    val filteredList = output.filter { it[0] != "File not found :" }
    val maxColumnWidths = List(filteredList[0].size) { index ->
        output.maxOfOrNull { it.getOrNull(index)?.length ?: 0 } ?: 0
    }

    for (row in output) {
        for ((index, item) in row.withIndex()) {
            print(item.padStart(maxColumnWidths[index] + 2))
        }
        println()
    }
}

private fun displayVersion() {
    println("iChainスキルテストアプリ\n" +
    "作者：Gert Vercruyssen\n" +
    "原作： wc (GNU coreutils) 8.22")
    exitProcess(0)
}

private fun displayHelp() {
    println("-c            print the byte counts\n" +
            "-m            print the character counts\n" +
            "-l            print the newline counts\n" +
            "-L            print the length of the longest line\n" +
            "-w            print the word counts\n" +
            "--help        display this help and exit\n" +
            "--version     output version information and exit")
}

private fun reportError(content: String, inputerror: Boolean = true) {
    System.err.println(content)
    if (inputerror)
        System.err.println("type --help for instructions")
    exitProcess(1)
}

data class Input(val filenames :List<String>, val arguments: List<Char> )

data class TextFile(val filename: String, val exists: Boolean, val content: String)