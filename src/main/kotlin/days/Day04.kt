package days

class Day04 : DayBase(), ISolver {
    override fun basic(): Any = basicResponse

    override fun advanced(): Any = advancedResponse

    override fun parse() {
        _input.forEach {
            val regex = "^(?<e1>\\d{1,})-(?<e2>\\d{1,}),(?<e3>\\d{1,})-(?<e4>\\d{1,})\$".toRegex()
            val matchResult = regex.find(it)!!
            val (e1, e2, e3, e4) = matchResult.destructured
            val elfGroup = ElfGroup(Elf(e1.toInt(), e2.toInt()), Elf(e3.toInt(), e4.toInt()))
            if(elfGroup.isContained())
                basicResponse ++
            if(elfGroup.isOverlapped())
                advancedResponse ++
        }
    }

    //region Members
    private data class Elf(val start : Int, val end : Int)
    private data class ElfGroup(val elf1 : Elf, val elf2 : Elf)
    private var basicResponse = 0
    private var advancedResponse = 0
    //endregion

    //region Methods
    private fun ElfGroup.isContained() : Boolean = (elf1.start >= elf2.start && elf1.end <= elf2.end) || (elf2.start >= elf1.start && elf2.end <= elf1.end)

    private fun ElfGroup.isOverlapped() : Boolean = (elf1.end >= elf2.start && elf1.start <= elf2.end)

    //endregion
}