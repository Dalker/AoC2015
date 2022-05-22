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

fun String.decompose(): List<String> {
    val atoms = MutableList(0) { "" }
    var prevChar: Char = ' '
    for (char in this + " ") {
        when {
            char == 'e' && prevChar != 'H' -> {
                if (prevChar != ' ') atoms.add("$prevChar")
                atoms.add("$char")
                prevChar = ' '
            }
            'a' <= char && char <= 'z' -> {
                atoms.add("$prevChar$char")
                prevChar = ' '
            }
            'A' <= prevChar && prevChar <= 'Z' -> {
                atoms.add("$prevChar")
                prevChar = char
            }
            else -> prevChar = char
        }
    }
    return atoms.toList()
}

class MoleculeMachine2(data: String) {
    val goal: List<String>
    var transforms = mutableListOf<Pair<String, List<String>>>()
    var atoms = mutableSetOf<String>()
    var unstableAtoms = mutableSetOf<String>()
    var stableAtoms = mutableSetOf<String>()

    init {
        val lines = data.trim().split("\n")
        for (line in lines) {
            if (line == "") break
            line.split(" => ").let {
                val product = it[1].decompose()
                transforms.add(it[0] to product)
                unstableAtoms.add(it[0])
                for (atom in product) atoms.add(atom)
            }
        }
        for (atom in atoms) if (atom !in unstableAtoms) stableAtoms.add(atom)
        println("atoms: $atoms")
        println("unstable atoms: $unstableAtoms")
        println("stable atoms: $stableAtoms")
        // findAtoms()
        goal = lines.last().decompose()
        println("goal: $goal")
    }
}

class MoleculeMachine(data: String) {

    var transforms = mutableListOf<Pair<String, String>>()
    val goal: String

    init {
        val lines = data.trim().split("\n")
        for (line in lines) {
            if (line == "") break
            line.split(" => ").let {
                transforms.add(it[0] to it[1])
            }
        }
        goal = lines.last()
    }

    override fun toString() = "transforms: " + transforms.toString() + "\ngoal: " + goal

    fun transform(molecules: List<String>): List<String> {
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
        return results.toSet().toList()
    }

    fun calibrate() {
        val result = transform(listOf(goal)).size
        println("calibration result: $result")
    }

    fun reverseTransforms() {
        transforms = transforms.map { Pair(it.second, it.first) }.toMutableList()
    }

    fun simpleSynthetize() {
        reverseTransforms()
        println("synthetized $goal in ${synthetize(goal)} steps")
    }

    fun smartSynthetize(breakingAtom: String) {
        reverseTransforms()
        var steps = 0
        var remainingRight = goal
        while (breakingAtom in remainingRight) {
            breakingAtom.toRegex().find(remainingRight)?.range?.first?.let {
                val left = remainingRight.take(it + breakingAtom.length)
                val right = remainingRight.drop(it + breakingAtom.length)
                var reduction = reduce(left)
                if (reduction.size < 1) {
                    for (n in 2..10) {
                        reduction = reduce(left, n)
                        if (reduction.size > 0) break
                    }
                }
                println("$left reduced to $reduction")
                remainingRight = reduction.first().first + right
                steps += reduction.first().second
            }
        }
        println("remaining: $remainingRight")
        steps += synthetize(remainingRight)!!
        println("steps: $steps")
    }

    fun reduce(goalMolecule: String, reducedLength: Int = 1): List<Pair<String, Int>> {
        var molecules = listOf(goalMolecule)
        var result = MutableList(0) { "" to 0 }
        for (step in 1..100) {
            molecules = transform(molecules)
            for (molecule in molecules) {
                if (molecule.decompose().size == reducedLength) {
                    result.add(molecule to step)
                }
            }
        }
        return result
    }

    fun synthetize(goalMolecule: String): Int? {
        var molecules = listOf(goalMolecule)
        for (step in 1..100) {
            molecules = transform(molecules)
            for (molecule in molecules) {
                if (molecule.decompose().size == 1) {
                    println("reached $molecule in $step steps")
                }
            }
            if (molecules.contains("e")) {
                return step
            }
        }
        return null
    }
}

fun main() {
    val realData = File("input/input19.txt").readText()
    MoleculeMachine(TEST_DATA).calibrate()
    MoleculeMachine(realData).calibrate()
    MoleculeMachine(TEST_DATA2).simpleSynthetize()
    MoleculeMachine2(realData)
    MoleculeMachine(realData).smartSynthetize("Ar")
}
