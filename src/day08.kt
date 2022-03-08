import java.io.File

const val TEST_FILE = "input/test08.txt"
const val INPUT_FILE = "input/input08.txt"

fun solve(inputFile: String) {
    println(
        File(inputFile).readText().let {
            "\"".toRegex().findAll(it).count() +
                """\\x[0-9a-f]{2}""".toRegex().findAll(it).count() * 3 +
                """\\\\""".toRegex().findAll(it).count()
        }
    )
}

fun solve2(inputFile: String) {
    println(
        File(inputFile).readLines().map { line ->
            2 + "\"".toRegex().findAll(line).count() +
                """\\""".toRegex().findAll(line).count()
        }.sum()
    )
}

fun debugSolve(inputFile: String) {
    for (line in File(inputFile).readLines()) {
        print(
            "\"".toRegex().findAll(line).count() +
                """\\x[0-9a-f][0-9a-f]""".toRegex().findAll(line).count() * 3 +
                """\\\\""".toRegex().findAll(line).count()
        )
        println(" : $line")
    }
}

fun main() {
    solve(TEST_FILE)
    solve(INPUT_FILE)
    solve2(TEST_FILE)
    solve2(INPUT_FILE)
}
