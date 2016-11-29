package edu.stanford.w6

import org.scalatest.{Matchers, FlatSpec}

class IntHashSetSpec extends FlatSpec with Matchers {

  "hash set" should "track elements" in {
    val set = IntHashSet(1, 6, 7, 7)

    set.contains(1) shouldBe true
    set.contains(7) shouldBe true
    set.contains(6) shouldBe true
    set.contains(8) shouldBe false
    set.contains(1111) shouldBe false

    set.size shouldBe 3
  }

  "hash set" should "resize backing array" in {
    val set = new IntHashSet()

    for(i <- 1 to 3000){
      set += i
    }

    set.contains(999) shouldBe true
    set.contains(100000) shouldBe false

  }

}
