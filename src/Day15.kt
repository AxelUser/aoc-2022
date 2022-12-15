import kotlin.math.abs

data class Day15Data(val scanner: Point<Long>, val beacon: Point<Long>, val distance: Long)

fun main() {
    fun getRange(data: Day15Data, row: Long): LongArray? {
        val xDiff = data.distance - abs(row - data.scanner.y)
        var start = data.scanner.x - xDiff
        var end = data.scanner.x + xDiff
        if (start == data.beacon.x) {
            start++
        } else if (end == data.beacon.x) {
            end--
        }

        return if (start <= end) longArrayOf(start, end) else null
    }

    fun part1(input: List<String>, row: Long): Long {
        val data = input.map {
            Regex("\\d++").findAll(it).map { it.value.toLong() }.toList()
                .let { (x1, y1, x2, y2) -> Day15Data(Point(y1, x1), Point(y2, x2), abs(x1 - x2) + abs(y1 - y2)) }
        }

        val ranges = data.asSequence()
            .filter { row in it.scanner.y - it.distance..it.scanner.y + it.distance }
            .map { getRange(it, row) }
            .filterNotNull()
            .sortedBy { it[0] }
            .toList()

        val merged = ranges.fold(mutableListOf<LongArray>()) { acc, range ->
            acc.apply {
                if (isEmpty() || acc.last()[1] < range[0]) {
                    add(range)
                } else if (acc.last()[1] >= range[0]) {
                    acc.last()[1] = maxOf(acc.last()[1], range[1])
                }
            }
        }

        val size = merged.sumOf { abs(it[1] - it[0]) + 1 }
        return size
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    check(part1(readInput("Day15_test"), 10) == 26L)
    check(part2(readInput("Day15_test")) == 0)

    println(part1(readInput("Day15"), 2_000_000))
    println(part2(readInput("Day15")))
}
