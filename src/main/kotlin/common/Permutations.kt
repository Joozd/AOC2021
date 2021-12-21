package common

class Permutations<T>(valuesToPermute: Collection<T>): Iterable<List<T>>{
    val contents = valuesToPermute.toList()
    var ii =  contents.indices.toList().toMutableList() // current order

    /**
     * Iterator will generate the next permutation
     */
    override operator fun iterator(): Iterator<List<T>> = object : Iterator<List<T>>{
        override operator fun hasNext() = ii.isNotEmpty()
        override operator fun next(): List<T>{
            val result = ii.map{contents[it]}
            ii.dropLast(1).indices.filter{
                ii[it] < ii[it+1]
            }.maxOrNull()?.let{ k ->
                val l = ii.indices.filter{ ii[it] > ii[k]}.maxOrNull() ?: return@let null.also{println("WARNING Permutations.Warning.1: THIS SHOULD NOT HAPPEN")}
                val temp = ii[k]
                ii[k] = ii [l]
                ii[l] = temp
                ii = (ii.take(k+1) + ii.drop(k+1).reversed()).toMutableList()
            } ?: ii.clear()
            return result
        }
    }
}