fun main() {
    fun List<String>.calcSums(): List<Int> {
        val sums = mutableListOf<Int>()
        sums.add(0)
        for (line in this) {
            if (line == "") {
                sums.add(0)
            } else {
                sums[sums.lastIndex] += line.toInt()
            }
        }
        return sums
    }

    fun List<Int>.topSum(n: Int): Int {
        return sortedDescending().take(n).sum()
    }

    fun part1(input: List<String>): Int {
        return input.calcSums().topSum(1)
    }

    fun part2(input: List<String>): Int {
        return input.calcSums().topSum(3)
    }

    check(part1(readInput("Day01_test")) == 24000)
    check(part2(readInput("Day01_test")) == 45000)

    println(part1(readInput("Day01")))
    println(part2(readInput("Day01")))
}
