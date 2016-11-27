package edu.stanford.w5

import edu.stanford.graph.WeightedUndirectedGraph
import edu.stanford.w5.ShortestPath.shortestPaths
import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source

class ShortestPathSpec extends FlatSpec with Matchers {

  trait TestGraph {
    val graph = WeightedUndirectedGraph(
      ("1", "2", 1),
      ("1", "3", 3),
      ("2", "3", 1),
      ("2", "4", 2),
      ("3", "4", 4),
      ("3", "5", 1),
      ("4", "5", 1)
    )
  }

  trait ProgQuestion5 {
    val graph = pq4Graph("dijkstraData.txt")
  }

  it should "find shortest paths" in new TestGraph {
    val paths = shortestPaths(graph, "1")

    paths("2") should be(1)
    paths("3") should be(2)
    paths("4") should be(3)
    paths("5") should be(3)
  }

  it should "find shortest paths in programmatic question #5" in new ProgQuestion5 {
    val paths = shortestPaths(graph, "1")

    val result = List(7, 37, 59, 82, 99, 115, 133, 165, 188, 197)
      .map(_.toString)
      .map(paths)
      .mkString(",")

    result should be("2599,2610,2947,2052,2367,2399,2029,2442,2505,3068")
  }


  def pq4Graph(file: String): WeightedUndirectedGraph = {

    val tuples = Source
      .fromURI(ClassLoader.getSystemResource(file).toURI)
      .getLines()
      .flatMap(parseLine)
      .toArray

    WeightedUndirectedGraph(tuples: _*)
  }

  def parseLine(line: String): Array[(String, String, Int)] = {
    val arr = line.split("\\s")
    val head = arr(0)

    arr
      .drop(1)
      .map(_.split(","))
      .map(p => (head, p(0), p(1).toInt))
  }
}
