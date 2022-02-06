/** AoC 2015 Day 04, by Dalker */
import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8

const val PUZZLE_INPUT = "ckczppom"

// credit for the following function extensions: https://stackoverflow.com/a/64172506/613191
fun md5(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray(UTF_8))
fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }

/** Find lowest integer such that hash(key + integer) has 5 leading zeroes */
fun mine(secret_key: String, difficulty: Int = 5): Int {
    var proof_of_work = 0
    val target = "0".repeat(difficulty)
    while (true) {
        val hash = md5(secret_key + (++proof_of_work).toString()).toHex()
        if (hash.take(difficulty) == target) return proof_of_work
    }
}

fun tests1() {
    print("Checking hint 1 (should be 609043): ")
    println(mine("abcdef"))
    print("Checking hint 2 (should be 1048970): ")
    println(mine("pqrstuv"))
}

fun solve(part2: Boolean = false) {
    print("Solving: ")
    println(
        if (part2) mine(PUZZLE_INPUT, difficulty = 6)
        else mine(PUZZLE_INPUT)
    )
}

fun main() {
    solve()
    solve(part2 = true)
}
