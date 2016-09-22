package edu.stanford.w1

import java.nio.file.Paths

import edu.stanford.common.CommonUtils._
import edu.stanford.w1.InversionsCounter.countInversions
import edu.stanford.w1.InversionsSpec.{ARRAY_FILE_PATH, INVERSIONS}
import org.scalatest.{FlatSpec, Matchers}

object InversionsSpec {
  val INVERSIONS = 2407905288L
  val ARRAY_FILE_PATH = Paths.get("src/test/resources/IntegerArray.txt")
}

class InversionsSpec extends FlatSpec with Matchers {

  it should "count inversions in array" in {

    val inversions = countInversions(Array[Integer](1, 3, 5, 7, 2, 4, 6))
    inversions should be(6)
  }

  it should "count inversions for ProgQuestion#1" in {
    val array = file2intArr(ARRAY_FILE_PATH)
    countInversions(array) should be(INVERSIONS)
  }

}
