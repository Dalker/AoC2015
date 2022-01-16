import java.io.File

const val INPUT_FILE = "input/input01.txt"
const val HINT1 = "))(((((" // should result in 3
const val HINT2 = ")())())" // should result in -3
const val HINT_PART2 = "()())" // should result in 55555

fun getRealInput() = File(INPUT_FILE).readLines()[0]

fun getFloor(directions: String): Int {
    var floor = 0
    for (char in directions) {
        when (char) {
            '(' -> floor += 1
            ')' -> floor -= 1
            else -> println("instruction $char is not recognised")
        }
    }
    return floor
}

fun findBasement(directions: String): Int {
    var floor = 0
    for ((index, char) in directions.withIndex()) {
        when (char) {
            '(' -> floor += 1
            ')' -> floor -= 1
            else -> println("instruction $char is not recognised")
        }
        if (floor == -1) return index + 1
    }
    return -1 // never reached basement
}


fun main() {
    println("hint 1 results in ${getFloor(HINT1)}")
    println("hint 2 results in ${getFloor(HINT2)}")
    println("real input results in ${getFloor(getRealInput())}")
    println("part 2 hint results in ${findBasement(HINT_PART2)}")
    println("part with real input results in ${findBasement(getRealInput())}")
}