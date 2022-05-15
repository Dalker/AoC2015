/** AoC 2015, Day 18, while learning Kotlin, by Dalker */
import java.io.File

const val TEST_DATA = """
.#.#.#
...##.
#....#
..#...
#.#..#
####..
"""

class Lights(initState: String, val part2: Boolean = false) {

    var state = initState.trim().split("\n").map {
        it.trim().toCharArray().map {
            when (it) {
                '.' -> 0
                '#' -> 1
                else -> throw RuntimeException("Unknown initial state marker: '$it'")
            }
        }.toMutableList()
    }

    val nRows = state.size
    val nCols = state.first().size

    init { if (part2) turnOnCorners() }

    override fun toString(): String = state.map { it.joinToString("") }.joinToString("\n")

    fun countNeighbours(row: Int, col: Int): Int {
        var count = 0
        for (r in (if (row > 0) row - 1 else row)..(if (row < nRows - 1) row + 1 else row)) {
            for (c in (if (col > 0) col - 1 else col)..(if (col < nCols - 1) col + 1 else col)) {
                if (!(r == row && c == col)) {
                    count += state[r][c]
                }
            }
        }
        return count
    }

    fun step() {
        val newState = List<MutableList<Int>>(nRows) { row ->
            MutableList<Int>(nCols) { col ->
                val on = state[row][col] == 1
                val onNeighbours = countNeighbours(row, col)
                when {
                    on && (onNeighbours == 2 || onNeighbours == 3) -> 1
                    !on && onNeighbours == 3 -> 1
                    else -> 0
                }
            }
        }
        state = newState
        if (part2) turnOnCorners()
    }

    fun turnOnCorners() {
        for (row in listOf(0, nRows - 1)) {
            for (col in listOf(0, nCols - 1)) {
                state[row][col] = 1
            }
        }
    }

    fun count() = state.map { it.sum() }.sum()
}

fun solve(data: String, nSteps: Int, part2: Boolean = false) {
    val lights = Lights(data, part2)
    repeat(nSteps) { lights.step() }
    println(lights.count())
}

fun main() {
    val realData = File("input/input18.txt").readText()
    solve(TEST_DATA, 4)
    solve(realData, 100)
    solve(TEST_DATA, 5, part2 = true)
    solve(realData, 100, part2 = true)
}
