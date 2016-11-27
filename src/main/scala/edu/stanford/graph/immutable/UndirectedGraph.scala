package edu.stanford.graph.immutable

object UndirectedGraph {

  def fromAdjacencyMatrix(matrix: Array[Array[String]]): Graph = {
    new UndirectedGraph().addAll(Graph.parseAdjMatrix(matrix): _*)
  }

}

case class UndirectedEdge(head: Vertex, tail: Vertex) extends Edge {

  override def incidentOn(vertex: Vertex): Boolean = {
    vertex == head || vertex == tail
  }
}

class UndirectedGraph(val edges: List[Edge] = List(),
                      val vertices: Set[Vertex] = Set())
  extends Graph {

  override protected def newGraph(edges: List[Edge], vertices: Set[Vertex]): Graph = {
    new UndirectedGraph(edges, vertices)
  }

  override protected def newEdge(head: Vertex, tail: Vertex): UndirectedEdge = {
    UndirectedEdge(head, tail)
  }

  override def neighbours(vertex: Vertex): List[Vertex] = {
    adjacencyMap(vertex)
      .map {
        case UndirectedEdge(`vertex`, tail) => tail
        case UndirectedEdge(head, `vertex`) => head
      }
  }

}

