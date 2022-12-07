import java.util.Stack

fun main() {

    fun List<String>.buildTree(): Map<String, Int> {
        val stack = Stack<String>()
        val dirs = mutableMapOf<String, Int>()
        dirs["/"] = 0
        stack.push("/")
        var i = 1
        while (i <= lastIndex) {
            if (this[i][0] == '$') {
                val cmd = this[i].substring(2..this[i].lastIndex).split(' ')
                when (cmd[0]) {
                    "cd" -> {
                        when (cmd[1]) {
                            ".." -> stack.pop()
                            else -> stack.push("${stack.peek()}${cmd[1]}/")
                        }
                        i++
                    }

                    "ls" -> {
                        var j = i + 1
                        while (j <= lastIndex && this[j][0] != '$') {
                            val f = this[j++].split(' ')
                            when (f[0]) {
                                "dir" -> dirs["${stack.peek()}${f[1]}/"] = 0
                                else -> {
                                    val size = f[0].toInt()
                                    stack.forEach {
                                        dirs[it] = dirs[it]!! + size
                                    }
                                }
                            }
                        }
                        i = j
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