data class RailwayGraph(
    val stations: Map<Int, Station>,
    val adjacency: Map<Int, List<Int>>,
    val startId: Int,
) {
    fun station(id: Int): Station =
        stations.getValue(id)

    fun successors(id: Int): List<Int> =
        adjacency.getOrDefault(id, emptyList())

    val stationIds: Set<Int>
        get() = stations.keys
}
