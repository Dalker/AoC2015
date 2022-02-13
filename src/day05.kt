/** AoC 2015, day 05, by Dalker, done 2022.02.13 */
import java.io.File
const val INPUT_FILE = "input/input05.txt"

private fun String.isVeryNaughty(): Boolean =
    this.contains("ab") || this.contains("cd") || this.contains("pq") || this.contains("xy")

private fun String.nVowels(): Int =
    this.filter { c -> c in "aeiou" }.length

private fun String.containsDouble(): Boolean =
    this.length > 2 &&
        !this.slice(0 until this.lastIndex).zip(this.slice(1..this.lastIndex)).filter {
            it.first == it.second
        }.isEmpty()

fun String.isNice(): Boolean =
    !this.isVeryNaughty() && this.nVowels() >= 3 && this.containsDouble()

fun String.isNaughty(): Boolean = !this.isNice()

fun testPart1(): Boolean =
    "ugknbfddgicrmopn".isNice() && "aaa".isNice() &&
        "jchzalrnumimnmhp".isNaughty() && "haegwjzuvuyypxyu".isNaughty() &&
        "dvszwmarrgswjxmb".isNaughty()

fun personalInput(): List<String> =
    File(INPUT_FILE).readLines()

fun solvePart1(): Int =
    personalInput().filter { it.isNice() }.size

fun main() {
    println("test1: ${testPart1()}")
    println("part1: ${solvePart1()}")
}
