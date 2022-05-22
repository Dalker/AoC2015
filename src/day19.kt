/** AoC 2015 Day 19 by Dalker, while learning Kotlin */
import java.io.File

const val TEST_DATA = """
H => HO
H => OH
O => HH

HOH
"""

const val TEST_DATA2 = """
e => H
e => O
H => HO
H => OH
O => HH

HOHOHO
"""

class MoleculeMachine(data: String) {

    var molecules = mutableListOf<String>()
    var transforms = mutableListOf<Pair<String, String>>()
    // val goal: String

    init {
        val lines = data.trim().split("\n")
        for (line in lines) {
            if (line == "") break
            line.split(" => ").let {
                transforms.add(it[0] to it[1])
            }
        }
        molecules.add(lines.last())
        // goal = lines.last()
    }

    override fun toString() = transforms.toString() + "\n" + molecules.toString()

    fun transform() {
        val results = mutableListOf<String>()
        for (transform in transforms) {
            for (molecule in molecules) {
                for (match in transform.first.toRegex().findAll(molecule)) {
                    val start = match.range.first
                    val end = match.range.last + 1
                    val result = molecule.take(start) + transform.second + molecule.drop(end)
                    results.add(result)
                }
            }
        }
        molecules = results.toSet().toMutableList()
    }

    fun calibrate() {
        // molecules = mutableListOf(goal)
        transform()
        println("calibration result: ${molecules.size}")
    }

    fun reverse() {
        transforms = transforms.map { Pair(it.second, it.first) }.toMutableList()
    }

    fun synthetize() {
        reverse()
        for (step in 1..100) {
            transform()
            if (molecules.contains("e")) {
                println("medicine may be synthesized in $step steps")
                break
            }
        }
    }
}

fun main() {
    val realData = File("input/input19.txt").readText()
    MoleculeMachine(TEST_DATA).calibrate()
    MoleculeMachine(realData).calibrate()
    MoleculeMachine(TEST_DATA2).synthetize()
    MoleculeMachine(realData).synthetize()
}
