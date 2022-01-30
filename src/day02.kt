import java.io.File

val pairs = listOf(Pair(0, 1), Pair(0, 2), Pair(1, 2)) // permutations of 0, 1, 2, useful later

/** Annoyingly, a class is necessary to get a file as a "resource" in Jetbrain's IDEA. Edit: an Object seems to work */
object Input {
    fun input() = File("input/input02.txt")
        .readText().trim().split("\n")
        .map { it.interpret() }
}

/** An interpretation of a "_x_x_" dimension string is a *sorted* list of dimensions */
fun String.interpret(): List<Int> = this.split("x").map { it.toInt() }.sorted()

/** Solution to part 1 */
fun List<Int>.requiredPaper(): Int {
    val areas = pairs.map { this[it.first] * this[it.second] }
    return areas[0] * 3 + areas[1] * 2 + areas[2] * 2
}

/** Solution to part 2 */
fun List<Int>.requiredRibbon(): Int {
    val smallestPerimeter = 2 * (this[0] + this[1])
    val volume = this[0] * this[1] * this[2]
    return smallestPerimeter + volume
}

fun checkHints() {
    val dimensions1 = "2x3x4".interpret()
    val dimensions2 = "1x1x10".interpret()
    println("Hint 1.1 (should be 58): ${dimensions1.requiredPaper()}")
    println("Hint 1.2 (should be 43): ${dimensions2.requiredPaper()}")
    println("Hint 2.1 (should be 34): ${dimensions1.requiredRibbon()}")
    println("Hint 2.2 (should be 14): ${dimensions2.requiredRibbon()}")
}

fun solvePart1() = Input.input().map { it.requiredPaper() }.sum()

fun solvePart2() = Input.input().map { it.requiredRibbon() }.sum()

fun main() {
    checkHints()
    println("total paper required: ${solvePart1()}")
    println("total ribbon required: ${solvePart2()}")
}
