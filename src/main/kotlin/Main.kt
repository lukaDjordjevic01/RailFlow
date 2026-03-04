
fun main() {
    val lines = generateSequence(::readLine).toList()
    var cursor = 0

    fun nextLine(): String = lines[cursor++]

    val (stationCount, trackCount) = nextLine().trim().split(" ").map(String::toInt)

    val stations = buildMap {
        repeat(stationCount) {
            val parts = nextLine().trim().split(" ").map(String::toInt)
            val station = Station(id = parts[0], unload = parts[1], load = parts[2])
            put(station.id, station)
        }
    }

    val adjacency = mutableMapOf<Int, MutableList<Int>>()
    repeat(trackCount) {
        val (from, to) = nextLine().trim().split(" ").map(String::toInt)
        adjacency.getOrPut(from) { mutableListOf() }.add(to)
    }

    val startId = nextLine().trim().toInt()

    val graph = RailwayGraph(
        stations = stations,
        adjacency = adjacency.mapValues { (_, v) -> v.toList() },
        startId = startId,
    )

    val arrivals = Solver.solve(graph)

    for (id in arrivals.keys.sorted()) {
        val cargo = arrivals.getValue(id).sorted()
        println("$id: $cargo")
    }
}
