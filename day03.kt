/* AoC 2015 Day 3 in Kotlin */
import java.io.File

class Delivery() {

    val visited = mutableSetOf(Pair(0, 0))
    var current = Pair(0, 0)

    fun move(movement: Char) {
        current = Pair(
            current.first + direction(movement).first,
            current.second + direction(movement).second,
        )
        visited.add(current)
    }

    constructor(directions: String) : this() {
        directions.forEach { move(it) }
    }

    companion object {
        fun direction(dir: Char) = when (dir) {
            '>' -> Pair(1, 0)
            '<' -> Pair(-1, 0)
            '^' -> Pair(0, 1)
            'v' -> Pair(0, -1)
            else -> {
                println("WRONG DIRECTION: $dir")
                Pair(0, 0)
            }
        }
    }

    fun countVisited() = visited.size
}

fun part1(input: String): Int = Delivery(input).countVisited()

fun part2(input: String): Int {
    val santas = listOf(Delivery(), Delivery())
    input.forEachIndexed { index, char -> santas[index % 2].move(char) }
    return santas[0].visited.union(santas[1].visited).size
}

class Tests {
    companion object {
        val part1Tests = hashMapOf(
            Pair(">", 2),
            Pair("^>v<", 4),
            Pair("^v^v^v^v^v", 2),
        )
        val part2Tests = hashMapOf(
            Pair("^v", 3),
            Pair("^>v<", 3),
            Pair("^v^v^v^v^v", 11),
        )
    }

    fun tests() {
        println("* tests for part 1 *")
        part1Tests.forEach { println("${part1(it.key)} should be ${it.value}") }
        println("* tests for part 2 *")
        part2Tests.forEach { println("${part2(it.key)} should be ${it.value}") }
    }
}

fun main() {
    val input = File("input/input03.txt").readText()
    Tests().tests()
    println("* part 1 *")
    println(part1(input))
    println("* part 2 *")
    println(part2(input))
}
