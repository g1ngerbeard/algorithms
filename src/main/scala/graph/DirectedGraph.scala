package graph

object DirectedGraph {

  def fromAdjacencyMatrix(matrix: Array[Array[String]]): Graph = {
    new DirectedGraph().addAll(Graph.parseAdjMatrix(matrix): _*)
  }

}

class DirectedGraph(val edges: List[Edge] = List(),
                    val vertices: Set[Vertex] = Set())
  extends Graph {

  override protected def newEdge(head: Vertex, tail: Vertex): DirectedEdge = {
    DirectedEdge(head, tail)
  }

  override protected def newGraph(edges: List[Edge], vertices: Set[Vertex]): Graph = {
    new DirectedGraph(edges, vertices)
  }

  override def neighbours(vertex: Vertex): List[Vertex] = {
    adjacencyMap(vertex).map(_.tail)
  }

}

case class DirectedEdge(head: Vertex, tail: Vertex) extends Edge {
  def incidentOn(vertex: Vertex) = head == vertex
}


