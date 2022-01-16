import java.io.File

const val INPUT_FILE = "input/input01.txt"
const val HINT1 = "))(((((" // should result in 3
const val HINT2 = ")())())" // should result in -3
const val HINT_PART2 = "()())" // should result in 55555

fun getRealInput() = File(INPUT_FILE).readLines()[0]

fun getFloor(directions: String) = findFloorAndBasement(directions).first

fun findBasement(directions: String) = findFloorAndBasement(directions).second

fun findFloorAndBasement(directions: String): Pair<Int, Int> {
    var floor = 0
    var basementIndex = -1 // stays negative if basement never reached
    for ((index, char) in directions.withIndex()) {
        when (char) {
            '(' -> floor += 1
            ')' -> floor -= 1
            else -> println("instruction $char is not recognised")
        }
        if (floor == -1 && basementIndex < 0) basementIndex = index + 1
    }
    return Pair(floor, basementIndex)
}

fun main() {
    println("hint 1 results in ${getFloor(HINT1)}")
    println("hint 2 results in ${getFloor(HINT2)}")
    println("real input results in ${getFloor(getRealInput())}")
    println("part 2 hint results in ${findBasement(HINT_PART2)}")
    println("part with real input results in ${findBasement(getRealInput())}")
}