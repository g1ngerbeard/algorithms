package edu.stanford.w5

trait Heap[K, V] {

  def add(entry: (K, V)): Unit

  def +=(entry: (K, V)) = add(entry)

  def addAll(pairs: (K, V)*): Unit = {
    pairs.foreach { case (k, v) => add(k, v) }
  }

  def addAll(keySupplier: V => K, values: V*): Unit = {
    values.foreach(v => add(keySupplier(v), v))
  }

  def delete(key: K): Option[V]

  def peekValue: Option[V] = peek.map(_._2)

  def peekKey: Option[K] = peek.map(_._1)

  def peek: Option[(K, V)]

  def popValue: Option[V] = pop.map(_._2)

  def popKey: Option[K] = pop.map(_._1)

  def pop: Option[(K, V)]

  def size: Int

  def isEmpty: Boolean
}
