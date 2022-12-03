package Days

class Day03 : DayBase(), ISolver {
    override fun basic(): Any = _basicResponse

    override fun advanced(): Any {
        parseAdvance()
        return _advancedResponse
    }

    private var _basicResponse = 0
    private var _advancedResponse = 0

    override fun parse() {
        _input.forEach {
            val compartmentSize = it.length / 2
            val firstCompartment = it.take(compartmentSize)
            val secondCompartment = it.drop(compartmentSize)
            val item = findItems(firstCompartment, secondCompartment)
            _basicResponse += convertItemToDigit(item.first())
        }
    }

    private fun parseAdvance() {
        val groupItems = mutableListOf<String>()
        _input.forEach {
            groupItems.add(it)
            if(groupItems.size == 3) {
                val badge = findItemsInGroups(groupItems)
                _advancedResponse += convertItemToDigit(badge)
                groupItems.clear()
            }
        }
    }

    private fun findItems(firstCompartment : String, secondCompartment : String) : MutableList<Char> {
        val retValue = mutableListOf<Char>()
        firstCompartment.forEach {
            if(secondCompartment.contains(it))
                retValue.add(it)
        }
        return retValue
    }

    private fun convertItemToDigit(item : Char) : Int {
        return if(item.isLowerCase()) item.code - 96 else item.code - 38
    }

    private fun findItemsInGroups(group : List<String>) : Char {
        var retValue = '_'
        val charsInFirstGroups = findItems(group[0], group[1])
        charsInFirstGroups.forEach {
            //  Match it with the last group
            if(group[2].contains(it))
                retValue = it
        }
        return retValue
    }
}