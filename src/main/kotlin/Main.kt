import days.*

fun main() {
    DaysBuilder("/home/simone/Scrivania/AdventOfCode/2022")             //    Set input path
        //.addDay(1, Day01())
        //.addDay(2, Day02())
        //.addDay(3, Day03())
        //.addDay(4, Day04())
        //.addDay(5, Day05())
        .addDay(6, Day06())
        //.setTest(6)
        .solve()                //    Default is 'both'
}