package edu.stanford.w3

import edu.stanford.Benchmark
import edu.stanford.common.MatrixPrinter
import edu.stanford.common.MatrixPrinter.Mode.VAR_ROW
import edu.stanford.common.TestUtils.parseMatrix
import edu.stanford.graph.immutable.UndirectedGraph
import edu.stanford.w4.GraphUtils
import edu.stanford.graph.WeightedUndirectedGraph
import org.scalatest.{FlatSpec, Matchers}

class GraphSpec extends FlatSpec with Matchers {

  trait TestGraph {
    val graph = new UndirectedGraph().addAll(
      "1" -> "2",
      "2" -> "3",
      "3" -> "4",
      "4" -> "1",
      "5" -> "1",
      "5" -> "2",
      "3" -> "7",
      "5" -> "6",
      "6" -> "7"
    )
  }

  trait UnConnectedTestGrapth {
    val graph = new UndirectedGraph().addAll(
      "a" -> "b",
      "a" -> "c",
      "a" -> "d",
      "c" -> "b",
      "c" -> "d",
      "f" -> "e",
      "g" -> "k",
      "k" -> "l",
      "g" -> "l"
    )
  }

  trait ProgQuestion3 {
    val testGraph = UndirectedGraph.fromAdjacencyMatrix(parseMatrix("kargerMinCut.txt"))
  }

  it should "build adjacency matrix from edu.stanford.graph" in new TestGraph {
    println(MatrixPrinter.printMatrix(graph.adjacencyMatrix, VAR_ROW))
  }

  it should "create edu.stanford.graph from adjacency matrix" in new ProgQuestion3 {
    val reBuiltGraph = UndirectedGraph.fromAdjacencyMatrix(testGraph.adjacencyMatrix)
    matrix2set(reBuiltGraph.adjacencyMatrix) shouldEqual matrix2set(testGraph.adjacencyMatrix)
  }

  "Random contractions algorithm" should "contract edu.stanford.graph" in new TestGraph {
    val result = RandomContractions.contract(graph)
    println(result)
  }

  ignore should "produce minimum cut of a edu.stanford.graph" in new ProgQuestion3 {
    val n = 10

    val results = (1 to n)
      .map(i => Benchmark.run(RandomContractions.contract(testGraph)))

    println(s"Average execution time ${results.map(_.duration).sum / n} ms")

    println(s"Minimum cut ${results.map(_.result.crossingEdges).min}")
  }

  "BFS" should "produce layers of the edu.stanford.graph" in new TestGraph {
    println(GraphUtils.layers(graph, "1"))
  }

  it should "compute connected components" in new UnConnectedTestGrapth {
    println(GraphUtils.connectedComponents(graph))
  }

  private def matrix2set(matrix: Array[Array[String]]): Set[Set[String]] = {
    matrix.map(_.toSet).toSet
  }

}
