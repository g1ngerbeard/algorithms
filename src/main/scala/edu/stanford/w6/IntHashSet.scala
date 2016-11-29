package edu.stanford.w6

import edu.stanford.w6.IntHashSet.primes

object IntHashSet {

  def apply(values: Int*): IntHashSet = {
    val hashSet: IntHashSet = new IntHashSet()

    for (value <- values) {
      hashSet += value
    }

    hashSet
  }

  val primes = List(
    29, 71, 113, 173, 229, 281, 349, 409, 463, 541, 601, 659, 733, 809, 863, 941, 1013, 1069, 1151,
    1223, 1291, 1373, 1451, 1511, 1583, 1657, 1733, 1811, 1889, 1987, 2053, 2129, 2213, 2287, 2357,
    2423, 2531, 2617, 2687, 2741, 2819, 2903, 2999, 3079, 3181, 3257, 3331, 3413, 3511, 3571, 3643,
    3727, 3821, 3907, 3989, 4057, 4139, 4231, 4297, 4409, 4493, 4583, 4657, 4751, 4831, 4937, 5003,
    5087, 5179, 5279, 5387, 5443, 5521, 5639, 5693, 5791, 5857, 5939, 6053, 6133, 6221, 6301, 6367,
    6473, 6571, 6673, 6761, 6833, 6917, 6997, 7103, 7207, 7297, 7411, 7499, 7561, 7643, 7723, 7829,
    7919, 8017, 8111, 8219, 8291, 8387, 8501, 8597, 8677, 8741, 8831, 8929, 9011, 9109, 9199, 9283,
    9377, 9439, 9533, 9631, 9733, 9811, 9887, 10007, 10099, 10177, 10271, 10343, 10459, 10567, 10657,
    10739, 10859, 10949, 11059, 11149, 11251, 11329, 11443, 11527, 11657, 11777, 11833, 11933, 12011)

}

class IntHashSet() {

  private val alpha = 0.5

  private var primesN = 0

  private var bucketsSize = primes(primesN)

  private var buckets = new Array[IntLinkedList](bucketsSize)

  private var count: Int = 0

  def +=(value: Int): Unit = {
    add(value)
  }

  def add(value: Int): Boolean = {
    val bucket = bucketN(value)
    val list = valueAt(bucket).getOrElse(IntLinkedList.empty())

    if (list.contains(value)) {
      false
    } else {
      buckets(bucket) = value :: list
      onValueAdded()
      true
    }
  }

  def contains(value: Int): Boolean = {
    valueAt(bucketN(value)).exists(_.contains(value))
  }

  def size: Int = count

  private def valueAt(idx: Int): Option[IntLinkedList] = {
    Option(buckets(idx))
  }

  private def bucketN(value: Int): Int = value % bucketsSize

  private def onValueAdded() {
    count += 1

    if ((bucketsSize / count) < alpha) {
      primesN += 1

      if (primesN < primes.length) {
        val newSize = primes(primesN)
        val newBuckets = new Array[IntLinkedList](newSize)

        // todo: regroup buckets?
        buckets.copyToArray(newBuckets)

        bucketsSize = newSize
        buckets = newBuckets
      } else {
        // todo: compute next prime
        throw new IllegalStateException("Primes list exhausted!")
      }
    }
  }

}



