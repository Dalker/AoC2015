/** AoC 2015 Day 14 - while learning Kotlin - by Dalker */
import java.io.File

class RacingReindeer(desc: String) {
    val regex = Regex("""([A-Z][a-z]*) can fly ([0-9]*) km/s for ([0-9]*) seconds, but then must rest for ([0-9]*) seconds.""")
    val name: String
    val speed: Int
    val flyTime: Int
    val restTime: Int
    init {
        regex.find(desc)!!.groupValues.also {
            name = it.get(1)
            speed = it.get(2).toInt()
            flyTime = it.get(3).toInt()
            restTime = it.get(4).toInt()
        }
    }

    fun race(duration: Int = 1000): Int {
        var timeLeft = duration
        var distance = 0
        while (timeLeft > 0) {
            distance += (if (timeLeft >= flyTime) flyTime else timeLeft) * speed
            timeLeft -= flyTime + restTime
        }
        return distance
    }
}

fun race(reindeers: List<RacingReindeer>, duration: Int = 1000) {
    reindeers.sortedByDescending { it.race(duration) }.first().apply {
        println("$name won, having travelled ${race(duration)} km.")
    }
}

fun race2(reindeers: List<RacingReindeer>, duration: Int = 1000) {
    val scores = reindeers.map { Pair(it, 0) }.toMap().toMutableMap()
    val distances = reindeers.map { Pair(it, 0) }.toMap().toMutableMap()
    for (time in 0 until duration) {
        reindeers.forEach {
            if (time % (it.flyTime + it.restTime) < it.flyTime) {
                distances[it] = distances[it]!! + it.speed
            }
        }
        val lead = distances.values.maxOrNull()!!
        reindeers.forEach {
            if (distances[it] == lead) {
                scores[it] = scores[it]!! + 1
            }
        }
    }
    /*
    reindeers.forEach {
        println("${it.name} travelled ${distances[it]} km and got a score of ${scores[it]}")
    }
    */
    reindeers.sortedByDescending { scores[it] }.first().apply {
        println("$name won, having scored ${scores[this]}.")
    }
}

fun main() {
    val testReindeers = listOf(
        "Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.",
        "Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds."
    ).map { RacingReindeer(it) }
    val realReindeers = File("input/input14.txt").readLines().map { RacingReindeer(it) }
    race(testReindeers)
    race(realReindeers, 2503)
    race2(testReindeers)
    race2(realReindeers, 2503)
}
