package edu.stanford.common

import org.apache.commons.lang3.StringUtils._

import scala.io.Source

object TestUtils {
  def parseMatrix(fileName: String): Array[Array[String]] = {
    val source = Source.fromURI(ClassLoader.getSystemResource(fileName).toURI)

    val result = for {
      line <- source.getLines()
    } yield normalizeSpace(line).split(SPACE)

    result.toArray
  }

  def getResourceLines(resourse: String): Iterator[String] = {
    Source
      .fromURI(ClassLoader.getSystemResource(resourse).toURI)
      .getLines()
  }

}
