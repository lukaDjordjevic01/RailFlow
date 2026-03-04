
data class Station(val id: Int, val unload: Int, val load: Int) {
    fun transfer(arrival: Set<Int>): Set<Int> = buildSet {
        addAll(arrival)
        remove(unload)
        add(load)
    }
}
