
fun computeRpo(graph: RailwayGraph): Pair<List<Int>, Map<Int, Int>> {
    val postorder = mutableListOf<Int>()
    val visited = mutableSetOf<Int>()

    val stack = ArrayDeque<Pair<Int, Iterator<Int>>>()

    visited.add(graph.startId)
    stack.addLast(graph.startId to graph.successors(graph.startId).iterator())

    while (stack.isNotEmpty()) {
        val (node, successors) = stack.last()

        if (successors.hasNext()) {
            val next = successors.next()
            if (visited.add(next)) {
                stack.addLast(next to graph.successors(next).iterator())
            }
        } else {
            stack.removeLast()
            postorder.add(node)
        }
    }

    val rpoList = postorder.asReversed()
    val rpoNumber = buildMap {
        rpoList.forEachIndexed { index, id -> put(id, index) }
    }

    return rpoList to rpoNumber
}
