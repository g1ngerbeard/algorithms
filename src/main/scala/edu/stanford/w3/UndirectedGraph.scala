package edu.stanford.w3

import edu.stanford.w3.UndirectedGraph.UndirectedEdge

object UndirectedGraph {

  case class UndirectedEdge(val head: Vertex, val tail: Vertex) extends Edge {

    def contains(vertex: Vertex): Boolean = {
      vertex.label == head.label || vertex.label == tail.label
    }

  }

  def fromAdjacencyMatrix(matrix: Array[Array[String]]): UndirectedGraph = {
    val pairs = for {
      row <- matrix
      tail <- row.tail
    } yield {
      (row.head, tail)
    }

    new UndirectedGraph().addAll(pairs: _*)
  }

}

class UndirectedGraph(val edges: List[UndirectedEdge] = List(),
                      val vertices: Set[Vertex] = Set()) extends Graph {

  private val adjacencyMap =
    vertices.map(vertex => (vertex, edges.filter(_.contains(vertex)))).toMap

  def vertex(label: String): Option[Vertex] = {
    vertices.find(_.label == label)
  }

  def edge(headVtx: Vertex, tailVtx: Vertex): Option[UndirectedEdge] = {
    adjacencyMap(headVtx).find(_.contains(tailVtx))
  }

  def edge(headLabel: String, tailLabel: String): Option[UndirectedEdge] = {
    (vertex(headLabel), vertex(tailLabel)) match {
      case (Some(head), Some(tail)) => edge(head, tail)
      case _ => None
    }
  }

  def add(headLabel: String, tailLabel: String): UndirectedGraph = {
    val vOne = vertex(headLabel).getOrElse(Vertex(headLabel))
    val vTwo = vertex(tailLabel).getOrElse(Vertex(tailLabel))

    edge(headLabel, tailLabel) match {
      case None => new UndirectedGraph(
        edges :+ UndirectedEdge(vOne, vTwo),
        vertices ++ Set(Vertex(headLabel), Vertex(tailLabel)))

      case _ => this
    }
  }

  def addAll(labelPairs: (String, String)*): UndirectedGraph = {

    def doAdd(labelPairs: List[(String, String)], graph: UndirectedGraph): UndirectedGraph = {
      labelPairs match {
        case (head, tail) :: rest => doAdd(rest, graph.add(head, tail))
        case Nil => graph
      }
    }

    doAdd(labelPairs.toList, this)
  }

  def add(label: String): UndirectedGraph = {
    new UndirectedGraph(edges, vertices + Vertex(label))
  }

  // todo: use list instead of array
  def adjacencyMatrix: Array[Array[String]] = {
    vertices
      .map(v => v.label +: neighbours(v))
      .map(_.toArray)
      .toArray
  }

  private def neighbours(vertex: Vertex): List[String] = {
    adjacencyMap(vertex)
      .map {
        case UndirectedEdge(`vertex`, tail) => tail
        case UndirectedEdge(head, `vertex`) => head
      }
      .map(_.label)
  }

}

