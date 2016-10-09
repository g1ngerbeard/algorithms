package graph

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

trait Graph[T <: Edge] {

  val edges: List[T]

  val vertices: Set[Vertex]

  protected val adjacencyMap: Map[Vertex, List[T]] =
    vertices.map(vertex => (vertex, edges.filter(_.incidentOn(vertex)))).toMap

  def vertex(label: String): Option[Vertex] = {
    vertices.find(_.label == label)
  }

  def edge(headVtx: Vertex, tailVtx: Vertex): Option[T] = {
    adjacencyMap(headVtx).find(_.incidentOn(tailVtx))
  }

  def edge(headLabel: String, tailLabel: String): Option[T] = {
    (vertex(headLabel), vertex(tailLabel)) match {
      case (Some(head), Some(tail)) => edge(head, tail)
      case _ => None
    }
  }

  protected def add(vertex: Vertex): Graph[T]

  protected def add(edge: T): Graph[T]

  def add(label: String): Graph[T] = {
    add(Vertex(label))
  }

  def add(headLabel: String, tailLabel: String): Graph[T] = {
    edge(headLabel, tailLabel) match {
      case None => add(newEdge(headLabel, tailLabel))
      case _ => this
    }
  }

  protected def newEdge(head: Vertex, tail: Vertex): T

  protected def newEdge(headLabel: String, tailLabel: String): T = {
    newEdge(Vertex(headLabel), Vertex(tailLabel))
  }

  def addAll(labelPairs: (String, String)*): Graph[T] = {

    def doAdd(labelPairs: List[(String, String)], graph: Graph[T]): Graph[T] = {
      labelPairs match {
        case (head, tail) :: rest => doAdd(rest, graph.add(head, tail))
        case Nil => graph
      }
    }

    doAdd(labelPairs.toList, this)
  }

  def delete(headLabel: String, tailLabel: String): Graph[T] = {
    throw new NotImplementedError()
  }

  def delete(label: String): Graph[T] = {
    throw new NotImplementedError()
  }

  def adjacencyMatrix: Array[Array[String]] = {
    vertices
      .map(v => v.label +: neighbours(v).map(_.label))
      .map(_.toArray)
      .toArray
  }

  protected def neighbours(vertex: Vertex): List[Vertex]

}

trait Edge {

  val head: Vertex

  val tail: Vertex

  def incidentOn(vertex: Vertex): Boolean

}

case class Vertex(label: String)

