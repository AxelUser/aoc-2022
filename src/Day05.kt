import java.util.Stack

private data class Command(val quantity: Int, val from: Int, val to: Int)

fun main() {

    fun List<String>.parseStacks(): Array<Stack<Char>> {
        val indices = Regex("\\d").findAll(last()).map { it.range.first }.toList()
        val stacks = Array(indices.size) { Stack<Char>() }
        for (i in lastIndex - 1 downTo 0) {
            for (j in 0..indices.lastIndex) {
                this[i][indices[j]].takeIf { it != ' ' }?.also { stacks[j].push(it) }
            }
        }

        return stacks
    }

    fun List<String>.parseCommands(): Sequence<Command> {
        val regex = Regex("\\d+")
        return sequence {
            for (s in this@parseCommands) {
                yield(regex.findAll(s).map { it.value.toInt() }.toList().let { Command(it[0], it[1] - 1, it[2] - 1) })
            }
        }
    }

    fun List<String>.parseInput(): Pair<Array<Stack<Char>>, Sequence<Command>> {
        val split = indexOf("")

        return take(split).parseStacks() to drop(split + 1).parseCommands()
    }

    fun part1(input: List<String>): String {
        val (stacks, commands) = input.parseInput()
        for (cmd in commands) {
            val sFrom = stacks[cmd.from]
            val sTo = stacks[cmd.to]
            repeat(cmd.quantity) {
                sTo.push(sFrom.pop())
            }
        }

        return stacks.map { it.peek() }.joinToString(separator = "")
    }

    fun part2(input: List<String>): String {
        val (stacks, commands) = input.parseInput()
        for (cmd in commands) {
            val sFrom = stacks[cmd.from]
            val sTo = stacks[cmd.to]
            with(Stack<Char>()) {
                repeat(cmd.quantity) {
                    push(sFrom.pop())
                }

                while (isNotEmpty()) {
                    sTo.push(pop())
                }
            }

        }

        return stacks.map { it.peek() }.joinToString(separator = "")
    }

    var input = readInput("Day05_test")
    check(part1(input) == "CMZ")
    check(part2(input) == "MCD")

    input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}