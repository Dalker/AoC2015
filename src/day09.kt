/** AoC 2015, Day 09, while learning Kotlin - by Dalker */

import java.io.File

const val TEST_FILE = "input/test09.txt"
const val INPUT_FILE = "input/input09.txt"
const val PARSER = """([A-Za-z]*) to ([A-Za-z]*) = ([0-9]*)"""

fun Pair<String, String>.sorted(): Pair<String, String> =
    if (this.first < this.second) this else Pair(this.second, this.first)

fun parseDistances(input: List<String>): Map<Pair<String, String>, Int> {
    return input.map {
        Regex(PARSER).find(it)!!.groupValues.let { values ->
            Pair(
                Pair(values[1], values[2]).sorted(),
                values[3].toInt()
            )
        }
    }.toMap()
}

fun getCities(input: List<String>): List<String> {
    val cities = mutableSetOf<String>()
    for (line in input) {
        Regex(PARSER).find(line)!!.groupValues.let {
            cities.add(it[1])
            cities.add(it[2])
        }
    }
    return cities.sorted()
}

/** permutations of List made from a Set */
tailrec fun <T> permutations(elements: List<T>): List<List<T>> =
    when (elements.size) {
        0 -> emptyList<List<T>>()
        1 -> listOf(listOf(elements.first()))
        else -> elements.map { element -> // NB: it is unique (came from a set)
            permutations(elements.minus(element)).map {
                it.plus(element)
            }
        }.flatten()
    }

fun solve(fname: String, second: Boolean = false) {
    val input = File(fname).readLines()
    val distances: Map<Pair<String, String>, Int> = parseDistances(input)
    val cities = getCities(input)
    // NB: half the permutations are enough, the others being the same reversed
    val pathLengths = permutations(cities) // .let {
        // it.chunked(it.size / 2).first()  <-- awkwardly gets off by 1 in part 2 !!??
        //                                      even when increasing size of chunk!!?
    // }
    .map { path ->
        path.zipWithNext().map { pair -> distances.get(pair.sorted())!! }.sum()
    }
    println(if (second) pathLengths.maxOrNull() else pathLengths.minOrNull())
}

fun main() {
    solve(TEST_FILE)
    solve(INPUT_FILE)
    solve(TEST_FILE, second = true)
    solve(INPUT_FILE, second = true) // 803 is too low
}
