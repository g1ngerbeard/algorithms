package graph

object UndirectedGraph {

  def fromAdjacencyMatrix(matrix: Array[Array[String]]): Graph[UndirectedEdge] = {
    new UndirectedGraph().addAll(Graph.parseAdjMatrix(matrix): _*)
  }

}

case class UndirectedEdge(head: Vertex, tail: Vertex) extends Edge {

  override def incidentOn(vertex: Vertex): Boolean = {
    vertex == head || vertex == tail
  }
}

class UndirectedGraph(val edges: List[UndirectedEdge] = List(),
                      val vertices: Set[Vertex] = Set())
  extends Graph[UndirectedEdge] {

  protected def add(vertex: Vertex): Graph[UndirectedEdge] = {
    new UndirectedGraph(edges, vertices + vertex)
  }

  protected def add(edge: UndirectedEdge): Graph[UndirectedEdge] = {
    new UndirectedGraph(edges :+ edge, vertices ++ Set(edge.head, edge.tail))
  }

  override protected def newEdge(head: Vertex, tail: Vertex): UndirectedEdge = {
    UndirectedEdge(head, tail)
  }

  protected def neighbours(vertex: Vertex): List[Vertex] = {
    adjacencyMap(vertex)
      .map {
        case UndirectedEdge(`vertex`, tail) => tail
        case UndirectedEdge(head, `vertex`) => head
      }
  }

}

