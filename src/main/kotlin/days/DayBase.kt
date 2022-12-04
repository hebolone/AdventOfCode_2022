package days

interface ISolver {
    fun basic() : Any
    fun advanced() : Any
    var isTest : Boolean
    fun setInput(input : List<String>)
    infix fun withInput(input : List<String>) {
        setInput(input)
    }
}

abstract class DayBase {
    protected var _input = listOf<String>()

    fun setInput(input : List<String>) {
        _input = input
        parse()
    }

    var isTest : Boolean = false

    protected abstract fun parse()
}