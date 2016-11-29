package edu.stanford.w6

import scala.annotation.tailrec

object IntLinkedList {

  def empty(): IntLinkedList = new IntLinkedList(None)

}

class IntLinkedList(headNode: Option[ListNode]) {

  def head: Option[Int] = headNode.map(_.value)

  def prepend(value: Int): IntLinkedList = {
    new IntLinkedList(Some(ListNode(value, headNode)))
  }

  def ::(value: Int): IntLinkedList = prepend(value)

  def contains(value: Int): Boolean = {
    @tailrec
    def traverse(headNode: Option[ListNode]): Boolean = {
      headNode match {
        case None => false
        case Some(node) => node match {
          case ListNode(`value`, _) => true
          case ListNode(_, next) => traverse(next)
        }
      }
    }

    traverse(headNode)
  }

}

private case class ListNode(value: Int, next: Option[ListNode])

