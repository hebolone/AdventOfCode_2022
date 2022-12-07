package days

import tools.*
import kotlin.math.abs

class Day07 : DayBase(), ISolver {
    override fun basic(): Any {
        val smallDirectorySize = 100000
        val directories = _fs.getAllDirectories()
        var totalSize = 0
        directories.forEach {
            val dirSize = _fs.getDirectoriesSize(it)
            if(dirSize < smallDirectorySize)
                totalSize += dirSize
        }
        return totalSize
    }

    override fun advanced(): Any {
        val totalSpace = 70000000
        val requiredFreeSpace = 30000000
        val directories = _fs.getAllDirectories()
        val occupiedSpace = _fs.getDirectoriesSize(_fs.getRoot()!!)
        val spaceNeeded = abs(totalSpace - requiredFreeSpace - occupiedSpace)
        val directoriesWithSize = mutableMapOf<Node<Item>, Int>()
        directories.forEach {
            val dirSize = _fs.getDirectoriesSize(it)
            if(dirSize > spaceNeeded)
                directoriesWithSize[it] = dirSize
        }
        return directoriesWithSize.minOf { it.value }
    }

    override fun parse() {
        val iterator = _fs.getIterator()
        _fs.addNode(Item(NODETYPE.DIRECTORY, "/"))

        _input.forEach {
            if(it.startsWith("$")) {
                //  This is a command
                val regex = "\\\$ (cd|ls)+\\s?(\\.\\.|/|\\w*)".toRegex()
                val matchResult = regex.find(it)
                matchResult?.let { mr ->
                    val (command, parameter) = mr.destructured
                    when(command) {
                        "cd" -> {
                            when(parameter) {
                                "/" -> iterator.goRoot()
                                ".." -> iterator.goUp()
                                else -> {
                                    val nodes = _fs.getChildren(iterator.current!!)
                                    iterator.goDown(nodes.first { context -> context.item.name == parameter })
                                }
                            }
                        }
                        "ls" -> {
                            //  Nothing at the moment
                        }
                        else -> throw Exception("Can't understand command: $command")
                    }
                }
            } else {
                //  This is an item
                val regex = "(dir|\\d+) (.*)".toRegex()
                val matchResult = regex.find(it)
                matchResult?.let { mr ->
                    val (first, name) = mr.destructured
                    if(first == "dir") {
                        //  Add a directory
                        _fs.addNode(Item(NODETYPE.DIRECTORY, name), iterator.current)
                    } else {
                        //  Add a file
                        _fs.addNode(Item(NODETYPE.FILE, name, first.toInt()), iterator.current)
                    }
                }
            }
        }

        _fs.listNode(_fs.getRoot()!!)
    }

    //region Members
    private enum class NODETYPE { DIRECTORY, FILE }
    private data class Item(val type : NODETYPE, val name : String, val size : Int = 0) {
        override fun toString() = when(type) {
            NODETYPE.DIRECTORY -> "${this.name} (dir)"
            NODETYPE.FILE -> "${this.name} (file, size=${this.size})"
        }
    }
    private val _fs = Tree<Item>()
    //endregion

    //region Methods
    private fun Tree<Item>.getDirectoriesSize(node: Node<Item>) : Int {
        val children = this.getChildren(node)
        var currentSize = 0
        children.forEach {
            currentSize += when(it.item.type) {
                NODETYPE.DIRECTORY -> this.getDirectoriesSize(it)
                NODETYPE.FILE -> it.item.size
            }
        }
        return currentSize
    }

    private fun Tree<Item>.getAllDirectories(node : Node<Item> = this.getRoot()!!, directories : MutableList<Node<Item>> = mutableListOf()) : List<Node<Item>> {
        val children = this.getChildren(node)
        directories.add(node)
        children.forEach {
            if(it.item.type == NODETYPE.DIRECTORY) {
                getAllDirectories(it, directories)
            }
        }
        return directories
    }
    //endregion
}