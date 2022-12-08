data class Input(
    val rows: Array<Array<MutableList<Int>>>,
    val cols: Array<Array<MutableList<Int>>>,
    val map: Array<IntArray>
)

fun main() {

    fun List<String>.parse(): Input {
        val rows = Array(size) { Array(10) { mutableListOf<Int>() } }
        val cols = Array(this[0].length) { Array(10) { mutableListOf<Int>() } }
        val map = Array(size) { IntArray(this[0].length) }
        for (row in indices) {
            for (col in this[row].indices) {
                val num = this[row][col] - '0'
                rows[row][num].add(col)
                cols[col][num].add(row)
                map[row][col] = num
            }
        }

        return Input(rows, cols, map)
    }


    fun part1(input: Input): Int {
        val (rows, cols, map) = input

        var count = (map.size - 1) * 2 + (map[0].size - 1) * 2

        for (row in 1 until map.lastIndex) {
            for (col in 1 until map[row].lastIndex) {
                val n = map[row][col]

                var leftCover = false
                var rightCover = false
                for (coverIdx in rows[row].drop(n).flatten()) {
                    if (coverIdx > col) rightCover = true
                    if (coverIdx < col) leftCover = true
                    if (leftCover && rightCover) break
                }

                if (!leftCover || !rightCover) {
                    count++
                    continue
                }

                var topCover = false
                var bottomCover = false
                for (coverIdx in cols[col].drop(n).flatten()) {
                    if (coverIdx > row) bottomCover = true
                    if (coverIdx < row) topCover = true
                    if (topCover && bottomCover) break
                }

                if (!topCover || !bottomCover) {
                    count++
                    continue
                }
            }
        }

        return count
    }

    fun part2(input: Input): Int {
        val (_, _, map) = input

        var maxScore = 0

        for (row in map.indices) {
            for (col in map[row].indices) {
                var seeLeft = 0
                for (i in col - 1 downTo 0) {
                    seeLeft++
                    if (map[row][i] >= map[row][col]) break
                }

                var seeRight = 0
                for (i in col + 1..map[row].lastIndex) {
                    seeRight++
                    if (map[row][i] >= map[row][col]) break
                }

                var seeTop = 0
                for (i in row - 1 downTo 0) {
                    seeTop++
                    if (map[i][col] >= map[row][col]) break
                }

                var seeBottom = 0
                for (i in row + 1..map.lastIndex) {
                    seeBottom++
                    if (map[i][col] >= map[row][col]) break
                }

                maxScore = maxOf(maxScore, seeLeft * seeRight * seeTop * seeBottom)
            }

        }

        return maxScore
    }

    var input = readInput("Day08_test").parse()
    check(part1(input) == 21)
    check(part2(input) == 8)

    input = readInput("Day08").parse()
    println(part1(input))
    println(part2(input))
}