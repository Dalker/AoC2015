/** AoC 2015 Day 11 by Dalker while learning Kotlin */

operator fun String.inc(): String = increment(this)
fun increment(s: String): String =
    if (s.last() == 'z') increment(s.dropLast(1)) + 'a' else s.dropLast(1) + (s.last() + 1)

fun getNextPass(pass: String): String {
    var newPass = pass
    while (!checkPass(++newPass)) {}
    return newPass
}

fun checkPass(pass: String): Boolean =
    containsStraight(pass) && hasNoForbidden(pass) && hasTwoPairs(pass)

fun containsStraight(pass: String): Boolean {
    for (i in 0..pass.lastIndex - 2) {
        if (pass[i + 1] == pass[i] + 1 && pass[i + 2] == pass[i] + 2) {
            return true
        }
    }
    return false
}

fun hasNoForbidden(pass: String): Boolean =
    !pass.contains('i') && !pass.contains('o') && !pass.contains('l')

fun hasTwoPairs(pass: String): Boolean {
    var i = 0
    var nPairs = 0
    while (i < pass.lastIndex) {
        if (pass[i] == pass[i + 1]) {
            ++nPairs
            i += 2
        } else {
            ++i
        }
    }
    return nPairs >= 2
}

fun runTests() {
    for (pass in listOf("hijklmmn", "abbceffg", "abbcegjk")) {
        print("$pass -> ")
        print("1st req " + if (containsStraight(pass)) "ok" else "ko")
        print(", 2nd req " + if (hasNoForbidden(pass)) "ok" else "ko")
        println(", 3rd req " + if (hasTwoPairs(pass)) "ok" else "ko")
    }
    for (pass in listOf("abcdefgh", "ghijklmn")) {
        println("next after '$pass' is ${getNextPass(pass)}")
    }
}

fun main() {
    val myInput = "hepxcrrq"
    runTests()
    val nextPass = getNextPass(myInput)
    println("part 1 solution: $nextPass")
    println("part 2 solution: ${getNextPass(nextPass)}")
}
