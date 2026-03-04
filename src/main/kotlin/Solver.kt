
object Solver {

    fun solve(graph: RailwayGraph): Map<Int, Set<Int>> {
        val arrivals: Map<Int, MutableSet<Int>> = buildMap {
            for (id in graph.stationIds) put(id, mutableSetOf())
        }

        val worklist = ArrayDeque<Int>()
        val inWorklist = mutableSetOf<Int>()

        worklist.addLast(graph.startId)
        inWorklist.add(graph.startId)

        while (worklist.isNotEmpty()) {
            val current = worklist.removeFirst()
            inWorklist.remove(current)

            val station = graph.station(current)
            val departure = station.transfer(arrivals.getValue(current))

            for (successor in graph.successors(current)) {
                val successorArrivals = arrivals.getValue(successor)
                // Check if departure contributes anything new (departure ⊄ arrivals).
                if (!successorArrivals.containsAll(departure)) {
                    successorArrivals.addAll(departure)
                    if (inWorklist.add(successor)) {
                        worklist.addLast(successor)
                    }
                }
            }
        }

        // Return an immutable snapshot.
        return arrivals.mapValues { (_, set) -> set.toSet() }
    }
}
