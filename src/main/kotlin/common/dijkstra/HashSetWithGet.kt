package common.dijkstra

/**
 * Pretty much the same as a HashMap but with a getter since I use that.
 */
class HashSetWithGet<T>(initialSize: Int? = null, loadFactor: Float? = null): MutableSet<T> {
    private val backingMap = makeBackingMap(initialSize, loadFactor)
    /**
     * Adds the specified element to the set.
     *
     * @return `true` if the element has been added, `false` if the element is already contained in the set.
     */
    override fun add(element: T): Boolean =
        if (backingMap.containsKey(element)) false
        else {
            backingMap[element] = element
            true
        }

    override fun addAll(elements: Collection<T>): Boolean {
        var modified = false
        elements.forEach {
            if (add(it))
                modified = true
        }
        return modified
    }

    override fun clear() = backingMap.clear()

    override fun iterator(): MutableIterator<T> = backingMap.keys.iterator()

    override fun remove(element: T): Boolean =
        backingMap.remove(element) != null

    override fun removeAll(elements: Collection<T>): Boolean {
        var modified = false
        elements.forEach {
            if (remove(it))
                modified = true
        }
        return modified
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        var modified = false
        backingMap.keys.forEach {
            if (it !in elements && remove(it))
                modified = true
        }
        return modified
    }

    override val size: Int
        get() = backingMap.size

    override fun contains(element: T): Boolean = backingMap[element] != null

    override fun containsAll(elements: Collection<T>): Boolean = elements.all{ contains(it) }

    override fun isEmpty(): Boolean = backingMap.isEmpty()

    /**
     * Replace an item in this set by one with the same signature.
     * @return true if an item was replaced, false if this was a new entry
     */
    fun replace(element: T): Boolean{
        val result = contains(element)
        backingMap[element] = element
        return result
    }

    /**
     * Replace an item if [prerequisite] is met.
     */
    fun replaceIf(element: T, prerequisite: (oldValue: T?) -> Boolean){
        if (prerequisite(this[element]))
            replace(element)
    }

    operator fun get(element: T) = backingMap[element]

    fun toList(): List<T> = backingMap.keys.toList()

    override fun toString() = toList().toString()

    //Make the backing Map
    private fun makeBackingMap(initialSize: Int?, loadFactor: Float?): HashMap<T, T>{
        if (initialSize == null) return HashMap()
        if (loadFactor == null) return HashMap(initialSize)
        return HashMap(initialSize, loadFactor)
    }
}