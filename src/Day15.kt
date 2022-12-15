import kotlin.math.abs

data class Day15Data(val scanner: Point<Long>, val beacon: Point<Long>, val distance: Long)

fun main() {
    fun List<String>.parse(): List<Day15Data> {
        return this.map {
            Regex("-?\\d++").findAll(it).map { it.value.toLong() }.toList()
                .let { (x1, y1, x2, y2) -> Day15Data(Point(y1, x1), Point(y2, x2), abs(x1 - x2) + abs(y1 - y2)) }
        }
    }

    fun getRange(data: Day15Data, row: Long): LongArray {
        val xDiff = data.distance - abs(row - data.scanner.y)
        val start = data.scanner.x - xDiff
        val end = data.scanner.x + xDiff

        return longArrayOf(start, end)
    }

    fun getRanges(data: List<Day15Data>, row: Long): List<LongArray> {
        val ranges = data.asSequence()
            .filter { row in it.scanner.y - it.distance..it.scanner.y + it.distance }
            .map { getRange(it, row) }
            .sortedBy { it[0] }
            .toList()

        return ranges.fold(mutableListOf()) { acc, range ->
            acc.apply {
                if (isEmpty() || acc.last()[1] < range[0]) {
                    add(range)
                } else if (acc.last()[1] >= range[0]) {
                    acc.last()[1] = maxOf(acc.last()[1], range[1])
                }
            }
        }
    }

    fun part1(data: List<Day15Data>, row: Long): Long {
        val sum = getRanges(data, row).sumOf { abs(it[1] - it[0]) + 1 }
        val beacons = data.map { it.beacon }.toSet().count { it.y == row }
        return sum - beacons
    }

    fun calcFreq(x: Long, y: Long): Long = x * 4_000_000 + y

    fun part2(data: List<Day15Data>, upperBound: Long): Long {
        for (row in 0..upperBound) {
            val ranges = getRanges(data, row)
            when {
                ranges.size == 1 && ranges[0][0] <= 0 && ranges[0][1] >= upperBound -> continue
                ranges.size == 1 && ranges[0][0] > 0 -> {
                    return calcFreq(0, row)
                }
                ranges.size == 1 && ranges[0][1] < upperBound -> {
                    return calcFreq(upperBound, row)
                }
                ranges.size == 2 -> {
                    return calcFreq(ranges[0][1] + 1, row)
                }
                else -> error(
                    "row:$row, ranges: ${
                        ranges.joinToString(prefix = "[", postfix = "]") {
                            it.joinToString(
                                prefix = "[",
                                postfix = "]"
                            )
                        }
                    }"
                )
            }
        }
        error("not found")
    }

    val testInput = readInput("Day15_test").parse()
    check(part1(testInput, 10) == 26L)
    check(part2(testInput, 20) == 56000011L)

    val input = readInput("Day15").parse()
    println(part1(input, 2_000_000))
    println(part2(input, 4_000_000))
}
