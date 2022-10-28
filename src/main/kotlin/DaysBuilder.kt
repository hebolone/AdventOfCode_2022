import java.io.File
import Days.*

interface IDaysBuilder {
    fun addDay(day : Int, solver : ISolver) : IDaysBuilder
    fun solve(day : Int, questionType : TQUESTIONTYPE = TQUESTIONTYPE.BOTH)
    fun setTest(vararg days : Int) : IDaysBuilder
}

enum class TQUESTIONTYPE {
    BASIC, ADVANCED, BOTH
}

class DaysBuilder(inputPath : String) : IDaysBuilder {
    private data class SetOfResult(val day : Int, val typeOfQuestion : TQUESTIONTYPE, val result : Any, val isTest : Boolean) {
        override fun toString() = "Day: $day (${typeOfQuestion}${if(isTest) " TEST" else ""}) = $result"
    }

    private val _Days = mutableMapOf<Int, ISolver>()
    private val _FilePattern = "$inputPath/day_%day%.txt"
    private val _FilePatternTest = "$inputPath/day_%day%_test.txt"

    override fun addDay(day : Int, solver : ISolver) : IDaysBuilder {
        _Days[day] = solver
        return this
    }

    override fun setTest(vararg days : Int) : IDaysBuilder {
        for(day in days) {
            val solver = if(_Days.containsKey(day)) _Days[day] else null
            solver?.isTest = true
        }
        return this
    }

    override fun solve(day : Int, questionType : TQUESTIONTYPE) {
        //    Select correct solver
        val solver = if(_Days.containsKey(day)) _Days[day] else null
        solver?.let  {
            //    Open correct input data file
            val input = getInput(day, solver.isTest)
            solver withInput input

            val results = mutableListOf<SetOfResult>()
            when(questionType) {
                TQUESTIONTYPE.BASIC -> results.add(SetOfResult(day, TQUESTIONTYPE.BASIC, solver.basic(), solver.isTest))
                TQUESTIONTYPE.ADVANCED -> results.add(SetOfResult(day, TQUESTIONTYPE.ADVANCED, solver.advanced(), solver.isTest))
                else -> {
                    results.add(SetOfResult(day, TQUESTIONTYPE.BASIC, solver.basic(), solver.isTest))
                    results.add(SetOfResult(day, TQUESTIONTYPE.ADVANCED, solver.advanced(), solver.isTest))
                }
            }
            results.forEach { println(it) }
        } ?: println("Incorrect day: $day is not present")
    }

    private fun Int.totalDigits(digits : Int = 2) : String = "${"0".repeat(kotlin.math.max(digits - toString().length, 0))}$this"

    private fun getInput(day : Int, isTest : Boolean = false) : List<String>  {
        val retValue = mutableListOf<String>()

        val filePattern = if(isTest) _FilePatternTest else _FilePattern
        val fileName = filePattern.replace("%day%", day.totalDigits())

        val inputFile = File(fileName)
        if(inputFile.exists())
            inputFile.forEachLine { retValue.add(it) }
        else
            throw Exception("Input file $fileName for day $day not found")
        return retValue
    }
}
