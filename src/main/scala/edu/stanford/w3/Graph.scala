package edu.stanford.w3

trait Graph {

  val edges: List[Edge]

  val vertices: Set[Vertex]

  def vertex(label: String): Option[Vertex]

  def edge(headLabel: String, tailLabel: String): Option[Edge]

  def add(headLabel: String, tailLabel: String): Graph

  def add(label: String): Graph

  def delete(headLabel: String, tailLabel: String): Graph = {
    throw new NotImplementedError()
  }

  def delete(label: String): Graph = {
    throw new NotImplementedError()
  }
}

trait Edge {

  val head: Vertex

  val tail: Vertex

}

case class Vertex(label: String)

