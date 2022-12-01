import Days.*

fun main() {
    DaysBuilder("/home/simone/Scrivania/AdventOfCode/2022")             //    Set input path
        .addDay(1, Day01())
        //.setTest(1)
        .solve(1)                //    Default is 'both'
}