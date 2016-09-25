package edu.stanford.w3

import edu.stanford.common.MatrixPrinter
import edu.stanford.common.MatrixPrinter.Mode.VAR_ROW
import org.scalatest.{FlatSpec, Matchers}

class GraphSpec extends FlatSpec with Matchers {

  trait TestGraph {
    val graph = new UndirectedGraph().addAll(
      ("1", "2"),
      ("2", "3"),
      ("3", "4"),
      ("4", "1"),
      ("3", "1"),
      ("5", "1"),
      ("5", "2")
    )
  }

  it should "build adjacency matrix from graph" in new TestGraph {
    println(MatrixPrinter.printMatrix(graph.adjacencyMatrix, VAR_ROW))
  }

  it should "create graph from adjacency matrix" in new TestGraph {
    val reBuiltGraph = UndirectedGraph.fromAdjacencyMatrix(graph.adjacencyMatrix)
    matrix2set(reBuiltGraph.adjacencyMatrix) shouldEqual matrix2set(graph.adjacencyMatrix)
  }

  private def matrix2set(matrix: Array[Array[String]]): Set[Set[String]] = {
    matrix.map(_.toSet).toSet
  }

}
