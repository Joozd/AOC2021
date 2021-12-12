package day12

class Cave(val name: String){
    private val _exits = ArrayList<String>()

    val exits: List<String> get() = _exits

    fun addExit(exit: String){
        if (exit != "start")
        _exits.add(exit)
    }
}