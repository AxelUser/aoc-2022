import java.util.*

private data class Day12Point(val coords: Pair<Int, Int>, val elevation: Int)

fun main() {

    fun List<String>.find(c: Char): List<Pair<Int, Int>> {
        val points = mutableListOf<Pair<Int, Int>>()

        for (y in indices) {
            for(x in this[y].indices) {
                if (this[y][x] == c) points.add(y to x)
            }
        }

        return points
    }

    fun List<IntArray>.elevationOf(p: Pair<Int, Int>): Int {
        return this[p.first][p.second]
    }

    fun List<IntArray>.bfs(start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
        val dirs = listOf(0, 1, 0, -1, 0)
        val queue: Queue<Day12Point> = LinkedList()
        queue.offer(Day12Point(start, this[start.first][start.second]))
        this[start.first][start.second] = Int.MAX_VALUE

        var steps = 0
        while (queue.isNotEmpty()) {
            steps++
            repeat(queue.size) {
                val (coords, elevation) = queue.poll()

                if (coords == end) {
                    return steps - 1
                }

                val next = dirs.windowed(2).map { coords.first + it[0] to coords.second + it[1] }
                    .filter { it.first in 0..lastIndex && it.second in 0..this[0].lastIndex && elevationOf(it) <= elevation + 1 }
                for (p in next) {
                    queue.offer(Day12Point(p, elevationOf(p)))
                    this[p.first][p.second] = Int.MAX_VALUE
                }
            }
        }

        return Int.MAX_VALUE
    }

    fun List<String>.solve(end: Pair<Int, Int>, vararg starts: Pair<Int, Int>): Int {
        var minSteps = Int.MAX_VALUE
        for (start in starts) {
            val map = map { s -> s.map { it - 'a' }.toIntArray() }.apply { this[start.first][start.second] = 0 }
                .apply { this[end.first][end.second] = 25 }

            minSteps = minOf(minSteps, map.bfs(start, end))
        }

        return minSteps
    }

    fun part1(input: List<String>): Int {
        val start = input.find('S').single()
        val end = input.find('E').single()
        return input.solve(end, start)
    }

    fun part2(input: List<String>): Int {
        val starts = input.find('a').toTypedArray()
        val end = input.find('E').single()
        return input.solve(end, *starts)
    }

    check(part1(readInput("Day12_test")) == 31)
    check(part2(readInput("Day12_test")) == 29)

    println(part1(readInput("Day12")))
    println(part2(readInput("Day12")))
}
