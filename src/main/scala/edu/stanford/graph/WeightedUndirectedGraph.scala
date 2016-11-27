package edu.stanford.graph

import scala.collection.mutable.{Map => MMap}

//todo: extract common interface with directed graph
object WeightedUndirectedGraph {
  def apply(edges: (String, String, Int)*): WeightedUndirectedGraph = {

    val adjacencyMap: MMap[String, List[(String, Int)]] = MMap().withDefaultValue(List.empty)

    for ((head, tail, weight) <- edges) {
      adjacencyMap += head -> (adjacencyMap(head) :+(tail, weight))
      adjacencyMap += tail -> (adjacencyMap(tail) :+(head, weight))
    }

    new WeightedUndirectedGraph(adjacencyMap)
  }
}

class WeightedUndirectedGraph(adjacencyMap: MMap[String, List[(String, Int)]]) {

  val vertices: List[String] = adjacencyMap.keys.toList

  def neighbours(vertex: String): List[(String, Int)] = {
    adjacencyMap(vertex)
  }

}



