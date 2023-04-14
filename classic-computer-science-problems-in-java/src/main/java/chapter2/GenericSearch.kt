package chapter2

import java.util.*
import java.util.function.Function
import java.util.function.Predicate

class GenericSearch {

    class Node<T>(
            val state: T,
            val parent: Node<T>? = null,
            val cost: Double = 0.0,
            val heuristic: Double = 0.0,
    ) : Comparable<Node<T>> {

        override fun compareTo(other: Node<T>): Int {
            val mine = cost + heuristic
            val theirs = other.cost + other.heuristic
            return mine.compareTo(theirs)
        }

    }

    companion object {
        fun <T : Comparable<T>> linearContains(list: List<T>, key: T): Boolean {
            return list.any { item -> item.compareTo(key) == 0 }
        }

        fun <T : Comparable<T>> binaryContains(list: List<T>, key: T): Boolean {
            var low = 0
            var high = list.size - 1
            while (low <= high) {
                val middle = (low + high) / 2
                val comparison = list[middle].compareTo(key)
                if (comparison < 0) {
                    low = middle + 1
                } else if (comparison > 0) {
                    high = middle - 1
                } else {
                    return true
                }
            }
            return false
        }

        // Depth First Search
        fun <T> dfs(initial: T, goalTest: Predicate<T>, successors: Function<T, List<T>>): Node<T>? {
            val frontier = Stack<Node<T>>()
            frontier.push(Node(initial, null))
            val explored = HashSet<T>()
            explored.add(initial)

            while (frontier.isNotEmpty()) {
                val currentNode = frontier.pop()
                val currentState = currentNode.state

                if (goalTest.test(currentState)) {
                    return currentNode
                }

                for (child in successors.apply(currentState)) {
                    if (explored.contains(child)) {
                        continue
                    }
                    explored.add(child)
                    frontier.push(Node(child, currentNode))
                }
            }
            return null
        }

        // Breadth First Search
        fun <T> bfs(initial: T, goalTest: Predicate<T>, successors: Function<T, List<T>>): Node<T>? {
            val frontier: Queue<Node<T>> = LinkedList()
            frontier.offer(Node(initial, null))
            val explored = HashSet<T>()
            explored.add(initial)

            while (frontier.isNotEmpty()) {
                val currentNode = frontier.poll()
                val currentState = currentNode.state

                if (goalTest.test(currentState)) {
                    return currentNode
                }

                for (child in successors.apply(currentState)) {
                    if (explored.contains(child)) {
                        continue
                    }
                    explored.add(child)
                    frontier.add(Node(child, currentNode))
                }
            }
            return null
        }

        fun <T> nodeToPath(node: Node<T>): List<T> {
            var currentNode: Node<T>? = node
            val path = ArrayList<T>()
            path.add(currentNode!!.state)
            while (currentNode?.parent != null) {
                currentNode = currentNode.parent
                path.add(0, currentNode!!.state)
            }
            return path
        }

        fun <T> astar(initial: T, goalTest: Predicate<T>, successors: Function<T, List<T>>,
                      heuristic: Function<T, Double>): Node<T>? {
            val frontier = PriorityQueue<Node<T>>()
            frontier.offer(Node(initial, null, 0.0, heuristic.apply(initial)))
            val explored = HashMap<T, Double>()
            explored[initial] = 0.0

            while (frontier.isNotEmpty()) {
                val currentNode = frontier.poll()
                val currentState = currentNode.state

                if (goalTest.test(currentState)) {
                    return currentNode
                }

                for (child in successors.apply(currentState)) {
                    val newCost = currentNode.cost + 1
                    if (!explored.containsKey(child) || explored[child]!! > newCost) {
                        explored[child] = newCost
                        frontier.offer(Node(child, currentNode, newCost, heuristic.apply(child)))
                    }
                }
            }
            return null
        }
    }
}
