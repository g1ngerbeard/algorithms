package edu.stanford.w3

import edu.stanford.common.Assertions._
import edu.stanford.w3.UndirectedGraph.UndirectedEdge
import org.apache.commons.lang3.RandomUtils

import scala.annotation.tailrec
import scala.collection.JavaConversions._

object RandomContractions {

  def contract(graph: UndirectedGraph): ContractionResult = {
    assertSizeGt(1, graph.vertices)

    doContract(ContractedGraph(graph)) match {
      case ContractedGraph(List(left, right), edges) => ContractionResult(left.vertices, right.vertices, edges.size)
      case list => throw new IllegalArgumentException("Result groups have invalid size")
    }
  }

  @tailrec
  def doContract(graph: ContractedGraph): ContractedGraph = {
    if (graph.isFinalState) {
      graph
    } else {
      doContract(graph.contractEdge(RandomUtils.nextInt(0, graph.edges.size)))
    }
  }

}

case class ContractionResult(leftGroup: Set[Vertex], rightGroup: Set[Vertex], crossingEdges: Int)

object ComposedVertex {
  def apply(vertex: Vertex): ComposedVertex = ComposedVertex(Set(vertex))
}

case class ComposedVertex(vertices: Set[Vertex]) {

  def containsAllFrom(other: ComposedVertex): Boolean = {
    other.vertices.forall(vertices.contains)
  }

  def containsAllFrom(edge: ContractableEdge): Boolean = {
    containsAllFrom(edge.head) && containsAllFrom(edge.tail)
  }

  def containsAnyFrom(other: ComposedVertex): Boolean = {
    other.vertices.exists(vertices.contains)
  }

  def containsAnyFrom(edge: ContractableEdge): Boolean = {
    containsAllFrom(edge.head) || containsAllFrom(edge.tail)
  }

}

object ContractableEdge {
  def apply(edge: UndirectedEdge): ContractableEdge = ContractableEdge(ComposedVertex(edge.head), ComposedVertex(edge.tail))
}

case class ContractableEdge(head: ComposedVertex, tail: ComposedVertex) {
  def contract() = ComposedVertex(head.vertices ++ tail.vertices)
}

object ContractedGraph {
  def apply(graph: UndirectedGraph): ContractedGraph = {
    val cVertexes = graph.vertices.map(ComposedVertex(_)).toList
    val cEdges = graph.edges.map(ContractableEdge(_))

    ContractedGraph(cVertexes, cEdges)
  }
}

case class ContractedGraph(vertices: List[ComposedVertex], edges: List[ContractableEdge]) {

  def isFinalState: Boolean = edges.isEmpty || vertices.size <= 2

  def contractEdge(index: Int): ContractedGraph = {
    val contractedVertex = edges(index).contract()

    val otherVertices = vertices.filterNot(_.containsAnyFrom(contractedVertex))

    val edgesWithoutCycles = edges
      .filterNot(contractedVertex.containsAllFrom)
      .map {
        case ContractableEdge(head, tail) if contractedVertex.containsAllFrom(head) => ContractableEdge(contractedVertex, tail)
        case ContractableEdge(head, tail) if contractedVertex.containsAllFrom(tail) => ContractableEdge(head, contractedVertex)
        case edge => edge
      }


    ContractedGraph(otherVertices :+ contractedVertex, edgesWithoutCycles)
  }

}
