package Days

class Day02 : DayBase(), ISolver {
    override fun basic() : Any = _points

    override fun advanced() : Any = _pointsAdvanced

    private data class Move(val move : String, val pointsForMyMove : Int, val pointsForPlay : Int, val routing : String)
    private val _moves = listOf(
        Move("A X", 1, 3, "Z"),
        Move("A Y", 2, 6, "X"),
        Move("A Z", 3, 0, "Y"),
        Move("B X", 1, 0, "X"),
        Move("B Y", 2, 3, "Y"),
        Move("B Z", 3, 6, "Z"),
        Move("C X", 1, 6, "Y"),
        Move("C Y", 2, 0, "Z"),
        Move("C Z", 3, 3, "X")
    )
    private var _points = 0
    private var _pointsAdvanced = 0

    override fun parse() {
        _input.forEach {
            val move = _moves.first { m -> m.move == it }
            _points += move.pointsForMyMove + move.pointsForPlay

            val convertedMove = "${it.take(1)} ${move.routing}"
            val advancedMove = _moves.first { m -> m.move == convertedMove }
            _pointsAdvanced += advancedMove.pointsForMyMove + advancedMove.pointsForPlay
        }
    }
}