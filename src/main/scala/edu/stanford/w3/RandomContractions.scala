package edu.stanford.w3

import edu.stanford.common.Assertions._
import edu.stanford.w3.UndirectedGraph.UndirectedEdge
import org.apache.commons.lang3.RandomUtils

import scala.annotation.tailrec

object RandomContractions {

  def contract(graph: UndirectedGraph): ContractionResult = {
    import scala.collection.JavaConversions._

    assertSizeGt(1, graph.vertices)

    val vertexes = graph.vertices.map(v => ComposedVertex(Set(v))).toList

    doContract(ContractedGraph(vertexes, graph.edges)) match {
      case ContractedGraph(groups, edges) => groups match {
        case List(ComposedVertex(left), ComposedVertex(right)) => ContractionResult(left, right, edges.size)
        case list => throw new IllegalArgumentException(s"Result groups have invalid size ${list.size}")
      }
      case corrupted => throw new IllegalStateException(s"Invalid result of graph contraction $corrupted")
    }

  }

  @tailrec
  def doContract(graph: ContractedGraph): ContractedGraph = {
    graph match {
      case ContractedGraph(_, Nil) => graph
      case ContractedGraph(vertices, _) if vertices.size <= 2 => graph
      case ContractedGraph(vertices, edges) =>

        val edgeToContract = graph.edges(RandomUtils.nextInt(0, graph.edges.size))

        val mergedVertex = graph.vertices
          .filter(_.containsAnyFrom(edgeToContract))
          .reduce((v1, v2) => ComposedVertex(v1.vertices ++ v2.vertices))

        val otherVertices = vertices.filterNot(_.containsAnyFrom(mergedVertex))
        val edgesWithoutCycles = edges.filterNot(mergedVertex.containsAllFrom(_))

        doContract(ContractedGraph(otherVertices :+ mergedVertex, edgesWithoutCycles))
    }

  }

}

case class ContractionResult(leftGroup: Set[Vertex], rightGroup: Set[Vertex], crossingEdges: Int)

case class ComposedVertex(vertices: Set[Vertex]) {
  def containsAnyFrom(edge: Edge): Boolean = {
    vertices.exists(Set(edge.head, edge.tail).contains)
  }

  def containsAllFrom(edge: Edge): Boolean = {
    vertices.contains(edge.head) && vertices.contains(edge.tail)
  }

  def containsAnyFrom(other: ComposedVertex): Boolean = {
    other.vertices.exists(vertices.contains)
  }

}

case class ContractedGraph(vertices: List[ComposedVertex], edges: List[UndirectedEdge])
