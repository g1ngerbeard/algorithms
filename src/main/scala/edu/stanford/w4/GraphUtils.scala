package edu.stanford.w4

import edu.stanford.graph.immutable.{Graph, Vertex}

import scala.annotation.tailrec
import scala.collection.immutable.{Queue, TreeMap}

object GraphUtils {

  def bfs(graph: Graph, firstLabel: String): Map[String, Int] = {

    @tailrec
    def bfs(explored: Set[Vertex], queue: Queue[Vertex], dist: Map[Vertex, Int]): Map[Vertex, Int] = {
      queue.dequeueOption match {
        case Some((vertex, rest)) =>
          val currDist = dist(vertex)

          val exploredDist = graph.neighbours(vertex)
            .filterNot(explored.contains)
            .map(_ -> (currDist + 1))
            .toMap

          val vertexes = exploredDist.keySet

          bfs(explored ++ vertexes, rest.enqueue(vertexes), dist ++ exploredDist)

        case None => dist
      }
    }

    graph.vertex(firstLabel) match {
      case Some(vertex) =>
        bfs(Set(vertex), Queue(vertex), Map(vertex -> 0))
          .map { case (Vertex(label), dist) => (label, dist) }
      case _ => throw new IllegalArgumentException("Non existent vertex")
    }

  }

  def layers(graph: Graph, firstLabel: String): Map[Int, Set[String]] = {
    val dist = bfs(graph, firstLabel)

    dist.foldLeft(TreeMap[Int, Set[String]]().withDefaultValue(Set())) {
      case (res, (label, d)) => res + (d -> (res(d) + label))
    }
  }

  def connectedComponents(graph: Graph): List[Set[String]] = {
    graph.vertices.foldLeft(List[Set[String]]()) {
      case (comp, vert) =>
        if (comp.exists(_.contains(vert.label))) {
          comp
        } else {
          comp :+ bfs(graph, vert.label).keySet
        }
    }
  }

}
