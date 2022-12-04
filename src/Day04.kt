fun main() {

    fun List<String>.parse(): Sequence<Pair<IntRange, IntRange>> {
        val regex = Regex("\\d+")

        return sequence {
            for (s in this@parse) {
                yield(regex.findAll(s).map { it.value.toInt() }.toList().let { (it[0]..it[1]) to (it[2]..it[3]) })
            }
        }
    }

    fun part1(input: Sequence<Pair<IntRange, IntRange>>): Int {
        return input.count { (first, second) ->
            (first.first >= second.first && first.last <= second.last) || (second.first >= first.first && second.last <= first.last)
        }
    }

    fun part2(input: Sequence<Pair<IntRange, IntRange>>): Int {
        return input.count { (first, second) -> second.contains(first.last) || first.contains(second.last) }
    }

    check(part1(readInput("Day04_test").parse()) == 2)
    check(part2(readInput("Day04_test").parse()) == 4)

    println(part1(readInput("Day04").parse()))
    println(part2(readInput("Day04").parse()))
}