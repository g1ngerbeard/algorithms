package edu.stanford.w3

import edu.stanford.Benchmark
import edu.stanford.common.MatrixPrinter
import edu.stanford.common.MatrixPrinter.Mode.VAR_ROW
import graph.{DirectedGraph, UndirectedGraph}
import org.apache.commons.lang3.StringUtils.{SPACE, normalizeSpace}
import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source

class GraphSpec extends FlatSpec with Matchers {

  trait TestGraph {
    val graph = new DirectedGraph().addAll(
      ("1", "2"),
      ("2", "3"),
      ("3", "4"),
      ("4", "1"),
      ("3", "1"),
      ("5", "1"),
      ("5", "2")
    )
  }

  trait ProgQuestion3 {
    val testGraph = UndirectedGraph.fromAdjacencyMatrix(parseMatrix("kargerMinCut.txt"))
  }

  it should "build adjacency matrix from graph" in new TestGraph {
    println(MatrixPrinter.printMatrix(graph.adjacencyMatrix, VAR_ROW))
  }

  it should "create graph from adjacency matrix" in new ProgQuestion3 {
    val reBuiltGraph = UndirectedGraph.fromAdjacencyMatrix(testGraph.adjacencyMatrix)
    matrix2set(reBuiltGraph.adjacencyMatrix) shouldEqual matrix2set(testGraph.adjacencyMatrix)
  }

  "Random contractions algorithm" should "contract graph" in new TestGraph {
    val result = RandomContractions.contract(graph)
    println(result)
  }

  it should "produce minimum cut of a graph" in new ProgQuestion3 {
    val n = 2

    val results = (1 to n)
      .map(i => Benchmark.run(RandomContractions.contract(testGraph)))

    println(s"Average execution time ${results.map(_.duration).sum / n} ms")

    println(s"Minimum cut ${results.map(_.result.crossingEdges).min}")
  }

  private def matrix2set(matrix: Array[Array[String]]): Set[Set[String]] = {
    matrix.map(_.toSet).toSet
  }

  private def parseMatrix(fileName: String): Array[Array[String]] = {
    val source = Source.fromURI(ClassLoader.getSystemResource(fileName).toURI)

    val result = for {
      line <- source.getLines()
    } yield normalizeSpace(line).split(SPACE)

    result.toArray
  }

}
