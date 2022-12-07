package tools

open class Tree<T> {
    private val _datas = mutableListOf<Node<T>>()

    open fun addNode(item: T, ancestor: Node<T>? = null): Node<T> {
        val node = Node(item, ancestor)
        _datas.add(node)
        return node
    }

    fun listNode(node: Node<T>) {
        val spaceIndent = " ".repeat(node.getLevel() * 2)
        println("$spaceIndent- ${node.item}")
        _datas.filter { it.ancestor == node }.forEach {
            if (_datas.any { d -> d.ancestor == it })
                listNode(it)
            else
                println("  $spaceIndent- ${it.item}")
        }
    }

    fun getChildren(node: Node<T>): List<Node<T>> = _datas.filter { it.ancestor == node }

    fun getRoot(): Node<T>? = _datas.firstOrNull { it.ancestor == null }

    fun getIterator(): TreeIterator<T> = TreeIterator(this)

    private fun Node<T>.getLevel(): Int {
        var retValue = 0
        var pointer: Node<T>? = this
        while (pointer != null) {
            retValue++
            pointer = pointer.ancestor
        }
        return retValue
    }
}

class TreeIterator<T>(private val tree : Tree<T>) {
    private var _current : Node<T>? = null

    val current get() = _current

    fun goUp() {
        _current = _current?.ancestor
    }

    fun goDown(node : Node<T>) {
        _current = tree.getChildren(_current!!).firstOrNull { it == node }
    }

    fun goRoot() {
        _current = tree.getRoot()
    }
}

data class Node<T>(val item : T, val ancestor : Node<T>? = null)