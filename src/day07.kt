import java.io.File

const val TEST_DATA = """
123 -> x
456 -> y
x AND y -> d
x OR y -> e
x LSHIFT 2 -> f
y RSHIFT 2 -> g
NOT x -> h
NOT y -> i
"""
const val INPUT_FILE = "input/input07.txt"

// following hack taken from https://discuss.kotlinlang.org/t/using-regex-in-a-when/1794/4
operator fun Regex.contains(text: CharSequence): Boolean = this.matches(text)
// goal: imitate nice Scala when(string) {regex -> ...} feature
// plus my own complementary hack below:
fun <T> Regex.let(text: CharSequence, lambda: (List<String>) -> T) =
    this.find(text)!!.groupValues.let(lambda)

class Circuit(val logging: Boolean = false) {
    val rules: MutableMap<String, String> = mutableMapOf()
    val values: MutableMap<String, UInt> = mutableMapOf()

    companion object {
        /* preferably calculate regexps only once, hence in companion object */
        val assignPattern = """([a-z]+|\d+)""".toRegex()
        val notPattern = """NOT ([a-z]+)""".toRegex()
        val andPattern = """([a-z]+|\d+) AND ([a-z]+)""".toRegex()
        val literalAndPattern = """(\d+) AND ([a-z]+)""".toRegex()
        val orPattern = """([a-z]+) OR ([a-z]+)""".toRegex()
        val lshiftPattern = """([a-z]+) LSHIFT (\d+)""".toRegex()
        val rshiftPattern = """([a-z]+) RSHIFT (\d+)""".toRegex()
    }

    constructor(instructions: String, logging: Boolean = false) : this(logging) {
        for (instruction in instructions.trim().split("\n")) {
            val (rule, wire) = instruction.split(" -> ")
            rules.put(wire, rule)
            if (logging) println("wire $wire has rule $rule")
        }
    }

    fun valueOf(wire: String): UInt = wire.toUIntOrNull() ?: values.get(wire) ?: compute(wire)

    fun compute(wire: String): UInt {
        if (logging) println("evaluating wire $wire")
        val rule = rules.get(wire)!!
        if (logging) println("must compute $wire with $rule")
        val result = when (rule) {
            in assignPattern -> valueOf(rule)
            in notPattern ->
                /* circumvent Kotlin not handling 16-bit bitwise operations... :-( */
                notPattern.let(rule) {
                    (65536u + valueOf(it[1]).inv() - 4294967296u).toUInt()
                }
            in andPattern -> andPattern.let(rule) { valueOf(it[1]) and valueOf(it[2]) }
            in orPattern -> orPattern.let(rule) { valueOf(it[1]) or valueOf(it[2]) }
            in lshiftPattern -> lshiftPattern.let(rule) { valueOf(it[1]) shl it[2].toInt() }
            in rshiftPattern -> rshiftPattern.let(rule) { valueOf(it[1]) shr it[2].toInt() }
            else -> 0u.also {
                println("wire $wire with rule $rule couldn't be interpreted")
            }
        }
        if (logging) println("assigned $wire with $result")
        values.put(wire, result)
        return result
    }
}

fun test1() {
    val circuit = Circuit(TEST_DATA)
    println(
        listOf("d", "e", "f", "g", "h", "i", "x", "y").map {
            "$it: ${circuit.valueOf(it)}"
        }.joinToString(", ", "Test values: ")
    )
}

fun solve(modifier: UInt? = null): UInt {
    val circuit = Circuit(File(INPUT_FILE).readText())
    modifier?.let {
        circuit.values.put("b", modifier)
    }
    val result = circuit.valueOf("a")
    println("part ${if (modifier == null) 1 else 2} result: $result")
    return result
}

fun main() {
    test1()
    val first_a = solve()
    solve(first_a)
}
