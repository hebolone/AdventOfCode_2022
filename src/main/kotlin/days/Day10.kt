package days

class Day10 : DayBase(), ISolver {
    override fun basic(): Any = execute()

    override fun advanced(): Any = videoTiming()

    override fun parse() {
        val commands = mutableListOf<Command>()

        _input.forEach {
            val regex = "^(\\w+)\\s?(-?\\d+)?\$".toRegex()
            val matchResult = regex.find(it)
            matchResult?.let { mr ->
                run {
                    val (instruction, value) = mr.destructured
                    val valueConverted = if(value == "") 0 else value.toInt()
                    commands.add(Command(TCOMMAND.valueOf(instruction.uppercase()), valueConverted))
                }
            }
        }

        var cycles = 1
        var registry = 1
        _states[cycles] = registry
        commands.forEach {
            when(it.instruction) {
                TCOMMAND.NOOP -> cycles ++
                TCOMMAND.ADDX -> {
                    cycles += 2
                    registry += it.value
                }
            }
            _states[cycles] = registry
        }
    }

    //region Members
    private enum class TCOMMAND { NOOP, ADDX }
    private data class Command(val instruction : TCOMMAND, val value : Int)
    private val _checkPoints = listOf(20, 60, 100, 140, 180, 220)
    private val _states = mutableMapOf<Int, Int>()
    //endregion

    //region Methods
    private fun execute() : Int {
        var retValue = 0
        _checkPoints.forEach {
            retValue += it * getStatus(_states, it)
        }
        return retValue
    }

    private fun videoTiming() : String {
        val videoMemory = mutableMapOf<Int, Int>()
        var spritePosition: Int
        var retValue = ""

        val scanLineLength = 40
        var currentLine = 0
        repeat(6) {
            (1..40).forEach {
                videoMemory[it - 1] = 0     //  Add pixel
                spritePosition = getStatus(_states, (scanLineLength * currentLine) + it)
                drawSprite(spritePosition, videoMemory)
            }
            retValue += videoMemory.print() + "\r\n"
            currentLine ++
            videoMemory.clear()
        }

        return retValue
    }

    private fun Map<Int, Int>.print() : String {
        var video = ""
        (0 until this.size).forEach {
            video += if(this[it] == 0) "." else "#"
        }
        return video
    }

    private fun drawSprite(position : Int, videoMemory : MutableMap<Int, Int>) {
        val lastPixel = videoMemory.keys.last()
        listOf(position -1, position, position + 1).forEach {
            if(it == lastPixel) videoMemory[lastPixel] = 1
        }
    }

    private fun getStatus(states : Map<Int, Int>, value : Int) : Int = if(states.containsKey(value)) {
            states[value]!!
        } else {
            val previousState = states.keys.last { s -> s < value }
            states[previousState]!!
        }
    //endregion
}