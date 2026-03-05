import java.util.TreeSet

object RpoSolver {

    fun solve(graph: RailwayGraph): Map<Int, Set<Int>> {
        val (_, rpoNumber) = computeRpo(graph)

        val arrivals: Map<Int, MutableSet<Int>> = buildMap {
            for (id in graph.stationIds) put(id, mutableSetOf())
        }

        val worklist = TreeSet<Int>(compareBy<Int> { rpoNumber[it] ?: Int.MAX_VALUE }.thenBy { it })

        worklist.add(graph.startId)

        while (worklist.isNotEmpty()) {
            val current = worklist.pollFirst()!!

            val station = graph.station(current)
            val departure = station.transfer(arrivals.getValue(current))

            for (successor in graph.successors(current)) {
                if (successor !in rpoNumber) continue

                val successorArrivals = arrivals.getValue(successor)
                if (!successorArrivals.containsAll(departure)) {
                    successorArrivals.addAll(departure)
                    worklist.add(successor)
                }
            }
        }

        return arrivals.mapValues { (_, set) -> set.toSet() }
    }
}
