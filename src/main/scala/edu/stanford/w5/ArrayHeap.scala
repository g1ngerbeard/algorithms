package edu.stanford.w5

import scala.annotation.tailrec

trait HeapOrder[K] {
  def order(child: K, parent: K)(implicit ordering: Ordering[K]): Boolean
}

trait MinHeapOrder[K] extends HeapOrder[K] {
  def order(childKey: K, parentKey: K)(implicit ordering: Ordering[K]): Boolean = {
    ordering.lt(childKey, parentKey)
  }
}

trait MaxHeapOrder[K] extends HeapOrder[K] {
  def order(childKey: K, parentKey: K)(implicit ordering: Ordering[K]): Boolean = {
    ordering.gt(childKey, parentKey)
  }
}

class MinArrayHeap[K, V]()(implicit ordering: Ordering[K]) extends ArrayHeap[K, V]() with MinHeapOrder[K]

class MaxArrayHeap[K, V](implicit ordering: Ordering[K]) extends ArrayHeap[K, V]() with MaxHeapOrder[K]

abstract class ArrayHeap[K, V]()(implicit ordering: Ordering[K]) extends Heap[K, V] {
  self: HeapOrder[K] =>

  private var length = 2

  private var count = 0

  private var nodes = new Array[(K, V)](length)

  def add(entry: (K, V)) {
    nodes(count) = entry
    bubbleUp(count)
    onNodeAdded()
  }

  private def onNodeAdded(): Unit = {
    count += 1

    if (count >= length) {
      length *= 2
      val newNodes = new Array[(K, V)](length)
      nodes.copyToArray(newNodes)
      nodes = newNodes
    }
  }

  override def peek: Option[(K, V)] = {
    if (isEmpty) {
      None
    } else {
      Some(nodes(0))
    }
  }

  override def pop: Option[(K, V)] = {
    if (!isEmpty) {
      val first = nodes(0)
      val last = nodes(count - 1)

      nodes(0) = last
      nodes(count - 1) = null
      onNodeRemoved()

      bubbleDown(0)

      Some(first)
    } else {
      None
    }
  }

  private def onNodeRemoved(): Unit = {
    count -= 1

    if (count < length / 2) {
      length /= 2
      val newNodes = new Array[(K, V)](length)
      nodes.copyToArray(newNodes)
      nodes = newNodes
    }
  }

  @tailrec
  private def bubbleUp(childIdx: Int) {
    val childKey = keyFor(childIdx)
    val parentIdx = parentOf(childIdx)
    val parentKey = keyFor(parentIdx)

    if (order(childKey, parentKey)) {
      swap(childIdx, parentIdx)
      bubbleUp(parentIdx)
    }
  }

  private def bubbleDown(i: Int) {
    orderedChildren(i)
      .find(orderKeys(_, i))
      .foreach(child => {
        swap(child, i)
        bubbleDown(child)
      })
  }

  private def orderedChildren(i: Int): List[Int] = {
    val left: Int = 2 * i + 1
    val right: Int = 2 * i + 2

    if (left > count - 1) {
      List.empty
    } else if (right > count - 1) {
      List(left)
    } else {
      List(left, right).sortWith(orderKeys)
    }
  }

  override def isEmpty: Boolean = count == 0

  override def size: Int = count

  //  todo: implement
  def delete(key: K): Option[V] = {
    throw new NotImplementedError()
    //    var delIndex: Int = -1
    //    var i: Int = 0
    //    while (i < nodes.length) {
    //      {
    //        if (nodes(i)._1 == key) {
    //          delIndex = i
    //          break 
    //        }
    //      }
    //      {
    //        i += 1
    //        i - 1
    //      }
    //    }
    //
    //    if (delIndex >= 0) {
    //      val node: (K, V) = nodes(delIndex)
    //      swap(delIndex, nodes.length - 1)
    //      bubbleDown(delIndex)
    //      Some(node._2)
    //    } else {
    //      None
    //    }
  }

  private def orderKeys(first: Int, second: Int): Boolean = {
    order(keyFor(first), keyFor(second))
  }

  private def keyFor(index: Int): K = nodes(index)._1

  private def parentOf(i: Int): Int = Math.floor(i / 2).toInt

  private def swap(left: Int, right: Int) {
    val buf = nodes(left)
    nodes(left) = nodes(right)
    nodes(right) = buf
  }

  override def toString: String = "ArrayHeap{" + "nodes=" + nodes + '}'

}
