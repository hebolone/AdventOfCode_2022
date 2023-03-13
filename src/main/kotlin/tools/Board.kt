package tools

open class Board<T>(val x : Int, val y : Int, initializer : () -> T) {
    protected val _cells : MutableList<T> = mutableListOf()
    private val _initializer = initializer
    init {
        (1..(x * y)).forEach { _ -> _cells.add(initializer()) }
    }
    fun getLinear(value : Int) : T = _cells[value]
    fun getSize() : Int = x * y
    fun printBoard(printFun : (t : T) -> String = { it.toString() }) : String {
        val sb = StringBuilder()
        (0 until y).forEach { _y -> run {
            (0 until x).forEach { _x ->
                sb.append(printFun(this[_x, _y]))
            }
            sb.appendLine()
        } }
        return sb.toString()
    }
    //  Indexer
    open operator fun get(xFrom : Int, yFrom : Int) : T = _cells[xFrom + yFrom * x]
    open operator fun get(value : Int) : T = _cells[value]
    open operator fun set(xFrom : Int, yFrom : Int, value : T) { _cells[xFrom + yFrom * x] = value }
    open operator fun set(index : Int, value : T) { _cells[index] = value }
    fun getCoordinatesFromIndex(index : Int) : Pair<Int, Int> {
        val y_derived = index / y
        val x_derived = index - (y * y_derived)
        return Pair(x_derived, y_derived)
    }
    fun slice(newX : Int, newY : Int) : Board<T> = slice(0, newX, 0, newY)
    fun slice(xStart : Int, yStart : Int, xEnd : Int, yEnd : Int) : Board<T> {
        val retValue = Board(xEnd - xStart, yEnd - yStart, _initializer)
        (xStart until xEnd).forEach { oldX ->
            run {
                (yStart until yEnd).forEach { oldY ->
                    run {
                        retValue[oldX, oldY] = this[oldX, oldY]
                    }
                }
            }
        }
        return retValue
    }
}

class BoardExtended<T>(xExt : Int, yExt : Int, initializerExt : () -> T) : Board<T>(xExt, yExt, initializerExt) {
    fun getOrNull(xFrom : Int, yFrom : Int) : T? {
        val index = xFrom + yFrom * x
        return if(xFrom < 0 || xFrom >= x || yFrom < 0 || yFrom >= y)
            null
        else
            _cells[index]
    }
    fun getOrNull(coordinate: Coordinate) : T? = getOrNull(coordinate.x, coordinate.y)
    fun convertIndexToCoordinate(index : Int) : Coordinate {
        val y_derived = index / y
        val x_derived = index - (x * y_derived)
        return Coordinate(x_derived, y_derived)
    }
    fun convertCoordinateToIndex(coordinate : Coordinate) : Int = coordinate.x + coordinate.y * x
    operator fun get(coordinate : Coordinate) : T = this[coordinate.x, coordinate.y]
}

data class Coordinate(var x : Int, var y : Int) {
    override fun equals(other: Any?): Boolean {
        if (other == null ||
            other !is Coordinate ||
            x != other.x || y != other.y) return false
        return true
    }
}
