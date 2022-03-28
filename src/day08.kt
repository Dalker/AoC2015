import java.io.File

const val TEST_FILE = "input/test08.txt"
const val INPUT_FILE = "input/input08.txt"

fun solve(inputFile: String) {
    File(inputFile).readText().let {
        "\"".toRegex().findAll(it).count() +
            """\\x[0-9a-f]{2}""".toRegex().findAll(it).count() * 3 +
            """\\\\""".toRegex().findAll(it).count()
    }.also { c -> println("La solution est $c.") }
}

fun solve2(inputFile: String) {
    File(inputFile).readLines().map {
        2 + "\"".toRegex().findAll(it).count() +
            """\\""".toRegex().findAll(it).count()
    }.sum().also { println(it) }
}

fun main() {
    solve(TEST_FILE)
    solve(INPUT_FILE)
    solve2(TEST_FILE)
    solve2(INPUT_FILE)
}
