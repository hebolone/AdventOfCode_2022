package days

import java.util.*
import tools.Coordinate

class Day09 : DayBase(), ISolver {
    override fun basic(): Any {
        _recorder.reset()
        buildSnake(2)
        _commands.forEach { go(it) }
        return _recorder.recordsCount
    }

    override fun advanced(): Any {
        _recorder.reset()
        buildSnake(10)
        _commands.forEach { go(it) }
        return _recorder.recordsCount
    }

    override fun parse() {
        _input.forEach {
            val regex = "^(\\w) (\\d+)".toRegex()
            val matchResult = regex.find(it)
            matchResult?.let { mr ->
                run {
                    val (direction, unit) = mr.destructured
                    _commands.add(Command(direction, unit.toInt()))
                }
            }
        }
    }

    //region Members
    private data class Command(val direction : String, val unit : Int)
    private class Recorder() {
        private var index = 0
        private val records = mutableMapOf<Int, Coordinate>()
        fun add(coordinate : Coordinate) {
            if(!records.containsValue(coordinate))
                records[++ index] = coordinate.copy()
        }
        fun reset() = records.clear()
        val recordsCount
            get() = records.size
    }
    private val _commands = mutableListOf<Command>()
    private val _recorder = Recorder()
    private val _snake : MutableMap<Int, Coordinate> = mutableMapOf()
    //endregion

    //region Methods
    private fun buildSnake(length : Int) {
        (0 until length).forEach { _snake[it] = Coordinate(0, 0) }
    }

    private fun go(command : Command) {
        repeat(command.unit) { _ ->
            //  Move Head
            when (command.direction.lowercase(Locale.getDefault())) {
                "l" -> _snake[0]!! += Coordinate(-1, 0)
                "u" -> _snake[0]!! += Coordinate(0, 1)
                "r" -> _snake[0]!! += Coordinate(1, 0)
                "d" -> _snake[0]!! += Coordinate(0, -1)
            }
            //  Move Tails
            (1 until _snake.size).forEach {
                moveTail(it - 1, it)
            }
            _recorder.add(_snake[_snake.size - 1]!!)
        }
    }

    private operator fun Coordinate.plusAssign(movement: Coordinate) {
        this.x += movement.x
        this.y += movement.y
    }

    private fun moveTail(tailBefore : Int, tailCurrent : Int) {
        //  Calculate who this tail is following
        val previous = _snake[tailBefore]!!
        val current = _snake[tailCurrent]!!
        val distanceFromPrevious = calculateDistance(previous, current)
        //  Take decision about tail
        val tailMovements = mapOf(
            //  Straight lines
            Coordinate(0, 2) to Coordinate(0, -1),
            Coordinate(2, 0) to Coordinate(-1, 0),
            Coordinate(0, -2) to Coordinate(0, 1),
            Coordinate(-2, 0) to Coordinate(1, 0),
            //  Horse movement
            Coordinate(-1, 2) to Coordinate(1, -1),
            Coordinate(1, 2) to Coordinate(-1, -1),
            Coordinate(2, 1) to Coordinate(-1, -1),
            Coordinate(2, -1) to Coordinate(-1, 1),
            Coordinate(1, -2) to Coordinate(-1, 1),
            Coordinate(-1, -2) to Coordinate(1, 1),
            Coordinate(-2, -1) to Coordinate(1, 1),
            Coordinate(-2, 1) to Coordinate(1, -1),
            //  Diagonal
            Coordinate(-2, -2) to Coordinate(1, 1),
            Coordinate(-2, 2) to Coordinate(1, -1),
            Coordinate(2, 2) to Coordinate(-1, -1),
            Coordinate(2, -2) to Coordinate(-1, 1)
            )
        if(tailMovements.containsKey(distanceFromPrevious)) {
            val movementToApply = tailMovements[tailMovements.keys.first { it == distanceFromPrevious }]
            current += movementToApply ?: Coordinate(0, 0)
        }
    }

    private fun calculateDistance(first : Coordinate, second : Coordinate) : Coordinate = Coordinate(-(first.x - second.x), -(first.y - second.y))

    private fun printSnake() = (0 until _snake.size).forEach { println("$it [${_snake[it]!!.x}, ${_snake[it]!!.y}]") }
    //endregion
}