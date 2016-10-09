package graph

object DirectedGraph {

  def fromAdjacencyMatrix(matrix: Array[Array[String]]): Graph[DirectedEdge] = {
    new DirectedGraph().addAll(Graph.parseAdjMatrix(matrix): _*)
  }

}

class DirectedGraph(val edges: List[DirectedEdge] = List(),
                    val vertices: Set[Vertex] = Set())
  extends Graph[DirectedEdge] {

  override protected def newEdge(head: Vertex, tail: Vertex): DirectedEdge = {
    DirectedEdge(head, tail)
  }

  override protected def add(vertex: Vertex): Graph[DirectedEdge] = {
    new DirectedGraph(edges, vertices + vertex)
  }

  override protected def add(edge: DirectedEdge): Graph[DirectedEdge] = {
    new DirectedGraph(edges :+ edge, vertices + edge.head + edge.tail)
  }

  override protected def neighbours(vertex: Vertex): List[Vertex] = {
    adjacencyMap(vertex).map(_.tail)
  }

}

case class DirectedEdge(head: Vertex, tail: Vertex) extends Edge {
  def incidentOn(vertex: Vertex) = head == vertex
}


