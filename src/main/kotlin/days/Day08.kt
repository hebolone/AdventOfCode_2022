package days

import tools.BoardExtended
import tools.Coordinate

class Day08 : DayBase(), ISolver {
    override fun basic(): Any = countVisibleTrees().size

    override fun advanced(): Any {
        var betterView = 0
        (0 until _x * _y).forEach {
            val coordinate = _board?.getCoordinateFromIndex(it) !!
            val sightLines = _board?.getSightLines(coordinate)!!
            val minimumTreeHeight = _board?.get(coordinate)!!
            val visibleTrees = countVisibleTrees(sightLines, minimumTreeHeight)
            if(visibleTrees > betterView)
                betterView = visibleTrees
        }
        return betterView
    }

    override fun parse() {
        val height = _input.count()
        val width = _input.first().length
        _board = BoardExtended(width, height) { 0 }
        var lineCounter = 0
        _input.forEach { y -> run {
                y.forEachIndexed { index, value -> run { _board!![index, lineCounter] = value.digitToInt() } }
                lineCounter ++
            }
        }
        _x = _board?.x!!
        _y = _board?.y!!
    }

    //region Members
    private var _board : BoardExtended<Int>? = null
    private var _x : Int = 0
    private var _y : Int = 0
    private enum class TYPEOFWATCHING { ROW, COLUMN }
    private enum class SIGHTDIRECTION { WEST, NORTH, EAST, SOUTH }
    //endregion

    //region Methods
    private fun getWatchingPositions() : Map<Coordinate, TYPEOFWATCHING> {
        val retValue = mutableMapOf<Coordinate, TYPEOFWATCHING>()
        (0 until _x).forEach {
            retValue[Coordinate(it, -1)] = TYPEOFWATCHING.COLUMN
            retValue[Coordinate(it, _y)] = TYPEOFWATCHING.COLUMN
        }
        (0 until _y).forEach {
            retValue[Coordinate(-1, it)] = TYPEOFWATCHING.ROW
            retValue[Coordinate(_x, it)] = TYPEOFWATCHING.ROW
        }
        return retValue
    }

    private fun countVisibleTrees() : List<Coordinate> {
        val treesSeen = mutableListOf<Coordinate>()
        val positions = getWatchingPositions()
        positions.forEach { position ->
            when(position.value) {
                TYPEOFWATCHING.ROW -> {
                    val rowTrees = _board?.getRow(position.key)!!
                    val visibleTrees = countVisibleTreesSingle(rowTrees)
                    //  Mark trees as already watched
                    visibleTrees.forEach {
                        if(!treesSeen.contains(it))
                            treesSeen.add(it)
                    }
                }
                TYPEOFWATCHING.COLUMN -> {
                    val columnTrees = _board?.getColumn(position.key)!!
                    val visibleTrees = countVisibleTreesSingle(columnTrees)
                    //  Mark trees as already watched
                    visibleTrees.forEach {
                        if(!treesSeen.contains(it))
                            treesSeen.add(it)
                    }
                }
            }
        }
        return treesSeen
    }

    private fun BoardExtended<Int>.getRow(watchingPosition : Coordinate) : List<Coordinate> {
        val retValue = mutableListOf<Coordinate>()
        (0 until _x).forEach {
            retValue.add(Coordinate(it, watchingPosition.y))
        }
        return if(watchingPosition.x == _x) retValue.reversed() else retValue
    }

    private fun BoardExtended<Int>.getColumn(watchingPosition : Coordinate) : List<Coordinate> {
        val retValue = mutableListOf<Coordinate>()
        (0 until _y).forEach {
            retValue.add(Coordinate(watchingPosition.x, it))
        }
        return if(watchingPosition.y == _y) retValue.reversed() else retValue
    }

    private fun countVisibleTreesSingle(line : List<Coordinate>) : List<Coordinate> {
        val retValue = mutableListOf<Coordinate>()
        var currentHeight = -1
        (line.indices).forEach { it ->
            var treeHeight = _board?.get(line[it]) ?: -1
            if(treeHeight > currentHeight) {
                retValue.add(line[it])
                currentHeight = treeHeight
            }
        }
        return retValue
    }

    private fun countVisibleTreesSingle(line : List<Coordinate>, currentTreeHeight : Int) : List<Coordinate> {
        val retValue = mutableListOf<Coordinate>()
        (line.indices).forEach { it ->
            var treeHeight = _board?.get(line[it]) ?: -1
            when {
                currentTreeHeight <= treeHeight -> {
                    retValue.add(line[it])
                    return retValue
                }
                currentTreeHeight > treeHeight -> retValue.add(line[it])
            }
        }
        return retValue
    }

    private fun BoardExtended<Int>.getSightLines(tree : Coordinate) : Map<SIGHTDIRECTION, List<Coordinate>> {
        //  Starting from a single tree inside the wood
        val retValue = mutableMapOf<SIGHTDIRECTION, List<Coordinate>>()
        listOf<SIGHTDIRECTION>(SIGHTDIRECTION.WEST, SIGHTDIRECTION.NORTH, SIGHTDIRECTION.EAST, SIGHTDIRECTION.SOUTH).forEach {
            when(it) {
                SIGHTDIRECTION.WEST -> {
                    val trees = mutableListOf<Coordinate>()
                    for (i in (0 until tree.x)) {
                        _board?.get(i, tree.y)?.let {
                            trees.add(Coordinate(i, tree.y))
                        }
                    }
                    retValue[it] = trees.reversed()
                }
                SIGHTDIRECTION.NORTH -> {
                    val trees = mutableListOf<Coordinate>()
                    for (i in (0 until tree.y)) {
                        _board?.get(tree.x, i)?.let {
                            trees.add(Coordinate(tree.x, i))
                        }
                    }
                    retValue[it] = trees.reversed()
                }
                SIGHTDIRECTION.EAST -> {
                    val trees = mutableListOf<Coordinate>()
                    for (i in ((tree.x + 1) until _x)) {
                        _board?.get(i, tree.y)?.let {
                            trees.add(Coordinate(i, tree.y))
                        }
                    }
                    retValue[it] = trees
                }
                SIGHTDIRECTION.SOUTH -> {
                    val trees = mutableListOf<Coordinate>()
                    for (i in ((tree.y + 1) until _y)) {
                        _board?.get(tree.x, i)?.let {
                            trees.add(Coordinate(tree.x, i))
                        }
                    }
                    retValue[it] = trees
                }
            }
        }
        return retValue
    }

    private fun countVisibleTrees(views : Map<SIGHTDIRECTION, List<Coordinate>>, treeHeight : Int) : Int {
        var retValue = mutableListOf<Int>()
        views.values.forEach {
            retValue.add(countVisibleTreesSingle(it, treeHeight).size)
        }
        //  Calculate sight value
        var index = 1
        retValue.forEach { index *= it }
        return index
    }
    //endregion
}