package edu.stanford.w4

import graph.{Graph, Vertex}

import scala.collection.immutable.Queue

object GraphUtils {

  // todo: return layers
  // todo: start vertex
  def bfs[T](graph: Graph, firstLabel: String): Unit = {

    def bfs(explored: Set[Vertex], queue: Queue[Vertex]): Unit = {
      queue.dequeueOption match {
        case Some((vertex, rest)) =>
          val unexplored = graph.neighbours(vertex)
            .filterNot(explored.contains)

          bfs(explored + vertex, rest ++ unexplored)
        case None =>
      }
    }

    graph.vertex(firstLabel) match {
      case Some(vertex) => bfs(Set(), Queue(vertex))
      case _ => throw new IllegalArgumentException("Non existent vertex")
    }

  }

  def dfs[T](graph: Graph, startLabel: String): Unit = {

    def dfs(explored: Set[Vertex], stack: List[Vertex]): Unit = {
      stack match {
        case vertex::rest =>
//          val unexplored = graph.neighbours(vertex)
//            .filterNot(explored.contains)
//          dfs(explored + vertex, unexplored ::: rest)
        case Nil =>
      }

    }

    graph.vertex(startLabel) match {
      case Some(vertex) => dfs(Set(vertex), List(vertex))
      case _ => throw new IllegalArgumentException("Non existent vertex")
    }

  }
}
