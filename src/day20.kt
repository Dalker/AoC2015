/** AoC 2015 Day 20 - by Dalker - while learning Kotlin */
import kotlin.math.sqrt

const val INPUT = 36000000
const val HOUSES = 50

fun addDivisors(input: Int, part2: Boolean = false): Int {
    var result = 0
    var root = (sqrt(input.toDouble())).toInt()
    for (n in 1..root) {
        if (n * n == input) {
            if (!part2 || input <= HOUSES * root)
                result += root
        } else if (input % n == 0) {
            if (!part2 || input <= HOUSES * n)
                result += n
            if (!part2 || input <= HOUSES * input / n)
                result += input / n
        }
    }
    return result
}

fun test1() {
    for (n in 1..10) {
        print("product of divisors of $n: ")
        println(addDivisors(n))
    }
}

fun solve(part2: Boolean = false) {
    var n = 1
    val factor = if (part2) 11 else 10
    while (true) {
        ++n
        if (addDivisors(n, part2) * factor >= INPUT) {
            println("solution ${if (part2) 2 else 1}: $n")
            break
        }
    }
}

fun main() {
    // test1()
    solve()
    solve(part2 = true)
}
