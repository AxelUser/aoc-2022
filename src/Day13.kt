sealed interface Day13Node : Comparable<Day13Node> {

    override operator fun compareTo(other: Day13Node): Int

    @JvmInline
    value class NumberNode(val value: Int) : Day13Node {
        override fun compareTo(other: Day13Node): Int {
            return when (other) {
                is NumberNode -> value.compareTo(other.value)
                is ArrayNode -> ArrayNode(listOf(this)).compareTo(other)
            }
        }

        override fun toString(): String = value.toString()
    }

    @JvmInline
    value class ArrayNode(val value: List<Day13Node>) : Day13Node {
        override fun compareTo(other: Day13Node): Int {
            return when (other) {
                is NumberNode -> this.compareTo(ArrayNode(listOf(other)))
                is ArrayNode -> {
                    for (i in 0..minOf(value.lastIndex, other.value.lastIndex)) {
                        val cmp = value[i].compareTo(other.value[i])
                        if (cmp != 0) return cmp
                    }

                    return value.size.compareTo(other.value.size)
                }
            }
        }

        override fun toString(): String = value.toString()
    }
}

fun main() {
    fun parse(tokens: ArrayDeque<String>): Day13Node {
        val parsed = mutableListOf<Day13Node>()
        while (tokens.isNotEmpty()) {
            when (val token = tokens.removeFirst()) {
                "[" -> parsed.add(parse(tokens))
                "]" -> return Day13Node.ArrayNode(parsed)
                else -> parsed.add(Day13Node.NumberNode(token.toInt()))
            }
        }

        return parsed[0]
    }

    fun part1(input: List<String>): Int {
        return input.asSequence()
            .filter(String::isNotEmpty)
            .map { Regex("\\[|]|\\d+").findAll(it).map(MatchResult::value).toList() }
            .map(::ArrayDeque)
            .map(::parse)
            .chunked(2)
            .foldIndexed(0) { idx, sum, (left, right) ->
                if (left < right) sum + idx + 1 else sum
            }
    }

    fun part2(input: List<String>): Int {
        val n2 = Day13Node.ArrayNode(listOf(Day13Node.ArrayNode(listOf(Day13Node.NumberNode(2)))))
        val n6 = Day13Node.ArrayNode(listOf(Day13Node.ArrayNode(listOf(Day13Node.NumberNode(6)))))
        return input.asSequence()
            .filter(String::isNotEmpty)
            .map { Regex("\\[|]|\\d+").findAll(it).map(MatchResult::value).toList() }
            .map(::ArrayDeque)
            .map(::parse)
            .plus(listOf(n2, n6))
            .sorted()
            .toList()
            .let { (it.indexOf(n2) + 1) * (it.indexOf(n6) + 1) }
    }

    check(part1(readInput("Day13_test")) == 13)
    check(part2(readInput("Day13_test")) == 140)

    println(part1(readInput("Day13")))
    println(part2(readInput("Day13")))
}
