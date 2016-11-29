package edu.stanford.w6

import edu.stanford.w5.{MaxArrayHeap, MinArrayHeap}
import org.apache.commons.lang3.StringUtils.EMPTY

object MedianAware {

  def median[K](elems: K*)(implicit ordering: Ordering[K]): K = {
    if (elems.isEmpty) {
      throw new IllegalArgumentException("No elements provided for median computation")
    }

    val m = new MedianAware[K]()
    elems.foreach(m.add)
    m.median.get
  }

}

//todo: use heapset
class MedianAware[K](implicit ordering: Ordering[K]) {

  val maxHeap = new MaxArrayHeap[K, String]()

  val minHeap = new MinArrayHeap[K, String]()

  def add(value: K): Unit = {
    maxHeap.peekKey match {
      case Some(key) if ordering.lt(key, value) => minHeap.add(value, EMPTY)
      case _ => maxHeap.add(value, EMPTY)
    }

    reBalance()
  }

  private def reBalance(): Unit = {
    val maxSize = maxHeap.size
    val minSize = minHeap.size

    if (minSize - maxSize > 1) {
      minHeap.popKey.foreach(maxHeap.add(_, EMPTY))
    } else if (maxSize - minSize > 1) {
      maxHeap.popKey.foreach(minHeap.add(_, EMPTY))
    }
  }

  def median: Option[K] = maxHeap.peekKey

}

