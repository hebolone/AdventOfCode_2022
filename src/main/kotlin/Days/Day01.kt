package Days

class Day01 : DayBase(), ISolver {
    override fun basic() : Any = _elvesCalories.max()

    override fun advanced() : Any = _elvesCalories.sortedByDescending { it }.take(3).sum()

    private val _elvesCalories = mutableListOf<Int>()

    override fun parse() {
        var currentElf = 0

        _input.forEach {
            if(it == "") {
                _elvesCalories.add(currentElf)
                currentElf = 0
            } else {
                currentElf += it.toInt()
            }
        }
    }
}