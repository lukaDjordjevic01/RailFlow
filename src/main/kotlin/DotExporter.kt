
fun exportDot(graph: RailwayGraph, arrivals: Map<Int, Set<Int>>): String = buildString {
    val reachable = mutableSetOf(graph.startId)
    val queue = ArrayDeque<Int>()
    queue.addLast(graph.startId)
    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()
        for (successor in graph.successors(current)) {
            if (reachable.add(successor)) {
                queue.addLast(successor)
            }
        }
    }

    appendLine("digraph RailFlow {")
    appendLine("    rankdir=LR;")
    appendLine("    node [shape=record];")
    appendLine()

    for (id in graph.stationIds.sorted()) {
        val station = graph.station(id)
        val cargo = arrivals.getOrDefault(id, emptySet()).sorted().joinToString(", ")
        val label = "$id | u:${station.unload} l:${station.load} | arr: \\{$cargo\\}"

        val color = when {
            id == graph.startId -> "#90EE90"
            id in reachable     -> "#ADD8E6"
            else                -> "#D3D3D3"
        }

        appendLine("    $id [label=\"$label\" style=filled fillcolor=\"$color\"];")
    }

    appendLine()

    for (from in graph.stationIds.sorted()) {
        for (to in graph.successors(from).sorted()) {
            appendLine("    $from -> $to;")
        }
    }

    appendLine("}")
}
