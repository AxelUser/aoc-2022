fun main() {
    fun List<String>.solve1(): Int {
        var res = 0
        for (rucksack in this) {
            val set = mutableSetOf<Char>()
            for (i in 0 until rucksack.length / 2) {
                set.add(rucksack[i])
            }

            for (i in rucksack.length / 2 until rucksack.length) {
                if (set.contains(rucksack[i])) {
                    res += if (rucksack[i].isUpperCase()) rucksack[i] - 'A' + 27 else rucksack[i] - 'a' + 1
                    break
                }
            }
        }

        return res
    }

    fun List<String>.solve2(): Int {
        var res = 0
        for (group in windowed(3, 3)) {
            val set = mutableMapOf<Char, Int>()
            for (gi in group.indices) {
                for (c in group[gi]) {
                    if (set.compute(c) { _, v -> if (v == null) 1 shl gi else v or (1 shl gi) } == 7) {
                        res += if (c.isUpperCase()) c - 'A' + 27 else c - 'a' + 1
                        break
                    }
                }

            }
        }

        return res
    }

    fun part1(input: List<String>): Int {
        return input.solve1()
    }

    fun part2(input: List<String>): Int {
        return input.solve2()
    }

    check(part1(readInput("Day03_test")) == 157)
    check(part2(readInput("Day03_test")) == 70)

    println(part1(readInput("Day03")))
    println(part2(readInput("Day03")))
}