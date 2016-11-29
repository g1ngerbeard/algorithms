package edu.stanford.w6

import scala.annotation.tailrec

object LongLinkedList {

  def empty(): LongLinkedList = new LongLinkedList(0, None)

}

class LongLinkedList(count: Int, headNode: Option[ListNode]) {

  def head: Option[Long] = headNode.map(_.value)

  def prepend(value: Long): LongLinkedList = {
    new LongLinkedList(count + 1, Some(ListNode(value, headNode)))
  }

  def ::(value: Long): LongLinkedList = prepend(value)

  def contains(value: Long): Boolean = {
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

private case class ListNode(value: Long, next: Option[ListNode])

