package days

class Day05 : DayBase(), ISolver {
    override fun basic(): Any {
        _moves.forEach {
            _stacks.moveCrates(it, TYPEOFMOVER.CRATEMOVER9000)
        }
        return _stacks.getLastCrates()
    }

    override fun advanced(): Any {
        parse()
        _moves.forEach {
            _stacks.moveCrates(it, TYPEOFMOVER.CRATEMOVER9001)
        }
        return _stacks.getLastCrates()
    }

    override fun parse() {
        _moves.clear()

        when(isTest) {
            true -> {
                _stacks[1] = "ZN"
                _stacks[2] = "MCD"
                _stacks[3] = "P"
            }
            false -> {
                _stacks[1] = "TPZCSLQN"
                _stacks[2] = "LPTVHCG"
                _stacks[3] = "DCZF"
                _stacks[4] = "GWTDLMVC"
                _stacks[5] = "PWC"
                _stacks[6] = "PFJDCTSZ"
                _stacks[7] = "VWGBD"
                _stacks[8] = "NJSQHW"
                _stacks[9] = "RCQFSLV"
            }
        }

        _input.forEach {
            val regex = "^move (\\d+) from (\\d+) to (\\d+)\$".toRegex()
            val matchResult = regex.find(it)
            matchResult?.let {
                val (noOfCrates, from, to) = matchResult.destructured
                _moves.add(Move(noOfCrates.toInt(), from.toInt(), to.toInt()))
            }
        }
    }

    //region Members
    private val _stacks = mutableMapOf<Int, String>()
    private val _moves = mutableListOf<Move>()
    private data class Move(val noOfCrates : Int, val from : Int, val to : Int)
    private enum class TYPEOFMOVER { CRATEMOVER9000, CRATEMOVER9001}
    //endregion

    //region Methods
    private fun MutableMap<Int, String>.moveCrates(move : Move, typeOfMove : TYPEOFMOVER) {
        val noOfCratesFrom = this[move.from]!!.length
        val cratesToBeMoved = when(typeOfMove) {
            TYPEOFMOVER.CRATEMOVER9000 -> this[move.from]!!.reversed().take(move.noOfCrates)
            TYPEOFMOVER.CRATEMOVER9001 -> this[move.from]!!.drop(noOfCratesFrom - move.noOfCrates)
        }
        this[move.from] = this[move.from]!!.take(noOfCratesFrom - move.noOfCrates)
        this[move.to] += cratesToBeMoved
    }

    private fun MutableMap<Int, String>.getLastCrates() : String = this.values.joinToString(separator = "") { it.last().toString() }
    //endregion
}