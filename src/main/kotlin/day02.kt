val pairs = listOf(Pair(0, 1), Pair(0, 2), Pair(1, 2))

class Input {
    fun input() = this::class.java.classLoader
        .getResource("input02.txt")
        .readText().trim().split("\n")
        .map { it.interpret() }
}

fun String.interpret(): List<Int> = this.split("x").map { it.toInt() }

fun List<Int>.requiredPaper(): Int {
    val areas = pairs.map { this[it.first] * this[it.second] }.sorted()
    return areas[0] * 3 + areas[1] * 2 + areas[2] * 2
}

fun requiredRibbon(dimString: String): Int {
    return 0
}

fun checkHints1() {
    print("Hint1 (should be 58): ")
    println("2x3x4".interpret().requiredPaper())
    print("Hint2 (should be 43): ")
    println("1x1x10".interpret().requiredPaper())
}

fun solvePart1() = Input().input().map { it.requiredPaper() }.sum()

fun main() {
    println("total paper required: ${solvePart1()}")
}