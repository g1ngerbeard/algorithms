package edu.stanford.w6

import java.util.concurrent.Executors.newFixedThreadPool

import edu.stanford.common.TestUtils
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}

class LongHashSetSpec extends FlatSpec with Matchers {

  trait ProgQuestion6_1 {
    val numbers = TestUtils
      .getResourceLines("algo1-programming_prob-2sum.txt")
      .map(_.toLong)
      .toArray
  }

  "hash set" should "track elements" in {
    val set = LongHashSet(1, 6, 7, 7)

    set.contains(1) shouldBe true
    set.contains(7) shouldBe true
    set.contains(6) shouldBe true
    set.contains(8) shouldBe false
    set.contains(1111) shouldBe false

    set.size shouldBe 3
  }

  "hash set" should "resize backing array" in {
    val set = new LongHashSet()

    for (i <- 1 to 3000) {
      set += i
    }

    set.contains(999) shouldBe true
    set.contains(100000) shouldBe false
  }

  ignore should "compute 2-sums" in new ProgQuestion6_1 {

    def count(from: Int, to: Int): Future[Int] = {
      Future((from to to)
        .count(t => numbers.exists { x =>
          val y = t - x
          x != y && set.contains(y)
        }))
    }

    implicit val ctx = ExecutionContext.fromExecutor(newFixedThreadPool(4))

    val set = LongHashSet(numbers: _*)

    val c1 = count(-10000, -5000)
    val c2 = count(-5001, 0)
    val c3 = count(1, 5000)
    val c4 = count(5001, 10000)

    val f = Future.reduce(List(c1, c2, c3, c4))((r, t) => r + t)

    f.onComplete(c => println(s"2-sum = $c"))

    Await.ready(f, 20 minutes)
  }


}
