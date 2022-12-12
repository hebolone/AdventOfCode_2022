package days

class Day11 : DayBase(), ISolver {
    override fun basic(): Any {
        _monkeys.init()
        execute(20, ::getBoredLevelBasic)
        val result = _monkeys.sortedByDescending { it.itemsInspected }.take(2).map { it.itemsInspected }
        return result[0] * result[1]
    }

    override fun advanced(): Any {
        _monkeys.clear()
        _monkeys.init()
        execute(10000, ::getBoredLevelAdvanced)
        val result = _monkeys.sortedByDescending { it.itemsInspected }.take(2).map { it.itemsInspected }
        return result[0].toULong() * result[1].toULong()
    }
    override fun parse() { }

    //region Members
    private data class Monkey(val index : Int, val items : MutableList<ULong>, val operation : (ULong, ULong) -> ULong, val operationValue : ULong, val divisor : ULong, val ifTrue : Int, val ifFalse : Int, var itemsInspected : Int = 0)
    private val _monkeys = mutableListOf<Monkey>()
    //endregion

    //region Methods
    //region Delegates for monkeys
    private fun square(old : ULong, value : ULong) : ULong = old * old
    private fun multiplier(old : ULong, value : ULong) : ULong = old * value
    private fun sum(old : ULong, value : ULong) : ULong = old + value
    private fun genericTest(input : ULong, value : ULong, ifTrue : Int, ifFalse : Int) : Int = if(input % value == 0.toULong()) ifTrue else ifFalse
    //endregion

    private fun MutableList<Monkey>.init() {
        if(isTest) {
            _monkeys.add(Monkey(0, mutableListOf(79u, 98u),                                 ::multiplier, 19U, 23U, 2, 3))
            _monkeys.add(Monkey(1, mutableListOf(54u, 65u, 75u, 74u),                       ::sum, 6U, 19U, 2, 0))
            _monkeys.add(Monkey(2, mutableListOf(79u, 60u, 97u),                            ::square, 0U, 13U, 1, 3))
            _monkeys.add(Monkey(3, mutableListOf(74u),                                      ::sum, 3U, 17U, 0, 1))
        } else {
            _monkeys.add(Monkey(0, mutableListOf(57u, 58u),                                 ::multiplier, 19U, 7U, 2, 3))
            _monkeys.add(Monkey(1, mutableListOf(66u, 52u, 59u, 79u, 94u, 73u),             ::sum, 1U, 19U, 4, 6))
            _monkeys.add(Monkey(2, mutableListOf(80u),                                      ::sum, 6U, 5U, 7, 5))
            _monkeys.add(Monkey(3, mutableListOf(82u, 81u, 68u, 66u, 71u, 83u, 75u, 97u),   ::sum, 5U, 11U, 5, 2))
            _monkeys.add(Monkey(4, mutableListOf(55u, 52u, 67u, 70u, 69u, 94u, 90u),        ::square, 0U,  17U, 0, 3))
            _monkeys.add(Monkey(5, mutableListOf(69u, 85u, 89u, 91u),                       ::sum, 7U, 13U, 1, 7))
            _monkeys.add(Monkey(6, mutableListOf(75u, 53u, 73u, 52u, 75u),                  ::multiplier, 7U, 2U, 0, 4))
            _monkeys.add(Monkey(7, mutableListOf(94u, 60u, 79u),                            ::sum, 2U, 3U, 1, 6))
        }
    }

    private fun getBoredLevelBasic(worryLevel : ULong) : ULong = worryLevel / 3U

    private fun getBoredLevelAdvanced(worryLevel : ULong) : ULong = worryLevel % 9699690U

    private fun execute(times : Int, handlerBoredLevel : (ULong) -> ULong) {
//        val checkPoints = listOf(1, 20/*, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000*/)

        repeat(times) { t ->
            _monkeys.forEach {
                it.items.forEach { item ->
                    //  Calculate worry level for current item
                    val worryLevel = handlerBoredLevel(it.operation(item, it.operationValue))
                    val monkeyRouting = genericTest(worryLevel, it.divisor, it.ifTrue, it.ifFalse)
                    //  Increment working items counter
                    it.itemsInspected ++
                    _monkeys[monkeyRouting].items.add(worryLevel)
                }
                it.items.clear()
            }
//            if(checkPoints.contains(t + 1)) {
//                println("== After Round ${t + 1} ==")
//                _monkeys.forEach {
//                    println("Monkey ${it.index} : items: ${it.itemsInspected}")
//                }
//            }
        }
    }
    //endregion
}