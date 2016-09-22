package edu.stanford.w1

import edu.stanford.w1.MergeSort.mergeSort
import org.scalatest.{FlatSpec, Matchers}

class SortSpec extends FlatSpec with Matchers {
  it should "sort list" in {

    val sortedList = mergeSort(Array[Integer](3, 4, 5, 1, 2, 3, 4))

    sortedList should be (sorted)
  }

}
