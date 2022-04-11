/** Advent of Code 2015, Day 13, while learning Kotlin, by Dalker*/
import java.io.File

const val HINT_DATA = """Alice would gain 54 happiness units by sitting next to Bob.
Alice would lose 79 happiness units by sitting next to Carol.
Alice would lose 2 happiness units by sitting next to David.
Bob would gain 83 happiness units by sitting next to Alice.
Bob would lose 7 happiness units by sitting next to Carol.
Bob would lose 63 happiness units by sitting next to David.
Carol would lose 62 happiness units by sitting next to Alice.
Carol would gain 60 happiness units by sitting next to Bob.
Carol would gain 55 happiness units by sitting next to David.
David would gain 46 happiness units by sitting next to Alice.
David would lose 7 happiness units by sitting next to Bob.
David would gain 41 happiness units by sitting next to Carol."""

val happiness = """([A-Za-z]+) would (gain|lose) ([0-9]+) happiness units by sitting next to ([A-Za-z]+).""".toRegex()

fun <T> permutations(elements: List<T>): List<List<T>> =
    when (elements.size) {
        0 -> emptyList<List<T>>()
        1 -> listOf(listOf(elements.first()))
        else -> elements.map { element -> // NB: it is unique (came from a set)
            permutations(elements.minus(element)).map {
                it.plus(element)
            }
        }.flatten()
    }

fun Pair<String, String>.sorted() = if (first < second) this else Pair(second, first)

fun solve(input: String, part2: Boolean = false): Int {
    val scores = mutableMapOf<Pair<String, String>, Int>().apply {
        input.split("\n").forEach {
            happiness.find(it)?.groupValues?.let {
                val score = if (it[2] == "gain") it[3].toInt() else -it[3].toInt()
                Pair(it[1], it[4]).sorted().let { put(it, (get(it) ?: 0) + score) }
            }
        }
    }
    val people = scores.keys.flatMap { (a, b) -> listOf(a, b) }.toSet().toMutableList()
    if (part2) {
        people.forEach {
            scores.put(("Dalker" to it).sorted(), 0)
        }
        people.add("Dalker")
    }
    val options = permutations(people).map {
        (it + listOf(it.first())).zipWithNext().map { it.sorted() }.toSet()
    }.toSet()
    return options.map { it.map { scores.get(it)!! }.sum() }.maxOrNull()!!.also { println(it) }
}

fun main() {
    val data = File("input/input13.txt").readText()
    val h1 = solve(HINT_DATA)
    val h2 = solve(HINT_DATA, part2 = true)
    println("difference for hint: ${h2 - h1}")
    val r1 = solve(data)
    val r2 = solve(data, part2 = true)
    println("difference for real: ${r2 - r1}")
}
