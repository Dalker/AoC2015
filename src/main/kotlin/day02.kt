class Input {
    fun input() = this::class.java.classLoader.getResource("input02.txt").readText().trim().split("\n")
}

fun requiredPaper(dimString: String): Int {
    val dimensions = dimString.split("x").map { it.toInt() }
    val areas = listOf(
        dimensions[0] * dimensions[1],
        dimensions[0] * dimensions[2],
        dimensions[1] * dimensions[2],
    ).sorted()
    return areas[0] * 3 + areas[1] * 2 + areas[2] * 2
}

fun checkHints1() {
    print("Hint1 (should be 58): ")
    println(requiredPaper("2x3x4"))
    print("Hint2 (should be 43): ")
    println(requiredPaper("1x1x10"))
}

fun solvePart1() = Input().input().map { requiredPaper(it) }.sum()

fun main() {
    println("total paper required: ${solvePart1()}")
}