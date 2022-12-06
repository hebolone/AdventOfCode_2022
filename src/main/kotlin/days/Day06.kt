package days

class Day06 : DayBase(), ISolver {
    override fun basic(): Any = findMarker(_input.first(), 4)

    override fun advanced(): Any = findMarker(_input.first(), 14)

    override fun parse() { }

    //region Methods
    private fun findMarker(s : String, noOfChar : Int) : Int {
        for(i in noOfChar .. s.length) {
            val chunk = s.substring(i - noOfChar, i)
            if(checkIfMarker(chunk)) {
                return i
            }
        }
        return -1
    }

    private fun checkIfMarker(s : String) : Boolean {
        s.forEachIndexed { index, it ->
            for((i, value) in s.withIndex())
                if(i > index && it == value)
                    return false
        }
        return true
    }
    //endregion
}