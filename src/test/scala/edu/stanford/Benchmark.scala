package edu.stanford

object Benchmark {

  def run[T](func: => T): Result[T] = {
    val start = System.currentTimeMillis()

    val result = func

    Result(System.currentTimeMillis() - start, result)
  }

}

case class Result[T](duration: Long, result: T)
