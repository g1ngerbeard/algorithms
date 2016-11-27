package edu.stanford.graph

import scala.collection.mutable.{Map => MMap}

object FastDirectedGraph {
  def apply(edges: (String, String)*): FastDirectedGraph = {

    val result: MMap[String, List[String]] = MMap().withDefaultValue(List.empty)

    for ((head, tail) <- edges) {
      result += head -> (result(head) :+ tail)
    }

    new FastDirectedGraph(result)
  }
}

class FastDirectedGraph(adjacencyMap: MMap[String, List[String]]) {

  val vertices: List[String] = adjacencyMap.keys.toList

  def neighbours(vertex: String): List[String] = {
    adjacencyMap(vertex)
  }

  lazy val reverted: FastDirectedGraph = {
    val result: MMap[String, List[String]] = MMap().withDefaultValue(List.empty)

    for {
      (head, tails) <- adjacencyMap
      tail <- tails
    } {
      result += (tail -> (result(tail) :+ head))
    }

    new FastDirectedGraph(result)
  }
}