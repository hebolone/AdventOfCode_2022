import Days.*

fun main() {
    DaysBuilder("/home/simone/Scrivania/AdventOfCode/2022")             //    Set input path
        //.addDay(1, Day01())
        .addDay(2, Day02())
        //.setTest(2)
        .solve(2)                //    Default is 'both'
}