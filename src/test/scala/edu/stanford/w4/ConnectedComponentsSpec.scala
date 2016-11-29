package edu.stanford.w4

import edu.stanford.common.TestUtils.getResourceLines
import edu.stanford.graph.FastDirectedGraph
import org.apache.commons.lang3.StringUtils._
import org.scalatest.{FlatSpec, Matchers}

class ConnectedComponentsSpec extends FlatSpec with Matchers {

  trait TestGraph {
    val graph = FastDirectedGraph(
      "1" -> "7",
      "7" -> "4",
      "4" -> "1",
      "7" -> "9",
      "9" -> "6",
      "6" -> "3",
      "3" -> "9",
      "9" -> "8",
      "8" -> "5",
      "5" -> "2",
      "2" -> "8"
    )
  }

  trait ProgQuestion4 {
    val graph = FastDirectedGraph(buildTestPairs("SCC.txt"): _*)
  }

  it should "count strongly connected components" in new TestGraph {
    val result = StronglyConnectedComponents.sccsizes(graph)
    println(result)
    result should be(List(3, 3, 3))
  }

  ignore should "calculate 5 largest SCCs" in new ProgQuestion4 {
    val sizes = StronglyConnectedComponents
      .sccsizes(graph)
      .sorted
      .reverse
      .take(5)

    sizes should be(List(434821, 968, 459, 313, 211))
  }

  private def buildTestPairs(resourse: String): Array[(String, String)] = {
    getResourceLines(resourse)
      .map(line => line.split(SPACE))
      .map(pair => (pair(0), pair(1)))
      .toArray
  }

}
