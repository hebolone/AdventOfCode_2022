import days.*

fun main() {
    DaysBuilder("/home/simone/Scrivania/AdventOfCode/2022")             //    Set input path
        //.addDay(1, Day01())
        //.addDay(2, Day02())
        //.addDay(3, Day03())
        //.addDay(4, Day04())
        //.addDay(5, Day05())
        //.addDay(6, Day06())
        //.addDay(7, Day07())
        //.addDay(8, Day08())
        //.addDay(9, Day09())
        //.addDay(10, Day10())
        .addDay(11, Day11())
        //.setTest(11)
        .solve()                //    Default is 'both'
}