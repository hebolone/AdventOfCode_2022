package Days

class Day02 : DayBase(), ISolver {
    override fun basic() : Any = _points

    override fun advanced() : Any = _pointsAdvanced

    private data class Move(val move : String, val pointsForMyMove : Int, val pointsForPlay : Int, val pointsForMyMoveAdvanced : Int, val pointsForPlayAdvanced : Int, val routing : String)
    private val _moves = listOf(
        Move("A X", 1, 3, 3, 0, "Z"),
        Move("A Y", 2, 6, 1, 3, "X"),
        Move("A Z", 3, 0, 2, 6, "Y"),
        Move("B X", 1, 0, 1, 0, "X"),
        Move("B Y", 2, 3, 2, 3, "Y"),
        Move("B Z", 3, 6, 3, 6, "Z"),
        Move("C X", 1, 6, 2, 0, "Y"),
        Move("C Y", 2, 0, 3, 3, "Z"),
        Move("C Z", 3, 3, 1, 6, "X")
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