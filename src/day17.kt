import java.io.File

val TEST_CONTAINERS = listOf(5, 20, 15, 10, 5)

class Fitter {
    var minDepth = 0
    var maxDepth = 0

    fun nFits(containers: List<Int>, content: Int = 150, depth: Int = 0, minimize: Boolean = false): Int =
        when {
            containers.isEmpty() || content == 0 -> 0
            else -> {
                if (depth > maxDepth) maxDepth = depth
                val ordered = containers.sorted().reversed()
                val first = ordered[0]
                val rest = ordered.drop(1)
                when {
                    content < first -> 0
                    content == first -> {
                        when {
                            !minimize -> {
                                if (depth < minDepth || minDepth == 0) minDepth = depth
                                1
                            }
                            depth == minDepth -> 1
                            else -> 0
                        }
                    }
                    else -> nFits(rest, content - first, depth + 1, minimize)
                } + nFits(rest, content, depth, minimize)
            }
        }
}

fun getContainers(): List<Int> =
    File("input/input17.txt").readLines().map { it.toInt() }

fun solve(containers: List<Int>, content: Int) {
    println("* Fitting $content in available containers *")
    val fitter = Fitter()
    println("possible fits: " + fitter.nFits(containers, content).toString())
    println("max containers: ${fitter.maxDepth}")
    println("min containers: ${fitter.minDepth}")
    println("fits with min containers: " + fitter.nFits(containers, content, minimize = true).toString())
}

fun main() {
    val real_containers = getContainers()
    solve(TEST_CONTAINERS, 25)
    solve(real_containers, 150)
}
