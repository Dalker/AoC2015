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

fun String.hasRepeatingPair(): Boolean {
    val pairs = hashMapOf<Pair<Char, Char>, Int>()
    for (pair in this.slice(0 until this.lastIndex).zip(this.slice(1..this.lastIndex))) {
        pairs.put(pair, if (pairs.contains(pair)) pairs[pair]!! + 1 else 1)
    }
    return !(
        pairs.keys.filter { pairs[it]!! > 1 }.filter {
            it.first != it.second || (it.first.toString() + it.second).let {
                this.substringAfter(it).contains(it)
            }
        }
        ).isEmpty()
}

fun String.hasTriplet(): Boolean =
    !this.slice(0 until this.lastIndex - 1).filterIndexed {
        idx, value -> this[idx + 2] == value
    }.isEmpty()

fun String.isReallyNice(): Boolean =
    this.hasRepeatingPair() && this.hasTriplet()

fun testPart1(): Boolean =
    "ugknbfddgicrmopn".isNice() && "aaa".isNice() &&
        "jchzalrnumimnmhp".isNaughty() && "haegwjzuvuyypxyu".isNaughty() &&
        "dvszwmarrgswjxmb".isNaughty()

fun testPart2(): Boolean =
    "qjhvhtzxzqqjkmpb".isReallyNice() && !"aaa".isReallyNice() &&
        "xxyxx".isReallyNice() && !"uurcxstgmygtbstg".isReallyNice() &&
        !"ieodomkazucvgmuy".isReallyNice()

fun personalInput(): List<String> =
    File(INPUT_FILE).readLines()

fun solvePart1(): Int =
    personalInput().filter { it.isNice() }.size

fun solvePart2(): Int =
    personalInput().filter { it.isReallyNice() }.size

fun main() {
    println("test1: ${testPart1()}")
    println("part1: ${solvePart1()}")
    println("test2: ${testPart2()}")
    println("part2: ${solvePart2()}")
}
