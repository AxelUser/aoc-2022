fun main() {

    fun List<String>.getStones(): Set<Point<Int>> {
        val map = mutableSetOf<Point<Int>>()
        for (path in this.map { it.split("->").map { it.trim().split(",").let { (x, y) -> Point(y.toInt(), x.toInt()) } } }) {
            for ((from, to) in path.windowed(2)) {
                val points = if (from.y == to.y) {
                    (minOf(from.x, to.x)..maxOf(from.x, to.x)).map { Point(from.y, it) }
                } else {
                    (minOf(from.y, to.y)..maxOf(from.y, to.y)).map { Point(it, from.x) }
                }
                points.forEach { map.add(it) }
            }
        }

        return map
    }

    fun Map<Point<Int>, Char>.simulate(start: Point<Int>, imaginaryFloor: Int): Point<Int> {
        var current = start
        while (true) {
            val next = arrayOf(
                Point(current.y + 1, current.x),
                Point(current.y + 1, current.x - 1),
                Point(current.y + 1, current.x + 1)
            ).firstOrNull { !this.containsKey(it) && it.y != imaginaryFloor } ?: return current

            current = next
        }
    }

    fun part1(input: List<String>): Int {
        val map = input.getStones().associateWith { '#' }.toMutableMap()
        val maxY = map.keys.maxOf { it.y }
        var count = 0
        while (true) {
            if (map.simulate(Point(0, 500), maxY + 1).takeIf { it.y != maxY }?.also { map[it] = 'o' } == null) break
            count++
        }
        return count
    }

    fun part2(input: List<String>): Int {
        val map = input.getStones().associateWith { '#' }.toMutableMap()
        val imaginaryFloor = map.keys.maxOf { it.y } + 2
        val start = Point(0, 500)
        var count = 0
        while (map.simulate(start, imaginaryFloor).also { map[it] = 'o' } != start) {
            count++
        }
        return count + 1
    }

    check(part1(readInput("Day14_test")) == 24)
    check(part2(readInput("Day14_test")) == 93)

    println(part1(readInput("Day14")))
    println(part2(readInput("Day14")))
}
