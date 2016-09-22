//package edu.stanford.w1
//
//import scala.collection.mutable
//
//object MergeSort {
//  def mergeSort[T <: Ordered[T]](array: Seq[T]): Seq[T] = {
//    new MergeSort[T]().sort(array)
//  }
//}
//
//class MergeSort[T <: Ordered[T]] {
//
//  private def sort(array: Seq[T]): Seq[T] = {
//    val len = array.length
//    if (len < 2) {
//      return array
//    }
//    val mid = len / 2
//    merge(sort(array.slice(0, mid)), sort(array.slice(mid, len)))
//  }
//
//  private def merge(left: Seq[T], right: Seq[T]): Seq[T] = {
//
//    val mLen = left.length + right.length
//
//    val merged = mutable.IndexedSeq[T]()
//
//    var k = 0
//    var i = 0
//    var j = 0
//
//    while (k < mLen) {
//
//      if (i >= left.length) {
//        return merged ++ right.slice(j, right.length)
//      } else if (j >= right.length) {
//        return merged ++ left.slice(i, left.length)
//      }
//
//      if (left(i) < right(j)) {
//        merged(k) = left(i)
//        i += 1
//      } else {
//        merged(k) = right(j)
//        j += 1
//      }
//
//      k += 1
//    }
//
//    merged
//  }
//
//}