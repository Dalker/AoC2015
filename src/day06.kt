/** AoC 2015, day 06, by Dalker, done 2022.02.20 */
import java.io.File
const val GRID_WITH = 1000
const val INPUT_FILE = "input/input06.txt"

fun max(a: Int, b: Int) = if (a >= b) a else b

abstract class Lights<T> {
    abstract val matrix: Array<Array<T>>

    abstract fun turnOn(rows: IntRange, cols: IntRange)
    abstract fun turnOff(rows: IntRange, cols: IntRange)
    abstract fun toggle(rows: IntRange, cols: IntRange)

    /** modify in some way all "lights" in the given rectangular 2D range */
    fun modify(rows: IntRange, cols: IntRange, action: (T) -> T) {
        for (row in rows)
            for (col in cols)
                matrix[row][col] = action(matrix[row][col])
    }
}

class BasicLights : Lights<Boolean>() {
    override val matrix: Array<Array<Boolean>> = Array(GRID_WITH) { Array(GRID_WITH) { false } }

    val on: Int
        get() = matrix.flatten().filter { it }.size

    override fun turnOn(rows: IntRange, cols: IntRange) = modify(rows, cols) { true }
    override fun turnOff(rows: IntRange, cols: IntRange) = modify(rows, cols) { false }
    override fun toggle(rows: IntRange, cols: IntRange) = modify(rows, cols) { !it }
}

class TunableLights : Lights<Int>() {
    override val matrix: Array<Array<Int>> = Array(GRID_WITH) { Array(GRID_WITH) { 0 } }

    val brightness: Int
        get() = matrix.flatten().sum()

    override fun turnOn(rows: IntRange, cols: IntRange) = modify(rows, cols) { it + 1 }
    override fun turnOff(rows: IntRange, cols: IntRange) = modify(rows, cols) { max(it - 1, 0) }
    override fun toggle(rows: IntRange, cols: IntRange) = modify(rows, cols) { it + 2 }
}

fun <T> processInput(file: String, lights: Lights<T>) {
    val input: List<String> = File(file).readLines()
    val pattern = "(?<command>[a-z ]*) ([0-9]+),([0-9]+) through ([0-9]+),([0-9]+)".toRegex()
    input.forEach {
        val match = pattern.find(it)!!.destructured.toList()
        val command = match[0]
        val (row1, col1, row2, col2) = match.drop(1).map { it.toInt() }
        when (command) {
            "turn on" -> lights.turnOn(row1..row2, col1..col2)
            "turn off" -> lights.turnOff(row1..row2, col1..col2)
            "toggle" -> lights.toggle(row1..row2, col1..col2)
        }
    }
}

fun solve1() {
    val lights = BasicLights()
    processInput(INPUT_FILE, lights)
    println("part 1 has ${lights.on} lights on.")
}

fun solve2() {
    val lights = TunableLights()
    processInput(INPUT_FILE, lights)
    println("part 2 has ${lights.brightness} brightness.")
}

fun main() {
    solve1()
    solve2()
}
