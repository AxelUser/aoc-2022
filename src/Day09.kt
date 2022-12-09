import kotlin.math.abs

fun main() {
    val diagonal = listOf(1, 1, -1, -1, 1)
    val straight = listOf(0, 1, 0, -1, 0)

    fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> = first + other.first to second + other.second
    fun Pair<Int, Int>.distanceTo(other: Pair<Int, Int>): Int =
        maxOf(abs(first - other.first), abs(second - other.second))

    fun Pair<Int, Int>.closestTo(other: Pair<Int, Int>): Pair<Int, Int> =
        (if (first != other.first && second != other.second) diagonal else straight)
            .windowed(2)
            .map { (row, col) -> this.plus(row to col) }
            .single { it.distanceTo(other) == 1 }

    val deltaMap = mapOf(
        'R' to (0 to 1),
        'L' to (0 to -1),
        'U' to (1 to 0),
        'D' to (-1 to 0)
    )

    fun List<String>.solve(k: Int): Int {
        val knots = Array(k) { 0 to 0 }
        val visited = mutableSetOf(0 to 0)
        for ((deltaH, steps) in this.map { it.split(' ').let { (d, s) -> deltaMap[d[0]]!! to s.toInt() } }) {
            repeat(steps) {
                knots[0] = knots[0].plus(deltaH)
                for (ki in 1..knots.lastIndex) {
                    if (knots[ki].distanceTo(knots[ki - 1]) < 2) break
                    knots[ki] = knots[ki].closestTo(knots[ki - 1])
                }
                visited.add(knots.last())
            }
        }

        return visited.size
    }

    fun part1(input: List<String>): Int {
        return input.solve(2)
    }

    fun part2(input: List<String>): Int {
        return input.solve(10)
    }

    var input = readInput("Day09_test")
    check(part1(input) == 13)
    check(part2(input) == 1)

    input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}