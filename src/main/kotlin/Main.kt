import days.*

fun main() {
    val currentDay = 4
    DaysBuilder("/home/simone/Scrivania/AdventOfCode/2022")             //    Set input path
        //.addDay(1, Day01())
        //.addDay(2, Day02())
        //.addDay(3, Day03())
        .addDay(4, Day04())
        //.setTest(currentDay)
        .solve(currentDay)                //    Default is 'both'
}