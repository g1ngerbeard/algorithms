package edu.stanford.w4

import edu.stanford.graph.FastDirectedGraph

import scala.collection.mutable.{Map => MMap, MutableList => MList, Set => MSet}

object StronglyConnectedComponents {

  // todo: output sscs
  def sccsizes(inputGraph: FastDirectedGraph): List[Int] = {
    val order: List[String] = ordering(inputGraph.reverted)

    val leadersMap = leaders(inputGraph, order)

    val result: MMap[String, Int] = MMap().withDefaultValue(0)

    for {
      (vertex, leader) <- leadersMap
    } {
      result += (leader -> (result(leader) + 1))
    }

    result.values.toList
  }

  private def ordering(graph: FastDirectedGraph): List[String] = {
    val order: MList[String] = MList.empty
    val explored: MSet[String] = MSet()

    for (iVertex <- graph.vertices) {
      if (!explored.contains(iVertex)) {
        dfs(iVertex)
      }
    }

    def dfs(vertex: String): Unit = {
      explored += vertex
      for {
        v <- graph.neighbours(vertex)
        if !explored.contains(v)
      } {
        dfs(v)
      }

      vertex +=: order
    }

    order.toList
  }

  private def leaders(graph: FastDirectedGraph, vertices: List[String]): MMap[String, String] = {
    val leaders: MMap[String, String] = MMap.empty
    val explored: MSet[String] = MSet()

    for (iVertex <- vertices) {
      if (!explored.contains(iVertex)) {
        dfs(iVertex, iVertex)
      }
    }

    def dfs(vertex: String, leader: String): Unit = {
      explored += vertex
      leaders += (vertex -> leader)

      for {
        v <- graph.neighbours(vertex)
        if !explored.contains(v)
      } {
        dfs(v, leader)
      }
    }

    leaders
  }
}


