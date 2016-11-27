package edu.stanford.w5

import edu.stanford.graph.WeightedUndirectedGraph

object ShortestPath {

  val DEFAULT_SHORTEST_PATH = 1000000

  def shortestPaths(graph: WeightedUndirectedGraph, from: String): Map[String, Int] = {
    var processed = Set(from)
    var result: Map[String, Int] = Map(from -> 0).withDefaultValue(DEFAULT_SHORTEST_PATH)

    // todo: heap
    def minValue(): (String, String, Int) = {
      val candidates = for {
        head <- processed
        (tail, weight) <- graph.neighbours(head)
        if !processed.contains(tail)
      } yield (head, tail, weight)

      candidates.minBy {
        case (h, _, w) => result(h) + w
      }

    }

    for (i <- 1 to (graph.vertices.size - 1)) {
      minValue() match {
        case (head, tail, weight) =>
          processed += tail
          result += tail -> (result(head) + weight)
      }
    }

    result
  }

}

