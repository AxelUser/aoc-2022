import java.util.*

fun main() {

    fun part1(input: List<String>): Int {
        var sum = 0
        var cmd = 0
        var x = 1
        val ops: Queue<Triple<Int, String, Int>> = LinkedList()

        for (cycle in 1..220) {
            if (ops.isNotEmpty() && ops.peek().first == cycle) {
                x += ops.poll().third
            }
            if (cmd < input.size && ops.isEmpty()) {
                val parts = input[cmd++].split(' ')
                when (parts[0]) {
                    "addx" -> ops.offer(Triple(cycle + 2, parts[0], parts[1].toInt()))
                }
            }

            if (cycle >= 20 && (cycle == 20 || (cycle - 20) % 40 == 0)) {
                sum += x * cycle
            }
        }

        return sum
    }

    fun part2(input: List<String>): String {
        val screen = Array(6) { BooleanArray(40) }
        var cmd = 0
        var x = 1
        val ops: Queue<Triple<Int, String, Int>> = LinkedList()

        for (cycle in 0 until 240) {
            if (cmd < input.size && ops.isEmpty()) {
                val parts = input[cmd++].split(' ')
                when (parts[0]) {
                    "addx" -> ops.offer(Triple(cycle + 1, parts[0], parts[1].toInt()))
                }
            }

            with(cycle % 40) {
                if (this in x - 1..x + 1) {
                    screen[cycle / 40][this] = true
                }
            }

            if (ops.isNotEmpty() && ops.peek().first == cycle) {
                x += ops.poll().third
            }
        }

        return screen.joinToString("\n") { it.joinToString("") { b -> if (b) "#" else " " } }
    }

    check(part1(readInput("Day10_test")) == 13140)
    println(part2(readInput("Day10_test")))

    println(part1(readInput("Day10")))
    println(part2(readInput("Day10")))
}
