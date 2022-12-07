import java.util.Stack

fun main() {

    fun List<String>.buildTree(): Map<String, Int> {
        val stack = Stack<String>()
        val dirs = mutableMapOf<String, Int>()
        dirs["/"] = 0
        stack.push("/")
        for (i in 1..lastIndex) {
            val line = this[i].split(' ')
            when (line[0]) {
                "$" -> when (line[1]) {
                    "cd" -> when (line[2]) {
                        ".." -> stack.pop()
                        else -> stack.push("${stack.peek()}${line[2]}/")
                    }
                }

                "ls" -> {}
                "dir" -> {
                    dirs["${stack.peek()}${line[1]}/"] = 0
                }

                else -> {
                    val size = line[0].toInt()
                    stack.forEach {
                        dirs[it] = dirs[it]!! + size
                    }
                }
            }
        }

        return dirs
    }

    fun part1(input: List<String>): Int {
        val dirs = input.buildTree()
        return dirs.values.filter { v -> v <= 100000 }.sum()
    }

    fun part2(input: List<String>): Int {
        val dirs = input.buildTree()
        val target = 30000000 - (70000000 - dirs["/"]!!)
        return dirs.values.filter { v -> v >= target }.min()
    }

    var input = readInput("Day07_test")
    check(part1(input) == 95437)
    check(part2(input) == 24933642)

    input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}