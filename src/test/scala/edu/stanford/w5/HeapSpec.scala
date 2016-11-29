package edu.stanford.w5

import java.lang.Math.floor
import java.util

import edu.stanford.common.TestUtils
import edu.stanford.w6.MedianAware
import org.apache.commons.lang3.StringUtils.EMPTY
import org.scalatest.{FlatSpec, Matchers}

class HeapSpec extends FlatSpec with Matchers {

  "Min heap" should "keep track of min element" in {
    val heap = new MinArrayHeap[Int, String]()

    heap += 1 -> "1"
    heap += 4 -> "4"
    heap += 5 -> "5"
    heap += 10 -> "10"

    heap.isEmpty shouldBe false
    assertPeek(heap, "1")
    assertPop(heap, "1")
    assertPeek(heap, "4")
    assertPop(heap, "4")
    heap += 1 -> "1"
    heap += 20 -> "20"
    assertPop(heap, "1")
    assertPop(heap, "5")
    assertPeek(heap, "10")
    assertPop(heap, "10")
    heap += 2 -> "2"
    assertPop(heap, "2")
    assertPop(heap, "20")

    heap.isEmpty shouldBe true
  }

  it should "resize properly" in {
    val heap = new MinArrayHeap[Int, String]()
    val n = 10000

    for (i <- n to 1 by -1) {
      heap += i -> EMPTY
    }

    for (i <- n to 1 by -1) {
      heap.isEmpty shouldBe false
      heap.pop
    }

    heap.isEmpty shouldBe true
  }


  "Max heap" should "keep track of max elem" in {
    val heap = new MaxArrayHeap[Int, String]()

    heap += 10 -> "10"
    heap += 2 -> "2"
    heap += 3 -> "3"
    heap += 11 -> "11"
    heap += 1 -> "1"
    heap += 20 -> "20"
    heap += 3 -> "3"

    heap.isEmpty shouldBe false
    assertPop(heap, "20")
    assertPop(heap, "11")
    assertPop(heap, "10")
    assertPop(heap, "3")
    assertPop(heap, "3")
    assertPop(heap, "2")
    assertPop(heap, "1")
    heap.isEmpty shouldBe true
  }

  "Heaps" should "maintain median" in {
    val arr: Array[Int] = Array[Int](1, 22, 33, 4, 10, 6, 120, 99999, 0, 56, 77, 12321, 0, 10, 22130, 44, 1241412, 1)

    val median = MedianAware.median(arr: _*)

    util.Arrays.sort(arr)
    val m = floor(arr.length / 2).toInt
    median shouldBe arr(m - 1)
  }

  it should "maintain median in ProgQuestion#6.1" in {
    val arr = TestUtils.getResourceLines("Median.txt")
      .filter(_.nonEmpty)
      .map(_.toInt)
      .toArray

    val median = MedianAware.median(arr: _*)
    util.Arrays.sort(arr)
    val m = floor(arr.length / 2).toInt

    median shouldBe arr(m - 1)
  }

  private def assertPeek[T](heap: Heap[_, T], value: T): Unit = {
    heap.peekValue shouldBe Option(value)
  }

  private def assertPop[T](heap: Heap[_, T], value: T): Unit = {
    heap.popValue shouldBe Option(value)
  }

  private def headIsEmpty(): Unit = {

  }

}
