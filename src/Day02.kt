fun main() {
    val wins = hashMapOf(
        1 to 3,
        2 to 1,
        3 to 2
    )

    fun List<String>.solve1(): Int {
        var score = 0
        for (round in this) {
            val opponent = round[0] - 'A' + 1
            val player = round[2] - 'X' + 1
            if (opponent == player) {
                score += 3 // draw
            } else if (wins[player] == opponent) {
                score += 6 // win
            }

            score += player
        }

        return score
    }

    fun List<String>.solve2(): Int {
        val loses = wins.map { (w,l) -> l to w }.toMap()
        var score = 0
        for (round in this) {
            val opponent = round[0] - 'A' + 1
            val player = when(round[2]) {
                'X' -> wins[opponent]!!
                'Y' -> opponent
                'Z' -> loses[opponent]!!
                else -> error("invalid option ${round[2]}")
            }

            score += player + (round[2] - 'X') * 3
        }

        return score
    }

    fun part1(input: List<String>): Int {
        return input.solve1()
    }

    fun part2(input: List<String>): Int {
        return input.solve2()
    }

    check(part1(readInput("Day02_test")) == 15)
    check(part2(readInput("Day02_test")) == 12)

    println(part1(readInput("Day02")))
    println(part2(readInput("Day02")))
}