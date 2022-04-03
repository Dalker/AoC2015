/** AoC 2015 Day 12, by Dalker while learning Kotlin */
import java.io.File

/** Simple char by char parser to add any present integer values, for part 1. */
fun jsum(s: String): Int {
    var sum = 0
    var current = ""
    for (c in s) {
        if (c.isDigit() || c == '-') {
            current += c
        } else {
            if (current != "") sum += current.toInt()
            current = ""
        }
    }
    if (current != "") sum += current.toInt()
    return sum
}

enum class Thing { ARRAY, OBJECT }
data class Level(val type: Thing) { var sum = 0; var red = false }

/** parser that also works for part 2. */
fun parser(s: String, part2: Boolean = false): Int {
    var level: Level? = null
    var levelStack = emptyList<Level>()
    var accumulator = ""
    for (c in s) {
        when (c) {
            '{' -> level = Level(Thing.OBJECT).also { levelStack += it }
            '}' -> {
                levelStack = levelStack.dropLast(1)
                val sum =
                    if (level!!.red || part2 && accumulator == "\"red\"") 0
                    else level.sum + (accumulator.toIntOrNull() ?: 0)
                if (levelStack.isEmpty()) return sum
                level = levelStack.last().also { it.sum += sum }
                accumulator = ""
            }
            '[' -> level = Level(Thing.ARRAY).also { levelStack += it }
            ']' -> {
                levelStack = levelStack.dropLast(1)
                level!!.sum += accumulator.toIntOrNull() ?: 0
                if (levelStack.isEmpty()) return level!!.sum
                level = levelStack.last().also { it.sum += level!!.sum }
                accumulator = ""
            }
            ':' -> accumulator = ""
            ',' -> {
                if (part2 && level!!.type == Thing.OBJECT && accumulator == "\"red\"") {
                    level.red = true
                } else {
                    level!!.sum += accumulator.toIntOrNull() ?: 0
                }
                accumulator = ""
            }
            else -> accumulator += c
        }
    }
    return -1
}

fun test1() {
    mapOf(
        "[1,2,3]" to 6,
        """{"a":2,"b":4}""" to 6,
        "[[[3]]]" to 3,
        """{"a":[-1,1]}""" to 0,
        """[-1,{"a":1}]""" to 0,
        "[]" to 0,
        "{}" to 0,
    ).forEach {
        val result = parser(it.key)
        if (result != it.value) println("failed test ${it.key} -> $result != ${it.value}")
    }
}

fun test2() {
    mapOf(
        "[1,2,3]" to 6,
        """[1,{"c":"red","b":2},3]""" to 4,
        """{"d":"red","e":[1,2,3,4],"f":5}""" to 0,
        """[1,"red",5]""" to 6,
    ).forEach {
        val result = parser(it.key, part2 = true)
        if (result != it.value) println("failed test ${it.key} -> $result != ${it.value}")
    }
}

fun main() {
    val lines = File("input/input12.txt").readText()
    test1()
    // println(jsum(lines))
    println(parser(lines))
    test2()
    println(parser(lines, part2=true))
}
