import java.util.*

data class Monkey(
    val items: Queue<Long>,
    val operation: (old: Long) -> Long,
    val test: Long,
    val trueBranch: Int,
    val falseBranch: Int
)

fun main() {

    fun List<String>.monkeys(): List<Monkey> {
        val res = mutableListOf<Monkey>()
        for (data in filter { it != "" }.map { it.trim() }.windowed(6, 6)) {
            val items = LinkedList(Regex("\\d+").findAll(data[1]).map { it.value.toLong() }.toMutableList())

            val func = if (data[2].contains("+")) { a: Long, b: Long -> a + b } else { a: Long, b: Long -> a * b }
            val op = data[2].split(" ").last().let {
                when (it) {
                    "old" -> { old: Long -> func(old, old) }
                    else -> { old: Long -> func(old, it.toLong()) }
                }
            }

            val test = data[3].split(" ").last().toLong()
            val tb = data[4].split(" ").last().toInt()
            val fb = data[5].split(" ").last().toInt()
            res.add(Monkey(items, op, test, tb, fb))
        }
        return res
    }

    fun solve(monkeys: List<Monkey>, rounds: Int, lower: (v: Long) -> Long): Long {
        val stats = LongArray(monkeys.size)
        repeat(rounds) {
            for ((i, m) in monkeys.withIndex()) {
                while (m.items.isNotEmpty()) {
                    stats[i]++
                    val newValue = lower(m.operation(m.items.poll()))
                    if (newValue % m.test == 0L) {
                        monkeys[m.trueBranch].items.offer(newValue)
                    } else {
                        monkeys[m.falseBranch].items.offer(newValue)
                    }
                }
            }
        }

        return stats.sortedArrayDescending().take(2).let { it[0] * it[1] }
    }

    fun part1(input: List<String>): Long {
        val monkeys = input.monkeys()
        return solve(monkeys, 20) { it / 3 }
    }

    fun part2(input: List<String>): Long {
        val monkeys = input.monkeys()
        val modulo = monkeys.map { it.test }.fold(1L) { acc, el -> acc * el }
        return solve(monkeys, 10000) { it % modulo}
    }

    check(part1(readInput("Day11_test")) == 10605L)
    check(part2(readInput("Day11_test")) == 2713310158)

    println(part1(readInput("Day11")))
    println(part2(readInput("Day11")))
}
