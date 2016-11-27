package edu.stanford.graph.immutable

object Graph {
  def parseAdjMatrix(matrix: Array[Array[String]]): Array[(String, String)] = {
    for {
      row <- matrix
      tail <- row.tail
    } yield {
      (row.head, tail)
    }
  }
}

trait Graph {

  val edges: List[Edge]

  val vertices: Set[Vertex]

  protected val adjacencyMap: Map[Vertex, List[Edge]] =
    vertices.map(vertex => (vertex, edges.filter(_.incidentOn(vertex)))).toMap

  def vertex(label: String): Option[Vertex] = {
    vertices.find(_.label == label)
  }

  def edge(headVtx: Vertex, tailVtx: Vertex): Option[Edge] = {
    adjacencyMap(headVtx).find(_.incidentOn(tailVtx))
  }

  def edge(headLabel: String, tailLabel: String): Option[Edge] = {
    (vertex(headLabel), vertex(tailLabel)) match {
      case (Some(head), Some(tail)) => edge(head, tail)
      case _ => None
    }
  }

  protected def newGraph(edges: List[Edge], vertices: Set[Vertex]): Graph

  protected def add(vertex: Vertex): Graph = {
    newGraph(edges, vertices + vertex)
  }

  protected def add(edge: Edge): Graph = {
    newGraph(edges :+ edge, vertices + edge.head + edge.tail)
  }

  def add(label: String): Graph = {
    add(Vertex(label))
  }

  def add(headLabel: String, tailLabel: String): Graph = {
    edge(headLabel, tailLabel) match {
      case None => add(newEdge(headLabel, tailLabel))
      case _ => this
    }
  }

  protected def newEdge(head: Vertex, tail: Vertex): Edge

  protected def newEdge(headLabel: String, tailLabel: String): Edge = {
    newEdge(Vertex(headLabel), Vertex(tailLabel))
  }

  def addAll(labelPairs: (String, String)*): Graph = {

    def doAdd(labelPairs: List[(String, String)], graph: Graph): Graph = {
      labelPairs match {
        case (head, tail) :: rest => doAdd(rest, graph.add(head, tail))
        case Nil => graph
      }
    }

    doAdd(labelPairs.toList, this)
  }

  def delete(headLabel: String, tailLabel: String): Graph = {
    throw new NotImplementedError()
  }

  def delete(label: String): Graph = {
    throw new NotImplementedError()
  }

  def adjacencyMatrix: Array[Array[String]] = {
    vertices
      .map(v => v.label +: neighbours(v).map(_.label))
      .map(_.toArray)
      .toArray
  }

  def neighbours(vertex: Vertex): List[Vertex]

}

trait Edge {

  val head: Vertex

  val tail: Vertex

  def incidentOn(vertex: Vertex): Boolean

}

case class Vertex(label: String)

