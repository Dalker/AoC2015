/** Second version of Day 2, to practice using classes and objects */
import java.io.File

class PresentBox {
    private val dimensions: List<Int>

    /** build from e.g. "2x5x10" string */
    constructor(dimensions: String) {
        this.dimensions = dimensions.split("x").map { it.toInt() }.sorted()
    }

    companion object {
        const val inputFile = "input/input02.txt"

        val pairs = listOf(Pair(0, 1), Pair(0, 2), Pair(1, 2))

        /** Factory method to get a list of PresentBoxes */
        fun getBoxes(): List<PresentBox> = File(inputFile)
            .readText().trim().split("\n")
            .map { PresentBox(it) }
    }

    fun requiredPaper(): Int {
        val areas = pairs.map { dimensions[it.first] * dimensions[it.second] }
        return areas[0] * 3 + areas[1] * 2 + areas[2] * 2
    }

    fun requiredRibbon(): Int {
        val smallestPerimeter = 2 * (dimensions[0] + dimensions[1])
        val volume = dimensions[0] * dimensions[1] * dimensions[2]
        return smallestPerimeter + volume
    }
}

fun checkHintsDay2() {
    val box1 = PresentBox("2x3x4")
    val box2 = PresentBox("1x1x10")
    println("Hint 1.1 (should be 58): ${box1.requiredPaper()}")
    println("Hint 1.2 (should be 43): ${box2.requiredPaper()}")
    println("Hint 2.1 (should be 34): ${box1.requiredRibbon()}")
    println("Hint 2.2 (should be 14): ${box2.requiredRibbon()}")
}

fun solveDay2Part1() = PresentBox.getBoxes().sumOf { it.requiredPaper() }

fun solveDay2Part2() = PresentBox.getBoxes().sumOf { it.requiredRibbon() }

fun main() {
    checkHintsDay2()
    println("total paper required: ${solveDay2Part1()}")
    println("total ribbon required: ${solveDay2Part2()}")
}
