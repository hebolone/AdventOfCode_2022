package Days

class Day01 : DayBase(), ISolver {
    override fun basic() : Any {
        val result = "1 basic"
        _input.forEach { println(it) }
        return result
    }
    override fun advanced(): Any {
        return "1 advanced"
    }
}