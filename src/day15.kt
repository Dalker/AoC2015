/** AoC 2015 Day 15 - while learning Kotlin - by Dalker */

import java.io.File

const val TEST_DATA = """
Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3
"""

const val template = """([A-Z][a-z]+): capacity (-?[0-9]+), durability (-?[0-9]+), flavor (-?[0-9]+), texture (-?[0-9]+), calories (-?[0-9]+)"""

fun readData(data: String): List<IntArray> =
    data.trim().split('\n').map {
        template.toRegex().find(it)!!.groupValues.let { value ->
            IntArray(5) { n -> value[n + 2].toInt() }
        }
    }

fun intSplit(total: Int, nParts: Int): List<List<Int>> =
    if (nParts == 1) listOf(listOf(total))
    else {
        val partitions = mutableListOf<List<Int>>()
        for (i in 0..total) {
            for (right in intSplit(total - i, nParts - 1)) {
                partitions.add(listOf(i) + right)
            }
        }
        partitions
    }

fun solve(ingredients: List<IntArray>, teaspoons: Int = 100, part2: Boolean = false) {
    var maxProduct = 0
    for (portions in intSplit(teaspoons, ingredients.size)) {
        var product = 1
        for (quality in 0 until ingredients.first().size - 1) {
            var sum = 0
            ingredients.zip(portions).forEach {
                sum += it.first[quality] * it.second
            }
            product *= if (sum <= 0) 0 else sum
        }
        val calories = ingredients.zip(portions).map { it.first.last() * it.second }.sum()
        if (part2 && calories != 500) product = 0
        if (product > maxProduct) maxProduct = product
    }
    println(maxProduct)
}

fun main() {
    val testData = readData(TEST_DATA)
    val realData = readData(File("input/input15.txt").readText())
    solve(testData)
    solve(realData)
    solve(testData, part2 = true)
    solve(realData, part2 = true)
}
