import java.io.File

val TICKER = mapOf(
    "children" to 3,
    "cats" to 7,
    "samoyeds" to 2,
    "pomeranians" to 3,
    "akitas" to 0,
    "vizslas" to 0,
    "goldfish" to 5,
    "trees" to 3,
    "cars" to 2,
    "perfumes" to 1,
)

val PATTERN = "Sue ([0-9]+): ([a-z]+): ([0-9]+), ([a-z]+): ([0-9]+), ([a-z]+): ([0-9]+)".toRegex()

fun test1(petName: String, petNumber: Int) = TICKER.get(petName) == petNumber

fun test2(petName: String, petNumber: Int): Boolean =
    TICKER.get(petName)!!.let { expectedNumber ->
        when (petName) {
            "cats", "trees" -> petNumber > expectedNumber
            "pomeranians", "goldfish" -> petNumber < expectedNumber
            else -> petNumber == expectedNumber
        }
    }

fun solve(fName: String, tester: (String, Int) -> Boolean = ::test1) {
    for (line in File(fName).readLines()) {
        PATTERN.find(line)?.groupValues?.let { group ->
            listOf(2 to 3, 4 to 5, 6 to 7).map { pair ->
                tester(group[pair.first], group[pair.second].toInt())
            }.reduce { a, b -> a && b }.let {
                if (it) println("Sue ${group[1]} matches!")
            }
        }
    }
}

fun main() {
    solve("input/input16.txt")
    solve("input/input16.txt", tester = ::test2)
}
