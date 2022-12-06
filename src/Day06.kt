fun main() {

    fun indexOfPacketEnd(buffer: String, k: Int): Int {
        val counts = IntArray(26)
        for (i in buffer.indices) {

            if (i >= k) counts[buffer[i - k] - 'a']--

            if (++counts[buffer[i] - 'a'] > 1) continue

            if (i >= k - 1 && counts.all { it <= 1 }) {
                return i + 1
            }
        }

        error("Could not find packet end")
    }

    fun part1(input: String): Int {
        return indexOfPacketEnd(input, 4)
    }

    fun part2(input: String): Int {
        return indexOfPacketEnd(input, 14)
    }

    var input = readInput("Day06_test")[0]
    check(part1(input) == 7)
    check(part2(input) == 19)

    input = readInput("Day06")[0]
    println(part1(input))
    println(part2(input))
}