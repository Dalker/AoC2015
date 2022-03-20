/** AoC 2015, Day 10, by Dalker */

fun lookAndSay(input: String): String {
    var output = ""
    var currentDigit = input.first()
    var currentCount = 0
    for (digit in input) {
        if (digit == currentDigit) {
            ++currentCount
        } else {
            output += currentCount.toString()
            output += currentDigit.toString()
            currentDigit = digit
            currentCount = 1
        }
    }
    output += currentCount.toString()
    output += currentDigit.toString()
    return output
}

/** Mémoisation + splitting, credits to @anceps for the original idea! (on Discord chat)*/
var memo = mutableMapOf(Pair("1", "11"))
fun lookAndSayMemo(input: String): String =
    memo.get(input) ?: when {
        input.length < 10 -> lookAndSay(input)
        else -> {
            var j = input.length / 2 - 1
            while (input[j] == input[j + 1]) ++j
            lookAndSayMemo(input.slice(0..j)) + lookAndSayMemo(input.slice(j + 1..input.lastIndex))
        }
    }.also { memo.set(input, it) }

fun test() {
    var current = "1"
    repeat(5) {
        current = lookAndSayMemo(current)
    }
    println("test1: after 5 iterations 1 becomes $current")
}

fun solve() {
    var current = "1113122113"
    for (j in 1..50) {
        current = lookAndSayMemo(current)
        if (j == 40 || j == 50) {
            println("length at iteration $j: ${current.length}")
        }
    }
}

fun main() {
    test()
    solve()
}
